package lz.com.kit.api;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * API 管理器服务
 */
public interface ApiService {


    @GET("http://lf.snssdk.com/api/news/feed/v62/?iid=12507202490&device_id=37487219424&refer=1&count=20&aid=13")
    Observable<String> getNewsArticle2(
            @Query("category") String category,
            @Query("max_behot_time") String maxBehotTime);


}

