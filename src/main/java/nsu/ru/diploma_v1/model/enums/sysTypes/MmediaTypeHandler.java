package nsu.ru.diploma_v1.model.enums.sysTypes;

import nsu.ru.diploma_v1.model.entity.SysAttribute;
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