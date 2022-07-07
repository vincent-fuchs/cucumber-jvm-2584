package my.project.dwh.domain.port;

import java.util.List;
import java.util.Optional;
import my.project.dwh.domain.model.Dwh;
import javax.annotation.Nonnull;

public interface ObtainDwh {

  @Nonnull
  default List<Dwh> getAllDwhs() {
    Dwh dwh = Dwh.builder().code(1L).description(
        "If you could read a leaf or tree\r\nyoud have no need of books.\r\n-- Alistair Cockburn (1987)")
        .build();
    return List.of(dwh);
  }

  default Optional<Dwh> getDwhByCode(Long code) {
    return Optional.of(Dwh.builder().code(1L).description(
        "If you could read a leaf or tree\r\nyoud have no need of books.\r\n-- Alistair Cockburn (1987)")
        .build());
  }
}
