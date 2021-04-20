package nsu.ru.diploma_v1.repository;

import nsu.ru.diploma_v1.model.entity.SysAggregation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SysAggregationRepository extends JpaRepository<SysAggregation, Long> {

    List<SysAggregation> findAll();

    Optional<SysAggregation> getSysAggregationById(int id);

    void deleteById(Integer id);
}
