package my.project.dwh.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import my.project.dwh.domain.port.RequestDwh;

@SpringBootApplication
@ComponentScan(basePackages = "my.project.dwh")
public class DwhRestAdapterApplication {

  @MockBean
  private RequestDwh requestDwh;

  public static void main(String[] args) {
    SpringApplication.run(DwhRestAdapterApplication.class, args);
  }
}
