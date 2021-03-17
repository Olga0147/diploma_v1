package nsu.ru.diploma_v1.model.enums.database;

import nsu.ru.diploma_v1.model.entity.SysAttribute;
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
}
