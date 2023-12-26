package cvb.com.br.composenote.di

import android.content.Context
import androidx.room.Room
import cvb.com.br.composenote.core.data.db.AppDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModuleProvide {

    @Provides
    @Singleton
    fun provideAppDataBase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            AppDataBase::class.java,
            AppDataBase.DATA_BASE_NAME
        ).build()

    @Provides
    @Singleton
    fun provideNoteDao(appDataBase: AppDataBase) =
        appDataBase.noteDao
}