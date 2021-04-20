package nsu.ru.diploma_v1.database.sys;

import lombok.RequiredArgsConstructor;
import nsu.ru.diploma_v1.model.entity.SysClass;
import nsu.ru.diploma_v1.model.entity.SysObject;
import nsu.ru.diploma_v1.repository.CustomRepository;
import nsu.ru.diploma_v1.repository.SysObjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SysObjectService {

    private final SysObjectRepository sysObjectRepository;
    private final CustomRepository customRepository;

    public List<SysObject> getSysObjects(){
        return sysObjectRepository.findAll();
    }

    public SysObject getSysObjectById(int id){
        return sysObjectRepository.getSysObjectById(id);
    }

    public SysObject saveSysObject(SysObject sysObject){
        return sysObjectRepository.save(sysObject);
    }

    @Transactional
    public String delete(int id){
        SysObject sysObject = getSysObjectById(id);
        SysClass sysClass = sysObject.getOwnerSysClass();

        if(!sysObject.getAssociationImplFromList().isEmpty() || !sysObject.getAssociationImplToList().isEmpty()){
            return "Невозможно удалить. Объект участвует в Реализациях Ассоциаций";
        }else if(!sysObject.getAggregationImplFromList().isEmpty() || !sysObject.getAggregationImplToList().isEmpty()){
            return "Невозможно удалить. Объект участвует в Реализациях Агрегаций";
        }else{
            sysClass.deleteObject(sysObject);
            boolean wtf = sysClass.getObjectList().contains(sysObject);
            int i = customRepository.deleteRowInTable("class_"+sysClass.getId(),id);
            int i1 = sysObjectRepository.deleteById(id);
            return "Объект успешно удален.";
        }
    }

}
