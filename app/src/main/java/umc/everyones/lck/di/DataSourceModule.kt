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

@Module
@InstallIn(ViewModelComponent::class)
object DataSourceModule {
    @Provides
    @ViewModelScoped
    fun provideTodayMatchDataSource(todayMatchDataSourceImpl: TodayMatchDataSourceImpl): TodayMatchDataSource =
        todayMatchDataSourceImpl

    fun provideHomeDataSource(homeDataSourceImpl: HomeDataSourceImpl): HomeDataSource =
        homeDataSourceImpl
}