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
import umc.everyones.lck.data.datasource.login.LoginDataSource
import umc.everyones.lck.data.datasourceImpl.LoginDataSourceImpl
import umc.everyones.lck.data.repositoryImpl.TestRepositoryImpl
import umc.everyones.lck.data.repositoryImpl.login.LoginRepositoryImpl
import umc.everyones.lck.data.repositoryImpl.party.ViewingPartyRepositoryImpl
import umc.everyones.lck.data.service.LoginService
import umc.everyones.lck.data.service.NaverService
import umc.everyones.lck.data.service.TestService
import umc.everyones.lck.data.service.party.ViewingPartyService
import umc.everyones.lck.domain.repository.TestRepository
import umc.everyones.lck.domain.repository.login.LoginRepository
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
    fun providesLoginDataSource(loginService: LoginService): LoginDataSource =
        LoginDataSourceImpl(loginService)

    @ViewModelScoped
    @Provides
    fun providesLoginRepository(loginDataSource: LoginDataSource): LoginRepository =
        LoginRepositoryImpl(loginDataSource) // LoginDataSource를 주입
    @ViewModelScoped
    @Provides
    fun providesViewingPartyRepository(
        viewingPartyRepositoryImpl: ViewingPartyRepositoryImpl
    ): ViewingPartyRepository = viewingPartyRepositoryImpl
}
