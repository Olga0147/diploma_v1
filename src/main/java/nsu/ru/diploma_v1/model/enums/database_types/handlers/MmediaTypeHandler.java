package nsu.ru.diploma_v1.model.enums.database_types.handlers;

import lombok.RequiredArgsConstructor;
import nsu.ru.diploma_v1.exception.EntityNotFoundException;
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
        return (Integer)value;//храним айдишник в таблице mmedia
    }

    @Override
    public String toString(Object object) {
        if(object == null){
            return "";
        }
        SysMmedia mmedia;
        Integer id = (Integer)object;
        try {
            mmedia = sysMMediaService.getSysMMediaByMMediaId(id);// throws EntityNotFoundException
        }catch (EntityNotFoundException e){
            return "";
        }
        SysResourceType type = mmedia.getResourceType();
        return SysResourceType.getTagForMMedia(id,type);
    }
}
