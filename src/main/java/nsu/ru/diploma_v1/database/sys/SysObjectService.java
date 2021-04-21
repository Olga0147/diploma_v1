package nsu.ru.diploma_v1.database.sys;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nsu.ru.diploma_v1.exception.EditException;
import nsu.ru.diploma_v1.exception.EntityNotFoundException;
import nsu.ru.diploma_v1.model.entity.SysClass;
import nsu.ru.diploma_v1.model.entity.SysObject;
import nsu.ru.diploma_v1.repository.CustomRepository;
import nsu.ru.diploma_v1.repository.SysObjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SysObjectService {

    private final SysObjectRepository sysObjectRepository;
    private final CustomRepository customRepository;

    public List<SysObject> getSysObjects(){
        return sysObjectRepository.findAll();
    }

    public SysObject getSysObjectById(int id) throws EntityNotFoundException{
        Optional<SysObject> object = sysObjectRepository.getSysObjectById(id);
        if(object.isPresent()){
            return object.get();
        }else{
            log.error(String.format("Объект %d не найден.",id));
            throw new EntityNotFoundException(String.format("Объект %d не найден.",id));
        }
    }

    public SysObject saveSysObject(SysObject sysObject){
        return sysObjectRepository.save(sysObject);
    }

    @Transactional
    public String delete(int id) throws EditException, EntityNotFoundException {
        SysObject sysObject = getSysObjectById(id);// throws EntityNotFoundException
        SysClass sysClass = sysObject.getOwnerSysClass();

        if(!sysObject.getAssociationImplFromList().isEmpty() || !sysObject.getAssociationImplToList().isEmpty()){
            log.error("Невозможно удалить. Объект участвует в Реализациях Ассоциаций");
            throw new EditException("Невозможно удалить. Объект участвует в Реализациях Ассоциаций");
        }else if(!sysObject.getAggregationImplFromList().isEmpty() || !sysObject.getAggregationImplToList().isEmpty()){
            log.error("Невозможно удалить. Объект участвует в Реализациях Агрегаций");
            throw new EditException("Невозможно удалить. Объект участвует в Реализациях Агрегаций");
        }else{
            sysClass.deleteObject(sysObject);
            customRepository.deleteRowInTable("class_"+sysClass.getId(),id);// throws EditException
            sysObjectRepository.deleteById(id);
            return "Объект успешно удален.";
        }
    }

}
