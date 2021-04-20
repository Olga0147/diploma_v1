package nsu.ru.diploma_v1.repository;

import nsu.ru.diploma_v1.model.entity.SysAssociation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SysAssociationRepository extends JpaRepository<SysAssociation, Long> {

    List<SysAssociation> findAll();

    Optional<SysAssociation> getSysAssociationById(int id);

    void deleteById(int id);
}
