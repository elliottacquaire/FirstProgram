package com.example.firstprogram

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.firstprogram.retrofit2.*
import com.orhanobut.logger.Logger
import com.readystatesoftware.chuck.ChuckInterceptor
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_net_work_request.*
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit


class NetWorkRequestActivity : AppCompatActivity(),View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_net_work_request)

        start.setOnClickListener(this)

    }

    //okhttpclient 请求  可以
    private fun initOkhttp(){
        val url = "http://wwww.baidu.com"
//        val url = "http://wwww.baidu.com"
        val okHttpClient = OkHttpClient()
        val request: Request = Request.Builder()
            .url(url)
            .get() //默认就是GET请求，可以不写
            .build()
        //request post
//        val mediaType: MediaType = MediaType.parse("text/x-markdown; charset=utf-8")
//        val requestBody = "I am Jdqm."
//        val request: Request = Request.Builder()
//            .url("https://api.github.com/markdown/raw")
//            .post(create(mediaType, requestBody))
//            .build()

        // POST方式提交流
//        val requestBody: RequestBody = object : RequestBody() {
//            @Nullable
//            override fun contentType(): MediaType? {
//                return MediaType.parse("text/x-markdown; charset=utf-8")
//            }
//            @Throws(IOException::class)
//            override fun writeTo(sink: BufferedSink) {
//                sink.writeUtf8("I am Jdqm.")
//            }
//        }
//        val request: Request = Builder()
//            .url("https://api.github.com/markdown/raw")
//            .post(requestBody)
//            .build()

        //POST提交文件
//        val file = File("test.md")
//        val request: Request = Builder()
//            .url("https://api.github.com/markdown/raw")
//            .post(create(mediaType, file))
//            .build()
        // POST方式提交表单
//        val requestBody: RequestBody = FormBody.Builder()
//            .add("search", "Jurassic Park")
//            .build()
//        val request: Request = Builder()
//            .url("https://en.wikipedia.org/w/index.php")
//            .post(requestBody)
//            .build()

        //POST方式提交分块请求
//        val body = MultipartBody.Builder("AaB03x")
//            .setType(MultipartBody.FORM)
//            .addPart(
//                Headers.of("Content-Disposition", "form-data; name=\"title\""),
//                RequestBody.create(null, "Square Logo")
//            )
//            .addPart(
//                Headers.of("Content-Disposition", "form-data; name=\"image\""),
//                create(MEDIA_TYPE_PNG, File("website/static/logo-square.png"))
//            )
//            .build()
//
//        val request: Request = Builder()
//            .header("Authorization", "Client-ID $IMGUR_CLIENT_ID")
//            .url("https://api.imgur.com/3/image")
//            .post(body)
//            .build()

        val call : okhttp3.Call = okHttpClient.newCall(request)
        call.enqueue(object : Callback<Any?>, okhttp3.Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {

            }

            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                Log.i("aaa", "------${response.body?.string()}")
            }

            override fun onFailure(call: Call<Any?>, t: Throwable) {

            }

            override fun onResponse(call: Call<Any?>, response: Response<Any?>) {
                Log.i("aaa", "------${response.body()?.toString()}")
            }
        })

    }

    override fun onClick(v: View?) {
        when(v?.id){
            /**
             * 1.基地址必须有（可以是全部，也可以是部分）
             * 2.添加拦截器(非必须)
             * 3.Converter是对于Call<T>中T的转换， Call<ResponseBody>--------->Call<Poju>
             * Call<T>中的Call也是可以被替换的，而返回值的类型就决定你后续的处理程序逻辑(非必须)
             * 4.CallAdapter可以对Call转换，Retrofit提供了多个CallAdapter，这里以RxJava的为例，用Observable<T>代替Call<T>(非必须)
             * 即Retrofit创建出接口的代理对象有多种形式，Call<T>、Observable<T>而后者就是Retrofit实现对Rxjava的支持
             * 5.初始化Retrofit
             * 6.用Retrofit创建出接口的代理对象，用代理对象来操作其方法,返回Call<Poju>
             * 通过Call<Poju>来请求入队，execute/enqueue(同步/异步)
             */
            R.id.start -> {
//                initOkhttp()
                val baseUrl = "http://apis.juhe.cn/"
                val url =
                    baseUrl + "mobile/get?phone=18856907654&key=5778e9d9cf089fc3b093b162036fc0e1"

                //多一层处理
                val httpLoggingInterceptor =
                    HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
                        override fun log(message: String) {
                            Log.i("Rxjava", message)
                        }
                    })
                httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)


