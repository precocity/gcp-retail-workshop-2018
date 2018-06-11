package com.precocityllc.gcp.retailwkshp.model;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import joinery.DataFrame;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.FileSystems;
import java.util.*;


public class RecsModel {

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

    protected static Logger LOGGER = LoggerFactory.getLogger(RecsModel.class);

    public RecsModel(final String gcpProject, final String bucketName, final String rowModelPath,
                     final String colModelPath, final String userModelPath, final String itemModePath,
                     final String userItemDataPath) {

        LOGGER.info("Initializing the Recommendations Model Loader");
        this.gcpProject = gcpProject;
        this.bucketName = bucketName;
        this.rowModelFile = rowModelPath;
        this.colModelFile = colModelPath;
        this.userModelFile = userModelPath;
        this.itemModelFile = itemModePath;
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
            tmpDir.mkdir();
            File modelDir = new File(new StringBuilder(temporaryStoragePath).append(File.separator).append("model").toString());
            modelDir.mkdir();
            File dataDir = new File(new StringBuilder(temporaryStoragePath).append(File.separator).append("data").toString());
            dataDir.mkdir();

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
            this.userMap = DataFrame.readCsv(getLocalPath(this.userModelFile));
            this.userItems = DataFrame.readCsv(getLocalPath(this.userItemDataFile)).groupBy(
                    new DataFrame.KeyFunction<Object>() {

                        @Override
                        public Object apply(List<Object> value) {
                            return Math.round((Double)value.get(0));
                        }
                    }).explode();

            LOGGER.info("Finished loading in-memory arrays.");

        }
        catch(Exception ex) {
            LOGGER.error("Error loading model data from Google Cloud Storage: {}", ex.getMessage(), ex);
            throw ex;
        }

    }

    public List<String> generateRecommendations(String userId, int numRecommendations) {


        DataFrame userRow = this.userMap.select((row) -> Long.parseLong(userId) == Math.round((Double)((List)row).get(1)));

        int userIndex = (int)(long)userRow.get(0, 0);

        HashSet viewedItems = new HashSet<>(
                Arrays.asList((this.userItems.get(Long.parseLong(userId)).col(1).toArray())));


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