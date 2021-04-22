package nsu.ru.diploma_v1.controller.edit_mode;

import lombok.RequiredArgsConstructor;
import nsu.ru.diploma_v1.configuration.urls.menu.EditMenu;
import nsu.ru.diploma_v1.configuration.urls.mode.EditMode;
import nsu.ru.diploma_v1.database.sys.SysTemplateService;
import nsu.ru.diploma_v1.model.entity.SysTemplate;
import nsu.ru.diploma_v1.model.enums.database_types.SysTypes;
import nsu.ru.diploma_v1.template_parse.resource_types.SysResourceType;
import nsu.ru.diploma_v1.database.sys.SysAssociationService;
import nsu.ru.diploma_v1.database.sys.SysClassService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

import static nsu.ru.diploma_v1.configuration.urls.ModePath.EDIT_MODE;
import static nsu.ru.diploma_v1.configuration.urls.mode.EditMode.GetForm;

@Controller
@RequiredArgsConstructor
public class GetFormController {

    private final EditMenu menu;
    private final SysClassService sysClassService;
    private final SysAssociationService sysAssociationService;
    private final SysTemplateService sysTemplateService;

    @GetMapping(EDIT_MODE)
    public String editStart(Model model) {
        model.addAttribute("title", "Старт");

        model.addAttribute("m", menu);

        return "start_edit";
    }

    @GetMapping(GetForm.GET_CLASS)
    public String editNewClass(Model model) {
        model.addAttribute("attributeTypes", SysTypes.getSysTypes());
        model.addAttribute("contentTypes", SysResourceType.values());

        model.addAttribute("title", "Создать Класс");
        model.addAttribute("id",1);

        model.addAttribute("m", menu);

        return "/edit_mode/new_class";
    }

    @GetMapping(GetForm.GET_OBJECT)
    public String editNewObject(Model model) {

        model.addAttribute("classIdents", sysClassService.getAllClassesIds());

        model.addAttribute("title", "Создать Объект");

        model.addAttribute("m", menu);

        return "/edit_mode/new_object";
    }

    @GetMapping(GetForm.GET_AGGREGATION)
    public String editNewAggregation(Model model) {

        model.addAttribute("classIdents", sysClassService.getAllClassesIds());

        model.addAttribute("title", "Создать Агрегацию");

        model.addAttribute("m", menu);

        return "/edit_mode/new_aggregation";
    }

    @GetMapping(GetForm.GET_ASSOCIATION)
    public String editNewAssociation(Model model) {

        List<String> list = sysClassService.getAllClassesIds();
        model.addAttribute("classIdents", list);

        model.addAttribute("title", "Создать Агрегацию");

        model.addAttribute("m", menu);

        return "/edit_mode/new_association";
    }

    @GetMapping(GetForm.GET_ASSOCIATION_IMPL)
    public String editNewAssociationImpl(Model model) {

        model.addAttribute("assIdents", sysAssociationService.getSysAssociationsIdsAndNames());

        model.addAttribute("title", "Создать Ассоциацию между Объектами");

        model.addAttribute("m", menu);

        return "/edit_mode/new_associationImpl";
    }

    @GetMapping(GetForm.GET_TEMPLATE)
    public String editNewTemplate(Model model) {

        model.addAttribute("classIdents", sysClassService.getAllClassesIds());

        model.addAttribute("title", "Создать Шаблон");

        model.addAttribute("m", menu);

        return "/edit_mode/new_template";
    }

    @GetMapping(GetForm.GET_RESOURCE)
    public String editNewResource(Model model) {

        model.addAttribute("classIdents", sysClassService.getAllClassesIds());

        model.addAttribute("title", "Добавить Ресурс");

        model.addAttribute("m", menu);

        return "/edit_mode/new_resource";
    }

    @GetMapping(EditMode.UpdateForm.UPDATE_RESOURCE)
    public String editOldResource(Model model,@PathVariable Integer id) {

        model.addAttribute("resourceId", id);

        model.addAttribute("title", "Изменить Ресурс");

        model.addAttribute("m", menu);

        return "/edit_mode/old_resource";
    }

    @GetMapping(EditMode.UpdateForm.UPDATE_TEMPLATE)
    public String editOldTemplate(Model model,@PathVariable Integer id) {

        SysTemplate template = sysTemplateService.getSysTemplate(id);

        model.addAttribute("templateId", id);

        model.addAttribute("title", "Изменить Шаблон");

        model.addAttribute("name", template.getName());
        model.addAttribute("description", template.getDescription()!=null ?template.getDescription():"");
        model.addAttribute("body", template.getBody());

        model.addAttribute("m", menu);

        return "/edit_mode/old_template_by_class";
    }

}
