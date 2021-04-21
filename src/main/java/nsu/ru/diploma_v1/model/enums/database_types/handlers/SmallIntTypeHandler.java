package nsu.ru.diploma_v1.model.enums.database_types.handlers;

import nsu.ru.diploma_v1.model.entity.SysAttribute;
import nsu.ru.diploma_v1.model.enums.database_types.AttributeTypeHandler;
import nsu.ru.diploma_v1.model.enums.database_types.SysTypes;
import org.springframework.stereotype.Component;

@Component
public class SmallIntTypeHandler implements AttributeTypeHandler {

    @Override
    public SysTypes getType() {
        return SysTypes.SMALLINT;
    }

    @Override
    public Object handle(SysAttribute attribute, Object value) {
        return Short.parseShort((String) value);
    }

    @Override
    public String toString(Object object) {
        return Integer.toString((Integer)object);
    }
}
