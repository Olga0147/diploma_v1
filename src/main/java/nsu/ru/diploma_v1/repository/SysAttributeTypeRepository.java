package nsu.ru.diploma_v1.repository;

import nsu.ru.diploma_v1.model.entity.SysAttributeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SysAttributeTypeRepository extends JpaRepository<SysAttributeType, Long> {
}
