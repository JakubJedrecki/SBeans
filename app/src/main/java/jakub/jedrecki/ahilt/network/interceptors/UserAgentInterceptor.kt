package jakub.jedrecki.ahilt.network.interceptors

import android.os.Build
import jakub.jedrecki.ahilt.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserAgentInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        val newUserAgent = String.format(
            Locale.UK,
            "%s %s(%s) (Android %s; Model: %s; Brand: %s; Device: %s; %s)",
            "SBeans",
            BuildConfig.VERSION_NAME,
            BuildConfig.VERSION_CODE,
            Build.VERSION.RELEASE,
            Build.BRAND,
            Build.MODEL,
            Build.DEVICE,
            Locale.getDefault().language
        )

        val builder = original.newBuilder()
            .header("User-Agent", newUserAgent)
            .method(original.method, original.body)

        val newRequest = builder.build()

        return chain.proceed(newRequest)
    }
}