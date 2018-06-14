# Specify the provider (GCP, AWS, Azure)
provider "google" {
	credentials = "${file("gce-terraform-key.json")}"
	project = "precocity-retail-workshop-2018"
	region = "us-central1"
}
