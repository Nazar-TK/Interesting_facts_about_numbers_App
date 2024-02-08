package com.example.interestinginfoaboutnumbers.di

import android.app.Application
import androidx.room.Room
import com.example.interestinginfoaboutnumbers.core.Constants
import com.example.interestinginfoaboutnumbers.data.local.NumberDatabase
import com.example.interestinginfoaboutnumbers.data.remote.NumbersApi
import com.example.interestinginfoaboutnumbers.data.repository.NumberRepositoryImpl
import com.example.interestinginfoaboutnumbers.domain.repository.NumberRepository
import com.example.interestinginfoaboutnumbers.domain.use_case.AddNumberUseCase
import com.example.interestinginfoaboutnumbers.domain.use_case.DeleteNotesUseCase
import com.example.interestinginfoaboutnumbers.domain.use_case.GetAllNumbersUseCase
import com.example.interestinginfoaboutnumbers.domain.use_case.GetNumberByIdUseCase
import com.example.interestinginfoaboutnumbers.domain.use_case.GetNumberUseCase
import com.example.interestinginfoaboutnumbers.domain.use_case.GetRandomNumberUseCase
import com.example.interestinginfoaboutnumbers.domain.use_case.NumberUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NumberModule {

    @Provides
    @Singleton
    fun provideNumbersApi(): NumbersApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
            .create(NumbersApi::class.java)
    }


    @Provides
    @Singleton
    fun provideNumberDatabase(app: Application): NumberDatabase {
        return Room.databaseBuilder(
            app,
            NumberDatabase::class.java,
            NumberDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideNumberRepository(api: NumbersApi, db: NumberDatabase): NumberRepository {
        return NumberRepositoryImpl(api, db.dao)
    }

    @Provides
    @Singleton
    fun provideGetNumberBuIdUseCase(repository: NumberRepository): GetNumberByIdUseCase {
        return GetNumberByIdUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(repository: NumberRepository): NumberUseCases {
        return NumberUseCases(
            addNumberUseCase = AddNumberUseCase(repository),
            getNumberUseCase = GetNumberUseCase(repository),
            getRandomNumberUseCase = GetRandomNumberUseCase(repository),
            getAllNumbersUseCase = GetAllNumbersUseCase(repository),
            deleteNotesUseCase = DeleteNotesUseCase(repository)
        )
    }
}