package nsu.ru.diploma_v1.controller.edit_mode;

import lombok.RequiredArgsConstructor;
import nsu.ru.diploma_v1.model.entity.SysAttribute;
import nsu.ru.diploma_v1.service.database.*;
import nsu.ru.diploma_v1.model.const_data.SystemTypes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

import static nsu.ru.diploma_v1.configuration.urls.mode.EditMode.PartForm;

@Controller
@RequiredArgsConstructor
public class PartFormController {
    private final SysClassService sysClassService;

    @GetMapping(PartForm.ATTRIBUTE_FORM)
    public String editNewPage(Model model,@PathVariable String length) {

        model.addAttribute("attributeTypes", SystemTypes.getTypes());
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
}
