package my.project.dwh.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import my.project.dwh.domain.exception.DwhNotFoundException;
import my.project.dwh.domain.model.Dwh;
import my.project.dwh.domain.model.DwhInfo;
import my.project.dwh.domain.port.RequestDwh;
import my.project.dwh.rest.exception.DwhExceptionResponse;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = DwhRestAdapterApplication.class, webEnvironment = RANDOM_PORT)
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
public class DwhResourceTest {

  private static final String LOCALHOST = "http://localhost:";
  private static final String API_URI = "/api/v1/dwhs";
  @LocalServerPort
  private int port;
  @Autowired
  private TestRestTemplate restTemplate;
  @Autowired
  private RequestDwh requestDwh;

  @Test
  @DisplayName("should start the rest adapter application")
  public void startup() {
    assertThat(Boolean.TRUE).isTrue();
  }

  @Test
  @DisplayName("should give dwhs when asked for dwhs with the support of domain stub")
  public void obtainDwhsFromDomainStub() {
    // Given
    Dwh dwh = Dwh.builder().code(1L).description("Johnny Johnny Yes Papa !!").build();
    Mockito.lenient().when(requestDwh.getDwhs())
        .thenReturn(DwhInfo.builder().dwhs(List.of(dwh)).build());
    // When
    String url = LOCALHOST + port + API_URI;
    ResponseEntity<DwhInfo> responseEntity = restTemplate.getForEntity(url, DwhInfo.class);
    // Then
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(responseEntity.getBody()).isNotNull();
    assertThat(responseEntity.getBody().getDwhs()).isNotEmpty().extracting("description")
        .contains("Johnny Johnny Yes Papa !!");
  }

  @Test
  @DisplayName("should give the dwh when asked for an dwh by code with the support of domain stub")
  public void obtainDwhByCodeFromDomainStub() {
    // Given
    Long code = 1L;
    String description = "Johnny Johnny Yes Papa !!";
    Dwh dwh = Dwh.builder().code(code).description(description).build();
    Mockito.lenient().when(requestDwh.getDwhByCode(code)).thenReturn(dwh);
    // When
    String url = LOCALHOST + port + API_URI + "/" + code;
    ResponseEntity<Dwh> responseEntity = restTemplate.getForEntity(url, Dwh.class);
    // Then
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(responseEntity.getBody()).isNotNull();
    assertThat(responseEntity.getBody()).isEqualTo(dwh);
  }

  @Test
  @DisplayName("should give exception when asked for an dwh by code that does not exists with the support of domain stub")
  public void shouldGiveExceptionWhenAskedForAnDwhByCodeFromDomainStub() {
    // Given
    Long code = -1000L;
    Mockito.lenient().when(requestDwh.getDwhByCode(code)).thenThrow(new
        DwhNotFoundException(code));
    // When
    String url = LOCALHOST + port + API_URI + "/" + code;
    ResponseEntity<DwhExceptionResponse> responseEntity = restTemplate
        .getForEntity(url, DwhExceptionResponse.class);
    // Then
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    assertThat(responseEntity.getBody()).isNotNull();
    assertThat(responseEntity.getBody()).isEqualTo(DwhExceptionResponse.builder()
        .message("Dwh with code " + code + " does not exist").path(API_URI + "/" + code)
        .build());
  }
}
