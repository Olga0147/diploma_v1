package nsu.ru.diploma_v1.model.enums.database_types.handlers;

import nsu.ru.diploma_v1.model.entity.SysAttribute;
import nsu.ru.diploma_v1.model.enums.database_types.AttributeTypeHandler;
import nsu.ru.diploma_v1.model.enums.database_types.SysTypes;
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

    @Override
    public String toString(Object object) {
        return Integer.toString((Integer)object);
    }
}
