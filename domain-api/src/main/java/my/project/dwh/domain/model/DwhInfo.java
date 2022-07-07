package my.project.dwh.domain.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.annotation.Nonnull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DwhInfo {

  @Nonnull
  private List<Dwh> dwhs;
}
