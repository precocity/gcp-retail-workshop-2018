import logging as log
import os

from google.cloud import storage


def write_to_gcs_bucket(bucket_name, data_lake_folder_name, file_name, buffer):
    try:
        gcs = storage.Client()
        bucket = gcs.get_bucket(bucket_name)
        blob = bucket.blob(data_lake_folder_name + '/' + file_name)
        blob.upload_from_string(buffer.getvalue())

    except Exception as e:
        log.error('Error {0}'.format(e))


def copy_gcs_file(source_bucket, source_filename, destination_bucket, destination_filename):
    """
    Copies an S3 file from one location to another. This is more performant than writing out the same buffer to two different locations.
    :param source_bucket: The source bucket to copy from.
    :param source_filename: The source filename to copy from, including the path, but excluding the bucket name.
    :param destination_bucket: The destination bucket to copy to.
    :param destination_filename: The destination file to copy to, including the path.
    """
    try:
        gcs = storage.Client()
        source_bucket = gcs.get_bucket(source_bucket)
        source_blob = source_bucket.blob(source_filename)
        destination_blob = source_bucket.blob(destination_filename)
        destination_blob.rewrite(source_blob)

    except Exception as e:
        log.error('Error {0}'.format(e))


def delete_gcs_file(bucket_name, file_name):
    try:
        gcs = storage.Client()
        bucket = gcs.get_bucket(bucket_name)
        blob = bucket.blob(file_name)
        blob.delete()

    except Exception as e:
        log.error('Error {0}'.format(e))


def delete_gcs_folder(bucket_name, folder_name):
    try:
        gcs = storage.Client()
        bucket = gcs.get_bucket(bucket_name)
        blobs = bucket.list_blobs(prefix=folder_name)
        for blob in blobs:
            log.info(blob.name)
            blob.delete()

    except Exception as e:
        log.error('Error {0}'.format(e))


def list_gcs_folder(folder_name, bucket_name):
    """
    Lists all the files in the S3 folder they reside in.
    :param bucket_name: Name of the bucket.
    :param folder_name: Name of the folder under which the files are deleted.
    :return: Response to indicate success or failure.
    """
    file_list = []
    try:
        gcs = storage.Client()
        bucket = gcs.get_bucket(bucket_name)
        blobs = bucket.list_blobs(prefix=folder_name)
        for blob in blobs:
            folder_name_split = blob.name.split('/')
            file_list.append(folder_name_split[2])
        return file_list

    except Exception as e:
        log.error('Error {0}'.format(e))
