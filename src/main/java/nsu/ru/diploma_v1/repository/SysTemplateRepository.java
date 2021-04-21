package nsu.ru.diploma_v1.repository;

import nsu.ru.diploma_v1.model.entity.SysTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SysTemplateRepository extends JpaRepository<SysTemplate, Long> {

    List<SysTemplate> findAll();

    Optional<SysTemplate> getSysTemplateById(int id);

    void deleteById(int id);
}
