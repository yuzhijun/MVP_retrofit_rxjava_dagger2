package com.lenovohit.yuzhijun.network;




import com.lenovohit.yuzhijun.model.HttpResult;
import com.lenovohit.yuzhijun.model.Weather2;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 网络接口类
 * Created by yuzhijun on 2016/3/29.
 */
public interface ApiService {
    @GET("service/getIpInfo.php")
    Observable<HttpResult<Weather2>> getWeatherResult4(@Query("ip") String ip);


}
