package nsu.ru.diploma_v1.model.enums.sysTypes;

import nsu.ru.diploma_v1.model.entity.SysAttribute;
import org.springframework.stereotype.Component;

@Component
public class TextTypeHandler implements AttributeTypeHandler {

    @Override
    public SysTypes getType() {
        return SysTypes.TEXT;
    }

    @Override
    public Object handle(SysAttribute attribute, Object value) {
        return (String)value;
    }
}
