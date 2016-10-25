package istd.eric.myapp1checkoffs;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import java.io.File;
import java.io.IOException;

public class CheckoffFilesActivity extends AppCompatActivity {

    Toast toast;

    private void showToast(String msg) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
        toast.show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkoff_files);
    }

    public void save(View v){
        String savetext = ((EditText) findViewById(R.id.saveloadtext)).getText().toString();

        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            File file = new File(getApplicationContext().getExternalFilesDir(null), "myfile.txt");
            FileOutputStream f=null;
            try {
                System.out.println(file.getAbsolutePath());
                file.createNewFile();
                f = new FileOutputStream(file);
                f.write(savetext.getBytes());
                showToast("file saved: " + file.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
                showToast("Cannot save!");
            } finally{
                try{
                    if (f!=null){f.close();}
                }catch (IOException e){e.printStackTrace();}
            }
        }else{
            showToast("Cannot save!");
        }
    }

    public void load(View v){
        EditText loadview = (EditText) findViewById(R.id.saveloadtext);
        File file = new File(getApplicationContext().getExternalFilesDir(null), "myfile.txt");
        FileInputStream f=null;
        try {
            f = new FileInputStream(file);
            StringBuilder builder = new StringBuilder();
            int c;
            while ((c = f.read()) != -1){
                builder.append((char) c);
            }
            String filecontent = builder.toString();
            loadview.setText(filecontent);
            showToast("file loaded:"+filecontent);
            Toast.makeText(getApplicationContext(), "file loaded successfully! From: " + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            showToast("Cannot load!");
        } finally{
            try{
                if (f!=null){f.close();}
            }catch (IOException e){e.printStackTrace();}
        }
    }

    public void delete(View v){
        File file = new File(getApplicationContext().getExternalFilesDir(null), "myfile.txt");
        file.delete();
        showToast("File deleted!");
    }
}
