package istd.eric.myapp1checkoffs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.view.View;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class CheckoffSQLActivity_entry extends AppCompatActivity {

    SQLiteDatabase db;
    TableLayout display_table;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkoff_sqlentry);

        db = (new StudentOpenHelper(this)).getWritableDatabase();
        display_table = (TableLayout) findViewById(R.id.display_table);

        displaytable();
    }

    public void goback(View v){
        finish();
    }

    public void save(View v){
        String name = ((EditText) findViewById(R.id.editText_name)).getText().toString();
        String address = ((EditText) findViewById(R.id.editText_address)).getText().toString();
        int phone = Integer.parseInt( ((EditText) findViewById(R.id.editText_phone)).getText().toString() );

        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("address", address);
        values.put("phone", phone);
        long newRowId = db.insert("students", null, values);
        displaytable();
    }

    public void delete(View v){
        String rowid = ((EditText) findViewById(R.id.editText_rowid)).getText().toString();
        db.delete("students","rowid=?",new String[] {rowid});
        displaytable();
    }

    public void update(View v){
        String rowid = ((EditText) findViewById(R.id.editText_rowid)).getText().toString();

        String name = ((EditText) findViewById(R.id.editText_name)).getText().toString();
        String address = ((EditText) findViewById(R.id.editText_address)).getText().toString();
        String phone = ((EditText) findViewById(R.id.editText_phone)).getText().toString();

        ContentValues values = new ContentValues();
        if(!name.equals("")){values.put("name", name);}
        if(!address.equals("")){values.put("address", address);}
        if(!phone.equals("")){values.put("phone", phone);}
        db.update("students", values, "rowid=?", new String[] {rowid});
        displaytable();
    }

    private void displaytable() {
        TableLayout.LayoutParams table_layout_params = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
        display_table.removeAllViews();

        TableRow th = new TableRow(this);
        addtext_to_th("id", th);
        addtext_to_th("Name", th);
        addtext_to_th("Address", th);
        addtext_to_th("Phone", th);
        display_table.addView(th, table_layout_params);

        Cursor c = db.query("students", new String[] { "rowid", "*" }, null, null, null, null, null);
        while (c.moveToNext()) {
            TableRow tr = new TableRow(this);
            addtext_to_tr(c.getString(0), tr);
            addtext_to_tr(c.getString(1), tr);
            addtext_to_tr(c.getString(2), tr);
            addtext_to_tr(c.getString(3), tr);
            display_table.addView(tr, table_layout_params);
        }
        c.close();
    }

    private void addtext_to_th(String text, TableRow th){
        TextView t_view = new TextView(this);
        t_view.setText(text);
        t_view.setPadding(0,8,0,8);
        t_view.setTypeface(null, Typeface.BOLD);
        th.addView(t_view);
    }

    private void addtext_to_tr(String text, TableRow tr){
        TextView t_view = new TextView(this);
        t_view.setText(text);
        t_view.setPadding(0,8,0,8);
        tr.addView(t_view);
    }
}

class StudentOpenHelper extends SQLiteOpenHelper {

    StudentOpenHelper(Context context) {
        super(context, "students", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE students (" +
                " name TEXT, " +
                " address TEXT, " +
                " phone INTEGER " +
                ");");
    }

    @Override
    public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion){}
}