package nsu.ru.diploma_v1.model.enums.sysTypes;

import nsu.ru.diploma_v1.model.entity.SysAttribute;
import org.springframework.stereotype.Component;

@Component
public class IntegerTypeHandler implements AttributeTypeHandler {

    @Override
    public SysTypes getType() {
        return SysTypes.INTEGER;
    }

    @Override
    public Object handle(SysAttribute attributev, Object value) {
        return Integer.parseInt((String) value);
    }
}
