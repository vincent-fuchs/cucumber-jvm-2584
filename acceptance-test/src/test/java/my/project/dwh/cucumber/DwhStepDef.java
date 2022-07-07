package my.project.dwh.cucumber;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import my.project.dwh.DwhE2EApplication;
import my.project.dwh.domain.model.Dwh;
import my.project.dwh.domain.model.DwhInfo;
import my.project.dwh.repository.dao.DwhDao;
import my.project.dwh.repository.entity.DwhEntity;
import my.project.dwh.rest.exception.DwhExceptionResponse;
import io.cucumber.datatable.DataTable;
import io.cucumber.java8.En;
import io.cucumber.java8.HookNoArgsBody;
import io.cucumber.spring.CucumberContextConfiguration;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;


@SpringBootTest(classes = DwhE2EApplication.class, webEnvironment = RANDOM_PORT)
@CucumberContextConfiguration
@ActiveProfiles("test")
public class DwhStepDef implements En {

  private static final String LOCALHOST = "http://localhost:";
  private static final String API_URI = "/api/v1/dwhs";
  @LocalServerPort
  private int port;
  private ResponseEntity responseEntity;

  public DwhStepDef(TestRestTemplate restTemplate, DwhDao dwhDao) {

    DataTableType(
        (Map<String, String> row) -> Dwh.builder().code(Long.parseLong(row.get("code")))
            .description(row.get("description")).build());
    DataTableType(
        (Map<String, String> row) -> DwhEntity.builder().code(Long.parseLong(row.get("code")))
            .description(row.get("description"))
            .build());

    Before((HookNoArgsBody) dwhDao::deleteAll);
    After((HookNoArgsBody) dwhDao::deleteAll);

    Given("the following dwhs exists in the library", (DataTable dataTable) -> {
      List<DwhEntity> poems = dataTable.asList(DwhEntity.class);
      dwhDao.saveAll(poems);
    });

    When("user requests for all dwhs", () -> {
      String url = LOCALHOST + port + API_URI;
      responseEntity = restTemplate.getForEntity(url, DwhInfo.class);
    });

    When("user requests for dwhs by code {string}", (String code) -> {
      String url = LOCALHOST + port + API_URI + "/" + code;
      responseEntity = restTemplate.getForEntity(url, Dwh.class);
    });

    When("user requests for dwhs by id {string} that does not exists", (String code) -> {
      String url = LOCALHOST + port + API_URI + "/" + code;
      responseEntity = restTemplate.getForEntity(url, DwhExceptionResponse.class);
    });

    Then("the user gets an exception {string}", (String exception) -> {
      assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
      Object body = responseEntity.getBody();
      assertThat(body).isNotNull();
      assertThat(body).isInstanceOf(DwhExceptionResponse.class);
      assertThat(((DwhExceptionResponse) body).getMessage()).isEqualTo(exception);
    });

    Then("the user gets the following dwhs", (DataTable dataTable) -> {
      List<Dwh> expectedDwhs = dataTable.asList(Dwh.class);
      assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
      Object body = responseEntity.getBody();
      assertThat(body).isNotNull();
      if (body instanceof DwhInfo) {
        assertThat(((DwhInfo) body).getDwhs()).isNotEmpty().extracting("description")
            .containsAll(expectedDwhs.stream().map(Dwh::getDescription)
                .collect(Collectors.toList()));
      } else if (body instanceof Dwh) {
        assertThat(body).isNotNull().extracting("description")
            .isEqualTo(expectedDwhs.get(0).getDescription());
      }
    });
  }
}


