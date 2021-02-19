package nsu.ru.diploma_v1.service.system;

import lombok.RequiredArgsConstructor;
import nsu.ru.diploma_v1.mapper.FormMapper;
import nsu.ru.diploma_v1.model.dto.NewClassForm;
import nsu.ru.diploma_v1.model.dto.ObjectAttribute;
import nsu.ru.diploma_v1.model.entity.SysAttribute;
import nsu.ru.diploma_v1.model.entity.SysClass;
import nsu.ru.diploma_v1.model.entity.SysObject;
import nsu.ru.diploma_v1.repository.CustomRepository;
import nsu.ru.diploma_v1.service.database.SysClassService;
import nsu.ru.diploma_v1.service.database.SysObjectService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Отвечает за основную логику системы
 */
@Service
@RequiredArgsConstructor
public class SystemService {

    private final SysClassService sysClassService;
    private final SysObjectService sysObjectService;

    private final CustomRepository customRepository;
    private final FormMapper formMapper;

    @Transactional
    public void saveClass(NewClassForm newClassForm, boolean isPage){

        //зарегистрировали класс
        SysClass sysClass = formMapper.convertToSysClass(newClassForm);
        sysClass.setPage(isPage);
        sysClass = sysClassService.saveClass(sysClass);

        //сгенерировали системное имя и обновили
        Integer classId = sysClass.getId();
        String sysName = "class_"+classId;
        sysClass.setSystemName(sysName);
        sysClassService.saveClass(sysClass);

        //зарегистрировали атрибуты класса
        List<SysAttribute> sysAttributeList = formMapper.convertToSysAttribute(newClassForm.getAttributes(),classId);
        sysAttributeList = sysClassService.saveAttributes(sysAttributeList);

        //создали таблицу
        customRepository.createTable(sysName,sysAttributeList);
    }

    @Transactional
    public void saveObject(List<ObjectAttribute> list, Integer classId){

        Map<String,Object> param = new HashMap<>();
        for (ObjectAttribute objectAttribute : list) {
            param.put(objectAttribute.getName(),objectAttribute.getValue());
        }

        //получить название таблицы
        SysClass sysClass = sysClassService.getClass(classId);
        String tableName = sysClass.getSystemName();

        //зарегистрировать объект
        SysObject sysObject = new SysObject();
        sysObject.setOwnerClassId(classId);
        sysObject = sysObjectService.saveObject(sysObject);
        Integer objectId = sysObject.getId();

        //получить типы атрибутов и привести к нужному типу
        Map<String,Object> param1 = new HashMap<>();
        List<SysAttribute> sysAttribute = sysClass.getAttributeList();
        for (SysAttribute attribute : sysAttribute) {
            switch (attribute.getAttributeType()){
                case 1:{
                    param1.put(attribute.getName(),Float.parseFloat((String) param.get(attribute.getName())) );
                    break;
                }
                case 2:{
                    param1.put(attribute.getName(),Integer.parseInt((String) param.get(attribute.getName())) );
                    break;
                }
                case 3:{
                    //TODO: MMEDIA : сохранить файл и сохранить к нему путь
                }
                case 4:{
                    param1.put( attribute.getName(),(String) param.get(attribute.getName()) );
                    break;
                }
                case 5:{
                    String str = (String) param.get(attribute.getName());
                    if(str.length()<=attribute.getAttributeSize()){
                        param1.put(attribute.getName(),str);
                    }
                    else {
                        //TODO: throw exception
                    }
                    break;
                }
                case 6:{
                    param1.put(attribute.getName(),Short.parseShort((String) param.get(attribute.getName())) );
                    break;
                }
                case 7:{
                    //TODO : к чему приводить и как обрабатывать?
                    param1.put( attribute.getName(),(String) param.get(attribute.getName()) );
                    break;
                }
            }
        }

        param1.put("id",objectId);
        //сохранить объект
        customRepository.insertInTable(param1,tableName);
    }

}
