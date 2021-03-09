package nsu.ru.diploma_v1.repository;

import nsu.ru.diploma_v1.model.entity.SysComposition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SysCompositionRepository  extends JpaRepository<SysComposition, Long> {

    List<SysComposition> findAll();
    SysComposition getSysCompositionById(int id);
}
