package nsu.ru.diploma_v1.mapper;

import lombok.RequiredArgsConstructor;
import nsu.ru.diploma_v1.model.dto.NewAttributeForm;
import nsu.ru.diploma_v1.model.dto.NewClassForm;
import nsu.ru.diploma_v1.model.entity.SysAttribute;
import nsu.ru.diploma_v1.model.entity.SysClass;
import nsu.ru.diploma_v1.model.enums.database_types.SysTypes;
import nsu.ru.diploma_v1.template_parse.resource_types.SysResourceType;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FormMapper {

    private final ModelMapper modelMapper;

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
            sysAttribute.setAttributeType(SysTypes.getIdByUserType(form.getType()));

            if(SysTypes.checkNeedContentType(form.getType())){sysAttribute.setResourceType(SysResourceType.valueOf(form.getContent()));}
            if(SysTypes.checkNeedSize(form.getType())){sysAttribute.setAttributeSize(form.getSize());}

            //ставим, что поля файлы могут быть нулю
            if(SysTypes.checkNeedContentType(form.getType())){sysAttribute.setCanBeNull(false);}
            else{sysAttribute.setCanBeNull(form.isNull());}

            list.add(sysAttribute);
        }

        return list;
    }

}
