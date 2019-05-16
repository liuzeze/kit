package lz.com.kit;

import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import lz.com.tools.view.BigImageView;
import lz.com.tools.view.LongImageView;
import lz.com.tools.inject.InjectLayout;

@InjectLayout(layoutId = R.layout.activity_long_image)
public class LongImageActivity extends BaseKitActivity {


    @BindView(R.id.longimage)
    LongImageView mLongImageView;
    @BindView(R.id.bigimage)
    BigImageView bigimage;

    @Override
    protected void initData() {

        InputStream stream = null;
        try {
            stream = getAssets().open("long.png");
            mLongImageView.setImageInput(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }


        InputStream stream1 = null;
        try {
            stream1 = getAssets().open("big.jpg");
            bigimage.setImageInput(stream1);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
