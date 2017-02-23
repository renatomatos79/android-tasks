package br.com.rmatos.task.dialogs;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by renato on 22/02/17.
 */

public class Progress {
    private Context context;
    private ProgressDialog dialog;

    public Progress(Context ctx){
        this.context = ctx;
        init();
    }

    public void show(String title, String message){
        if (dialog == null){
            init();
        }
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.show();
    }

    public void close(){
        if (dialog != null){
            dialog.dismiss();
            dialog = null;
        }
    }

    private void init(){
        dialog = new ProgressDialog(context);
        dialog.setIndeterminate(true);
        dialog.setCancelable(true);

        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if (context instanceof IProgressEvent){
                    IProgressEvent progressDialog = (IProgressEvent)context;
                    progressDialog.onCancel(dialog);
                }
            }
        });
    }
}
