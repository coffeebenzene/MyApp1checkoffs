package istd.eric.myapp1checkoffs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class CheckoffHW1Activity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    Toast toast;
    int settingcount = 0;

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
        setContentView(R.layout.activity_checkoff_hw1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImageDetail[] detailrows = {
                new ImageDetail(R.drawable.awesomeface, "awesomeface", "description of awesomeface"),
                new ImageDetail(R.drawable.uwaaa, "UWAAAA!!!", "Uwa"+new String(new char[100]).replace("\0", "aa")+"!"),
                new ImageDetail(R.drawable.awesomeface, "awesomeface2", "description of awesomeface"),
                new ImageDetail(R.drawable.drown, "Drowned", "drowned, but still good"),
                new ImageDetail(R.drawable.discover, "cardboard box", "You need to be snake to do this."),
                new ImageDetail(R.drawable.mine, "Watch out for the floor", "Play minesweeper, it'll help"),
                new ImageDetail(R.drawable.drown, "Drowned2", "drowned, but better"),
                new ImageDetail(R.drawable.drown, "Drowned3", "drowned, but best"),
                new ImageDetail(R.drawable.drown, "Drowned4", "drowned, but ...oh snap"),
                new ImageDetail(R.drawable.drown, "Drowned5", "ArrayIndexOutOfBoundsException"),
                new ImageDetail(R.drawable.discover, "cardboard box", "new box."),
        };

        DetailsAadapter adapter = new DetailsAadapter(getApplicationContext(), R.id.detailslist, detailrows);

        ListView detailslist = (ListView) findViewById(R.id.detailslist);
        detailslist.setAdapter(adapter);
        detailslist.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        view.findViewById(R.id.description).setSelected(true);
        if (position==0){
            Intent intent = new Intent(this, CheckoffHW1Activity_FirstItem.class);
            startActivity(intent);
        } else if (position==1){
            showToast("you clicked the 2nd item. UWAAAAAA!!!111ones!");
        } else {
            ImageDetail item = (ImageDetail) parent.getItemAtPosition(position);
            if(item.getTitle().startsWith("Drowned")){
                showToast("There is nothing. Solve.");
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id){
            case R.id.action_awesomeface:
                showToast("awesomeface icon pressed");
                return true;
            case R.id.action_set3:
                showToast("SET3 pressed");
                return true;
            case R.id.action_set2:
                showToast("Settings two pressed");
                return true;
            case R.id.action_settings:
                String[] setting_msg = {"Oi, you're not supposed to click me",
                                        "stop clicking me",
                                        ">_< Please stop.",
                                        "Uguuuuuuu",
                                        "GAAAAHHHHHHHHHHHH",
                                        "Ok, there's no more messages",
                                        "REALLY!",
                                        "Keep clicking me and I'll crash the app by ArrayIndexOutOfBoundsException!",
                                        "There's really no if statement to reset the count on this!",
                                        "DON'T SAY I DIDN'T I WARN YOU!"};
                showToast(setting_msg[settingcount]);
                settingcount += 1;
                return true;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}