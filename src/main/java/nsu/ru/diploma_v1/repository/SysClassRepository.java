package nsu.ru.diploma_v1.repository;

import nsu.ru.diploma_v1.model.entity.SysClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SysClassRepository extends JpaRepository<SysClass, Long> {


    List<SysClass> findAll();

    Optional<SysClass> getSysClassById(Integer id);

}
