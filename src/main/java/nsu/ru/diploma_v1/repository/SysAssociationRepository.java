package nsu.ru.diploma_v1.repository;

import nsu.ru.diploma_v1.model.entity.SysAssociation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysAssociationRepository extends JpaRepository<SysAssociation, Long> {

    List<SysAssociation> findAll();

    SysAssociation getSysAssociationById(int id);

    void deleteById(int id);
}
