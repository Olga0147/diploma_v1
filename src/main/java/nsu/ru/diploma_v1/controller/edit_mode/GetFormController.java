package nsu.ru.diploma_v1.controller.edit_mode;

import lombok.RequiredArgsConstructor;
import nsu.ru.diploma_v1.configuration.urls.menu.EditMenu;
import nsu.ru.diploma_v1.service.database.SysClassService;
import nsu.ru.diploma_v1.model.const_data.SystemTypes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import static nsu.ru.diploma_v1.configuration.urls.mode.EditMode.GetForm;
import static nsu.ru.diploma_v1.configuration.urls.ModePath.EDIT_MODE;

@Controller
@RequiredArgsConstructor
public class GetFormController {

    private final EditMenu menu;
    private final SysClassService sysClassService;

    @GetMapping(EDIT_MODE)
    public String editStart(Model model) {
        model.addAttribute("title", "Старт");

        model.addAttribute("m", menu);

        return "start_edit";
    }

    @GetMapping(GetForm.GET_PAGE)
    public String editNewPage(Model model) {
        model.addAttribute("attributeTypes", SystemTypes.getTypes());

        model.addAttribute("title", "Создать Страницу");
        model.addAttribute("id",1);

        model.addAttribute("m", menu);

        return "/edit_mode/new_page";
    }

    @GetMapping(GetForm.GET_CLASS)
    public String editNewClass(Model model) {
        model.addAttribute("attributeTypes", SystemTypes.getTypes());

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

    private String getPath(String str){
        return str.substring(0, str.length() - 4);
        }
}
