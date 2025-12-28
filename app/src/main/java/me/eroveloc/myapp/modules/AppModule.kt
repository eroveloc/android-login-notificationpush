package me.eroveloc.myapp.modules

import androidx.room.Room
import me.eroveloc.myapp.data.local.AppDatabase
import me.eroveloc.myapp.data.local.daos.UserDao
import me.eroveloc.myapp.data.repositories.NotificationRepository
import me.eroveloc.myapp.data.repositories.UserRepository
import me.eroveloc.myapp.data.services.IUserService
import me.eroveloc.myapp.domain.repositories.INotificationRepository
import me.eroveloc.myapp.domain.repositories.IUserRepository
import me.eroveloc.myapp.ui.login.viewmodels.HomeViewModel
import me.eroveloc.myapp.ui.login.viewmodels.LoginViewModel
import me.eroveloc.myapp.ui.login.viewmodels.RegisterViewModel
import me.eroveloc.myapp.ui.login.viewmodels.SplashViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

val AppModule = module {

    single {
        provideRetrofit()
    }

    single {
        get<Retrofit>().create(IUserService::class.java)

    }

    single<AppDatabase> {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "myapp_database_name"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    single<UserDao> {
        get<AppDatabase>().userDao()
    }

    single<IUserRepository> {
        UserRepository(get())
    }

    single<INotificationRepository> {
        NotificationRepository(androidContext())
    }

    viewModel {
        SplashViewModel(get())
    }

    viewModel {
        LoginViewModel(get(), get())
    }

    viewModel {
        RegisterViewModel(get(), get())
    }

    viewModel {
        HomeViewModel(get())
    }
}

fun provideRetrofit(): Retrofit {

    // Crear el Interceptor
    val loggingInterceptor = HttpLoggingInterceptor().apply {
        // 'BODY' es el nivel más detallado: muestra headers, url y el JSON de respuesta
        level = HttpLoggingInterceptor.Level.BODY
    }

    // Crear el OkHttpClient y agregar el interceptor
    val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS) // Opcional: Configurar timeouts
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    return Retrofit.Builder()
        .baseUrl("https://examwebapi20251226003419-dpfpbrbkc8e9ftcc.mexicocentral-01.azurewebsites.net/api/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}