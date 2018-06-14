# Specify the provider (GCP, AWS, Azure)
provider "google" {
	credentials = "${file("~/gce-airflow-key.json")}"
	project = "precocity-retail-workshop-2018"
	region = "us-central1"
}
