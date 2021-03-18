package nsu.ru.diploma_v1.model.enums.relation.handlers;

import nsu.ru.diploma_v1.model.entity.SysObject;
import nsu.ru.diploma_v1.model.enums.relation.AggregationTypeHandler;
import nsu.ru.diploma_v1.model.enums.relation.AggregationTypes;
import org.springframework.stereotype.Component;
import org.w3c.dom.NamedNodeMap;

@Component
public class ObjectTypeHandler implements AggregationTypeHandler {

    @Override
    public AggregationTypes getType(){
        return AggregationTypes.OBJECT;
    }

    @Override
    public String handle(SysObject sysObject, NamedNodeMap attributes, String innerText){
        return "NULL";
    }
}
