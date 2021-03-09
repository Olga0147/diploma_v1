package nsu.ru.diploma_v1.model.sysTypes;

import nsu.ru.diploma_v1.model.entity.SysAttribute;
import nsu.ru.diploma_v1.model.enums.SysTypes;
import org.springframework.stereotype.Component;

@Component
public class MmediaTypeHandler implements AttributeTypeHandler {

    @Override
    public SysTypes getType() {
        return SysTypes.MMEDIA;
    }

    @Override
    public Object handle(SysAttribute attribute, Object value) {
        //TODO: MMEDIA : сохранить файл и сохранить к нему путь
        return null;
    }
}
