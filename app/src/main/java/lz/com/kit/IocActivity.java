package lz.com.kit;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import lz.com.tools.inject.InjectView;
import lz.com.tools.inject.LayoutId;
import lz.com.tools.inject.OnClick;
import lz.com.tools.inject.OnLongClick;


@LayoutId(R.layout.activity_ioc)
public class IocActivity extends BaseActivity {

    @InjectView(R.id.btn1)
    Button bt1;

    @OnClick(R.id.btn)
    public void onShow(View view) {
        Toast.makeText(this, "onclick", Toast.LENGTH_SHORT).show();
    }

    @OnLongClick(R.id.btn)
    public boolean onLongShow(View view) {
        Toast.makeText(this, "onlongclick", Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public void init() {
        super.init();
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mActivity, "bt1", Toast.LENGTH_SHORT).show();

            }
        });
    }


}
