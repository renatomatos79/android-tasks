package br.com.rmatos.task.http;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by renato on 22/02/17.
 */

public class DownloadHelper {


    public static File CreateFile(String fileName) {
        File dir = Environment.getExternalStorageDirectory();
        File file = new File(dir, fileName);
        try {
            file.createNewFile();
        } catch (IOException e) {
            file = null;
        }
        return file;
    }

    public static DownloadTask downloadAssync(Context context, String sourceFileName, String destFileName){
        final DownloadTask downloadTask = new DownloadTask(context, sourceFileName, destFileName);
        downloadTask.execute();
        return downloadTask;
    }
}
