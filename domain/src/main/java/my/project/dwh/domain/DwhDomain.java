package my.project.dwh.domain;

import java.util.Optional;
import my.project.dwh.domain.exception.DwhNotFoundException;
import my.project.dwh.domain.model.Dwh;
import my.project.dwh.domain.model.DwhInfo;
import my.project.dwh.domain.port.ObtainDwh;
import my.project.dwh.domain.port.RequestDwh;

public class DwhDomain implements RequestDwh {

  private ObtainDwh obtainDwh;

  public DwhDomain() {
    this(new ObtainDwh() {
    });
  }

  public DwhDomain(ObtainDwh obtainDwh) {
    this.obtainDwh = obtainDwh;
  }

  @Override
  public DwhInfo getDwhs() {
    return DwhInfo.builder().dwhs(obtainDwh.getAllDwhs()).build();
  }

  @Override
  public Dwh getDwhByCode(Long code) {
    Optional<Dwh> dwh = obtainDwh.getDwhByCode(code);
    return dwh.orElseThrow(() -> new DwhNotFoundException(code));
  }
}
