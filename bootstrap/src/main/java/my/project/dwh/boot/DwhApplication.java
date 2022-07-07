package my.project.dwh.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "my.project.dwh")
public class DwhApplication {

  public static void main(String[] args) {
    SpringApplication.run(DwhApplication.class, args);
  }
}
