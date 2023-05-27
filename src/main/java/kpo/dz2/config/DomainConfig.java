package kpo.dz2.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EntityScan("kpo.dz2.domain")
@EnableJpaRepositories("kpo.dz2.repos")
@EnableTransactionManagement
public class DomainConfig {
}
