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
import umc.everyones.lck.data.repositoryImpl.HomeRepositoryImpl
import umc.everyones.lck.data.repositoryImpl.TestRepositoryImpl
import umc.everyones.lck.data.repositoryImpl.TodayMatchRepositoryImpl
import umc.everyones.lck.data.service.NaverService
import umc.everyones.lck.data.service.TestService
import umc.everyones.lck.domain.repository.TestRepository
import umc.everyones.lck.domain.repository.home.HomeRepository
import umc.everyones.lck.domain.repository.match.TodayMatchRepository
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
    fun providesTodayMatchRepository(
        todayMatchRepositoryImpl: TodayMatchRepositoryImpl
    ): TodayMatchRepository = todayMatchRepositoryImpl

    @ViewModelScoped
    @Provides
    fun providesHomeRepository(
        homeRepositoryImpl: HomeRepositoryImpl
    ): HomeRepository = homeRepositoryImpl
}
