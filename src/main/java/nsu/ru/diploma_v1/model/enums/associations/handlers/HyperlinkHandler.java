package nsu.ru.diploma_v1.model.enums.associations.handlers;

import lombok.RequiredArgsConstructor;
import nsu.ru.diploma_v1.configuration.urls.ModePath;
import nsu.ru.diploma_v1.model.entity.SysAssociationImpl;
import nsu.ru.diploma_v1.model.entity.SysObject;
import nsu.ru.diploma_v1.model.enums.aggregations.AggregationTypeHandler;
import nsu.ru.diploma_v1.model.enums.aggregations.AggregationTypes;
import nsu.ru.diploma_v1.model.enums.associations.AssociationTypeHandler;
import nsu.ru.diploma_v1.model.enums.associations.AssociationTypes;
import nsu.ru.diploma_v1.service.database.SysObjectService;
import nsu.ru.diploma_v1.service.system.TemplateService;
import org.springframework.stereotype.Component;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import java.util.List;

@Component
@RequiredArgsConstructor
public class HyperlinkHandler implements AssociationTypeHandler {

    private final TemplateService templateService;
    private final SysObjectService sysObjectService;

    //<div><association associationId="2" templateId="5" type="hyperlink">TITLE AUTHOR</association></div>

    @Override
    public AssociationTypes getType(){
        return AssociationTypes.HYPERLINK;
    }

    @Override
    public String handle(int objectId, NamedNodeMap attributes, String innerText){

    //<association associationId="1" templateId="2" delimiter="<br>" type="hyperlink">TITLE AUTHOR</association>

        Node association = attributes.getNamedItem("associationId");
        Node template = attributes.getNamedItem("templateId");
        Node delimiter = attributes.getNamedItem("delimiter");
        String[] fields = innerText.split(" ");

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

            Integer toSysObjectId = sysAssociation.getToSysObject().getId();
            String inner = templateService.getObjectFields(fields,toSysObjectId);
            //TODO: проверка, что корректен шаблон для объекта
            String currentResult = String.format("<a href=\"%s/%d/%d\"> %s </a>",ModePath.USER_MODE,toSysObjectId,templateId,inner);
            result.append(currentResult).append(del);
        }
        if(delimiter != null) {
            result.delete(result.length() - del.length() - 1, result.length() - 1);
        }
        String resultWithoutRoot = templateService.clearXMLMeta(result.toString());
        return resultWithoutRoot;

    }
}
