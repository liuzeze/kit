package lz.com.kit;


import android.os.Bundle;
import android.view.Gravity;

import androidx.appcompat.app.AppCompatActivity;

import lz.com.tools.inject.LayoutId;
import lz.com.tools.textview.SuperTextView;

@LayoutId(value = R.layout.activity_textview,titleName = "textview")
public class TextViewActivity extends BaseActivity {

    @Override
    public void init() {
        super.init();
        SuperTextView superTextView =  findViewById(R.id.setDiv_stv);
        superTextView.setTopDividerLineColor(getResources().getColor(R.color.colorAccent))
                .setBottomDividerLineColor(getResources().getColor(R.color.colorPrimary));
    }


}
