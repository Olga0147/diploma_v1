package nsu.ru.diploma_v1.service.database;

import lombok.RequiredArgsConstructor;
import nsu.ru.diploma_v1.model.entity.SysAttribute;
import nsu.ru.diploma_v1.model.entity.SysClass;
import nsu.ru.diploma_v1.model.entity.SysObject;
import nsu.ru.diploma_v1.model.entity.SysTemplate;
import nsu.ru.diploma_v1.repository.SysAttributeRepository;
import nsu.ru.diploma_v1.repository.SysClassRepository;
import org.springframework.stereotype.Service;

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

    public List<SysClass> getPages(){
        return sysClassRepository.getSysClassesByIsPageIsTrue();
    }

    public List<SysClass> getClasses(){
        return sysClassRepository.getSysClassesByIsPageIsFalse();
    }

    public SysClass getClass(int classId){
        return sysClassRepository.getSysClassById(classId);
    }

    public List<SysAttribute> getAttributes(int classId){ return sysClassRepository.getSysClassById(classId).getAttributeList();}

    public SysClass saveClass(SysClass sysClass){
        return sysClassRepository.save(sysClass);
    }

    public List<SysTemplate> getTemplate(int classId){ return sysClassRepository.getSysClassById(classId).getTemplateList();}

    public List<SysObject> getObject(int classId){ return sysClassRepository.getSysClassById(classId).getObjectList();}

    public List<SysAttribute> saveAttributes(List<SysAttribute> list){
        List<SysAttribute> newList = new LinkedList<>();
        for (SysAttribute attribute : list) {
            //TODO ERROR
            SysAttribute current = sysAttributeRepository.save(attribute);
            newList.add(current);
        }
        return newList;
    }

    public List<String> getAllPagesIds(){
        List<SysClass> pages = sysClassRepository.getSysClassesByIsPageIsTrue();
        List<String> str = new LinkedList<>();
        for (SysClass page : pages) {
            str.add(page.getId()+"-"+page.getName());
        }
        return str;
    }

    public List<String> getAllClassesIds(){
        List<SysClass> classes = sysClassRepository.getSysClassesByIsPageIsFalse();
        List<String> str = new LinkedList<>();
        for (SysClass sysClass : classes) {
            str.add(sysClass.getId()+"-"+sysClass.getName());
        }
        return str;
    }
}
