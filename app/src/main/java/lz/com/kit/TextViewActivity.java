package lz.com.kit;


import lz.com.tools.inject.InjectLayout;
import lz.com.tools.textview.SuperTextView;

@InjectLayout(layoutId = R.layout.activity_textview,titleName = "textview")
public class TextViewActivity extends BaseKitActivity {

    @Override
    public void initData() {
        SuperTextView superTextView =  findViewById(R.id.setDiv_stv);
        superTextView.setTopDividerLineColor(getResources().getColor(R.color.colorAccent))
                .setBottomDividerLineColor(getResources().getColor(R.color.colorPrimary));
    }


}
