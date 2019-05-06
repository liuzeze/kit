package lz.com.kit.app;

import android.annotation.SuppressLint;

import com.lz.fram.observer.Transformer;
import com.orhanobut.logger.Logger;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.functions.Consumer;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class LoggerInterceptor implements Interceptor {
    private static final String TAG = "Http";
    private StringBuilder sb;

    @SuppressLint("CheckResult")
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        MediaType mediaType = response.body().contentType();
        String content = response.body().string();

        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                sb = new StringBuilder();
                long t1 = System.nanoTime();
                sb.append(request.method()).append(";").append(request.url()).append(";");
                Logger.e(TAG, sb.toString());

                sb = new StringBuilder();
                long t2 = System.nanoTime();
                String[] split = request.url().url().toString().split("/");
                String route = split[split.length - 1].split("\\?")[0];
                sb.append(response.code()).append("; ")
                        .append((int) ((t2 - t1) / 1e6)).append("ms; /")
                        .append(split[split.length - 2]).append("/")
                        .append(route).append(";\n")
                        .append(content);
                Logger.e(TAG, sb.toString());

            }
        }).compose(Transformer.switchSchedulersObser()).subscribe(o -> {

        }, throwable -> Logger.e(TAG, throwable.getMessage()));


        return response.newBuilder()
                .body(ResponseBody.create(mediaType, content))
                .build();
    }
}
