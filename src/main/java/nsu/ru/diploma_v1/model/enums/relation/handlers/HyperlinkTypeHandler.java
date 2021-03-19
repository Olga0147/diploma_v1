package nsu.ru.diploma_v1.model.enums.relation.handlers;

import nsu.ru.diploma_v1.configuration.urls.ModePath;
import nsu.ru.diploma_v1.model.entity.SysObject;
import nsu.ru.diploma_v1.model.enums.relation.AggregationTypeHandler;
import nsu.ru.diploma_v1.model.enums.relation.AggregationTypes;
import org.springframework.stereotype.Component;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

@Component
public class HyperlinkTypeHandler implements AggregationTypeHandler {

    //<div>She reads: <aggregation aggregationId="1" templateId="2" objectId="3" type="hyperlink">Book</aggregation></div>

    //<div>He reads: <aggregation aggregationId="1" templateId="2" objectId="4" type="hyperlink">Bad Book</aggregation></div>

    @Override
    public AggregationTypes getType(){
        return AggregationTypes.HYPERLINK;
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


        return String.format("<a href=\"%s/%d/%d\"> %s </a>",ModePath.USER_MODE,objectId,templateId,innerText);
    }
}
