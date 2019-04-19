package lz.com.kit;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import lz.com.tools.dialog.LpDialogUtils;
import lz.com.tools.inject.LayoutId;
import lz.com.tools.inject.OnClick;

@LayoutId(R.layout.activity_dialog)
public class DialogActivity extends BaseActivity {

    @OnClick({R.id.show, R.id.show1, R.id.show2, R.id.show3, R.id.show4})
    public void onShow(View view) {
        switch (view.getId()) {
            case R.id.show:
                LpDialogUtils.alertDialog(this, "标题", "");
                break;
            case R.id.show1:

                View inflate = View.inflate(this, R.layout.layout_dialog, null);
                LpDialogUtils.alertViewDialog(this, inflate, null, null, null, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                break;
            case R.id.show2:
                LpDialogUtils.alertDialog(this, "", "内容", null, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                break;
            case R.id.show3:
                LpDialogUtils.alertContentDialog(this, "内容", null, null, null, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                break;
  case R.id.show4:
/*
      BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this); bottomSheetDialog.setContentView(R.layout.dialog_goods_sku_layout); bottomSheetDialog.show();
*/

                break;

            default:
                break;
        }
    }


}
