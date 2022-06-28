package com.tmdb.network.di

import com.tmdb.network.services.GenreService
import com.tmdb.network.services.movies.MovieDetailsService
import com.tmdb.network.services.movies.MoviePaginatedService
import com.tmdb.network.utils.RequestInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {
    private const val BASE_URL = "https://api.themoviedb.org/3/"

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(RequestInterceptor())
            .build()
    }


    @Provides
    @Singleton
    fun provideRetrofitClient(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideMovieDetailsService(client: Retrofit): MovieDetailsService =
        client.create(MovieDetailsService::class.java)

    @Provides
    @Singleton
    fun provideMoviePaginatedService(client: Retrofit): MoviePaginatedService =
        client.create(MoviePaginatedService::class.java)

    @Provides
    @Singleton
    fun provideGenreService(client: Retrofit): GenreService =
        client.create(GenreService::class.java)
}
