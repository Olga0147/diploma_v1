package nsu.ru.diploma_v1.model.enums.database;

import nsu.ru.diploma_v1.model.entity.SysAttribute;
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
}
