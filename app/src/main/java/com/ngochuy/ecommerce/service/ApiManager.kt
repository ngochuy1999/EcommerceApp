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

    @GET("login")
    fun login(
            @Query("username") username: String,
            @Query("password") password: String
    ): Call<ResultApi>

    @POST("signUp")
    fun signUp(
            @Query("username") username: String,
            @Query("password") password: String,
            @Query("name") name: String,
            @Query("email") email: String,
            @Query("phone") phone: String,
            @Query("address") address: String
    ): Call<ResultApi>

    @PUT("updateUser")
    fun changeInfoAcc(
            @Query("userId") userId: Int,
            @Query("name") name: String,
            @Query("email") email: String,
            @Query("phone") phone: String,
            @Query("address") address: String,
            @Query("avatar") avatar: String
    ): Call<ResultApi>

    @GET("changePassword")
    fun changePass(
            @Query("userId") userId: Int,
            @Query("oldPass") oldPass: String,
            @Query("newPass") newPass: String
    ): Call<ResultApi>

    @POST("")
    fun forgotPassword(email: String): Call<Int>

    @POST("userInfoById")
    fun getUserInfoByUserID(@Query("userId") userID: Int): Call<ArrayList<User>>

    @GET("products")
    fun getListProductSale(): Call<ArrayList<Product>>

    @GET("products")
    fun getAllProducts(): Call<ArrayList<Product>>

    @GET("")
    fun getListProductOfCategory(@Query("") cateId: Int): Call<ArrayList<Product>>

    @GET("products/{id}")
    fun getProductDetailByID(@Path("id") productID: Int): Call<Product>

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

    @GET("")
    fun getProductsCart(@Query("userId") userID: Int): Call<ArrayList<Product>>


    @PUT("checkOut")
    fun addOrder(
            @Query("userId") userID: Int,
            @Query("name") name: String,
            @Query("phone") phone: String,
            @Query("email") email: String,
            @Query("address") address: String,
            @Query("note") note: String
    ): Call<ResultApi>

    @GET("")
    fun getAllOrder(@Query("userId") userId: Int): Call<ArrayList<Order>>

    @GET("")
    fun getAllOrderByStatus(
            @Query("userId") userId: Int,
            @Query("statusId") statusId: Int
    ): Call<ArrayList<Order>>

    @GET("")
    fun getAllOrderStatus(): Call<ArrayList<OrderStatus>>

    @PUT("")
    fun cancelOrder(
            @Query("orderId") orderId: Int
    ): Call<ResultApi>


    @GET("")
    fun getAllOrderItem( @Query("orderId") orderId: Int): Call<ArrayList<OrderItem>>

}