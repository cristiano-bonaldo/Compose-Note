package cvb.com.br.composenote.di

import cvb.com.br.composenote.feature_note.data.data_source.local.NoteLocalDS
import cvb.com.br.composenote.feature_note.data.repository.NoteRepositoryImpl
import cvb.com.br.composenote.feature_note.domain.data_source.NoteDS
import cvb.com.br.composenote.feature_note.domain.repository.NoteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModuleBind {
    @Binds
    @Singleton
    abstract fun bindNoteDataSource(noteDS: NoteLocalDS): NoteDS

    @Binds
    @Singleton
    abstract fun bindNoteRepository(noteRepository: NoteRepositoryImpl): NoteRepository
}