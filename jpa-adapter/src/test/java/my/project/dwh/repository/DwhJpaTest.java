package my.project.dwh.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import my.project.dwh.domain.model.Dwh;
import my.project.dwh.domain.port.ObtainDwh;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class DwhJpaTest {

  @Autowired
  private ObtainDwh obtainDwh;

  @Test
  @DisplayName("should start the application")
  public void startup() {
    assertThat(Boolean.TRUE).isTrue();
  }

  @Test
  @DisplayName("given dwhs exist in database when asked should return all dwhs from database")
  public void shouldGiveMeDwhsWhenAskedGivenDwhExistsInDatabase() {
    // Given from /liquibase/changes/changelog-00.00.01.02.json
    // When
    List<Dwh> dwhs = obtainDwh.getAllDwhs();
    // Then
    assertThat(dwhs).isNotNull().extracting("description").contains("Twinkle twinkle little star");
  }

  @Test
  @DisplayName("given dwhs exists in database when asked for dwh by id should return the dwh")
  public void shouldGiveTheDwhWhenAskedByIdGivenThatDwhByThatIdExistsInDatabase() {
    // Given from /liquibase/changes/changelog-00.00.01.02.json
    // When
    Optional<Dwh> dwh = obtainDwh.getDwhByCode(1L);
    // Then
    assertThat(dwh).isNotNull().isNotEmpty().get().isEqualTo(Dwh.builder().code(1L).description("Twinkle twinkle little star").build());
  }

  @Test
  @DisplayName("given dwhs exists in database when asked for dwh by id that does not exist should give empty")
  public void shouldGiveNoDwhWhenAskedByIdGivenThatDwhByThatIdDoesNotExistInDatabase() {
    // Given from /liquibase/changes/changelog-00.00.01.02.json
    // When
    Optional<Dwh> dwh = obtainDwh.getDwhByCode(-1000L);
    // Then
    assertThat(dwh).isEmpty();
  }
}
