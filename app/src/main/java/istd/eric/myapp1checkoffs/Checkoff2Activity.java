package istd.eric.myapp1checkoffs;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;
import android.view.animation.LinearInterpolator;

public class Checkoff2Activity extends AppCompatActivity {

    static Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkoff2);

        final View toberotated = findViewById(R.id.goaway);
        ObjectAnimator rotate = ObjectAnimator.ofFloat(toberotated, "rotation", 0, 360, 0);
        rotate.setDuration(3000).setRepeatCount(ObjectAnimator.INFINITE);
        rotate.setInterpolator(new LinearInterpolator());
        rotate.start();

        final View toberesized = findViewById(R.id.uwaaa);
        ObjectAnimator bigger_x = ObjectAnimator.ofFloat(toberesized, "scaleX", 1.5f).setDuration(500);
        ObjectAnimator bigger_y = ObjectAnimator.ofFloat(toberesized, "scaleY", 1.5f).setDuration(500);
        ObjectAnimator smaller_x = ObjectAnimator.ofFloat(toberesized, "scaleX", 1f).setDuration(500);
        ObjectAnimator smaller_y = ObjectAnimator.ofFloat(toberesized, "scaleY", 1f).setDuration(500);

        AnimatorSet resize = new AnimatorSet();
        resize.play(bigger_x).with(bigger_y);
        resize.play(smaller_x).after(bigger_x);
        resize.play(smaller_y).after(bigger_y);
        resize.addListener(new AnimatorListenerAdapter(){
            private int played = 0;
            public void onAnimationEnd(Animator animator ){
                super.onAnimationEnd(animator);
                animator.setStartDelay(0);
                played += 1;
                if (played < 4){
                    animator.start();
                }
            }
        });
        resize.setStartDelay(2000);
        resize.start();

        final View tobetranslated = findViewById(R.id.heeelp);
        ObjectAnimator translate = ObjectAnimator.ofFloat(tobetranslated, "TranslationX", 600).setDuration(800);
        translate.setRepeatCount(ObjectAnimator.INFINITE);
        translate.setRepeatMode(ObjectAnimator.REVERSE);
        translate.start();
    }

    public void gopage2(View v){
        Intent intent = new Intent(this, Checkoff2Activity_page2.class);
        startActivity(intent);
    }

    public void onRadioButtonClicked(View v){
        boolean checked = ((RadioButton) v).isChecked();
        CharSequence msg = "unknown button??";
        int v_id = v.getId();

        if (v_id == R.id.radioButton1){
            msg = "Radio button1 checked!";
        } else if (v_id == R.id.radioButton2){
            msg = "Radio button2 checked!";
        }

        if (toast != null){
            toast.cancel();
        }
        toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void goemail(View v){
        Intent intent = new Intent(this, Checkoff2Activity_emailpage.class);
        startActivity(intent);
    }
}
