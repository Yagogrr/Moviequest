package com.example.moviequest

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager


interface PartieService {
    @DELETE("/Partie/{id}")
    suspend fun deletePartie(@Path("id") id: Int): Response<Unit>


    @GET("/Partie")
    suspend fun listParties(): Response<List<Partie>>
}


class PartieAPI {
    companion object {
        private var mAPI: PartieService? = null

        @Synchronized
        fun API(): PartieService {
            if (mAPI == null) {
                val client: OkHttpClient = getUnsafeOkHttpClient()
                val gsondateformat = GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                    .create()
                mAPI = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create(gsondateformat))
                    .baseUrl("https://13.216.198.223")
                    .client(client)
                    .build()
                    .create(PartieService::class.java)
            }
            return mAPI!!
        }
    }
}

// (Unsafe OkHttpClient - Keep this part if your API uses HTTPS with potential certificate issues,
// but understand the security implications and consider removing for production if possible by fixing certificates)
private fun getUnsafeOkHttpClient(): OkHttpClient {
    try {
        // Create a trust manager that does not validate certificate chains
        val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
            @Throws(CertificateException::class)
            override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {}

            @Throws(CertificateException::class)
            override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {}

            override fun getAcceptedIssuers(): Array<X509Certificate> {
                return arrayOf()
            }
        })

        // Install the all-trusting trust manager
        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCerts, SecureRandom())
        // Create an ssl socket factory with our all-trusting manager
        val sslSocketFactory: SSLSocketFactory = sslContext.socketFactory

        val builder = OkHttpClient.Builder()
        builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
        builder.hostnameVerifier { _, _ -> true }

        return builder.build()
    } catch (e: Exception) {
        throw RuntimeException(e)
    }
}