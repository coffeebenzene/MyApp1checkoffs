package istd.eric.myapp1checkoffs;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Checkoff3Activity extends AppCompatActivity implements
                                                            Frag1.OnFragmentInteractionListener,
                                                            Frag2.OnFragmentInteractionListener,
                                                            Frag3.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkoff3);
        frag_display(findViewById(R.id.frag1));
    }

    public void frag_display(View v){
        Fragment frag;
        if(v.getTag().equals("frag2")){ //Can also use v.getId()==R.id.frag2
            frag = new Frag2();
        } else if (v.getTag().equals("frag3")){
            frag = new Frag3();
        } else {
            frag = new Frag1();
        }
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_elem, frag);
        fragmentTransaction.commit();
    }

    public void onFragmentInteraction(Uri uri){
    };
}
