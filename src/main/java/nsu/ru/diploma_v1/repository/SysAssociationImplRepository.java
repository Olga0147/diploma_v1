package nsu.ru.diploma_v1.repository;

import nsu.ru.diploma_v1.model.entity.SysAssociationImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SysAssociationImplRepository extends JpaRepository<SysAssociationImpl, Long> {

    Optional<SysAssociationImpl> getSysAssociationImplById(int id);

    Integer deleteById(int id);
}
