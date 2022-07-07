package my.project.dwh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import my.project.dwh.domain.DwhDomain;
import my.project.dwh.domain.port.ObtainDwh;
import my.project.dwh.domain.port.RequestDwh;
import my.project.dwh.repository.config.JpaAdapterConfig;

@SpringBootApplication
public class DwhE2EApplication {

  public static void main(String[] args) {
    SpringApplication.run(DwhE2EApplication.class);
  }

  @TestConfiguration
  @Import(JpaAdapterConfig.class)
  static class DwhConfig {

    @Bean
    public RequestDwh getRequestDwh(final ObtainDwh obtainDwh) {
      return new DwhDomain(obtainDwh);
    }
  }
}
