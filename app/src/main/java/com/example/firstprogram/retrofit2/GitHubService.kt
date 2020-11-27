package com.example.firstprogram.retrofit2

import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.adapter.rxjava2.Result
import retrofit2.http.*

/**
 * Retrofit的Url组合规则

BaseUrl                                          和URL有关的注解中提供的值              最后结果

http://localhost:4567/path/to/other/             /post                               http://localhost:4567/post

http://localhost:4567/path/to/other/             post                                http://localhost:4567/path/to/other/post

http://localhost:4567/path/to/other/             https://github.com/ikidou           https://github.com/ikidou

从上面不能难看出以下规则：

如果你在注解中提供的url是完整的url，则url将作为请求的url。
如果你在注解中提供的url是不完整的url，且不以 / 开头，则请求的url为baseUrl+注解中提供的值
如果你在注解中提供的url是不完整的url，且以 / 开头，则请求的url为baseUrl的主机部分+注解中提供的值

 */

interface GitHubService {

    @GET
    fun getGetDataO(@Url url: String): Observable<ResponseBody>

    @GET
    fun getGetDataOGson(@Url url: String): Observable<Bean>

    @GET
    fun getGetData(@Url url: String): Call<ResponseBody>

    @GET
    fun getGetDataBean(@Url url: String): Call<Bean>

    //Get请求，方法中指定@Path参数和@Query参数
    //@path参数用于替换url中{}的部分，
    //@Query将在url地址中追加类似"page=1"的字符串
    @GET("{mobile}/get?")
    fun getPathData(
        @Path("mobile") mobile: String?,
        @Query("phone") phone: String?,
        @Query("key") key: String?
    ): Call<ResponseBody>

    @GET("mobile/get?")
    fun getQueryMapData(@QueryMap map: Map<String, String>): Call<ResponseBody>

    /**
     * 当GET、POST...HTTP等方法中没有设置Url时，则必须使用 {@link Url}提供
     * 对于Query和QueryMap，如果不是String（或Map的第二个泛型参数不是String）时
     * 会被默认会调用toString转换成String类型
     * Url支持的类型有 okhttp3.HttpUrl, String, java.net.URI, android.net.Uri
     * {@link retrofit2.http.QueryMap} 用法和{@link retrofit2.http.FieldMap} 用法一样，不再说明
     */
    @GET //当有URL注解时，这里的URL就省略了
    fun gcitys(): Call<ResponseBody>

    @FormUrlEncoded
    @POST("/pdc-api-gateway/drs-mobile-bff/v1/mbff/sms/token")
    fun codeLoginRequest(
        @Field("verifyCode") verify_code: String,
        @Field("phone") phone: String

    ): Observable<ResponseBody>

    /**
     * [FormUrlEncoded] 表明是一个表单格式的请求（Content-Type:application/x-www-form-urlencoded）
     * `Field("username")` 表示将后面的 `String name` 中name的取值作为 username 的值
     */
    @POST("/form")
    @FormUrlEncoded
    fun testFormUrlEncoded1(
        @Field("username") name: String?,
        @Field("age") age: Int
    ): Call<ResponseBody?>?

    /**
     * Map的key作为表单的键
     */
    @POST("/form")
    @FormUrlEncoded
    fun testFormUrlEncoded2(@FieldMap map: Map<String?, Any?>?): Call<ResponseBody?>?

    /**
     * [Part] 后面支持三种类型，[RequestBody]、[okhttp3.MultipartBody.Part] 、任意类型
     * 除 [okhttp3.MultipartBody.Part] 以外，其它类型都必须带上表单字段([okhttp3.MultipartBody.Part] 中已经包含了表单字段的信息)，
     */
    @POST("/form")
    @Multipart
    fun testFileUpload1(
        @Part("name") name: RequestBody?,
        @Part("age") age: RequestBody?,
        @Part file: MultipartBody.Part?
    ): Call<ResponseBody?>?

    /**
     * PartMap 注解支持一个Map作为参数，支持 [RequestBody] 类型，
     * 如果有其它的类型，会被[retrofit2.Converter]转换，如后面会介绍的 使用[com.google.gson.Gson] 的 [retrofit2.converter.gson.GsonRequestBodyConverter]
     * 所以[MultipartBody.Part] 就不适用了,所以文件只能用** @Part MultipartBody.Part **
     */
    @POST("/form")
    @Multipart
    fun testFileUpload2(
        @PartMap args: Map<String?, RequestBody?>?,
        @Part file: MultipartBody.Part?
    ): Call<ResponseBody?>?

    @POST("/form")
    @Multipart
    fun testFileUpload3(@PartMap args: Map<String?, RequestBody?>?): Call<ResponseBody?>?

    @GET("/headers?showAll=true")
    @Headers(*["CustomHeader1: customHeaderValue1", "CustomHeader2: customHeaderValue2"])
    fun testHeader(@Header("CustomHeader3") customHeaderValue3: String?): Call<ResponseBody?>?


    @POST("weather/city")
    fun citys(): Call<ResponseBody>

    @POST("musicRankings")
    fun listRepos(): Call<ResponseBody>

    /**
     * 像下面的这种情况最后我们无法获取到返回的Header和响应码的，如果我们需要这两者，提供两种方案：
    1、用Observable<Response<T>> 代替 Observable<T> ,这里的Response指retrofit2.Response
    2、用Observable<Result<T>> 代替 Observable<T>，这里的Result是指retrofit2.adapter.rxjava.Result,这个Result中包含了Response的实例
     */
    @GET("musicRankings")
    fun listReposS(): Observable<BaseBean<List<ContentBean>>>

    @GET("musicRankings1")
    fun listReposS1(): Observable<Response<BaseBean<List<ContentBean>>>>

    @GET("musicRankings")
    fun listReposS2(): Observable<Result<BaseBean<List<ContentBean>>>>

    @GET("musicRankings")
    fun listMusics(): Call<Response<ResponseBody>>

    @GET("musicRankingsDetails?type=1")
    fun listMusicsD(): Call<Response<ResponseBody>>

    @GET("musicBroadcasting")
    fun listMusicsBroad(): Call<Response<ResponseBody>>

    @FormUrlEncoded
    @POST("mobile/get")
    fun  //注意  不是 /  结束
            postFile(
        @Field("phone") phone: String?,
        @Field("key") key: String?
    ): Call<ResponseBody>

    /**
     * method 表示请求的方法，区分大小写
     * path表示路径
     * hasBody表示是否有请求体
     */
    @HTTP(method = "GET", path = "blog/{id}", hasBody = false)
    fun getBlog(@Path("id") id: Int): Call<ResponseBody?>?


}