package my.project.dwh.domain.port;

import my.project.dwh.domain.model.Dwh;
import my.project.dwh.domain.model.DwhInfo;

public interface RequestDwh {

  DwhInfo getDwhs();
  Dwh getDwhByCode(Long code);
}
