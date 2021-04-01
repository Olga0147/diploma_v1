package nsu.ru.diploma_v1.repository;

import nsu.ru.diploma_v1.model.entity.SysMmedia;
import nsu.ru.diploma_v1.model.entity.SysResource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SysMMediaRepository extends JpaRepository<SysMmedia, Long> {

    List<SysMmedia> getSysMmediaByOwnerObjectId(int objectId);

    SysMmedia getSysMmediaById(int id);
}
