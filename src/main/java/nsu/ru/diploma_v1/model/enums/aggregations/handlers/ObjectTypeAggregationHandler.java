package nsu.ru.diploma_v1.model.enums.aggregations.handlers;

import lombok.RequiredArgsConstructor;
import nsu.ru.diploma_v1.model.entity.SysAggregationImpl;
import nsu.ru.diploma_v1.model.enums.aggregations.AggregationTypeHandler;
import nsu.ru.diploma_v1.model.enums.aggregations.AggregationTypes;
import nsu.ru.diploma_v1.service.database.SysAggregationService;
import nsu.ru.diploma_v1.service.system.TemplateService;
import org.springframework.stereotype.Component;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * Вставить весь объект и создать агрегацию
 */
@Component
@RequiredArgsConstructor
public class ObjectTypeAggregationHandler implements AggregationTypeHandler {

    private final TemplateService templateService;
    private final SysAggregationService sysAggregationService;

    //<div><aggregation aggregationId="1" templateId="2" objectId="3" type="object"></aggregation></div>
    //div><aggregation aggregationId="1" templateId="2" objectId="4" type="object"></aggregation></div>

    @Override
    public AggregationTypes getType(){
        return AggregationTypes.OBJECT;
    }

    @Override
    public String handle(NamedNodeMap attributes, String innerText, Integer attributeId, Integer fromObjectId){

        Node template = attributes.getNamedItem("templateId");
        Node object = attributes.getNamedItem("objectId");

        if(attributes.getNamedItem("aggregationId") == null || template == null || object == null){
            //TODO: log or tell user
            return "";
        }

        Integer toTemplateId = Integer.parseInt(template.getTextContent());
        Integer toObjectId = Integer.parseInt(object.getTextContent());

        String result = templateService.getObjectInTemplate(toObjectId,toTemplateId);
        String resultWithoutRoot = templateService.clearXMLMeta(result);
        return resultWithoutRoot;
    }

    @Override
    public Integer checkExist(NamedNodeMap attributes, Integer attributeId, Integer fromObjectId) {
        SysAggregationImpl sysAggregation = getAggregation(attributes, attributeId, fromObjectId);
        boolean check = sysAggregationService.checkExist(sysAggregation);
        if(!check){
            sysAggregationService.saveSysAggregationImpl(sysAggregation);
        }
        return check ? sysAggregation.getId() : null;
    }

    private SysAggregationImpl getAggregation(NamedNodeMap attributes, Integer attributeId, Integer fromObjectId){
        Node aggregation = attributes.getNamedItem("aggregationId");
        Node template = attributes.getNamedItem("templateId");
        Node object = attributes.getNamedItem("objectId");

        Integer aggregationId = Integer.parseInt(aggregation.getTextContent());
        Integer toTemplateId = Integer.parseInt(template.getTextContent());
        Integer toObjectId = Integer.parseInt(object.getTextContent());

        return new SysAggregationImpl(
                aggregationId,
                fromObjectId,
                toObjectId,
                toTemplateId,
                attributeId,
                AggregationTypes.OBJECT);
    }
}
