package umc.everyones.lck.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import umc.everyones.lck.data.datasource.ViewingPartyDataSource
import umc.everyones.lck.data.datasourceImpl.ViewingPartyDataSourceImpl
import umc.everyones.lck.data.datasourceImpl.ViewingPartyListPagingSource
import umc.everyones.lck.data.service.party.ViewingPartyService
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
object DataSourceModule {
    @Provides
    @ViewModelScoped
    fun provideViewingPartyDataSource(viewingPartyDataSourceImpl: ViewingPartyDataSourceImpl): ViewingPartyDataSource =
        viewingPartyDataSourceImpl
}