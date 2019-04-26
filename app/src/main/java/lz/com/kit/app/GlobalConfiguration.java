/*
 * Copyright 2017 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package lz.com.kit.app;


import com.lz.fram.BuildConfig;
import com.lz.fram.net.http.ConfigModule;
import com.lz.fram.net.http.GlobalConfigBuild;
import com.lz.fram.net.http.OkhttpFactory;
import com.lz.fram.net.http.RetrofitFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * 请求框架参数赋值
 */
public class GlobalConfiguration implements ConfigModule {


    @Override
    public void applyOptions(GlobalConfigBuild.Builder builder) {
        if (BuildConfig.DEBUG) {
            //Release 时,让框架不再打印 Http 请求和响应的信息
//            builder.addInterceptor(new LoggerInterceptor());
//            builder.addNetworkInterceptor(new OkNetworkMonitorInterceptor());
        }

        builder
                .baseurl("https://api.douban.com/")
                .retrofitConfiguration(new RetrofitFactory.RetrofitConfiguration() {
                    @Override
                    public void configRetrofit(Retrofit.Builder builder) {
                        //retrofit  信息配置
                        //builder.addConverterFactory(ScalarsConverterFactory.create());
                        // .addConverterFactory(GsonConverterFactory.create())
                    }
                })
                .okhttpConfiguration(new OkhttpFactory.OkhttpConfiguration() {
                    @Override
                    public void configOkhttp(OkHttpClient.Builder builder) {
                        //这里可以自己自定义配置Okhttp的参数
                        builder.writeTimeout(10, TimeUnit.SECONDS);
                        builder.readTimeout(10, TimeUnit.SECONDS);
                        builder.connectTimeout(10, TimeUnit.SECONDS);
                    }
                });


    }

}
