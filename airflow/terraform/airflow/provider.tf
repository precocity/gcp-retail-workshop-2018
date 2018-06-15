# Specify the provider (GCP, AWS, Azure)
provider "google" {
	credentials = "${file("~/gce-airflow-key.json")}"
	project = "{DEVSHELL_PROJECT_ID}"
	region = "us-central1"
}
