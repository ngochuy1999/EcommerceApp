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

        private const val BASE_URL = "http://192.168.1.3:8080/rest/"

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
//    @GET("getCode")
//    fun getOtp(@Header("email") email:String): Call<String>

    //user
    @POST("login")
    fun login(
        @Query("email") username: String,
        @Query("password") password: String
    ): Call<ResultApi>

    @POST("signup")
    fun signUp(
        @Query("email") email: String,
        @Query("name") name: String,
        @Query("password") password: String,
        @Query("phone") phone: String,
        @Query("address") address: String
    ): Call<ResultApi>

    @PUT("users")
    fun changeInfoAcc(
        @Query("id") userId: Int,
        @Query("name") name: String,
        @Query("phone") phone: String,
        @Query("address") address: String,
    ): Call<ResultApi>

    @PUT("password")
    fun changePass(
        @Query("id") userId: Int,
        @Query("oldPassword") oldPass: String,
        @Query("newPassword") newPass: String
    ): Call<ResultApi>

    @POST("")
    fun forgotPassword(email: String): Call<Int>

    @GET("users")
    fun getUserInfoByUserID(@Header("id") userId: Int): Call<User>

    //List Address
    @GET("address")
    fun getAddress( @Query("userId") userId: Int): Call<ArrayList<ShoppingAddress>>

    @POST("address")
    fun addAddress( @Body addressRequest: AddressRequest): Call<ResultApi>

    @DELETE("address")
    fun deleteAddress( @Query("addressId") addressId: Int): Call<ResultApi>

    @GET("addressDefault")
    fun getAddressDefault( @Query("userId") userId: Int): Call<ShoppingAddress>

    //product
    @GET("products")
    fun getListProductSale(): Call<ArrayList<ProductDetail>>

    @GET("products")
    fun getAllProducts(): Call<ArrayList<ProductDetail>>

    @GET("products/{id}")
    fun getProductDetailByID(@Path("id") productID: Int): Call<ProductDetail>

    @GET("slides")
    fun getListSlider(): Call<ArrayList<Slide>>

    // category
    @GET("brands")
    fun getListCategory(): Call<ArrayList<Category>>

    @GET("getByBrand")
    fun getListProductOfCategory(@Query("filter") cate: String): Call<ArrayList<ProductDetail>>

    //=============Invoice==========

    //order
    @POST("invoices")
    fun addOrder(@Body invoiceRequest: InvoiceRequest): Call<ResultApi>

    //get invoice
    @GET("invoices")
    fun getAllOrder (@Query("userId") orderId: Int): Call<ArrayList<Invoice>>

    @GET("accomplish")
    fun getAccomplishOrderItem( @Query("userId") userId: Int): Call<ArrayList<Invoice>>

    @GET("confirm")
    fun getConfirmOrderItem( @Query("userId") userId: Int): Call<ArrayList<Invoice>>

    @GET("payment")
    fun getPaymentOrderItem( @Query("userId") userId: Int): Call<ArrayList<Invoice>>

    @GET("delivery")
    fun getDeliverOrderItem( @Query("userId") userId: Int): Call<ArrayList<Invoice>>

    @GET("cancel")
    fun getCancelOrderItem( @Query("userId") userId: Int): Call<ArrayList<Invoice>>

    @GET("invoiceDetail")
    fun getDetailInvoice( @Query("invoiceId") invoiceId: Int): Call<ArrayList<InvoiceDetail>>

    @POST("cancelInvoice")
    fun cancelInvoice( @Query("invoiceId") invoiceId: Int): Call<ResultApi>

}