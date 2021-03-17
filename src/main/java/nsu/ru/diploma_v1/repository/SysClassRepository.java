package nsu.ru.diploma_v1.repository;

import nsu.ru.diploma_v1.model.entity.SysClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysClassRepository extends JpaRepository<SysClass, Long> {


    List<SysClass> findAll();

    SysClass getSysClassById(Integer id);

}
