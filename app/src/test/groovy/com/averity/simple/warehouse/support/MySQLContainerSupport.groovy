package com.averity.simple.warehouse.support

import org.testcontainers.containers.MySQLContainer
import org.testcontainers.ext.ScriptUtils
import org.testcontainers.jdbc.JdbcDatabaseDelegate
import org.testcontainers.utility.DockerImageName

class MySQLContainerSupport {

    static final DockerImageName MYSQL_IMAGE = DockerImageName.parse('mysql:5.7.34')

    static final String DATASOURCE_TEST_USERNAME = 'root'
    static final String DATASOURCE_TEST_PASSWORD = ''
    static final String DATASOURCE_SCHEMA = 'sw'

    private final MySQLContainer mysql = new MySQLContainer<>(MYSQL_IMAGE)
            .withDatabaseName(DATASOURCE_SCHEMA)
            .withUsername(DATASOURCE_TEST_USERNAME)
            .withPassword(DATASOURCE_TEST_PASSWORD)

    def startContainer() {
        mysql.start()

        def delegate = new JdbcDatabaseDelegate(mysql, '')
        ScriptUtils.runInitScript(delegate, 'schema/warehouse-schema.sql')
        ScriptUtils.runInitScript(delegate, 'schema/warehouse-dataset.sql')

        System.setProperty('DATASOURCE_URL', mysql.jdbcUrl)
        System.setProperty('DATASOURCE_USERNAME', DATASOURCE_TEST_USERNAME)
        System.setProperty('DATASOURCE_PASSWORD', DATASOURCE_TEST_PASSWORD)
    }

    def stopContainer() {
        mysql.stop()
    }
}
