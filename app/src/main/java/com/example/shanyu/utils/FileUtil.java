package com.example.shanyu.utils;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import androidx.core.content.FileProvider;

import com.example.shanyu.MyApplication;
import com.litesuits.common.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtil {

    public static String ROOT_PATH = MyApplication.context.getExternalCacheDir() + "/qy";
    public static String IMAGE = "image";//图片
    public static String AUDIO = "audio";//音频
    public static String VIDEO = "video";//视频
    public static String JC = "jiancai";//剪裁
    public static String UPDATA = "updata";//更新

    /**
     * 创建路径
     *
     * @param type
     * @param name
     */
    public static String getNewFile(String type, String name) {

        if (StringUtil.isEmpty(type) || StringUtil.isEmpty(name))
            return null;

        File file_p;
        switch (type) {
            case "image":
            case "audio":
            case "video":
            case "jiancai":
            case "updata":
                file_p = new File(ROOT_PATH, type);
                break;
            default:
                LogUtil.i("选择的文件类型错误...");
                return null;
        }

        if (!file_p.exists())
            file_p.mkdirs();

        File file_c = new File(file_p, name);
        if (!file_c.exists()) {
            try {
                file_c.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return file_c.getAbsolutePath();
    }

    /**
     * 创建路径
     *
     * @param type
     * @return
     */
    public static String getNewFile(String type) {
        if (StringUtil.isEmpty(type))
            return null;

        String fileName;
        switch (type) {
            case "image":
                fileName = System.currentTimeMillis() + ".jpg";
                break;
            case "audio":
                fileName = System.currentTimeMillis() + ".mp3";
                break;
            case "video":
                fileName = System.currentTimeMillis() + ".mp4";
                break;
            default:
                LogUtil.i("选择的文件类型错误...");
                return null;
        }

        return getNewFile(type, fileName);

    }

    /**
     * 获取Uri
     *
     * @param context
     * @param type
     * @param name
     * @return
     */
    public static Uri getUri(Context context, String type, String name) {

        String path = getNewFile(type, name);

        if (path == null) return null;

        Uri uri = getUriByFile(context, new File(path));
        return uri;
    }

    /**
     * 获取Uri
     *
     * @param context
     * @param type
     * @return
     */
    public static Uri getUri(Context context, String type) {

        String path = getNewFile(type);

        if (path == null) return null;

        Uri uri = getUriByFile(context, new File(path));
        return uri;
    }

    /**
     * File转Uri
     *
     * @param context
     * @param imageFile
     * @return
     */
    public static Uri getUriByFile(Context context, File imageFile) {

        Uri uri;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(context, "com.example.shanyu.provider", imageFile);
        } else {
            uri = Uri.fromFile(imageFile);
        }
        return uri;
    }


    /**
     * Uri转File
     *
     * @param context
     * @param contentUri
     * @return
     */
    public static File getFilePathFromURI(Context context, Uri contentUri) {
        String fileName = getFileName(contentUri);
        if (!StringUtil.isEmpty(fileName)) {
            File copyFile = new File(getNewFile(JC, fileName));
            copy(context, contentUri, copyFile);
            return copyFile;
        }
        return null;
    }

    public static String getFilePathByUri(Context context, Uri uri) {
        String path = null;
        // 以 file:// 开头的
        if (ContentResolver.SCHEME_FILE.equals(uri.getScheme())) {
            path = uri.getPath();
            return path;
        }
        // 以 content:// 开头的，比如 content://media/extenral/images/media/17766
        if (ContentResolver.SCHEME_CONTENT.equals(uri.getScheme()) && Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.Media.DATA}, null, null, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    if (columnIndex > -1) {
                        path = cursor.getString(columnIndex);
                    }
                }
                cursor.close();
            }
            return path;
        }
        // 4.4及之后的 是以 content:// 开头的，比如 content://com.android.providers.media.documents/document/image%3A235700
        if (ContentResolver.SCHEME_CONTENT.equals(uri.getScheme()) && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (DocumentsContract.isDocumentUri(context, uri)) {
                if (isExternalStorageDocument(uri)) {
                    // ExternalStorageProvider
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];
                    if ("primary".equalsIgnoreCase(type)) {
                        path = Environment.getExternalStorageDirectory() + "/" + split[1];
                        return path;
                    }
                } else if (isDownloadsDocument(uri)) {
                    // DownloadsProvider
                    final String id = DocumentsContract.getDocumentId(uri);
                    final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),
                            Long.valueOf(id));
                    path = getDataColumn(context, contentUri, null, null);
                    return path;
                } else if (isMediaDocument(uri)) {
                    // MediaProvider
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];
                    Uri contentUri = null;
                    if ("image".equals(type)) {
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    } else if ("video".equals(type)) {
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                    } else if ("audio".equals(type)) {
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                    }
                    final String selection = "_id=?";
                    final String[] selectionArgs = new String[]{split[1]};
                    path = getDataColumn(context, contentUri, selection, selectionArgs);
                    return path;
                }
            }
        }
        return null;
    }

    private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    private static String getFileName(Uri uri) {
        if (uri == null) return null;
        String fileName = null;
        String path = uri.getPath();
        int cut = path.lastIndexOf('/');
        if (cut != -1) {
            fileName = path.substring(cut + 1);
        }
        return fileName;
    }

    private static void copy(Context context, Uri srcUri, File dstFile) {
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(srcUri);
            if (inputStream == null) return;
            OutputStream outputStream = new FileOutputStream(dstFile);
            IOUtils.copy(inputStream, outputStream);
            inputStream.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除文件
     */
    public static void deletFile() {
        File file = new File(ROOT_PATH);
        if (file.exists())
            file.delete();
    }

}
