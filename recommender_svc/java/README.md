# Building and Deploying the Recommendation API Service

1. Open Cloud Shell in your project

1. Clone the repository:

```bash
git clone https://github.com/precocity/gcp-retail-workshop-2018.git
```

3. `cd gcp-retail-workshop-2018/recommender_svc/java`

1. Build and push the containter to Google Container Repository:

```bash
gcloud container builds submit --tag gcr.io/$DEVSHELL_PROJECT_ID/retail-wkshp-recs .
```

5. Launch a single-node GKE "cluster":

```bash
gcloud container clusters create retail-wrkshp-k8s \
  --zone us-central1-a \
  --num-nodes 1 \
  --cluster-version=1.10.2 \
  --scopes=storage-ro
```

6. Get the GKE Cluster credentials:

```bash
gcloud container clusters get-credentials retail-wrkshp-k8s --zone us-central1-a
```

7. Create the config map using your project id and associated gcs bucket:

```bash
kubectl create configmap recs-svc-config --from-literal=gcp.project=$DEVSHELL_PROJECT_ID --from-literal=gcs.bucket=recommender_$DEVSHELL_PROJECT_ID
```

8. Edit the `k8s/deployment.yml` file to grab the correct image for your specific google project:

`image: gcr.io/precocity-retail-workshop-2018/retail-wkshp-recs` should be `image: gcr.io/<YOUR PROJECT ID>/retail-wkshp-recs`

9. Deploy the application:

```bash
kubectl apply -f ./k8s/deployment.yml
```

10. Open the firewall to allow for external traffic to the service:

```bash
gcloud compute firewall-rules create allow-k8s-rec-svc \
  --allow=tcp:30333 \
  --description="Allow access to the product recommendation service" \
  --source-ranges=0.0.0.0/0 
```

