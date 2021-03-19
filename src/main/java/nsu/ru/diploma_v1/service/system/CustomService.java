package nsu.ru.diploma_v1.service.system;

import lombok.RequiredArgsConstructor;
import nsu.ru.diploma_v1.exception.ValidationException;
import nsu.ru.diploma_v1.mapper.FormMapper;
import nsu.ru.diploma_v1.model.dto.NewClassForm;
import nsu.ru.diploma_v1.model.dto.ObjectAttribute;
import nsu.ru.diploma_v1.model.entity.*;
import nsu.ru.diploma_v1.model.enums.database_types.AttributeTypeHandler;
import nsu.ru.diploma_v1.model.enums.database_types.SysTypes;
import nsu.ru.diploma_v1.repository.CustomRepository;
import nsu.ru.diploma_v1.service.database.SysClassService;
import nsu.ru.diploma_v1.service.database.SysObjectService;
import nsu.ru.diploma_v1.service.database.SysTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
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
public class CustomService {

    private final SysClassService sysClassService;
    private final SysObjectService sysObjectService;

    private final CustomRepository customRepository;
    private final FormMapper formMapper;

    private final Map<Integer, AttributeTypeHandler> attributeTypeMap = new HashMap<>();

    @Autowired
    public void setAttributeTypes(List<AttributeTypeHandler> types) {
        types.forEach(type -> attributeTypeMap.put(type.getType().getId(), type));
    }

    @Transactional
    public void saveClass(NewClassForm newClassForm, boolean isPage){

        //зарегистрировали класс
        SysClass sysClass = formMapper.convertToSysClass(newClassForm);
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

        Map<String,Object> dataNameValue = new HashMap<>();
        for (ObjectAttribute objectAttribute : list) {
            dataNameValue.put(objectAttribute.getName(),objectAttribute.getValue());
        }

        //получить название таблицы
        SysClass sysClass = sysClassService.getClassById(classId);
        String tableName = sysClass.getSystemName();

        //зарегистрировать объект
        SysObject sysObject = new SysObject();
        sysObject.setOwnerClassId(classId);
        sysObject = sysObjectService.saveSysObject(sysObject);
        Integer objectId = sysObject.getId();

        //получить типы атрибутов и привести к нужному типу
        Map<String,Object> param = new HashMap<>();
        List<SysAttribute> sysAttribute = sysClass.getAttributeList();
        for (SysAttribute attr : sysAttribute) {

            AttributeTypeHandler attributeTypeHandler = attributeTypeMap.get(attr.getAttributeType());
            Object value = null;
            try{
                value = attributeTypeHandler.handle(attr,dataNameValue.get(attr.getName()));
            }catch (ValidationException e){
                //TODO: error handler
            }
            param.put(attr.getName(), value);
        }

        param.put("id",objectId);
        //сохранить объект
        customRepository.insertInTable(param,tableName);
    }

    public Map<String, Object> getObject(@NonNull String tableName, @NonNull int id){
        //TODO: исключение что не нашли
        return customRepository.getObject(tableName, id);
    }

    public Map<String, AttributeAndValue> getObjectForTemplate(Integer objectId){
        SysObject sysObject = sysObjectService.getSysObjectById(objectId);
        SysClass sysClass = sysObject.getOwnerSysClass();
        Map<String, Object> object = getObject(sysClass.getSystemName(),objectId);

        Map<String, AttributeAndValue> nameValueFields = new HashMap<>();

        List<SysAttribute> sysAttributes = sysClass.getAttributeList();
        for (SysAttribute attr : sysAttributes) {

            Integer type = attr.getAttributeType();

            AttributeTypeHandler attributeTypeHandler = attributeTypeMap.get(type);
            String notParsedValue = attributeTypeHandler.toString(object.get(attr.getName()));

            Integer t = attr.getAttributeType();
            int t1 = SysTypes.XMEMO.getId();

            String parsedValue = notParsedValue;//;

//            if(t.equals(t1)){
//                parsedValue = templateService.parseXmemo(sysObject,notParsedValue);
//            }else{
//                parsedValue = notParsedValue;
//            }

            AttributeAndValue attributeAndValue = new AttributeAndValue(
                    attr.getName(),
                    parsedValue,
                    SysTypes.getType(attr.getAttributeType())
            );

            nameValueFields.put(attr.getName(),attributeAndValue);
        }

        //TODO: сравнить что шаблон принадлежит классу

        //SysTemplate template = sysTemplateService.getSysTemplate(templateId);

        return nameValueFields;
    }

}
