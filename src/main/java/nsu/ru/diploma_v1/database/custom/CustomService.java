package nsu.ru.diploma_v1.database.custom;

import lombok.RequiredArgsConstructor;
import nsu.ru.diploma_v1.configuration.urls.mode.UserMode;
import nsu.ru.diploma_v1.exception.EditException;
import nsu.ru.diploma_v1.exception.EntityNotFoundException;
import nsu.ru.diploma_v1.exception.ValidationException;
import nsu.ru.diploma_v1.mapper.FormMapper;
import nsu.ru.diploma_v1.model.dto.NewClassForm;
import nsu.ru.diploma_v1.model.dto.ObjectAttribute;
import nsu.ru.diploma_v1.model.entity.*;
import nsu.ru.diploma_v1.model.enums.database_types.AttributeTypeHandler;
import nsu.ru.diploma_v1.model.enums.database_types.SysTypes;
import nsu.ru.diploma_v1.repository.CustomRepository;
import nsu.ru.diploma_v1.database.sys.SysClassService;
import nsu.ru.diploma_v1.database.sys.SysObjectService;
import nsu.ru.diploma_v1.template_parse.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Отвечает за основную логику системы
 */
@Service
@RequiredArgsConstructor
public class CustomService {

    private final SysClassService sysClassService;
    private final SysObjectService sysObjectService;
    private TemplateService templateService;

    private final CustomRepository customRepository;
    private final FormMapper formMapper;

    private final Map<Integer, AttributeTypeHandler> attributeTypeMap = new HashMap<>();

    @Autowired
    public void setAttributeTypes(List<AttributeTypeHandler> types) {
        types.forEach(type -> attributeTypeMap.put(type.getType().getId(), type));
    }


    public void setTemplateService(TemplateService templateService) {
        this.templateService = templateService;
    }

    @Transactional
    public SysClass saveClass(NewClassForm newClassForm) throws EntityNotFoundException {

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
        customRepository.createTable(sysName,sysAttributeList); // throws EntityNotFoundException
        return sysClass;
    }

    @Transactional
    public SysObject saveObject(List<ObjectAttribute> list, Integer classId) throws EditException {

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
                //сохранить агрегации
                if(attr.getAttributeType() == SysTypes.XMEMO.getId()){
                    templateService.parseXMemoToSaveObject( (String)value,attr.getId(),objectId);
                }
            }catch (ValidationException e){
                //TODO: error handler
            }
            param.put(attr.getName(), value);
        }

        param.put("id",objectId);
        //сохранить объект
        customRepository.insertInTable(param,tableName);// throws EditException
        return sysObject;
    }

    @Transactional
    public SysObject updateObject(List<ObjectAttribute> list, Integer objectId) throws EditException{

        Map<String,Object> dataNameValue = new HashMap<>();
        for (ObjectAttribute objectAttribute : list) {
            dataNameValue.put(objectAttribute.getName(),objectAttribute.getValue());
        }

        SysObject sysObject = sysObjectService.getSysObjectById(objectId);

        //получить название таблицы
        SysClass sysClass =  sysObject.getOwnerSysClass();
        String tableName = sysClass.getSystemName();


        //получить типы атрибутов и привести к нужному типу
        Map<String,Object> param = new HashMap<>();
        List<SysAttribute> sysAttribute = sysClass.getAttributeList();
        for (SysAttribute attr : sysAttribute) {

            if(!dataNameValue.containsKey(attr.getName())){continue;}

            AttributeTypeHandler attributeTypeHandler = attributeTypeMap.get(attr.getAttributeType());
            Object value = null;
            try{
                value = attributeTypeHandler.handle(attr,dataNameValue.get(attr.getName()));
                //сохранить агрегации
                if(attr.getAttributeType() == SysTypes.XMEMO.getId()){
                    templateService.parseXMemoToSaveObject( (String)value,attr.getId(),objectId);
                }
            }catch (ValidationException e){
                //TODO: error handler
            }
            param.put(attr.getName(), value);
        }

        param.put("id",objectId);
        //обновить объект
        customRepository.updateRowInTable(param,tableName);// throws EditException
        return sysObject;
    }

    public Map<String, Object> getObject(@NonNull SysClass sysClass, @NonNull int id) throws EntityNotFoundException{
        return customRepository.getObject(sysClass.getSystemName(), id);
    }

    public Map<String, Object> getObjectMMediaAndXMemo(@NonNull SysClass sysClass, Map<String, Object> obj){
        //TODO: исключение что не нашли
        List<SysAttribute> attributeList = sysClass.getAttributeList();

        for (SysAttribute attribute : attributeList) {
            if(attribute.getAttributeType()==SysTypes.MMEDIA.getId()){
                if(obj.get(attribute.getName()) == null){
                    String replace = String.format(
                            "<div  id=\"div_%s\">" +
                                    "<form id=\"form_%s\" onsubmit=\"sendPost('%s','%s'); " +
                                    "return false;\" method=\"post\" class=\"attribute\">" +
                                        "<input type=\"file\" id=\"%s\">" +
                                        "<p><input type=\"submit\" value=\"Сохранить\"></p>" +
                                    "</form>" +
                            " </div>",
                            attribute.getName(),
                            attribute.getName(),
                            obj.get("id"),
                            attribute.getName(),
                            attribute.getName()
                            );
                    obj.put(attribute.getName(),replace);
                }else{
                    obj.put(attribute.getName(), String.format("<a href=\"%s\">%s</a>",
                                                    UserMode.GetFile.GET_MMEDIA.replace("{id}",obj.get(attribute.getName()).toString()),
                                                    attribute.getName()
                                                    )
                    );
                }
            }else if(attribute.getAttributeType()!=SysTypes.XMEMO.getId()){
                obj.remove(attribute.getName());
            }
        }
        obj.remove("id");
        return obj;
    }

    public Map<String, AttributeAndValue> getObjectForTemplate(Integer objectId) throws EntityNotFoundException{
        SysObject sysObject = sysObjectService.getSysObjectById(objectId);
        SysClass sysClass = sysObject.getOwnerSysClass();
        Map<String, Object> object = customRepository.getObject(sysClass.getSystemName(),objectId);// throws EntityNotFoundException

        Map<String, AttributeAndValue> nameValueFields = new HashMap<>();

        List<SysAttribute> sysAttributes = sysClass.getAttributeList();
        for (SysAttribute attr : sysAttributes) {

            Integer type = attr.getAttributeType();

            AttributeTypeHandler attributeTypeHandler = attributeTypeMap.get(type);
            String value = attributeTypeHandler.toString(object.get(attr.getName()));

            AttributeAndValue attributeAndValue = new AttributeAndValue(
                    attr.getName(),
                    value,
                    SysTypes.getType(attr.getAttributeType()),
                    attr.getId()
            );

            nameValueFields.put(attr.getName(),attributeAndValue);
        }

        //TODO: сравнить что шаблон принадлежит классу

        //SysTemplate template = sysTemplateService.getSysTemplate(templateId);

        return nameValueFields;
    }

}
