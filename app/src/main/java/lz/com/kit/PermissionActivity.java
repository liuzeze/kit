package lz.com.kit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.Toast;

import lz.com.tools.inject.InjectLayout;

@InjectLayout(layoutId = R.layout.activity_permission, titleName = "权限申请")
public class PermissionActivity extends BaseKitActivity {
    /**
     * 需要进行检测的权限数组
     */
    protected String[] needPermissions = {Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE};


    private void checkedPermission() {
        //权限未通过
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //是否需要弹框提示权限信息
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {

                new AlertDialog.Builder(this)
                        .setTitle("权限申请")
                        .setIcon(R.mipmap.cloud_2)
                        .setMessage("不给权限应用要崩溃了")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .setPositiveButton("去申请", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(PermissionActivity.this, needPermissions, 100);
                            }
                        })
                        .show()
                ;
            } else {
                ActivityCompat.requestPermissions(PermissionActivity.this, needPermissions, 100);
            }


        }

    }

    public void toRequest(View view) {
        checkedPermission();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 100) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(PermissionActivity.this, "申请成功", Toast.LENGTH_SHORT).show();
            }
            for (String permission : permissions) {
                System.out.print(permission + "===");
            }
        }
    }

    @Override
    protected void initData() {

    }
}
