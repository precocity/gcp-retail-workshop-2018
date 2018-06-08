## Initial Setup

Start here.

1. Sign-up for Google Cloud using your Google account (if you haven't already).
2. Create New Project and make note of the project name.
3. Create a Compute Engine instance:
  1. Click "Compute Engine" on the left nav.
  2. Click on "Create Instance"
  3. Give a name in the text box (or default name will be instance-1), click "Create" at the bottom.
  4. You will be re-directed to the list of instances page. Wait until the instance provisioning is complete.
  5. Click on the "SSH" dropdown for the created instance and select "Open in browser window" to login to the VM instance.
4. Configure `gcloud auth`:
  1. `gcloud config set account [your-email-address]`
  2. `gcloud auth login`
    1. Select `Y` upon prompt.
    2. Ctrl + Click on the URL that's displayed on the console to open it in the browser.
    3. Choose / Login to the Google Account in the browser (or alternatively copy-paste the entire URL in the browser).
    4. Click Allow.
    5. Copy the auth code displayed in the browser and paste it in the console window.
    6. On successful verification you should see a message: "You are now logged in as [your-email-address]".
  3. `gcloud config set project <project-name>`
5. Install `git`.
  1. `sudo apt-get update`
  2. `sudo apt-get install -y git`
6. `git clone https://github.com/precocity/gcp-retail-workshop-2018.git`
