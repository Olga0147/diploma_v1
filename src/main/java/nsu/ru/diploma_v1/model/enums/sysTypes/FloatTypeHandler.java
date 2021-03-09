package nsu.ru.diploma_v1.model.enums.sysTypes;

import nsu.ru.diploma_v1.model.entity.SysAttribute;
import org.springframework.stereotype.Component;

@Component
public class FloatTypeHandler implements AttributeTypeHandler {

    @Override
    public SysTypes getType() {
        return SysTypes.FLOAT;
    }

    @Override
    public Object handle(SysAttribute attribute, Object value) {
        return Float.parseFloat((String) value);
    }


}
