package nsu.ru.diploma_v1.service.database;

import lombok.RequiredArgsConstructor;
import nsu.ru.diploma_v1.model.entity.SysAttribute;
import nsu.ru.diploma_v1.model.entity.SysClass;
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

    /**
     * Работа с классом
     */

    public List<SysClass> getPages(){
        return sysClassRepository.getSysClassesByIsPageIsTrue();
    }

    public List<SysClass> getClasses(){
        return sysClassRepository.getSysClassesByIsPageIsFalse();
    }

    public SysClass getClassById(int classId){
        return sysClassRepository.getSysClassById(classId);
    }

    public SysClass saveClass(SysClass sysClass){
        return sysClassRepository.save(sysClass);
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

    /**
     * Работа с атрибутами
     */

    public List<SysAttribute> getAttributes(int classId){ return sysClassRepository.getSysClassById(classId).getAttributeList();}

    public List<SysAttribute> saveAttributes(List<SysAttribute> list){
        List<SysAttribute> newList = new LinkedList<>();
        for (SysAttribute attribute : list) {
            //TODO ERROR
            SysAttribute current = sysAttributeRepository.save(attribute);
            newList.add(current);
        }
        return newList;
    }
}
