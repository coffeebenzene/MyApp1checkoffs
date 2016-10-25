package istd.eric.myapp1checkoffs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class CheckoffSQLActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkoff_sql);
    }

    public void validate(View v){
        String password = ((EditText) findViewById(R.id.password)).getText().toString();
        if (password.equals("12345678")){
            Intent intent = new Intent(this, CheckoffSQLActivity_entry.class);
            startActivity(intent);
        }
    }
}

