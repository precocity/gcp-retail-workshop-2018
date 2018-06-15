# Specify the provider (GCP, AWS, Azure)
provider "google" {
	credentials = "${file("~/gce-airflow-key.json")}"
	project = "{GOOGLE_CLOUD_PROJECT}"
	region = "us-central1"
}
