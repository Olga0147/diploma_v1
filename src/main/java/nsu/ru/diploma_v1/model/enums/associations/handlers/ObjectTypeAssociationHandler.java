package nsu.ru.diploma_v1.model.enums.associations.handlers;

import lombok.RequiredArgsConstructor;
import nsu.ru.diploma_v1.model.entity.SysAssociationImpl;
import nsu.ru.diploma_v1.model.entity.SysObject;
import nsu.ru.diploma_v1.model.enums.associations.AssociationTypeHandler;
import nsu.ru.diploma_v1.model.enums.associations.AssociationTypes;
import nsu.ru.diploma_v1.service.database.SysObjectService;
import nsu.ru.diploma_v1.service.system.TemplateService;
import org.springframework.stereotype.Component;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import java.util.List;

/**
 * Вставить ссылки на объекты по ассоциации
 */
@Component
@RequiredArgsConstructor
public class ObjectTypeAssociationHandler implements AssociationTypeHandler {

    private final TemplateService templateService;
    private final SysObjectService sysObjectService;

    //<association associationId="1" templateId="2" delimiter="<hr>" type="object"></association>

    @Override
    public AssociationTypes getType(){
        return AssociationTypes.OBJECT;
    }

    @Override
    public String handle(int objectId, NamedNodeMap attributes, String innerText){

        Node association = attributes.getNamedItem("associationId");
        Node template = attributes.getNamedItem("templateId");
        Node delimiter = attributes.getNamedItem("delimiter");

        if(association == null || template == null ){
            //TODO: log or tell user
            return "";
        }
        String del = "";
        if(delimiter != null){
            del = delimiter.getTextContent();
        }

        Integer templateId = Integer.parseInt(template.getTextContent());

        SysObject sysObject = sysObjectService.getSysObjectById(objectId);
        List<SysAssociationImpl> list = sysObject.getAssociationImplFromList();

        StringBuilder result = new StringBuilder();

        for (SysAssociationImpl sysAssociation : list) {
            //TODO: проверка, что корректен шаблон для объекта
            String currentResult = templateService.getObjectInTemplate(sysAssociation.getToObjectId(),templateId);
            result.append(currentResult).append(del);
        }
        if(delimiter != null) {
            result.delete(result.length() - del.length() - 1, result.length() - 1);
        }

        String resultWithoutRoot = templateService.clearXMLMeta(result.toString());
        return resultWithoutRoot;
    }
}
