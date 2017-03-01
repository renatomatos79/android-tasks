# android-tasks
Neste aplicativo mostro como criar um Helper para carregar uma janela do tipo ProgressDialog e realizar a troca de mensagens 
entre a janela e a Activity que a gerencia. Também veremos como criar tarefas assíncronas para gerenciar o download de 
imagens que por sua vez serão baixadas para a área de Storage do seu dispositivo.

# Helper para baixar arquivos
public static DownloadTask downloadAssync(Context context, String sourceFileName, String destFileName){
  final DownloadTask downloadTask = new DownloadTask(context, sourceFileName, destFileName);
  downloadTask.execute();
  return downloadTask;
}

# Helper para persistir os dados no dispostivo
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

# Carregando a janela de progresso
progress = new Progress(MainActivity.this);
progress.show("Baixando arquivo", "nome do arquivo.jpg");

# Sendo notificado se o usuário cancelar o download
public interface IProgressEvent {
    void onCancel(DialogInterface dialog);
}

public void onCancel(DialogInterface dialog) {
  Toast.makeText(this, "Download cancelado", Toast.LENGTH_LONG).show();
}
