package com.tushar.tddandroid.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tushar.tddandroid.data.local.ShoppingDao
import com.tushar.tddandroid.data.local.ShoppingItemDatabase
import com.tushar.tddandroid.data.remote.PixabayAPI
import com.tushar.tddandroid.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideShoppingItemDatabase(@ApplicationContext context: Context) : RoomDatabase{
        return Room.databaseBuilder(
            context,
            ShoppingItemDatabase::class.java,
            Constants.DATABASE_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun provideShoppingDao(database: ShoppingItemDatabase) : ShoppingDao{
        return database.shoppingDao()
    }

    @Singleton
    @Provides
    fun provideShoppingDao() : PixabayAPI{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.BASE_URL)
            .build()
            .create(PixabayAPI::class.java)
    }

}