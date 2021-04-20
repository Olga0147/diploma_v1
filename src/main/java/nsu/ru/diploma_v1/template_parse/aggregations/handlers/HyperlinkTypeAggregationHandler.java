package nsu.ru.diploma_v1.template_parse.aggregations.handlers;

import lombok.RequiredArgsConstructor;
import nsu.ru.diploma_v1.configuration.urls.ModePath;
import nsu.ru.diploma_v1.model.entity.SysAggregationImpl;
import nsu.ru.diploma_v1.template_parse.aggregations.AggregationTypeHandler;
import nsu.ru.diploma_v1.template_parse.aggregations.AggregationTypes;
import nsu.ru.diploma_v1.database.sys.SysAggregationService;
import org.springframework.stereotype.Component;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

@Component
@RequiredArgsConstructor
public class HyperlinkTypeAggregationHandler implements AggregationTypeHandler {

    private final SysAggregationService sysAggregationService;

    //<div>She reads: <aggregation aggregationId="1" templateId="2" objectId="3" type="hyperlink">Book</aggregation></div>

    //<div>He reads: <aggregation aggregationId="1" templateId="2" objectId="4" type="hyperlink">Bad Book</aggregation></div>

    @Override
    public AggregationTypes getType(){
        return AggregationTypes.HYPERLINK;
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

        return String.format("<a href=\"%s/%d/%d\"> %s </a>",ModePath.USER_MODE,toObjectId,toTemplateId,innerText);
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
                AggregationTypes.HYPERLINK);
    }
}
