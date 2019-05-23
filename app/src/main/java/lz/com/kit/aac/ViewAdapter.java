package lz.com.kit.aac;

import android.text.TextUtils;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import lz.com.tools.glide.GlideUtils;

/**
 * -----------作者----------日期----------变更内容-----
 * -          刘泽      2019-05-23       创建class
 */
public class ViewAdapter {

    @BindingAdapter(value = {"imgUrl"}, requireAll = false)
    public static void loadRoundImage(ImageView imageView,String url){
        if (!TextUtils.isEmpty(url)) {
            GlideUtils.loadRoundImage(imageView.getContext(),url,imageView,10);
        }
    }
}
