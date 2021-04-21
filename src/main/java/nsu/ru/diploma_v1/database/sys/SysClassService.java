package nsu.ru.diploma_v1.database.sys;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nsu.ru.diploma_v1.exception.EditException;
import nsu.ru.diploma_v1.exception.EntityNotFoundException;
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
import java.util.Optional;

/**
 * Таблицы Класс и Атрибуты
 */
@Slf4j
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

    public SysClass getClassById(int id) throws EntityNotFoundException{
        Optional<SysClass> sysClass = sysClassRepository.getSysClassById(id);
        if(sysClass.isPresent()){
            return sysClass.get();
        }else{
            log.error(String.format("Класс %d не найден.",id));
            throw new EntityNotFoundException(String.format("Класс %d не найден.",id));
        }
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

    public List<SysAttribute> getAttributes(int id)throws EntityNotFoundException{
        Optional<SysClass> sysClass = sysClassRepository.getSysClassById(id);
        if(sysClass.isPresent()){
            return sysClass.get().getAttributeList();
        }else{
            log.error(String.format("Класс %d не найден.",id));
            throw new EntityNotFoundException(String.format("Класс %d не найден.",id));
        }
    }

    public List<SysAttribute> getAttributesNotMMedia(int id)throws EntityNotFoundException{
        Optional<SysClass> sysClass = sysClassRepository.getSysClassById(id);
        if(sysClass.isEmpty()){
            log.error(String.format("Класс %d не найден.",id));
            throw new EntityNotFoundException(String.format("Класс %d не найден.",id));
        }
        List<SysAttribute> list = sysClass.get().getAttributeList();
        List<SysAttribute> notMMedia = new LinkedList<>();
        for (SysAttribute sysAttribute : list) {
            if(sysAttribute.getAttributeType() != SysTypes.MMEDIA.getId()){
                notMMedia.add(sysAttribute);
            }
        }
        return notMMedia;
    }

    @Transactional
    public List<SysAttribute> saveAttributes(List<SysAttribute> list) throws EditException{
        List<SysAttribute> newList = new LinkedList<>();
        for (SysAttribute attribute : list) {
            try {
                String name = attribute.getName();
                attribute.setName(name.replaceAll(" ","_"));
                SysAttribute current = sysAttributeRepository.save(attribute);
                newList.add(current);
            }catch (Exception e){
                throw new EditException("Атрибуты не моут быть сохранены.");
            }
        }
        return newList;
    }

    @Transactional
    public String delete(int id) throws EditException,EntityNotFoundException {
        Optional<SysClass> sysClass = sysClassRepository.getSysClassById(id);
        if(sysClass.isEmpty()){
            log.error(String.format("Класс %d не найден.",id));
            throw new EntityNotFoundException(String.format("Класс %d не найден.",id));
        }
        if(!sysClass.get().getAssociationToList().isEmpty() || !sysClass.get().getAssociationFromList().isEmpty()){
            log.info("Невозможно удалить. Класс участвует в Ассоциациях");
            throw new EditException("Невозможно удалить. Класс участвует в Ассоциациях");
        }else if(!sysClass.get().getAggregationFromList().isEmpty() || !sysClass.get().getAggregationToList().isEmpty()){
            log.info("Невозможно удалить. Класс участвует в Агрегациях");
            throw new EditException("Невозможно удалить. Класс участвует в Агрегациях");
        }else if(!sysClass.get().getObjectList().isEmpty()){
            log.info("Невозможно удалить. У Класса есть Объекты");
            throw new EditException("Невозможно удалить. У Класса есть Объекты");
        }else if(!sysClass.get().getTemplateList().isEmpty()){
            log.info("Невозможно удалить. У Класса есть Шаблоны");
            throw new EditException("Невозможно удалить. У Класса есть Шаблоны");
        }else {
            sysAttributeRepository.deleteAllByOwnerClassId(id);
            customRepository.deleteTable("class_"+sysClass.get().getId());// throws EditException
            sysClassRepository.delete(sysClass.get());
            return "Удалено успешно";
        }
    }
}
