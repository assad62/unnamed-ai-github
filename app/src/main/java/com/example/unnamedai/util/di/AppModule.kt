package com.example.unnamedai.util.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.unnamedai.data.local.DatabaseRepositoryImp
import com.example.unnamedai.data.local.UnnamedAiDatabase
import com.example.unnamedai.data.remote.ApiRepositoryImp
import com.example.unnamedai.data.remote.UnnamedAiApi
import com.example.unnamedai.domain.repository.ApiRepository
import com.example.unnamedai.domain.repository.DatabaseRepository
import com.example.unnamedai.domain.use_case.UseCases
import com.example.unnamedai.domain.use_case.local.DeleteConversation
import com.example.unnamedai.domain.use_case.local.GetAllConversation
import com.example.unnamedai.domain.use_case.local.SaveConversation
import com.example.unnamedai.domain.use_case.remote.AskChatGBT
import com.example.unnamedai.util.BASE_URL
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
    fun provideDatabaseRepository(dp: UnnamedAiDatabase): DatabaseRepository {
        return DatabaseRepositoryImp(dp.unnamedAiDao)
    }

    @Provides
    @Singleton
    fun provideApiRepository(api: UnnamedAiApi): ApiRepository {
        return ApiRepositoryImp(api)
    }

    @Provides
    @Singleton
    fun provideBookDatabase(app: Application): UnnamedAiDatabase {
        return Room.databaseBuilder(
            app,
            UnnamedAiDatabase::class.java,
            UnnamedAiDatabase.Database_Name
        ).build()
    }

    @Provides
    @Singleton
    fun provideUnnamedAiApi(): UnnamedAiApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UnnamedAiApi::class.java)
    }

    @Provides
    @Singleton
    fun provideUseCases(
        databaseRepository: DatabaseRepository,
        apiRepository: ApiRepository,
        @ApplicationContext context: Context
    ) = UseCases(
        getAllConversation = GetAllConversation(databaseRepository),
        saveConversation = SaveConversation(databaseRepository),
        deleteConversation = DeleteConversation(databaseRepository),
        askChatGBT = AskChatGBT(apiRepository),
    )
}