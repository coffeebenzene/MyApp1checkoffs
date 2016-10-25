package istd.eric.myapp1checkoffs;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CheckoffFirebaseActivity extends AppCompatActivity {

    ArrayList<Student> students = new ArrayList<Student>();
    TableLayout display_table;
    boolean search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkoff_firebase);

        display_table = (TableLayout) findViewById(R.id.display_table);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("student");
        myRef.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot snapshot){
                students = new ArrayList<Student>();
                for ( DataSnapshot child : snapshot.getChildren()){
                    Student s = child.getValue(Student.class);
                    students.add(s);
                }
                display_table();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(databaseError);
            }
        } );
    }

    private void display_table(){
        TextView s_name;
        TextView s_pillar;

        TableLayout.LayoutParams table_layout_params = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
        display_table.removeAllViews();

        TableRow th = new TableRow(this);
        TextView h_name = new TextView(this);
        h_name.setText("Name");
        h_name.setTypeface(null, Typeface.BOLD);
        h_name.setPadding(0,8,0,8);
        th.addView(h_name);
        TextView h_pillar = new TextView(this);
        h_pillar.setText("Pillar");
        h_pillar.setTypeface(null, Typeface.BOLD);
        h_pillar.setPadding(0,8,0,8);
        th.addView(h_pillar);
        display_table.addView(th, table_layout_params);

        String num_text = ((EditText) findViewById(R.id.editText_number)).getText().toString();
        int num_disp = students.size();
        if (search){
            try {
                num_disp = Integer.parseInt(num_text);
            } catch (NumberFormatException e){
            }
        }
        String name_search = ((EditText) findViewById(R.id.editText_name)).getText().toString();
        String pillar_search = ((EditText) findViewById(R.id.editText_pillar)).getText().toString();

        Student s;
        for (int i=0; i < num_disp; ++i){
            s = students.get(i);

            if (search){
                if (!name_search.equals("") && !s.name.contains(name_search)){
                    continue;
                }
                if (!pillar_search.equals("") && !s.pillar.contains(pillar_search)){
                    continue;
                }
            }

            TableRow tr = new TableRow(this);

            s_name = new TextView(this);
            s_name.setText(s.name);
            s_name.setPadding(0,8,0,8);
            tr.addView(s_name);

            s_pillar = new TextView(this);
            s_pillar.setText(s.pillar);
            s_pillar.setPadding(0,8,0,8);
            tr.addView(s_pillar);

            display_table.addView(tr, table_layout_params);
        }
    }

    public void save_fb(View v){
        String name = ((EditText) findViewById(R.id.editText_name)).getText().toString();
        String pillar = ((EditText) findViewById(R.id.editText_pillar)).getText().toString();
        if (name.equals("")){
            return;
        }
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("student/"+name);
        myRef.setValue(new Student(name, pillar));
        search = false;
        display_table();
    }

    public void search_fb(View v){
        search = true;
        display_table();
    }
}

class Student {
    String name;
    String pillar;

    public Student(){
    }

    public Student(String name, String pillar){
        this.name = name;
        this.pillar = pillar;
    }

    public String getName() {
        return name;
    }

    public String getPillar() {
        return pillar;
    }

    public String toString(){
        return "Student(name=" + name +", pillar=" + pillar + ")" ;
    }
}