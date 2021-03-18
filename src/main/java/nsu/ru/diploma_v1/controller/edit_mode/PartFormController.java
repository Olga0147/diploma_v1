package nsu.ru.diploma_v1.controller.edit_mode;

import lombok.RequiredArgsConstructor;
import nsu.ru.diploma_v1.model.entity.*;
import nsu.ru.diploma_v1.model.enums.database_types.SysTypes;
import nsu.ru.diploma_v1.service.database.SysAssociationService;
import nsu.ru.diploma_v1.service.database.SysClassService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.LinkedList;
import java.util.List;

import static nsu.ru.diploma_v1.configuration.urls.mode.EditMode.PartForm;

@Controller
@RequiredArgsConstructor
public class PartFormController {
    private final SysClassService sysClassService;
    private final SysAssociationService sysAssociationService;

    @GetMapping(PartForm.ATTRIBUTE_FORM)
    public String editNewPage(Model model,@PathVariable String length) {

        model.addAttribute("attributeTypes", SysTypes.getSysTypes());
        model.addAttribute("id",Integer.parseInt(length)+1);

        return "/edit_mode/new_attribute";
    }

    @GetMapping(PartForm.OBJECT_FORM)
    public String editNewObject(Model model, @PathVariable Integer classId) {

        List<SysAttribute> list = sysClassService.getAttributes(classId);
        for (SysAttribute sysAttribute : list) {
            sysAttribute.setOwnerSysClass(null);
        }
        model.addAttribute("attributes", list);
        model.addAttribute("classId", classId);

        model.addAttribute("title", "Создать Объект");

        return "/edit_mode/new_object_by_class";
    }

    @GetMapping(PartForm.ASSOCIATION_IMPL_FORM)
    public String editNewAssociationImpl(Model model, @PathVariable Integer associationId) {

        SysAssociation sysAssociation = sysAssociationService.getSysAssociation(associationId);
        int fromClassId = sysAssociation.getFromClassId();
        int toClassId  = sysAssociation.getToClassId();

        SysClass fromClass = sysClassService.getClassById(fromClassId);
        SysClass toClass = sysClassService.getClassById(toClassId);

        List<SysObject> fromObject = fromClass.getObjectList();
        List<SysObject> toObject = toClass.getObjectList();

        List<String> idAndNameFromObject = new LinkedList<>();
        for (SysObject sysObject : fromObject) {
            idAndNameFromObject.add(String.format("%d",sysObject.getId()));
        }

        List<String> idAndNameToObject = new LinkedList<>();
        for (SysObject sysObject : toObject) {
            idAndNameToObject.add(String.format("%d",sysObject.getId()));
        }

        model.addAttribute("associationId", associationId);
        model.addAttribute("fromObjects", idAndNameFromObject);
        model.addAttribute("toObjects", idAndNameToObject);

        model.addAttribute("title", "Выбрать из доступных Объектов");

        return "/edit_mode/new_associationImpl_by_association";
    }

    @GetMapping(PartForm.TEMPLATE_FORM)
    public String editNewTemplate(Model model, @PathVariable Integer classId) {

        model.addAttribute("classId", classId);

        model.addAttribute("title", "Создать Шаблон");

        return "/edit_mode/new_template_by_class";
    }
}
