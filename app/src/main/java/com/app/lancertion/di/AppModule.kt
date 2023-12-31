package com.app.lancertion.di

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.room.Room
import com.app.lancertion.common.Constants
import com.app.lancertion.common.dataStore
import com.app.lancertion.data.data_source.DiagnoseDatabase
import com.app.lancertion.data.remote.LancertionAuthApi
import com.app.lancertion.data.remote.LancertionDiagnoseApi
import com.app.lancertion.data.repository.LancertionAuthRepositoryImpl
import com.app.lancertion.data.repository.LancertionCommunityRepositoryImpl
import com.app.lancertion.data.repository.LancertionDiagnoseRepositoryImpl
import com.app.lancertion.domain.repository.LancertionAuthRepository
import com.app.lancertion.domain.repository.LancertionCommunityRepository
import com.app.lancertion.domain.repository.LancertionDiagnoseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLancertionDiagnoseApi(): LancertionDiagnoseApi {
        return Retrofit.Builder()
            .baseUrl(Constants.DIAGNOSE_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LancertionDiagnoseApi::class.java)
    }

    @Provides
    @Singleton
    fun provideLancertionAuthApi(): LancertionAuthApi {
        return Retrofit.Builder()
            .baseUrl(Constants.AUTH_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LancertionAuthApi::class.java)
    }

    @Provides
    @Singleton
    fun provideLancertionDiagnoseRepository(api: LancertionDiagnoseApi, db: DiagnoseDatabase): LancertionDiagnoseRepository {
        return LancertionDiagnoseRepositoryImpl(api, db.DiagnoseDao)
    }

    @Provides
    @Singleton
    fun provideLancertionAuthRepository(
        api: LancertionAuthApi,
        dataStore: DataStore<Preferences>
    ): LancertionAuthRepository {
        return LancertionAuthRepositoryImpl(api, dataStore)
    }

    @Provides
    @Singleton
    fun provideLancertionCommunityRepository(
        api: LancertionAuthApi
    ): LancertionCommunityRepository {
        return LancertionCommunityRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideDataStorePreferences(@ApplicationContext appContext: Context): DataStore<Preferences> {
        return appContext.dataStore
    }

    @Provides
    @Singleton
    fun provideDiagnoseDatabase(app: Application): DiagnoseDatabase {
        return Room.databaseBuilder(
            app,
            DiagnoseDatabase::class.java,
            DiagnoseDatabase.DATABASE_NAME
        ).build()
    }
}