//                val interceptor = HostInterceptor(this)

                val okhttpClient = OkHttpClient.Builder()
                    .retryOnConnectionFailure(true)//设置重连
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .callTimeout(15, TimeUnit.SECONDS)
                    .addNetworkInterceptor(httpLoggingInterceptor)//网络拦截器（打印信息更丰富，消息实体内容长度类型等）
//                    .addInterceptor(interceptor)
                    .addInterceptor(ChuckInterceptor(applicationContext).showNotification(false))  //查看网络请求
                    .build()

                //有没有OkHttpClient 都可以  可以单独用 Retrofit
                val retrofit = Retrofit.Builder()
//                    .baseUrl("https://api.github.com/")
                    .baseUrl("https://api.apiopen.top/")
//                    .baseUrl("https://jisuweather.api.bdymkt.com/")
//                    .baseUrl("http://qqlykm.cn/api/yan/yd.php?city=2019-6-18")
//                    .baseUrl("https://qa9.porsche-preview.cn/")
//                    .baseUrl(baseUrl)
                    //添加解析器，可选。如果不添加，则结果只能解析为ResponseBody类型，需自己解析为UserBean。
                    .addConverterFactory(GsonConverterFactory.create())
                    //addConverterFactory是有先后顺序的，如果有多个ConverterFactory都支持同一种类型，那么就是只有第一个才会被使用
                    .addConverterFactory(StringConverterFactory.create())
//                    .addConverterFactory()
                    //添加Retrofit对Rxjava2的支持，可选 区分Rxjava
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    //配置处理请求的client，有没有OkHttpClient 都可以
//                    .client(OkHttpClient())
                    .client(okhttpClient)
                    .build()

                val service = retrofit.create(GitHubService::class.java)

                //  //retrofit 未增加  .addConverterFactory(GsonConverterFactory.create())
                service.codeLoginRequest("666666", "13811112222")
//                  service.listReposS()
//                service.getGetDataO((url)) //在子线程取数据
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    //在主线程显示ui
                    // compile 'io.reactivex.rxjava2:rxandroid:2.0.1'  这个库下才有AndroidSchedulers.mainThread
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : Observer<ResponseBody> {

                        override fun onSubscribe(d: Disposable) {
                        }

                        override fun onNext(t: ResponseBody) {
                            Logger.d("ResponseBody---${t.source()}")
                        }

                        override fun onError(e: Throwable) {
                            Logger.d("Throwable--${e.message}")
                        }

                        override fun onComplete() {
                            Logger.d("onComplete")
                        }
                    })

                service.listReposS1()
//                  service.listReposS()
//                service.getGetDataO((url)) //在子线程取数据
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    //在主线程显示ui
                    // compile 'io.reactivex.rxjava2:rxandroid:2.0.1'  这个库下才有AndroidSchedulers.mainThread
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : Observer<Response<BaseBean<List<ContentBean>>>> {

                        override fun onSubscribe(d: Disposable) {
                        }

                        override fun onNext(t: Response<BaseBean<List<ContentBean>>>) {
                            Logger.d("ResponseBody---${t.code()}-${t.message()}--${t.isSuccessful}")
                        }

                        override fun onError(e: Throwable) {
                            Logger.d("Throwable--${e.message}")
                        }

                        override fun onComplete() {
                            Logger.d("onComplete")
                        }
                    })

                service.listReposS()
