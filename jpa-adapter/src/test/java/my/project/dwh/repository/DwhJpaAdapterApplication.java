package my.project.dwh.repository;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import my.project.dwh.domain.port.ObtainDwh;
import my.project.dwh.repository.dao.DwhDao;

@SpringBootApplication
public class DwhJpaAdapterApplication {

  public static void main(String[] args) {
    SpringApplication.run(DwhJpaAdapterApplication.class, args);
  }

  @TestConfiguration
  static class DwhJpaTestConfig {

    @Bean
    public ObtainDwh getObtainDwhRepository(DwhDao dwhDao) {
      return new DwhRepository(dwhDao);
    }
  }
}
