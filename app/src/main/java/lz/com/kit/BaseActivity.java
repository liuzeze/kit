package lz.com.kit;

import android.os.Bundle;
import android.transition.Explode;
import android.transition.Slide;
import android.transition.Transition;
import android.view.Gravity;
import android.view.animation.DecelerateInterpolator;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * -----------作者----------日期----------变更内容-----
 * -          刘泽      2019-01-22       创建class
 */
public class BaseActivity extends AppCompatActivity {
    public static final int EXPLODE_CODE = 1;
    public static final int EXPLODE_XML = 2;
    public static final int SLIDE_CODE = 3;
    public static final int SLIDE_XML = 4;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Transition transition = null;
        switch (getIntent().getIntExtra("type", -1)) {
            case EXPLODE_CODE:
                transition = new Explode();
                transition.setDuration(1000);
                transition.setInterpolator(new DecelerateInterpolator());
                break;
         /*   case EXPLODE_XML:
                transition = TransitionInflater.from(this).inflateTransition(R.transition.simple_explode);
                break;*/
            case SLIDE_CODE:
                transition = new Slide();
                ((Slide) transition).setSlideEdge(Gravity.RIGHT);
                transition.setDuration(300);
                transition.setInterpolator(new DecelerateInterpolator());
                break;
           /* case SLIDE_XML:
                transition = TransitionInflater.from(this).inflateTransition(R.transition.simple_slide);
                break;*/
        }
        if (transition != null) {
            getWindow().setEnterTransition(transition);
            getWindow().setExitTransition(transition);
        }
        super.onCreate(savedInstanceState);
    }
}
