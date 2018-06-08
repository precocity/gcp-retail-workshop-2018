## Initial Setup

Start here.

Expected Time: TBD

1. Sign-up for Google Cloud using your Google account (if you haven't already).
2. Create New Project and make note of the project name.
3. Create a Compute Engine instance:
  * Click "Compute Engine" on the left nav.
  * Click on "Create Instance"
  * Give a name in the text box (or default name will be instance-1), click "Create" at the bottom.
  * You will be re-directed to the list of instances page. Wait until the instance provisioning is complete.
  * Click on the "SSH" dropdown for the created instance and select "Open in browser window" to login to the VM instance.
4. Configure `gcloud auth`:
  * `gcloud config set account [your-email-address]`
  * `gcloud auth login`
    * Select `Y` upon prompt.
    * Ctrl + Click on the URL that's displayed on the console to open it in the browser (or alternatively copy-paste the entire URL in the browser).
    * Choose / Login to the Google Account in the browser.
    * Click Allow.
    * Copy the auth code displayed in the browser and paste it in the console window.
    * On successful verification you should see a message: "You are now logged in as [your-email-address]".
  * `gcloud config set project [project-name]`
  * `gcloud auth application-default login`
    * Select `Y` upon prompt.
    * Ctrl + Click on the URL that's displayed on the console to open it in the browser.
    * Choose / Login to the Google Account in the browser (or alternatively copy-paste the entire URL in the browser).
    * Click Allow.
    * Copy the auth code displayed in the browser and paste it in the console window.
    * On successful verification you should see a message: "These credentials will be used by any library that requests Application Default Credentials.".
5. Install `git`.
  * `sudo apt-get update`
  * `sudo apt-get install -y git`
6. Install `java` & `maven`.
  * `sudo apt-get install -y default-jdk`
  * `sudo apt-get install -y maven`
6. `git clone https://github.com/precocity/gcp-retail-workshop-2018.git`
