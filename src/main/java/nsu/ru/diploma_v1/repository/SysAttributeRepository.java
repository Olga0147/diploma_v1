package nsu.ru.diploma_v1.repository;

import nsu.ru.diploma_v1.model.entity.SysAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SysAttributeRepository extends JpaRepository<SysAttribute, Long> {

    Integer deleteAllByOwnerClassId(int id);
}
