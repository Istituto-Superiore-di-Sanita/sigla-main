package it.cnr.contab.spring.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("liquibase")
@Configuration
public class LiquibaseConfiguration extends AbstractLiquibaseConfiguration {

    @Override
    protected String getDbChangelogMaster() {
        return "db-changelog-master.xml";
    }
}
