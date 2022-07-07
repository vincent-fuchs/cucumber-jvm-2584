package my.project.dwh.boot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import my.project.dwh.domain.DwhDomain;
import my.project.dwh.domain.port.ObtainDwh;
import my.project.dwh.domain.port.RequestDwh;
import my.project.dwh.repository.config.JpaAdapterConfig;

@Configuration
@Import(JpaAdapterConfig.class)
public class BootstrapConfig {

  @Bean
  public RequestDwh getRequestDwh(ObtainDwh obtainDwh) {
    return new DwhDomain(obtainDwh);
  }
}
