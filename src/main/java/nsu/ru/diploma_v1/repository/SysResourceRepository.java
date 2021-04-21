package nsu.ru.diploma_v1.repository;

import nsu.ru.diploma_v1.model.entity.SysResource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SysResourceRepository extends JpaRepository<SysResource, Long> {

    List<SysResource> getSysResourceByOwnerClassId(int classId);

    Optional<SysResource> getSysResourceById(int id);

    void deleteById(int id);
}
