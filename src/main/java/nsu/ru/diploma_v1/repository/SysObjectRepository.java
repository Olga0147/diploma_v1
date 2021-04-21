package nsu.ru.diploma_v1.repository;

import nsu.ru.diploma_v1.model.entity.SysObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SysObjectRepository extends JpaRepository<SysObject, Long> {

    List<SysObject> findAll();

    Optional<SysObject> getSysObjectById(int id);

    Integer deleteById(int id);
}
