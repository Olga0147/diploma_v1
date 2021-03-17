package nsu.ru.diploma_v1.model.enums.database;

import nsu.ru.diploma_v1.model.entity.SysAttribute;
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
}