//                service.getGetDataO((url)) //在子线程取数据
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    //在主线程显示ui
                    // compile 'io.reactivex.rxjava2:rxandroid:2.0.1'  这个库下才有AndroidSchedulers.mainThread
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : Observer<BaseBean<List<ContentBean>>> {

                        override fun onSubscribe(d: Disposable) {
                        }

                        override fun onNext(t: BaseBean<List<ContentBean>>) {
                            Logger.d("ResponseBody---${t}")
                        }

                        override fun onError(e: Throwable) {
                            Logger.d("Throwable--${e.message}")
                        }

                        override fun onComplete() {
                            Logger.d("onComplete")
                        }
                    })

                //retrofit 增加  .addConverterFactory(GsonConverterFactory.create())
                service.getGetDataOGson((url)) //在子线程取数据
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    //在主线程显示ui
                    // compile 'io.reactivex.rxjava2:rxandroid:2.0.1'  这个库下才有AndroidSchedulers.mainThread
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : Observer<Bean> {

                        override fun onSubscribe(d: Disposable) {
                        }

                        override fun onNext(t: Bean) {
                            Logger.d("ResponseBody---${t}")
                            Toast.makeText(applicationContext, t.resultcode, Toast.LENGTH_LONG)
                                .show()
                        }

                        override fun onError(e: Throwable) {
                            Logger.d("Throwable--${e.message}")
                        }

                        override fun onComplete() {
                            Logger.d("onComplete")
                        }
                    })

                Thread {
                    Logger.d("thead name - ${Thread.currentThread().name}")
                }.start()

                //有问题---
//                val call: Call<ResponseBody> = service.getGetData(url)
//                val callBean: Call<Bean> = service.getGetDataBean(url)
//                callBean.enqueue(object : Callback<Bean> {
//                    override fun onResponse(
//                        call: Call<Bean>,
//                        response: Response<Bean>
//                    ) {
//                        //主线程
//                        try {
//                            //response.body().string()中的 (.string)只能使用一次
//                            Logger.d("response---->" + response.code())
////                            Log.i("aaa","response---->" + response.body().toString())
//                        } catch (e: IOException) {
//                            e.printStackTrace()
//                        }
//                    }
//
//                    override fun onFailure(call: Call<Bean>, t: Throwable) {
//                        Logger.i("Throwable---->" + t.message)
//                    }
//                })

//                val citys: Call<ResponseBody> = service.codeLoginRequest("666666", "13811112222")
//                citys.enqueue(object : Callback<ResponseBody> {
//                    override fun onResponse(
//                        call: Call<ResponseBody>,
//                        response: Response<ResponseBody>
//                    ) {
//                        Log.i("aaa", "------${response.body()?.string()}")
//                    }
//
//                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
//                        Log.i("aaa", "------${t.message}")
//                    }
//
//                })

//                val reposCallS: Call<String> = service.listReposS()
//                reposCallS.enqueue(object : Callback<String>{
//                    override fun onResponse(call: Call<String>, response: Response<String>) {
//                        Log.i("aaa","------${response}")
//                    }
//
//                    override fun onFailure(call: Call<String>, t: Throwable) {
//                        Log.i("aaa","------${t.message}")
//                    }
//
//                })

//                val reposCallBody: Call<ResponseBody> = service.listRepos()
//                reposCallBody.enqueue(object : Callback<ResponseBody>{
//                    override fun onResponse(
//                        call: Call<ResponseBody>,
//                        response: Response<ResponseBody>
//                    ) {
//                        Log.i("aaa","------${response.body()?.string()}")
//                    }
//
//                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
//                        Log.i("aaa","------${t.message}")
//                    }
//
//                })

//                val reposCall: Call<Response<ResponseBody>> = service.listMusics()
//                reposCall.enqueue(object : Callback<Response<ResponseBody>>{
//                    override fun onResponse(
//                        call: Call<Response<ResponseBody>>,
//                        response: Response<Response<ResponseBody>>
//                    ) {
//                        Log.i("aaa","------${response}")
//                    }
//
//                    override fun onFailure(call: Call<Response<ResponseBody>>, t: Throwable) {
//                        Log.i("aaa","------${t.message}")
//                    }
//
//                })

//                val repos: Call<List<Repo>?>? = service.listRepos()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
//        if (subscribe != null && subscribe.isUnsubscribed()) {
//            subscribe.unsubscribe();
//        }
    }
}