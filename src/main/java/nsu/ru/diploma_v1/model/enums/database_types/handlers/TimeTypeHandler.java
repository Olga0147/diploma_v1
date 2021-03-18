package nsu.ru.diploma_v1.model.enums.database_types.handlers;

import nsu.ru.diploma_v1.model.entity.SysAttribute;
import nsu.ru.diploma_v1.model.enums.database_types.AttributeTypeHandler;
import nsu.ru.diploma_v1.model.enums.database_types.SysTypes;
import org.springframework.stereotype.Component;

@Component
public class TimeTypeHandler implements AttributeTypeHandler {

    @Override
    public SysTypes getType() {
        return SysTypes.TIME;
    }

    @Override
    public Object handle(SysAttribute attribute, Object value) {
        //TODO DATE : convert and check for bd
        return null;
    }

    @Override
    public String toString(Object object) {
        return null;
    }
}
