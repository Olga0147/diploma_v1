package nsu.ru.diploma_v1.controller.edit_mode;

import lombok.RequiredArgsConstructor;
import nsu.ru.diploma_v1.configuration.MenuUrlEdit;
import nsu.ru.diploma_v1.configuration.SysUrl;
import nsu.ru.diploma_v1.model.entity.SysAttribute;
import nsu.ru.diploma_v1.service.database.SysClassService;
import nsu.ru.diploma_v1.utils.SysTypes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

import static nsu.ru.diploma_v1.configuration.PathsModeConstants.GET_EDIT_MODE_PREF;
import static nsu.ru.diploma_v1.configuration.SysUrl.*;

@Controller
@RequiredArgsConstructor
public class EditorController {

    private final SysTypes sysTypes;
    private final SysUrl sysUrl;
    private final MenuUrlEdit menuUrlEdit;
    private final SysClassService sysClassService;

    @GetMapping(GET_EDIT_MODE_PREF)
    public String editStart(Model model) {
        model.addAttribute("title", "Старт");

        model.addAttribute("menu_url", sysUrl);
        model.addAttribute("m", menuUrlEdit);

        return "start_edit";
    }

    @GetMapping(GET_NEW_PAGE)
    public String editNewPage(Model model) {
        model.addAttribute("title", "Создать Страницу");
        model.addAttribute("id",1);
        model.addAttribute("attributeTypes",sysTypes.getTypes());

        model.addAttribute("menu_url", sysUrl);
        model.addAttribute("m", menuUrlEdit);

        return "/edit_mode/new_page";
    }

    @GetMapping(GET_NEW_CLASS)
    public String editNewClass(Model model) {
        model.addAttribute("title", "Создать Класс");
        model.addAttribute("id",1);
        model.addAttribute("attributeTypes",sysTypes.getTypes());

        model.addAttribute("menu_url", sysUrl);
        model.addAttribute("m", menuUrlEdit);

        return "/edit_mode/new_class";
    }

    @GetMapping(GET_NEW_OBJECT_BY_CLASS)
    public String editNewObject(Model model, @PathVariable Integer classId) {
        model.addAttribute("title", "Создать Объект");

        List<SysAttribute> list = sysClassService.getAttributes(classId);
        for (SysAttribute sysAttribute : list) {
            sysAttribute.setOwnerSysClass(null);
        }
        model.addAttribute("attributes", list);
        model.addAttribute("classId", classId);
        model.addAttribute("menu_url", sysUrl);
        model.addAttribute("m", menuUrlEdit);

        return "/edit_mode/new_object_by_class";
    }

    private String getPath(String str){
        return str.substring(0, str.length() - 4);
        }
}
