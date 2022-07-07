package my.project.dwh.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import my.project.dwh.domain.model.Dwh;
import my.project.dwh.domain.port.ObtainDwh;
import my.project.dwh.repository.dao.DwhDao;
import my.project.dwh.repository.entity.DwhEntity;
import javax.annotation.Nonnull;

public class DwhRepository implements ObtainDwh {

  private DwhDao dwhDao;

  public DwhRepository(DwhDao dwhDao) {
    this.dwhDao = dwhDao;
  }

  @Override
  @Nonnull
  public List<Dwh> getAllDwhs() {
    return dwhDao.findAll().stream().map(DwhEntity::toModel).collect(Collectors.toList());
  }

  @Override
  public Optional<Dwh> getDwhByCode(Long code) {
    Optional<DwhEntity> dwhEntity = dwhDao.findByCode(code);
    return dwhEntity.map(DwhEntity::toModel);
  }
}
