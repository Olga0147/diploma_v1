package nsu.ru.diploma_v1.model.enums.database_types.handlers;

import lombok.RequiredArgsConstructor;
import nsu.ru.diploma_v1.model.entity.SysAttribute;
import nsu.ru.diploma_v1.model.entity.SysMmedia;
import nsu.ru.diploma_v1.model.enums.database_types.AttributeTypeHandler;
import nsu.ru.diploma_v1.model.enums.database_types.SysTypes;
import nsu.ru.diploma_v1.template_parse.resource_types.SysResourceType;
import nsu.ru.diploma_v1.database.sys.SysMMediaService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MmediaTypeHandler implements AttributeTypeHandler {

    private final SysMMediaService sysMMediaService;

    @Override
    public SysTypes getType() {
        return SysTypes.MMEDIA;
    }

    @Override
    public Object handle(SysAttribute attribute, Object value) {
        //TODO: MMEDIA : сохранить файл и сохранить к нему путь
        //храним айдишник на таблицу
        return (Integer)value;
    }

    @Override
    public String toString(Object object) {
        if(object == null){
            return "";
        }
        Integer id = (Integer)object;
        SysMmedia mmedia = sysMMediaService.getSysMMediaByMMediaId(id);
        if(mmedia == null){
            return "";
        }
        SysResourceType type = mmedia.getResourceType();
        return SysResourceType.getTagForMMedia(id,type);
    }
}
