# Create a new instance
resource "google_compute_instance" "debian" {
   name = "airflow"
   machine_type = "n1-standard-2"
   zone = "us-central1-c"
   boot_disk {
      initialize_params {
      image = "debian-9-stretch-v20180510"
   }
}
network_interface {
   network = "default"
   access_config {}
}
metadata {
   sshKeys = "${var.gce_ssh_user}:${file(var.gce_ssh_pub_key_file)}"
}
service_account {
   scopes = ["userinfo-email", "compute-ro", "storage-ro"]
}
}
resource "google_compute_firewall" "default" {
  name    = "default-allow-8080"
  network = "default"
  allow {
    protocol = "tcp"
    ports    = ["8080"]
  }
}
