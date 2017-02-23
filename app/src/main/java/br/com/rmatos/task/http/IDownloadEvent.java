package br.com.rmatos.task.http;

/**
 * Created by renato on 23/02/17.
 */

public interface IDownloadEvent {
    void onFinish(String sourceFileName, String destFileName, String result);
}
