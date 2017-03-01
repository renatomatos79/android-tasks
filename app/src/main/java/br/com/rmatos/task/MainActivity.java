package br.com.rmatos.task;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;

import br.com.rmatos.task.dialogs.IProgressEvent;
import br.com.rmatos.task.dialogs.Progress;
import br.com.rmatos.task.http.DownloadHelper;
import br.com.rmatos.task.http.DownloadTask;
import br.com.rmatos.task.http.IDownloadEvent;

public class MainActivity extends AppCompatActivity implements IProgressEvent, IDownloadEvent {

    private EditText txtURL;
    private EditText txtDestFileName;
    private Button btnShow;
    private Progress progress;
    private DownloadTask downloadTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progress = null;
        downloadTask = null;

        this.txtURL = (EditText)findViewById(R.id.txtURL);
        this.txtURL.setText("https://firebasestorage.googleapis.com/v0/b/ead-technology.appspot.com/o/security.jpg?alt=media&token=73811049-7829-4f69-8899-9a90f34e7230");

        this.txtDestFileName = (EditText)findViewById(R.id.txtDestFileName);
        this.txtDestFileName.setText("file.jpg");

        this.btnShow = (Button)findViewById(R.id.btnShow);
        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(txtURL.getText().toString())){
                    Toast.makeText(MainActivity.this, "Informe uma URL para download", Toast.LENGTH_LONG).show();
                    return;
                }

                if (TextUtils.isEmpty(txtDestFileName.getText().toString())){
                    Toast.makeText(MainActivity.this, "Informe um nome para o arquivo destino", Toast.LENGTH_LONG).show();
                    return;
                }

                progress = new Progress(MainActivity.this);
                progress.show("Baixando arquivo", txtDestFileName.getText().toString());

                File file = DownloadHelper.CreateFile(txtDestFileName.getText().toString());
                if (file == null){  
                    Toast.makeText(MainActivity.this, "Nao foi possivel criar o arquivo", Toast.LENGTH_LONG).show();
                } else {
                    String dest = file.getAbsolutePath();
                    downloadTask = DownloadHelper.downloadAssync(MainActivity.this, txtURL.getText().toString(), dest);
                }
            }
        });

    }

    @Override
    public void onCancel(DialogInterface dialog) {
        downloadTask.cancel(true);
        Toast.makeText(this, "Download cancelado", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFinish(String sourceFileName, String destFileName, String result) {
        progress.close();
        progress = null;

        String message = result == null ? "Download Concluido" : result;

        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
