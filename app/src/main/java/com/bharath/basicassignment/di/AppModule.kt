package com.bharath.basicassignment.di


import android.app.Application
import androidx.annotation.OptIn
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.room.Room
import com.bharath.basicassignment.BuildConfig
import com.bharath.basicassignment.data.LocalDatabase
import com.bharath.basicassignment.data.repo.LocalRepoImpl
import com.bharath.basicassignment.data.repo.RemoteRepoImpl
import com.bharath.basicassignment.domain.repo.local.LocalRepository
import com.bharath.basicassignment.domain.repo.remote.RemoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.storage.Storage
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {


    @Provides
    @Singleton
    fun provideInstallSupabase(): SupabaseClient {
        return createSupabaseClient(
            supabaseUrl = BuildConfig.SUPABASE_PROJECT_URL,
            supabaseKey = BuildConfig.SUPABASE_KEY,
            builder = {
                install(Postgrest)
                install(Storage)
            }

        )
    }


    @Provides
    @Singleton
    fun provideRemoteRepo(supabaseClient: SupabaseClient): RemoteRepository {
        return RemoteRepoImpl(supabaseClient)
    }




    @Provides
    @Singleton
    fun provideRoomDatabase(app: Application): LocalDatabase {
        return Room.databaseBuilder(
            app,
            LocalDatabase::class.java,
            "LocalDb"
        ).build()
    }


    @Provides
    @Singleton
    fun provideGetLocalRepo(database: LocalDatabase): LocalRepository {
        return LocalRepoImpl(
            database.dao
        )
    }
}