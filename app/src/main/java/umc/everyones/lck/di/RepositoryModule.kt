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
import umc.everyones.lck.data.repositoryImpl.community.CommunityRepositoryImpl
import umc.everyones.lck.data.repositoryImpl.party.ViewingPartyRepositoryImpl
import umc.everyones.lck.data.service.NaverService
import umc.everyones.lck.data.service.TestService
import umc.everyones.lck.data.service.party.ViewingPartyService
import umc.everyones.lck.domain.repository.TestRepository
import umc.everyones.lck.domain.repository.home.HomeRepository
import umc.everyones.lck.domain.repository.match.TodayMatchRepository
import umc.everyones.lck.domain.repository.community.CommunityRepository
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
    fun providesTodayMatchRepository(
        todayMatchRepositoryImpl: TodayMatchRepositoryImpl
    ): TodayMatchRepository = todayMatchRepositoryImpl

    @ViewModelScoped
    @Provides
    fun providesHomeRepository(
        homeRepositoryImpl: HomeRepositoryImpl
    ): HomeRepository = homeRepositoryImpl

    @Provides
    @ViewModelScoped
    fun providesNaverRepository(
        naverRepositoryImpl: NaverRepositoryImpl
    ): NaverRepository = naverRepositoryImpl

    @ViewModelScoped
    @Provides
    fun providesViewingPartyRepository(
        viewingPartyRepositoryImpl: ViewingPartyRepositoryImpl
    ): ViewingPartyRepository = viewingPartyRepositoryImpl

    @ViewModelScoped
    @Provides
    fun providesCommunityRepository(
        communityRepositoryImpl: CommunityRepositoryImpl
    ): CommunityRepository = communityRepositoryImpl
}
