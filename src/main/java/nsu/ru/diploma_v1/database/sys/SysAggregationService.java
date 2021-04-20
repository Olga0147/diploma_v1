package nsu.ru.diploma_v1.database.sys;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nsu.ru.diploma_v1.exception.EditException;
import nsu.ru.diploma_v1.exception.EntityNotFoundException;
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

@Slf4j
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

    public SysAggregation getSysAggregation(int id) throws EntityNotFoundException{
        Optional<SysAggregation> aggregation = sysAggregationRepository.getSysAggregationById(id);
        if(aggregation.isPresent()){
            return aggregation.get();
        }else{
            log.error(String.format("Агрегация %d не найдена.",id));
            throw new EntityNotFoundException(String.format("Агрегация %d не найдена.",id));
        }
    }

    public SysAggregation saveSysAggregation(@NonNull SysAggregation sysAggregation){
        return sysAggregationRepository.save(sysAggregation);
    }

    //----

    public List<SysAggregationImpl> getSysAggregationsImpl(){
        return sysAggregationImplRepository.findAll();
    }

    public SysAggregationImpl getSysAggregationImpl(int id)throws EntityNotFoundException{
        Optional<SysAggregationImpl> aggregation = sysAggregationImplRepository.getSysAggregationImplById(id);
        if(aggregation.isPresent()){
            return aggregation.get();
        }else{
            log.error(String.format("Реализация Агрегации %d не была найдена.",id));
            throw new EntityNotFoundException(String.format("Реализация Агрегации %d не была найдена.",id));
        }
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

    //---

    @Transactional
    public String deleteAggregation(Integer id) throws EntityNotFoundException, EditException{
        Optional<SysAggregation> aggregation = sysAggregationRepository.getSysAggregationById(id);
        if (aggregation.isEmpty()) {
            log.error(String.format("Агрегация %d не найдена.",id));
            throw new EntityNotFoundException(String.format("Агрегация %d не найдена.",id));
        }
        if(aggregation.get().getSysAggregationList().isEmpty()){
            sysAggregationRepository.deleteById(id);
            return "Успешно удалено";
        }
        else{
            log.info(String.format("Агрегация %d не доступна для удаления.",id));
            throw new EditException("Удаление невозможно, существуют реализации данной агрегации. " +
                    "Сначала необходимо удалить в Шаблоне указания на создание агрегаций.");
        }
    }

    @Transactional
    public void deleteAggregationImplByTemplateId(int id){
        sysAggregationImplRepository.deleteAllByToTemplateId(id);
    }

}
