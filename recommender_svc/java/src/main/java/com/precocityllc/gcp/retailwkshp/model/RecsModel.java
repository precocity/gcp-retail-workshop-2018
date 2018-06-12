package com.precocityllc.gcp.retailwkshp.model;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import joinery.DataFrame;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.FileSystems;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Component
public class RecsModel implements IRecsModel {

    private final String gcpProject;
    private final String bucketName;
    private final String rowModelFile;
    private final String colModelFile;
    private final String userModelFile;
    private final String itemModelFile;
    private final String userItemDataFile;

    private INDArray rowFactor;
    private INDArray colFactor;
    private DataFrame itemMap;
    private DataFrame userMap;
    private Map<Object, DataFrame<Object>> userItems;

    private static final String temporaryStoragePath = "tmp";

    protected static final Logger LOGGER = LoggerFactory.getLogger(RecsModel.class);

    public RecsModel(@Value("${recs.gcp.project}") final String gcpProject,
                     @Value("${recs.gcp.bucket}") final String bucketName,
                     @Value("${recs.rowmodel.path}") final String rowModelPath,
                     @Value("${recs.colmodel.path}") final String colModelPath,
                     @Value("${recs.usermodel.path}") final String userModelPath,
                     @Value("${recs.itemmodel.path}") final String itemModelPath,
                     @Value("${recs.useritem.path}") final String userItemDataPath) {

        LOGGER.info("Initializing the Recommendations Model Loader");
        this.gcpProject = gcpProject;
        this.bucketName = bucketName;
        this.rowModelFile = rowModelPath;
        this.colModelFile = colModelPath;
        this.userModelFile = userModelPath;
        this.itemModelFile = itemModelPath;
        this.userItemDataFile = userItemDataPath;
    }


    public void loadModel() throws Exception {

        LOGGER.info("Loading model files from Google Cloud Storage");

        try {
            Storage storage = StorageOptions.getDefaultInstance().getService();
            Bucket bucket = storage.get(this.bucketName);

            final String[] filesToDownload =
                    new String[]{rowModelFile, colModelFile, userModelFile, itemModelFile, userItemDataFile};

            LOGGER.info("Ensuring that local directory structure exists...");
            File tmpDir = new File(temporaryStoragePath);
            boolean mkdirResult;
            mkdirResult = tmpDir.mkdir();
            if(mkdirResult == false)
                LOGGER.info("Tmp dir already created.");
            File modelDir = new File(new StringBuilder(temporaryStoragePath).append(File.separator).append("model").toString());
            mkdirResult = modelDir.mkdir();
            if(mkdirResult == false)
                LOGGER.info("Model dir already created.");
            File dataDir = new File(new StringBuilder(temporaryStoragePath).append(File.separator).append("data").toString());
            mkdirResult = dataDir.mkdir();
            if(mkdirResult == false)
                LOGGER.info("Data dir already created.");


            for (String modelFile : filesToDownload) {
                Blob theFile = bucket.get(modelFile);
                LOGGER.info("Fetching model file {} from GCS", modelFile);

                theFile.downloadTo(FileSystems.getDefault().getPath(temporaryStoragePath, modelFile),
                        Blob.BlobSourceOption.userProject(this.gcpProject));
            }

            LOGGER.info("Finished loading model files...");

            LOGGER.info("Loading model data into in-memory arrays...");
            this.rowFactor = Nd4j.readNumpy(getLocalPath(this.rowModelFile), ",");
            this.colFactor = Nd4j.readNumpy(getLocalPath(this.colModelFile), ",");

            this.itemMap = DataFrame.readCsv(getLocalPath(this.itemModelFile));

            // Hack to read the User Map since Joinery has no way to specify userId is a String
            // as opposed to number
            CSVParser userMapParser = CSVParser.parse(new FileReader(getLocalPath(userModelFile)), CSVFormat.DEFAULT);
            List userIndicesList = new ArrayList<>();
            List userIdList = new ArrayList<>();
            userMapParser.getRecords().stream().forEach(record -> {
                userIndicesList.add(Integer.parseInt(record.get(0)));
                userIdList.add(record.get(1));
            });

            this.userMap = new DataFrame<Object>(
                    userIndicesList,
                    Arrays.asList("userIndex", "userId"),
                    Arrays.<List<Object>>asList(
                            userIndicesList,
                            userIdList
                    )
            );


            CSVParser userItemParser = CSVParser.parse(new FileReader(getLocalPath(userItemDataFile)), CSVFormat.DEFAULT);
            List userIdsFromBrowse = new ArrayList();
            List productIdsFromBrowse = new ArrayList();
            userItemParser.getRecords().stream().forEach(record -> {
                userIdsFromBrowse.add(record.get(0));
                productIdsFromBrowse.add(record.get(1));
            } );

            DataFrame tmpUserItemDf = new DataFrame<Object>(
                    IntStream.rangeClosed(0, userIdsFromBrowse.size() - 1).boxed().collect(Collectors.toList()),
                    Arrays.asList("userId", "productId"),
                    Arrays.<List<Object>>asList(
                            userIdsFromBrowse,
                            productIdsFromBrowse
                    )
            );

            this.userItems = tmpUserItemDf.groupBy("userId").explode();


            LOGGER.info("Finished loading in-memory arrays.");

        }
        catch(Exception ex) {
            LOGGER.error("Error loading model data from Google Cloud Storage: {}", ex.getMessage(), ex);
            throw ex;
        }

    }

    public List<String> generateRecommendations(String userId, int numRecommendations) {


        DataFrame userRow = this.userMap.select((row) -> userId.equals((String)((List)row).get(1)));

        int userIndex = (int)userRow.get(0, 0);

        HashSet viewedItems = new HashSet<>(
                Arrays.asList((this.userItems.get(userId).col(1).toArray())));


        List viewedItemIndices = itemMap.select((row) -> viewedItems.contains(((List)row).get(1))).col(0);

        //Get the row in the user matrix for the specified user -- and remove the index column
        INDArray userFactor = rowFactor.getRow(userIndex).getColumns(1, 2, 3, 4, 5);

        INDArray itemFactor = colFactor.getColumns(1,2,3,4,5);
        INDArray userRecs = itemFactor.mmul(userFactor.reshape(userFactor.columns(), 1));


        INDArray[] secondSort = Nd4j.sortWithIndices(userRecs, 0, false);

        //Grab the number of recommendations requested + the number of "purchased" products
        List<String> recommendations = new ArrayList();
        for(int i = 0; i < numRecommendations + viewedItemIndices.size(); i++) {
            int itemIndex = secondSort[0].getRow(i).getInt(0);
            if(!viewedItemIndices.contains(itemIndex))
                recommendations.add((String)itemMap.get(itemIndex, 1));
            if(recommendations.size() == numRecommendations)
                break;
        }

        return recommendations;
    }

    protected String getLocalPath(String modelFile) {
        return FileSystems.getDefault().getPath(temporaryStoragePath, modelFile).toString();
    }
}