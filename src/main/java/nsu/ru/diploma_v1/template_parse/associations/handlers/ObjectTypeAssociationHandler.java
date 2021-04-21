package nsu.ru.diploma_v1.template_parse.associations.handlers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nsu.ru.diploma_v1.exception.EntityNotFoundException;
import nsu.ru.diploma_v1.model.entity.SysAssociationImpl;
import nsu.ru.diploma_v1.model.entity.SysObject;
import nsu.ru.diploma_v1.template_parse.associations.AssociationTypeHandler;
import nsu.ru.diploma_v1.template_parse.associations.AssociationTypes;
import nsu.ru.diploma_v1.database.sys.SysObjectService;
import nsu.ru.diploma_v1.template_parse.TemplateService;
import org.springframework.stereotype.Component;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import java.util.List;

/**
 * Вставить ссылки на объекты по ассоциации
 */
@Slf4j
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
    public String handle(int objectId, NamedNodeMap attributes, String innerText) throws EntityNotFoundException {

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

        SysObject sysObject;
        try {
            sysObject = sysObjectService.getSysObjectById(objectId);// throws EntityNotFoundException
        }catch (EntityNotFoundException  e){
            log.error(String.format("Не найден объект %d",objectId));
            return "";
        }
        List<SysAssociationImpl> list = sysObject.getAssociationImplFromList();

        StringBuilder result = new StringBuilder();

        for (SysAssociationImpl sysAssociation : list) {
            //TODO: проверка, что корректен шаблон для объекта
            String currentResult = templateService.getObjectInTemplate(sysAssociation.getToObjectId(),templateId);// throws EntityNotFoundException
            result.append(currentResult).append(del);
        }
        if(delimiter != null) {
            result.delete(result.length() - del.length() - 1, result.length() - 1);
        }

        String resultWithoutRoot = templateService.clearXMLMeta(result.toString());
        return resultWithoutRoot;
    }
}
