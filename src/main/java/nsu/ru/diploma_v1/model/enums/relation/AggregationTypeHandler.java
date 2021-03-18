package nsu.ru.diploma_v1.model.enums.relation;

import nsu.ru.diploma_v1.model.entity.SysObject;
import org.w3c.dom.NamedNodeMap;

public interface AggregationTypeHandler {

    AggregationTypes getType();

    String handle(SysObject sysObject, NamedNodeMap attributes, String innerText);
}
