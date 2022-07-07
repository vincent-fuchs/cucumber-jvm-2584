package my.project.dwh.repository.dao;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import my.project.dwh.repository.entity.DwhEntity;

@Repository
public interface DwhDao extends JpaRepository<DwhEntity, Long> {

  Optional<DwhEntity> findByCode(Long code);
}
