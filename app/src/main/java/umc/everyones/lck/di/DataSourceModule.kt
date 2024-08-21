package umc.everyones.lck.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import umc.everyones.lck.data.datasource.HomeDataSource
import umc.everyones.lck.data.datasource.TodayMatchDataSource
import umc.everyones.lck.data.datasourceImpl.HomeDataSourceImpl
import umc.everyones.lck.data.datasourceImpl.TodayMatchDataSourceImpl
import umc.everyones.lck.data.datasource.NaverDataSource
import umc.everyones.lck.data.datasource.ViewingPartyDataSource
import umc.everyones.lck.data.datasource.community.CommunityDataSource
import umc.everyones.lck.data.datasourceImpl.community.CommunityDataSourceImpl
import umc.everyones.lck.data.datasourceImpl.naver.NaverDataSourceImpl
import umc.everyones.lck.data.datasourceImpl.party.ViewingPartyDataSourceImpl

@Module
@InstallIn(ViewModelComponent::class)
object DataSourceModule {
    @Provides
    @ViewModelScoped
    fun provideTodayMatchDataSource(todayMatchDataSourceImpl: TodayMatchDataSourceImpl): TodayMatchDataSource =
        todayMatchDataSourceImpl
    @Provides
    @ViewModelScoped
    fun provideHomeDataSource(homeDataSourceImpl: HomeDataSourceImpl): HomeDataSource =
        homeDataSourceImpl

    @Provides
    @ViewModelScoped
    fun provideViewingPartyDataSource(viewingPartyDataSourceImpl: ViewingPartyDataSourceImpl): ViewingPartyDataSource =
        viewingPartyDataSourceImpl

    @Provides
    @ViewModelScoped
    fun provideNaverDataSource(naverDataSourceImpl: NaverDataSourceImpl): NaverDataSource =
        naverDataSourceImpl

    @Provides
    @ViewModelScoped
    fun provideCommunityDataSource(communityDataSourceImpl: CommunityDataSourceImpl): CommunityDataSource =
        communityDataSourceImpl
}