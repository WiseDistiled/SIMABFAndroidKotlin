package com.tiramakan.simabf.bootstrap.beansProviders

import com.github.ajalt.timberkt.Timber

import com.google.gson.Gson
import com.squareup.otto.Bus
import com.tiramakan.simabf.bootstrap.APIAuthenticator
import com.tiramakan.simabf.bootstrap.util.MyEmulatorDetector
import com.tiramakan.simabf.bootstrap.util.MyPreferences
import okhttp3.Interceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import okio.Buffer
import java.io.BufferedInputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLConnection
import java.security.GeneralSecurityException
import java.security.KeyStore
import java.security.cert.CertificateFactory
import java.util.*
import javax.net.ssl.*


/**
 * Created by tiramakan on 19/12/2015.
 */

class RetrofitLoginProviderImpl(private val gson: Gson, private val authenticator: APIAuthenticator, internal var myEmulatorDetector: MyEmulatorDetector, internal var preferences: MyPreferences, private val bus: Bus) : RetrofitLoginProvider {
    private//  public static final String API_BASE_URL = "http://10.0.3.2:9191/"; Pour genymotion
    val apiBaseURL: String
        get() {
                return preferences.webserviceURL
         }

            override// add logging as last interceptor
            val retrofit: Retrofit
            get() {
                try {
                val builder: Retrofit.Builder
                val logging = HttpLoggingInterceptor(object:HttpLoggingInterceptor.Logger {
                    override fun log(message:String) {
                        Timber.tag("RETROFIT LOGIN").i(message)
                    }
                })
                logging.level = HttpLoggingInterceptor.Level.BODY
                val trustManager: X509TrustManager
                val sslSocketFactory: SSLSocketFactory
                try {
                    trustManager = trustManagerForCertificates(trustedCertificatesInputStream())
                    val sslContext = SSLContext.getInstance("TLS")
                    sslContext.init(null, arrayOf<TrustManager>(trustManager), null)
                    sslSocketFactory = sslContext.socketFactory
                } catch (e: GeneralSecurityException) {
                    throw RuntimeException(e)
                }

                val httpClient = OkHttpClient.Builder()
                        .sslSocketFactory(sslSocketFactory, trustManager)
                        .addNetworkInterceptor(logging)
                        .connectTimeout(10, TimeUnit.SECONDS)
                        .readTimeout(0, TimeUnit.NANOSECONDS)
                        .writeTimeout(30, TimeUnit.SECONDS)
                        .addNetworkInterceptor(logging)
                        .addInterceptor(object:Interceptor {
                            @Throws(IOException::class)
                            override fun intercept(chain: Interceptor.Chain): Response {
                                val original = chain.request()
                                val requestBuilder = original.newBuilder().addHeader("Accept", "application/json")
                                        .method(original.method, original.body)
                                val request = requestBuilder.build()
                                return chain.proceed(request)
                            }
                        }).build()
                builder = Retrofit.Builder()
                        .baseUrl(apiBaseURL)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                return builder.client(httpClient).build()
            } catch (e: Exception) {
        bus.post("Impossible d'accéder au serveur rest  : " + preferences.webserviceURL + e.message)
        throw AssertionError(e)
    }
            }
    /**
     * Returns an input stream containing one or more certificate PEM files. This implementation just
     * embeds the PEM files in Java strings; most applications will instead read this from a resource
     * file that gets bundled with the application.
     */
    private fun trustedCertificatesInputStream(): InputStream {
        // PEM files for root certificates of Comodo and Entrust. These two CAs are sufficient to view
        // https://publicobject.com (Comodo) and https://squareup.com (Entrust). But they aren't
        // sufficient to connect to most HTTPS sites including https://godaddy.com and https://visa.com.
        // Typically developers will need to get a PEM file from their organization's TLS administrator.

        //565741316repl_1.crt
        val comodoRsaCertificationAuthority = ("-----BEGIN CERTIFICATE-----\n" +
                "MIIGLjCCBRagAwIBAgIRANwzftBvRAvuXCfm3uHlAb4wDQYJKoZIhvcNAQELBQAw\n" +
                "gY8xCzAJBgNVBAYTAkdCMRswGQYDVQQIExJHcmVhdGVyIE1hbmNoZXN0ZXIxEDAO\n" +
                "BgNVBAcTB1NhbGZvcmQxGDAWBgNVBAoTD1NlY3RpZ28gTGltaXRlZDE3MDUGA1UE\n" +
                "AxMuU2VjdGlnbyBSU0EgRG9tYWluIFZhbGlkYXRpb24gU2VjdXJlIFNlcnZlciBD\n" +
                "QTAeFw0yMTEwMDcwMDAwMDBaFw0yMjExMDcyMzU5NTlaMBYxFDASBgNVBAMTC3Np\n" +
                "bWEtYmYubmV0MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA3wiVx+xt\n" +
                "ML4RVwBa2jML2+lYo24i0Uh3oNZJlCjrr3YFavSo4ONVDvt4dKIeoN5KtQjCn1Mi\n" +
                "z9f+qsCW/uTHozmd69sjVUXoZUVVmPPwhi6UzRTUU6f+OBPL5Hc+UDhZC+6sYzdv\n" +
                "x1FxyA/gHRdMwJfYYGJ+zHcq/7y8LmF8J1ft6nIDs1/iTzVjP7L0Xx4WN49id8q9\n" +
                "0KLwPTpaA82aAYdm4XWDL9djiFE64lDLxOXc3D7UDByNzJcEECS6EKdPLCA18j0E\n" +
                "F4HpW6/uLhQiGRq+vPkYInp+hHtLI+gXIZf9UYwKY+nCYMo9YK9Az/dfmZTY0AID\n" +
                "OwsoSR6tvn3wpwIDAQABo4IC+zCCAvcwHwYDVR0jBBgwFoAUjYxexFStiuF36Zv5\n" +
                "mwXhuAGNYeEwHQYDVR0OBBYEFECDoOjqNJZtojv23wIpRj2H+ng2MA4GA1UdDwEB\n" +
                "/wQEAwIFoDAMBgNVHRMBAf8EAjAAMB0GA1UdJQQWMBQGCCsGAQUFBwMBBggrBgEF\n" +
                "BQcDAjBJBgNVHSAEQjBAMDQGCysGAQQBsjEBAgIHMCUwIwYIKwYBBQUHAgEWF2h0\n" +
                "dHBzOi8vc2VjdGlnby5jb20vQ1BTMAgGBmeBDAECATCBhAYIKwYBBQUHAQEEeDB2\n" +
                "ME8GCCsGAQUFBzAChkNodHRwOi8vY3J0LnNlY3RpZ28uY29tL1NlY3RpZ29SU0FE\n" +
                "b21haW5WYWxpZGF0aW9uU2VjdXJlU2VydmVyQ0EuY3J0MCMGCCsGAQUFBzABhhdo\n" +
                "dHRwOi8vb2NzcC5zZWN0aWdvLmNvbTCCAYAGCisGAQQB1nkCBAIEggFwBIIBbAFq\n" +
                "AHcARqVV63X6kSAwtaKJafTzfREsQXS+/Um4havy/HD+bUcAAAF8WuLcEwAABAMA\n" +
                "SDBGAiEA1A1ZOCVt4TG7t2JkJ34uEj6oqlTT6pcWjIM3G5ZazTMCIQCEZUsGpSbM\n" +
                "PFKJnu7PP44RINsQZdH/hMAHvWtZs9JZ7QB3AEHIyrHfIkZKEMahOglCh15OMYsb\n" +
                "A+vrS8do8JBilgb2AAABfFri29QAAAQDAEgwRgIhAOg1mBDDA/VqV0d21g1F36JM\n" +
                "h6+WxuFrBgmBXhS8ipAVAiEAmCDvNEzUE2qi4nslnzmO8pIaP5oxJ6SI0q6EoMnn\n" +
                "D/YAdgApeb7wnjk5IfBWc59jpXflvld9nGAK+PlNXSZcJV3HhAAAAXxa4turAAAE\n" +
                "AwBHMEUCIQCxWUt+cAKCnW/ojMPhlPMO84gXlBljRspA/7ZxRMQ3ZwIgWLg9Vfcp\n" +
                "F+iMIIRSRg/P/wSWO7l8E/kF/hAFZuI9BW0wIgYDVR0RBBswGYILc2ltYS1iZi5u\n" +
                "ZXSCCmRpb3NwYi5uZXQwDQYJKoZIhvcNAQELBQADggEBAKIX0ZW7Ssid2f2FuTUK\n" +
                "CCijn5kbq4J1z+H2YnlcJnSW87VVGHIjoE4WcvkU4dh51imNe/voATQ5g76zTHHa\n" +
                "spl5zYotqp/qrA3sRBY8ozYeb0n2UIoO9HiwQUDNSaePZGA6xJPSVsxj6JFIK6Zf\n" +
                "5AW+1qPx5Wg7gxBIicXpg//DYEggDmDyny/8Bh4EAuD9HfgokDgCjj1MCkqd9I3r\n" +
                "qhMCfF2O3dDjQjdzSsbvHfwVxM/KoDwHXwkwZ9RyNgMIJPv5vVli5IY5ZsCeGa2W\n" +
                "NmCMmX3JPYQMq5zD5sr13ka/7GepUWiEfmGPReF9gh7DgezgJC5c9w+xVlp2VgJz\n" +
                "EII=\n" +
                "-----END CERTIFICATE-----\n")

        //USERTrustRSAAAACA.crt
        val entrustRootCertificateAuthority = ("-----BEGIN CERTIFICATE-----\n" +
                "MIIFgTCCBGmgAwIBAgIQOXJEOvkit1HX02wQ3TE1lTANBgkqhkiG9w0BAQwFADB7\n" +
                "MQswCQYDVQQGEwJHQjEbMBkGA1UECAwSR3JlYXRlciBNYW5jaGVzdGVyMRAwDgYD\n" +
                "VQQHDAdTYWxmb3JkMRowGAYDVQQKDBFDb21vZG8gQ0EgTGltaXRlZDEhMB8GA1UE\n" +
                "AwwYQUFBIENlcnRpZmljYXRlIFNlcnZpY2VzMB4XDTE5MDMxMjAwMDAwMFoXDTI4\n" +
                "MTIzMTIzNTk1OVowgYgxCzAJBgNVBAYTAlVTMRMwEQYDVQQIEwpOZXcgSmVyc2V5\n" +
                "MRQwEgYDVQQHEwtKZXJzZXkgQ2l0eTEeMBwGA1UEChMVVGhlIFVTRVJUUlVTVCBO\n" +
                "ZXR3b3JrMS4wLAYDVQQDEyVVU0VSVHJ1c3QgUlNBIENlcnRpZmljYXRpb24gQXV0\n" +
                "aG9yaXR5MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAgBJlFzYOw9sI\n" +
                "s9CsVw127c0n00ytUINh4qogTQktZAnczomfzD2p7PbPwdzx07HWezcoEStH2jnG\n" +
                "vDoZtF+mvX2do2NCtnbyqTsrkfjib9DsFiCQCT7i6HTJGLSR1GJk23+jBvGIGGqQ\n" +
                "Ijy8/hPwhxR79uQfjtTkUcYRZ0YIUcuGFFQ/vDP+fmyc/xadGL1RjjWmp2bIcmfb\n" +
                "IWax1Jt4A8BQOujM8Ny8nkz+rwWWNR9XWrf/zvk9tyy29lTdyOcSOk2uTIq3XJq0\n" +
                "tyA9yn8iNK5+O2hmAUTnAU5GU5szYPeUvlM3kHND8zLDU+/bqv50TmnHa4xgk97E\n" +
                "xwzf4TKuzJM7UXiVZ4vuPVb+DNBpDxsP8yUmazNt925H+nND5X4OpWaxKXwyhGNV\n" +
                "icQNwZNUMBkTrNN9N6frXTpsNVzbQdcS2qlJC9/YgIoJk2KOtWbPJYjNhLixP6Q5\n" +
                "D9kCnusSTJV882sFqV4Wg8y4Z+LoE53MW4LTTLPtW//e5XOsIzstAL81VXQJSdhJ\n" +
                "WBp/kjbmUZIO8yZ9HE0XvMnsQybQv0FfQKlERPSZ51eHnlAfV1SoPv10Yy+xUGUJ\n" +
                "5lhCLkMaTLTwJUdZ+gQek9QmRkpQgbLevni3/GcV4clXhB4PY9bpYrrWX1Uu6lzG\n" +
                "KAgEJTm4Diup8kyXHAc/DVL17e8vgg8CAwEAAaOB8jCB7zAfBgNVHSMEGDAWgBSg\n" +
                "EQojPpbxB+zirynvgqV/0DCktDAdBgNVHQ4EFgQUU3m/WqorSs9UgOHYm8Cd8rID\n" +
                "ZsswDgYDVR0PAQH/BAQDAgGGMA8GA1UdEwEB/wQFMAMBAf8wEQYDVR0gBAowCDAG\n" +
                "BgRVHSAAMEMGA1UdHwQ8MDowOKA2oDSGMmh0dHA6Ly9jcmwuY29tb2RvY2EuY29t\n" +
                "L0FBQUNlcnRpZmljYXRlU2VydmljZXMuY3JsMDQGCCsGAQUFBwEBBCgwJjAkBggr\n" +
                "BgEFBQcwAYYYaHR0cDovL29jc3AuY29tb2RvY2EuY29tMA0GCSqGSIb3DQEBDAUA\n" +
                "A4IBAQAYh1HcdCE9nIrgJ7cz0C7M7PDmy14R3iJvm3WOnnL+5Nb+qh+cli3vA0p+\n" +
                "rvSNb3I8QzvAP+u431yqqcau8vzY7qN7Q/aGNnwU4M309z/+3ri0ivCRlv79Q2R+\n" +
                "/czSAaF9ffgZGclCKxO/WIu6pKJmBHaIkU4MiRTOok3JMrO66BQavHHxW/BBC5gA\n" +
                "CiIDEOUMsfnNkjcZ7Tvx5Dq2+UUTJnWvu6rvP3t3O9LEApE9GQDTF1w52z97GA1F\n" +
                "zZOFli9d31kWTz9RvdVFGD/tSo7oBmF0Ixa1DVBzJ0RHfxBdiSprhTEUxOipakyA\n" +
                "vGp4z7h/jnZymQyd/teRCBaho1+V\n" +
                "-----END CERTIFICATE-----\n")
        return Buffer()
                .writeUtf8(comodoRsaCertificationAuthority)
                .writeUtf8(entrustRootCertificateAuthority)
                .inputStream()
    }
    @Throws(GeneralSecurityException::class)
    private fun trustManagerForCertificates(`in`: InputStream): X509TrustManager {
        val certificateFactory = CertificateFactory.getInstance("X.509")
        val certificates = certificateFactory.generateCertificates(`in`)
        require(!certificates.isEmpty()) { "expected non-empty set of trusted certificates" }

        // Put the certificates a key store.
        val password = "Babemba*1979".toCharArray() // Any password will work.
        val keyStore = newEmptyKeyStore(password)
        var index = 0
        for (certificate in certificates) {
            val certificateAlias = Integer.toString(index++)
            keyStore.setCertificateEntry(certificateAlias, certificate)
        }

        // Use it to build an X509 trust manager.
        val keyManagerFactory = KeyManagerFactory.getInstance(
                KeyManagerFactory.getDefaultAlgorithm())
        keyManagerFactory.init(keyStore, password)
        val trustManagerFactory = TrustManagerFactory.getInstance(
                TrustManagerFactory.getDefaultAlgorithm())
        trustManagerFactory.init(keyStore)
        val trustManagers = trustManagerFactory.trustManagers
        check(!(trustManagers.size != 1 || trustManagers[0] !is X509TrustManager)) { "Unexpected default trust managers:" + Arrays.toString(trustManagers) }
        return trustManagers[0] as X509TrustManager
    }

    @Throws(GeneralSecurityException::class)
    private fun newEmptyKeyStore(password: CharArray): KeyStore {
        try {
            val keyStore = KeyStore.getInstance(KeyStore.getDefaultType())
            val `in`: InputStream? = null // By convention, 'null' creates an empty key store.
            keyStore.load(`in`, password)
            return keyStore
        } catch (e: IOException) {
            throw AssertionError(e)
        }

    }
}