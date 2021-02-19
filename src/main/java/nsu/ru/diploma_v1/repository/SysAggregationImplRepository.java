package nsu.ru.diploma_v1.repository;

import nsu.ru.diploma_v1.model.entity.SysAggregationImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SysAggregationImplRepository extends JpaRepository<SysAggregationImpl, Long> {

    SysAggregationImpl getSysAggregationImplById(int id);
}
