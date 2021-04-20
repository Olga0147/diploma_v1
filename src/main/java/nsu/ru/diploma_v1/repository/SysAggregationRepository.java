package nsu.ru.diploma_v1.repository;

import nsu.ru.diploma_v1.model.entity.SysAggregation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysAggregationRepository extends JpaRepository<SysAggregation, Long> {

    List<SysAggregation> findAll();

    SysAggregation getSysAggregationById(int id);

    SysAggregation removeById(int id);

    void deleteById(Integer id);
}
