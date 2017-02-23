package br.com.rmatos.task.http;

import android.content.Context;
import android.os.AsyncTask;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import br.com.rmatos.task.dialogs.Progress;

/**
 * Created by renato on 23/02/17.
 */

public class DownloadTask extends AsyncTask<String, Void, String> {
    private Context context;
    private String sourceFileName;
    private String destFileName;
    public DownloadTask(Context ctx, String srcFileName, String dstFileName) {
        this.context = ctx;
        this.sourceFileName = srcFileName;
        this.destFileName = dstFileName;
    }

    @Override
    protected void onPreExecute(){
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... files) {
        return download(this.sourceFileName, this.destFileName);
    }

    @Override
    protected void onPostExecute(String result) {
        if (this.context instanceof IDownloadEvent){
            IDownloadEvent event = (IDownloadEvent)this.context;
            event.onFinish(this.sourceFileName, this.destFileName, result);
        }
    }

    public String download(String sourceFileName, String destFileName) {
        InputStream input = null;
        OutputStream output = null;
        HttpURLConnection connection = null;
        try
        {
            URL url = new URL(sourceFileName);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return "Server returned HTTP " + connection.getResponseCode() + " " + connection.getResponseMessage();
            }
            //int fileLength = connection.getContentLength();
            // download the file
            input = connection.getInputStream();
            output = new FileOutputStream(destFileName);

            byte buffer[] = new byte[4096];
            // long total = 0;
            int count;
            while ((count = input.read(buffer)) != -1)
            {
                if (isCancelled()) {
                    input.close();
                    return null;
                }
                // total += count;
                // publishing the progress....
                // if (fileLength > 0) // only if total length is known
                //     publishProgress((int) (total * 100 / fileLength));
                output.write(buffer, 0, count);
            }
        }
        catch (Exception e)
        {
            return e.toString();
        }
        finally
        {
            try
            {
                if (output != null){
                    output.close();
                    output=null;
                }
                if (input != null){
                    input.close();
                    input=null;
                }
            }
            catch (IOException ignored) { }
            if (connection != null){
                connection.disconnect();
                connection = null;
            }
        }
        return null;
    }
}
