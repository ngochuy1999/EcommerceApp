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

    @POST("")
    fun login(email: String, password: String): Call<Int>

    @POST("")
    fun signUp(user: User): Call<Int>

    @POST("")
    fun forgotPassword(email: String): Call<Int>

    @GET("products")
    fun getListProductSale(): Call<ArrayList<Product>>

    @GET("")
    fun getAllProducts(): Call<ArrayList<Product>>

    @GET("")
    fun getListProductOfCategory(@Query("") cateId: Int): Call<ArrayList<Product>>

    @GET("products")
    fun getProductDetailByID(@Query("id") productID: Int): Call<ArrayList<Product>>

    @GET("")
    fun getListSlider(): Call<ArrayList<Slide>>


    @GET("")
    fun getListCategory(): Call<ArrayList<Category>>


    @GET("")
    fun getCartCount(@Query("") userID: Int): Call<Int>

    @POST("")
    fun plusCart(
            @Query("") userID: Int,
            @Query("") productID: Int,
            @Query("") quantity: Int
    ): Call<Boolean>

    @DELETE("")
    fun delItemCart(@Query("") userID: Int, @Query("") productID: Int): Call<Boolean>

    @POST("")
    fun minusCart(@Query("") userID: Int, @Query("") productID: Int): Call<Boolean>

}