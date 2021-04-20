package nsu.ru.diploma_v1.database.sys;

import lombok.RequiredArgsConstructor;
import nsu.ru.diploma_v1.model.entity.SysAttribute;
import nsu.ru.diploma_v1.model.entity.SysClass;
import nsu.ru.diploma_v1.model.enums.database_types.SysTypes;
import nsu.ru.diploma_v1.repository.CustomRepository;
import nsu.ru.diploma_v1.repository.SysAttributeRepository;
import nsu.ru.diploma_v1.repository.SysClassRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

/**
 * Таблицы Класс и Атрибуты
 */
@Service
@RequiredArgsConstructor
public class SysClassService {

    private final SysClassRepository sysClassRepository;
    private final SysAttributeRepository sysAttributeRepository;
    private final CustomRepository customRepository;

    /**
     * Работа с классом
     */

    public List<SysClass> getClasses(){
        return sysClassRepository.findAll();
    }

    public SysClass getClassById(int classId){
        return sysClassRepository.getSysClassById(classId);
    }

    public SysClass saveClass(SysClass sysClass){
        return sysClassRepository.save(sysClass);
    }

    public List<String> getAllClassesIds(){
        List<SysClass> classes = sysClassRepository.findAll();
        List<String> str = new LinkedList<>();
        for (SysClass sysClass : classes) {
            str.add(sysClass.getId()+"-"+sysClass.getName());
        }
        return str;
    }

    /**
     * Работа с атрибутами
     */

    public List<SysAttribute> getAttributes(int classId){ return sysClassRepository.getSysClassById(classId).getAttributeList();}

    public List<SysAttribute> getAttributesNotMMedia(int classId){
        List<SysAttribute> list = sysClassRepository.getSysClassById(classId).getAttributeList();
        List<SysAttribute> notMMedia = new LinkedList<>();
        for (SysAttribute sysAttribute : list) {
            if(sysAttribute.getAttributeType() != SysTypes.MMEDIA.getId()){
                notMMedia.add(sysAttribute);
            }
        }
        return notMMedia;
    }

    public List<SysAttribute> saveAttributes(List<SysAttribute> list){
        List<SysAttribute> newList = new LinkedList<>();
        for (SysAttribute attribute : list) {
            //TODO ERROR
            SysAttribute current = sysAttributeRepository.save(attribute);
            newList.add(current);
        }
        return newList;
    }

    @Transactional
    public String delete(int id){
        SysClass sysClass = sysClassRepository.getSysClassById(id);
        if(!sysClass.getAssociationToList().isEmpty() || !sysClass.getAssociationFromList().isEmpty()){
            return "Невозможно удалить. Класс участвует в Ассоциациях";
        }else if(!sysClass.getAggregationFromList().isEmpty() || !sysClass.getAggregationToList().isEmpty()){
            return "Невозможно удалить. Класс участвует в Агрегациях";
        }else if(!sysClass.getObjectList().isEmpty()){
            return "Невозможно удалить. У Класса есть Объекты";
        }else if(!sysClass.getTemplateList().isEmpty()){
            return "Невозможно удалить. У Класса есть Шаблоны";
        }else {
            sysAttributeRepository.deleteAllByOwnerClassId(id);
            customRepository.deleteTable("class_"+sysClass.getId());
            sysClassRepository.delete(sysClass);
            return "Удалено успешно";
        }
    }
}
