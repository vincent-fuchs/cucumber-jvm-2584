package my.project.dwh.repository.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import my.project.dwh.domain.port.ObtainDwh;
import my.project.dwh.repository.DwhRepository;
import my.project.dwh.repository.dao.DwhDao;

@Configuration
@EntityScan("my.project.dwh.repository.entity")
@EnableJpaRepositories("my.project.dwh.repository.dao")
public class JpaAdapterConfig {

  @Bean
  public ObtainDwh getDwhRepository(DwhDao dwhDao) {
    return new DwhRepository(dwhDao);
  }
}
