package nsu.ru.diploma_v1.model.enums.relation.handlers;

import lombok.RequiredArgsConstructor;
import nsu.ru.diploma_v1.model.entity.SysObject;
import nsu.ru.diploma_v1.model.enums.relation.AggregationTypeHandler;
import nsu.ru.diploma_v1.model.enums.relation.AggregationTypes;
import nsu.ru.diploma_v1.service.system.TemplateService;
import org.springframework.stereotype.Component;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

@Component
@RequiredArgsConstructor
public class ObjectTypeHandler implements AggregationTypeHandler {

    private final TemplateService templateService;

    //<div><aggregation aggregationId="1" templateId="2" objectId="3" type="object"></aggregation></div>
    //div><aggregation aggregationId="1" templateId="2" objectId="4" type="object"></aggregation></div>

    @Override
    public AggregationTypes getType(){
        return AggregationTypes.OBJECT;
    }

    @Override
    public String handle(SysObject sysObject, NamedNodeMap attributes, String innerText){

        Node aggregation = attributes.getNamedItem("aggregationId");
        Node template = attributes.getNamedItem("templateId");
        Node object = attributes.getNamedItem("objectId");

        if(aggregation== null || template == null || object == null){
            //TODO: log or tell user
            return "";
        }

        Integer aggregationId = Integer.parseInt(aggregation.getTextContent());
        Integer templateId = Integer.parseInt(template.getTextContent());
        Integer objectId = Integer.parseInt(object.getTextContent());

        //todo make new aggregation impl

        String result = templateService.getObjectInTemplate(objectId,templateId);
        String resultWithoutRoot = templateService.clearXMLMeta(result);
        return resultWithoutRoot;
    }
}
