package nsu.ru.diploma_v1.service.database;

import lombok.RequiredArgsConstructor;
import nsu.ru.diploma_v1.model.entity.SysAggregation;
import nsu.ru.diploma_v1.model.entity.SysAggregationImpl;
import nsu.ru.diploma_v1.repository.SysAggregationImplRepository;
import nsu.ru.diploma_v1.repository.SysAggregationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SysAggregationService {

    private final SysAggregationRepository sysAggregationRepository;
    private final SysAggregationImplRepository sysAggregationImplRepository;

    public List<SysAggregation> getSysAggregations(){
        return sysAggregationRepository.findAll();
    }

    public List<String> getSysAggregationsIdsAndNames(){
        List<String> result = new LinkedList<>();

        List<SysAggregation> list = sysAggregationRepository.findAll();
        for (SysAggregation sysAggregation : list) {
            result.add(String.format("%d-%s",sysAggregation.getId(),sysAggregation.getName()));
        }
        return result;
    }

    public SysAggregation getSysAggregation(int id){
        return sysAggregationRepository.getSysAggregationById(id);
    }

    public SysAggregation saveSysAggregation(SysAggregation sysAggregation){
        return sysAggregationRepository.save(sysAggregation);
    }

    //----

    public List<SysAggregationImpl> getSysAggregationsImpl(){
        return sysAggregationImplRepository.findAll();
    }

    public SysAggregationImpl getSysAggregationImpl(int id){
        return sysAggregationImplRepository.getSysAggregationImplById(id);
    }

    public void saveSysAggregationImpl(SysAggregationImpl sysAggregation){
        sysAggregationImplRepository.save(sysAggregation);
    }

    public boolean checkExist(SysAggregationImpl sysAggregation){
        Optional<SysAggregationImpl> impl =
                sysAggregationImplRepository.getSysAggregationImplByAggregationIdAndToTemplateIdAndFromObjectIdAndToObjectIdAndAttributeId(
                sysAggregation.getAggregationId(),
                sysAggregation.getToTemplateId(),
                sysAggregation.getFromObjectId(),
                sysAggregation.getToObjectId(),
                sysAggregation.getAttributeId()
        );
        return impl.isPresent();
    }

    public Map<Integer, SysAggregationImpl> getXMemoAggregationList(int fromObjectId, int attributeId){
        List<SysAggregationImpl> list =
                sysAggregationImplRepository.getSysAggregationImplsByFromObjectIdAndAttributeId(fromObjectId, attributeId);
        return list.stream().collect(Collectors.toMap(SysAggregationImpl::getId, impl->impl));
    }

    @Transactional
    public void deleteAll(Map<Integer, SysAggregationImpl> map){
        for (Integer key : map.keySet()) {
            sysAggregationImplRepository.deleteById(key);
        }
    }

}
