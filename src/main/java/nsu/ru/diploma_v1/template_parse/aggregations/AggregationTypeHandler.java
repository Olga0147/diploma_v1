package nsu.ru.diploma_v1.template_parse.aggregations;

import org.w3c.dom.NamedNodeMap;

public interface AggregationTypeHandler {

    AggregationTypes getType();

    String handle(NamedNodeMap attributes, String innerText, Integer attributeId, Integer fromObjectId);

    Integer checkExist(NamedNodeMap attributes,Integer attributeId, Integer fromObjectId);
}
