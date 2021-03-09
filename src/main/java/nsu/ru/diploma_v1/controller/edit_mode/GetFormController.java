package nsu.ru.diploma_v1.controller.edit_mode;

import lombok.RequiredArgsConstructor;
import nsu.ru.diploma_v1.configuration.urls.menu.EditMenu;
import nsu.ru.diploma_v1.model.enums.sysTypes.SysTypes;
import nsu.ru.diploma_v1.service.database.SysAggregationService;
import nsu.ru.diploma_v1.service.database.SysClassService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

import static nsu.ru.diploma_v1.configuration.urls.ModePath.EDIT_MODE;
import static nsu.ru.diploma_v1.configuration.urls.mode.EditMode.GetForm;

@Controller
@RequiredArgsConstructor
public class GetFormController {

    private final EditMenu menu;
    private final SysClassService sysClassService;
    private final SysAggregationService sysAggregationService;

    @GetMapping(EDIT_MODE)
    public String editStart(Model model) {
        model.addAttribute("title", "Старт");

        model.addAttribute("m", menu);

        return "start_edit";
    }

    @GetMapping(GetForm.GET_PAGE)
    public String editNewPage(Model model) {
        model.addAttribute("attributeTypes", SysTypes.getSysTypes());

        model.addAttribute("title", "Создать Страницу");
        model.addAttribute("id",1);

        model.addAttribute("m", menu);

        return "/edit_mode/new_page";
    }

    @GetMapping(GetForm.GET_CLASS)
    public String editNewClass(Model model) {
        model.addAttribute("attributeTypes", SysTypes.getSysTypes());

        model.addAttribute("title", "Создать Класс");
        model.addAttribute("id",1);

        model.addAttribute("m", menu);

        return "/edit_mode/new_class";
    }

    @GetMapping(GetForm.GET_OBJECT)
    public String editNewObject(Model model) {

        model.addAttribute("classIdents", sysClassService.getAllClassesIds());
        model.addAttribute("pageIdents", sysClassService.getAllPagesIds());

        model.addAttribute("title", "Создать Объект");

        model.addAttribute("m", menu);

        return "/edit_mode/new_object";
    }

    @GetMapping(GetForm.GET_AGGREGATION)
    public String editNewAggregation(Model model) {

        model.addAttribute("classIdents", sysClassService.getAllClassesIds());
        model.addAttribute("pageIdents", sysClassService.getAllPagesIds());

        model.addAttribute("title", "Создать Агрегацию");

        model.addAttribute("m", menu);

        return "/edit_mode/new_aggregation";
    }

    @GetMapping(GetForm.GET_ASSOCIATION)
    public String editNewAssociation(Model model) {

        List<String> list = sysClassService.getAllClassesIds();
        list.addAll(sysClassService.getAllPagesIds());
        model.addAttribute("classIdents", list);

        model.addAttribute("title", "Создать Агрегацию");

        model.addAttribute("m", menu);

        return "/edit_mode/new_association";
    }

    @GetMapping(GetForm.GET_COMPOSITION)
    public String editNewComposition(Model model) {

        List<String> list = sysClassService.getAllClassesIds();
        list.addAll(sysClassService.getAllPagesIds());
        model.addAttribute("classIdents", list);

        model.addAttribute("title", "Создать Композицию");

        model.addAttribute("m", menu);

        return "/edit_mode/new_composition";
    }

    @GetMapping(GetForm.GET_AGGREGATION_IMPL)
    public String editNewAggregationImpl(Model model) {

        model.addAttribute("aggrIdents", sysAggregationService.getSysAggregationsIdsAndNames());

        model.addAttribute("title", "Создать Агрегацию между Объектами");

        model.addAttribute("m", menu);

        return "/edit_mode/new_aggregationImpl";
    }

    private String getPath(String str){
        return str.substring(0, str.length() - 4);
        }
}
