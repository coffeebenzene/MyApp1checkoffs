package istd.eric.myapp1checkoffs;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Checkoff2Activity_emailpage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkoff2_emailpage);
    }

    public void sendemail(View v){
        String[] to = ((EditText) findViewById(R.id.to)).getText().toString().split(",");
        String[] cc = ((EditText) findViewById(R.id.cc)).getText().toString().split(",");
        String subject = ((EditText) findViewById(R.id.subject)).getText().toString();
        String message = ((EditText) findViewById(R.id.message)).getText().toString();

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setData(Uri.parse("mailto:"));
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_EMAIL, to);
        intent.putExtra(Intent.EXTRA_CC, cc);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        intent = Intent.createChooser(intent, "Send Email");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
