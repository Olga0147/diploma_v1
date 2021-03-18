package nsu.ru.diploma_v1.controller.user_mode;

import lombok.RequiredArgsConstructor;
import nsu.ru.diploma_v1.configuration.urls.mode.UserMode;
import nsu.ru.diploma_v1.model.entity.*;
import nsu.ru.diploma_v1.model.enums.database_types.AttributeTypeHandler;
import nsu.ru.diploma_v1.model.enums.database_types.SysTypes;
import nsu.ru.diploma_v1.service.database.SysObjectService;
import nsu.ru.diploma_v1.service.database.SysTemplateService;
import nsu.ru.diploma_v1.service.system.CustomService;
import nsu.ru.diploma_v1.service.system.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class PageController {

    private final SysObjectService sysObjectService;
    private final CustomService customService;
    private final SysTemplateService sysTemplateService;

    private final TemplateService templateService;
    private final Map<Integer, AttributeTypeHandler> attributeTypeMap = new HashMap<>();

    @Autowired
    public void setAttributeTypes(List<AttributeTypeHandler> types) {
        types.forEach(type -> attributeTypeMap.put(type.getType().getId(), type));
    }

    @GetMapping(UserMode.Show.GET_OBJECT)
    public String getObjectInTemplate(@PathVariable Integer object_id,@PathVariable Integer id){
        SysObject sysObject = sysObjectService.getSysObjectById(object_id);
        SysClass sysClass = sysObject.getOwnerSysClass();
        Map<String, Object> object = customService.getObject(sysClass.getSystemName(),object_id);

        Map<String,AttributeAndValue> nameValueFields = new HashMap<>();

        List<SysAttribute> sysAttributes = sysClass.getAttributeList();
        for (SysAttribute attr : sysAttributes) {

            Integer type = attr.getAttributeType();

            AttributeTypeHandler attributeTypeHandler = attributeTypeMap.get(type);
            String notParsedValue = attributeTypeHandler.toString(object.get(attr.getName()));

            Integer t = attr.getAttributeType();
            int t1 = SysTypes.XMEMO.getId();

            String parsedValue;

            if(t.equals(t1)){
                parsedValue = templateService.parseXmemo(sysObject,notParsedValue);
            }else{
                parsedValue = notParsedValue;
            }

            AttributeAndValue attributeAndValue = new AttributeAndValue(
                    attr.getName(),
                    parsedValue,
                    SysTypes.getType(attr.getAttributeType())
            );

            nameValueFields.put(attr.getName(),attributeAndValue);
        }


        //TODO: сравнить что шаблон принадлежит классу

        SysTemplate template = sysTemplateService.getSysTemplate(id);

        return templateService.parseTemplate(nameValueFields,template.getBody());
       // return templateService.parseTemplate(null,null);
    }

}
