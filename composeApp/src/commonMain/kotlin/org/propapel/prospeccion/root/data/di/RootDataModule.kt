package org.propapel.prospeccion.root.data.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.propapel.prospeccion.root.data.repository.CustomerRepositoryImpl
import org.propapel.prospeccion.root.data.repository.InteractionRepositoryImpl
import org.propapel.prospeccion.root.data.repository.ProfileRepositoryImpl
import org.propapel.prospeccion.root.data.repository.ReminderRepositoryImpl
import org.propapel.prospeccion.root.domain.repository.CustomerRepository
import org.propapel.prospeccion.root.domain.repository.InteractionRepository
import org.propapel.prospeccion.root.domain.repository.ProfileRepository
import org.propapel.prospeccion.root.domain.repository.ReminderRepository

val rootDataModule  = module {
    singleOf(::ProfileRepositoryImpl).bind<ProfileRepository>()
    singleOf(::ReminderRepositoryImpl).bind<ReminderRepository>()
    singleOf(::CustomerRepositoryImpl).bind<CustomerRepository>()
    singleOf(::InteractionRepositoryImpl).bind<InteractionRepository>()
}