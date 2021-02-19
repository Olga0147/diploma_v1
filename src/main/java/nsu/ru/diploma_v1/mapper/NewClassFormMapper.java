package nsu.ru.diploma_v1.mapper;

import lombok.RequiredArgsConstructor;
import nsu.ru.diploma_v1.model.dto.NewAttributeForm;
import nsu.ru.diploma_v1.model.dto.NewClassForm;
import nsu.ru.diploma_v1.model.entity.SysAttribute;
import nsu.ru.diploma_v1.model.entity.SysClass;
import nsu.ru.diploma_v1.utils.SysTypes;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class NewClassFormMapper {

    private final ModelMapper modelMapper;
    private final SysTypes sysTypes;

//    @PostConstruct
//    private void configureMapper() {
//        modelMapper.createTypeMap(NewAttributeForm.class, SysAttribute.class)
//                .addMappings(mapper ->
//                        mapper.using(getNewAttrConverter())
//                                .map(NewAttributeForm::getType, VSysAttribute::setCity));
//    }


    @Transactional
    public SysClass convertToSysClass(NewClassForm newClassForm) {
        return modelMapper.map(newClassForm,SysClass.class);
    }

    @Transactional
    public List<SysAttribute> convertToSysAttribute(List<NewAttributeForm> attributeFormList,Integer classId) {
        //Type listType = new TypeToken<List<SysAttribute>>(){}.getType();

        List<SysAttribute> list = new LinkedList<>();

        for (NewAttributeForm form : attributeFormList) {
            SysAttribute sysAttribute = new SysAttribute();
            sysAttribute.setName(form.getName());
            sysAttribute.setOwnerClassId(classId);
            sysAttribute.setAttributeType(sysTypes.getIdByTypeForUser(form.getType()));
            if(sysTypes.checkNeedSize(form.getType())){sysAttribute.setAttributeSize(form.getSize());}
            sysAttribute.setCanBeNull(form.isNull());
            list.add(sysAttribute);
        }

        return list;
    }

}
