package nsu.ru.diploma_v1.repository;

import nsu.ru.diploma_v1.model.entity.SysAggregationImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SysAggregationImplRepository extends JpaRepository<SysAggregationImpl, Long> {

    SysAggregationImpl getSysAggregationImplById(int id);

    Optional<SysAggregationImpl>
    getSysAggregationImplByAggregationIdAndToTemplateIdAndFromObjectIdAndToObjectIdAndAttributeId(
            int aggregationId,
           int toTemplateId,
           int fromObjectId,
           int toObjectId,
           int attributeId);

    List<SysAggregationImpl> getSysAggregationImplsByFromObjectIdAndAttributeId(int fromObjectId,
                                                                                int attributeId);

    void deleteById(int id);

}
