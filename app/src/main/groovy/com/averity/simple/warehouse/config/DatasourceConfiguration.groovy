package com.averity.simple.warehouse.config

import org.jooq.conf.RenderQuotedNames
import org.jooq.conf.Settings
import org.jooq.impl.DefaultConfiguration
import org.springframework.boot.autoconfigure.jooq.DefaultConfigurationCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.annotation.EnableTransactionManagement

@Configuration
@EnableTransactionManagement
class DatasourceConfiguration {

    @Bean
    Settings jooqSettings() {
        new Settings(renderQuotedNames: RenderQuotedNames.NEVER)
    }

    @Bean
    DefaultConfigurationCustomizer jooqConfigurationCustomizer(Settings settings) {
        { DefaultConfiguration configuration -> configuration.set(settings) }
    }
}
