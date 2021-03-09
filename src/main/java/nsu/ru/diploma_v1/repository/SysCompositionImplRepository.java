package nsu.ru.diploma_v1.repository;

import nsu.ru.diploma_v1.model.entity.SysCompositionImpl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SysCompositionImplRepository  extends JpaRepository<SysCompositionImpl, Long> {

    List<SysCompositionImpl> findAll();
    SysCompositionImpl getSysCompositionImplById(int id);
}
