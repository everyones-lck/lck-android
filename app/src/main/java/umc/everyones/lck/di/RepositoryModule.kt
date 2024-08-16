package umc.everyones.lck.di

import android.app.Application
import android.content.Context
import umc.everyones.lck.domain.repository.NaverRepository
import umc.everyones.lck.data.repositoryImpl.NaverRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.scopes.ViewModelScoped
import umc.everyones.lck.data.repositoryImpl.TestRepositoryImpl
import umc.everyones.lck.data.repositoryImpl.about_lck.AboutLckRepositoryImpl
import umc.everyones.lck.data.repositoryImpl.party.ViewingPartyRepositoryImpl
import umc.everyones.lck.data.service.NaverService
import umc.everyones.lck.data.service.TestService
import umc.everyones.lck.data.service.party.ViewingPartyService
import umc.everyones.lck.domain.repository.TestRepository
import umc.everyones.lck.domain.repository.about_lck.AboutLckRepository
import umc.everyones.lck.domain.repository.party.ViewingPartyRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationContextModule {
    @Provides
    @Singleton
    fun provideApplicationContext(application: Application): Context = application
}

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @ViewModelScoped
    @Provides
    fun providesTestRepository(
        testService: TestService
    ): TestRepository = TestRepositoryImpl(testService)

    @ViewModelScoped
    @Provides
    fun providesNaverRepository(naverService: NaverService): NaverRepository =
        NaverRepositoryImpl(naverService)

    @ViewModelScoped
    @Provides
    fun providesViewingPartyRepository(
        viewingPartyRepositoryImpl: ViewingPartyRepositoryImpl
    ): ViewingPartyRepository = viewingPartyRepositoryImpl

    @ViewModelScoped
    @Provides
    fun providesAboutLckRepository(
        aboutLckRepositoryImpl: AboutLckRepositoryImpl
    ): AboutLckRepository = aboutLckRepositoryImpl
}
