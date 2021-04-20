package nsu.ru.diploma_v1.repository;

import nsu.ru.diploma_v1.model.entity.SysAssociationImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SysAssociationImplRepository extends JpaRepository<SysAssociationImpl, Long> {

    SysAssociationImpl getSysAssociationImplById(int id);

    Integer deleteById(int id);
}
