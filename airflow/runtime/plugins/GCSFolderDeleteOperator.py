from airflow.models import BaseOperator
from airflow.plugins_manager import AirflowPlugin
from airflow.utils.decorators import apply_defaults

from gcs_handler import delete_gcs_folder


class GCSFolderDeleteOperator(BaseOperator):

    @apply_defaults
    def __init__(self, bucket_name, folder_name, *args, **kwargs):
        super(GCSFolderDeleteOperator, self).__init__(*args, **kwargs)
        self.bucket_name = bucket_name
        self.folder_name = folder_name

    def execute(self, context):
        return delete_gcs_folder(self.bucket_name, self.folder_name)


class GCSFolderDeletePlugin(AirflowPlugin):
    name = "gcs_folder_delete_plugin"
    operators = [GCSFolderDeleteOperator]
    hooks = []
    executors = []
    macros = []
    admin_views = []
    flask_blueprints = []
    menu_links = []
