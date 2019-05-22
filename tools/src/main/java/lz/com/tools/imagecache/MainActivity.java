package lz.com.tools.imagecache;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.ListView;

import lz.com.tools.R;
import lz.com.tools.base.BaseActivity;

public class MainActivity extends BaseActivity {

   
    @Override
    protected void initData() {


        ImageCache.getInstance().init(this,Environment.getExternalStorageDirectory()+"/dn");


        Bitmap bitmap=ImageCache.getInstance().getBitmapFromMemory("123");
        if(null==bitmap){
            //如果内存没数据，就去复用池找
            Bitmap reuseable=ImageCache.getInstance().getReuseable(60,60,1);
            //reuseable能复用的内存
            //从磁盘找
            bitmap = ImageCache.getInstance().getBitmapFromDisk("123",reuseable);
            //如果磁盘中也没缓存,就从网络下载
            if(null==bitmap){
                bitmap=ImageResize.resizeBitmap(this,R.mipmap.clear,80,80,false,reuseable);
                ImageCache.getInstance().putBitmapToMemeory("123",bitmap);
                ImageCache.getInstance().putBitMapToDisk("123",bitmap);
                Log.i("jett","从网络加载了数据");
            }else{
                Log.i("jett","从磁盘中加载了数据");
            }

        }else{
            Log.i("jett","从内存中加载了数据");
        }


    }

    void i(Bitmap bitmap){
        Log.i("jett","图片"+bitmap.getWidth()+"x"+bitmap.getHeight()+" 内存大小是:"+bitmap.getByteCount());
    }

    @Override
    public void showErrorMsg(String msg) {

    }
}
