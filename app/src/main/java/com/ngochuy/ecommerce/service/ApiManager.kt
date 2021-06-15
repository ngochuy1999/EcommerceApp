package com.ngochuy.ecommerce.service


import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.http.*
import com.ngochuy.ecommerce.data.*
import okhttp3.Interceptor
import java.util.concurrent.TimeUnit

interface ApiManager {
    companion object {

        private const val BASE_URL = "https://api-phone-shop.herokuapp.com/"

        fun create(): ApiManager {
            val requestInterceptor = Interceptor { chain ->
                // Interceptor take only one argument which is a lambda function so parenthesis can be omitted

                val url = chain.request()
                        .url
                        .newBuilder()
                        .build()

                val request = chain.request()
                        .newBuilder()
                        .url(url)
                        .build()

                return@Interceptor chain.proceed(request)   //explicitly return a value from whit @ annotation. lambda always returns the value of the last expression implicitly
            }
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BASIC

            val okHttpClient = OkHttpClient.Builder()
                    .addInterceptor(requestInterceptor)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .build()

            return Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ApiManager::class.java)

        }
    }

    // getOTP
    @GET("getCode")
    fun getOtp(@Header("email") email:String): Call<String>

    //user
    @POST("login")
    fun login(
            @Header("email") username: String,
            @Header("password") password: String
    ): Call<ResultApi>

    @POST("users")
    fun signUp(
            @Header("email") email: String,
            @Header("name") name: String,
            @Header("password") password: String,
            @Header("phone") phone: String,
            @Header("address") address: String
    ): Call<ResultApi>

    @PUT("users")
    fun changeInfoAcc(
            @Header("id") userId: Int,
            @Header("email") email: String,
            @Header("name") name: String,
            @Header("phone") phone: String,
            @Header("address") address: String,
    ): Call<ResultApi>

    @PUT("password")
    fun changePass(
            @Header("id") userId: Int,
            @Header("old_password") oldPass: String,
            @Header("new_password") newPass: String
    ): Call<ResultApi>

    @POST("")
    fun forgotPassword(email: String): Call<Int>

    @GET("users")
    fun getUserInfoByUserID(@Header("id") userId: Int): Call<User>

    //product
    @GET("products")
    fun getListProductSale(): Call<ArrayList<Product>>

    @GET("products")
    fun getAllProducts(): Call<ArrayList<Product>>

    @GET("products/{id}")
    fun getProductDetailByID(@Path("id") productID: Int): Call<Product>

    @GET("slides")
    fun getListSlider(): Call<ArrayList<Slide>>

    // category
    @GET("brands")
    fun getListCategory(): Call<ArrayList<String>>

    @GET("getByBrand")
    fun getListProductOfCategory(@Header("filter") cate: String): Call<ArrayList<Product>>

    // cart
    @GET("countCarts/{id}")
    fun getCartCount(@Path("id") userID: Int): Call<Int>

    @POST("carts")
    fun plusCart(
            @Header("id_user") userID: Int,
            @Header("id") productID: Int,
            @Header("sl") quantity: Int
    ): Call<ResultApi>

    @DELETE("carts")
    fun delItemCart(@Header("id_user") userID: Int, @Header("id") productID: Int): Call<ResultApi>

    @PUT("carts")
    fun minusCart(@Query("") userID: Int, @Query("") productID: Int): Call<Boolean>

    @GET("carts")
    fun getProductsCart(@Header("id") userID: Int): Call<ArrayList<Product>>

    //order
    @POST("sold")
    fun addOrder(@Header("id") orderId: Int): Call<ResultApi>

    @GET("sold")
    fun getAllOrderItem( @Header("id_user") orderId: Int): Call<ResultOrder>
}