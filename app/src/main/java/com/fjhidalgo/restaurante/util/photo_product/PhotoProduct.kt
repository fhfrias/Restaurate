package com.fjhidalgo.restaurante.util.photo_product

import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import com.fjhidalgo.restaurante.data.model.Auth
import com.mikhaellopez.circularimageview.CircularImageView
import org.json.JSONObject
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.*

class PhotoProduct {

    companion object{

        fun uploadImage(path: String, activity: AppCompatActivity, civProfilePhoto: CircularImageView) {

            var f = File(path)
            f = File(compressBitmap(f, 0, 50))


        }

        fun loadImageToCiV(path: String, civProfilePhoto: CircularImageView) {

            civProfilePhoto.setImageBitmap(BitmapFactory.decodeFile(path))
        }

        fun compressBitmap(file: File, sampleSize: Int, quality: Int): String {

            val outPath = file.absolutePath

            try {
                val options = BitmapFactory.Options()
                //options.inSampleSize = sampleSize
                val inputStream = FileInputStream(file)

                val selectedBitmap = BitmapFactory.decodeStream(inputStream, null, options)
                inputStream.close()

                val outputStream = FileOutputStream(outPath)
                selectedBitmap?.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
                outputStream.close()
                selectedBitmap?.recycle()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return outPath
        }

        fun getPath(context: Context, uri: Uri): String? {

            // DocumentProvider
            if (DocumentsContract.isDocumentUri(context, uri)) {
                // ExternalStorageProvider
                if (isExternalStorageDocument(uri)) {
                    var docId = DocumentsContract.getDocumentId(uri)
                    var split = docId.split(":")
                    var type = split[0]

                    if ("primary".equals(type)) {
                        return Environment.getExternalStorageDirectory().toString() + "/" + split[1]
                    }

                    // TODO handle non-primary volumes
                }
                // DownloadsProvider
                else if (isDownloadsDocument(uri)) {

                    var id = DocumentsContract.getDocumentId(uri)
                    var contentUri = ContentUris.withAppendedId(
                            Uri.parse("content://downloads/public_downloads"), id.toLong())

                    return getDataColumn(context, contentUri, null, null)
                }
                // MediaProvider
                else if (isMediaDocument(uri)) {
                    var docId = DocumentsContract.getDocumentId(uri)
                    var split = docId.split(":")
                    var type = split[0]

                    var contentUri: Uri? = null
                    if ("image".equals(type)) {
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    } else if ("video".equals(type)) {
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                    } else if ("audio".equals(type)) {
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                    }

                    var selection = "_id=?"
                    var selectionArgs = arrayOf(split[1])

                    return getDataColumn(context, contentUri, selection, selectionArgs)
                }
            }
            // MediaStore (and general)
            else if ("content".equals(uri.scheme)) {

                // Return the remote address
                if (isGooglePhotosUri(uri))
                    return uri.lastPathSegment.toString()

                return getDataColumn(context, uri, null, null)
            }
            // File
            else if ("file".equals(uri.scheme)) {
                return uri.path.toString()
            }

            return null
        }

        /**
         * Get the value of the data column for this Uri. This is useful for
         * MediaStore Uris, and other file-based ContentProviders.
         *
         * @param context The context.
         * @param uri The Uri to query.
         * @param selection (Optional) Filter used in the query.
         * @param selectionArgs (Optional) Selection arguments used in the query.
         * @return The value of the _data column, which is typically a file path.
         */
        fun getDataColumn(context: Context, uri: Uri?, selection: String?,
                                  selectionArgs: Array<String>?): String? {

            var cursor: Cursor? = null
            var column = "_data"
            var projection = arrayOf(column)

            try {
                cursor = context.contentResolver.query(uri!!, projection, selection, selectionArgs,
                        null)
                if (cursor != null && cursor.moveToFirst()) {
                    var index = cursor.getColumnIndexOrThrow(column)
                    return cursor.getString(index)
                }
            } finally {
                if (cursor != null)
                    cursor.close()
            }
            return null
        }


        /**
         * @param uri The Uri to check.
         * @return Whether the Uri authority is ExternalStorageProvider.
         */
        fun isExternalStorageDocument(uri: Uri): Boolean {
            return "com.android.externalstorage.documents".equals(uri.authority)
        }

        /**
         * @param uri The Uri to check.
         * @return Whether the Uri authority is DownloadsProvider.
         */
        fun isDownloadsDocument(uri: Uri): Boolean {
            return "com.android.providers.downloads.documents".equals(uri.authority)
        }

        /**
         * @param uri The Uri to check.
         * @return Whether the Uri authority is MediaProvider.
         */
        fun isMediaDocument(uri: Uri): Boolean {
            return "com.android.providers.media.documents".equals(uri.authority)
        }

        /**
         * @param uri The Uri to check.
         * @return Whether the Uri authority is Google Photos.
         */
        fun isGooglePhotosUri(uri: Uri): Boolean {
            return "com.google.android.apps.photos.content".equals(uri.authority)
        }
    }
}