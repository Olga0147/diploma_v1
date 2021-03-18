package nsu.ru.diploma_v1.model.enums.database_types.handlers;

import nsu.ru.diploma_v1.model.entity.SysAttribute;
import nsu.ru.diploma_v1.model.enums.database_types.AttributeTypeHandler;
import nsu.ru.diploma_v1.model.enums.database_types.SysTypes;
import org.springframework.stereotype.Component;

@Component
public class XmemoTypeHandler implements AttributeTypeHandler {

    @Override
    public SysTypes getType() {
        return SysTypes.XMEMO;
    }

    @Override
    public Object handle(SysAttribute attribute, Object value) {
        //TODO : check relations in it, delete old, make new
        return (String)value;
    }

    @Override
    public String toString(Object object) {
        return String.valueOf(object);
    }
}
