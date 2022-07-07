package my.project.dwh;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import my.project.dwh.domain.DwhDomain;
import my.project.dwh.domain.exception.DwhNotFoundException;
import my.project.dwh.domain.model.Dwh;
import my.project.dwh.domain.model.DwhInfo;
import my.project.dwh.domain.port.ObtainDwh;
import my.project.dwh.domain.port.RequestDwh;

@ExtendWith(MockitoExtension.class)
class AcceptanceTest {

  @Test
  @DisplayName("should be able to get dwhs when asked for dwhs from hard coded dwhs")
  void getDwhsFromHardCoded() {
  /*
      RequestDwh    - left side port
      DwhDomain     - hexagon (domain)
      ObtainDwh     - right side port
   */
    RequestDwh requestDwh = new DwhDomain(); // the dwh is hard coded
    DwhInfo dwhInfo = requestDwh.getDwhs();
    assertThat(dwhInfo).isNotNull();
    assertThat(dwhInfo.getDwhs()).isNotEmpty().extracting("description")
        .contains(
            "If you could read a leaf or tree\r\nyoud have no need of books.\r\n-- Alistair Cockburn (1987)");
  }

  @Test
  @DisplayName("should be able to get dwhs when asked for dwhs from stub")
  void getDwhsFromMockedStub(@Mock ObtainDwh obtainDwh) {
    // Stub
    Dwh dwh = Dwh.builder().code(1L).description(
        "I want to sleep\r\nSwat the flies\r\nSoftly, please.\r\n\r\n-- Masaoka Shiki (1867-1902)")
        .build();
    Mockito.lenient().when(obtainDwh.getAllDwhs()).thenReturn(List.of(dwh));
    // hexagon
    RequestDwh requestDwh = new DwhDomain(obtainDwh);
    DwhInfo dwhInfo = requestDwh.getDwhs();
    assertThat(dwhInfo).isNotNull();
    assertThat(dwhInfo.getDwhs()).isNotEmpty().extracting("description")
        .contains(
            "I want to sleep\r\nSwat the flies\r\nSoftly, please.\r\n\r\n-- Masaoka Shiki (1867-1902)");
  }

  @Test
  @DisplayName("should be able to get dwh when asked for dwh by id from stub")
  void getDwhByIdFromMockedStub(@Mock ObtainDwh obtainDwh) {
    // Given
    // Stub
    Long code = 1L;
    String description = "I want to sleep\\r\\nSwat the flies\\r\\nSoftly, please.\\r\\n\\r\\n-- Masaoka Shiki (1867-1902)";
    Dwh expectedDwh = Dwh.builder().code(code).description(description).build();
    Mockito.lenient().when(obtainDwh.getDwhByCode(code))
        .thenReturn(Optional.of(expectedDwh));
    // When
    RequestDwh requestDwh = new DwhDomain(obtainDwh);
    Dwh actualDwh = requestDwh.getDwhByCode(code);
    assertThat(actualDwh).isNotNull().isEqualTo(expectedDwh);
  }

  @Test
  @DisplayName("should throw exception when asked for dwh by id that does not exists from stub")
  void getExceptionWhenAskedDwhByIdThatDoesNotExist(@Mock ObtainDwh obtainDwh) {
    // Given
    // Stub
    Long code = -1000L;
    Mockito.lenient().when(obtainDwh.getDwhByCode(code)).thenReturn(Optional.empty());
    // When
    RequestDwh requestDwh = new DwhDomain(obtainDwh);
    // Then
    assertThatThrownBy(() -> requestDwh.getDwhByCode(code)).isInstanceOf(
        DwhNotFoundException.class)
        .hasMessageContaining("Dwh with code " + code + " does not exist");
  }
}
