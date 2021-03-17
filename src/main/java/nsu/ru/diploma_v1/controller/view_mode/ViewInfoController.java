package nsu.ru.diploma_v1.controller.view_mode;

import lombok.RequiredArgsConstructor;
import nsu.ru.diploma_v1.configuration.urls.menu.ViewMenu;
import nsu.ru.diploma_v1.service.database.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import static nsu.ru.diploma_v1.configuration.urls.ModePath.VIEW_MODE;
import static nsu.ru.diploma_v1.configuration.urls.mode.ViewMode.DetailInfo;
import static nsu.ru.diploma_v1.configuration.urls.mode.ViewMode.Info;

@Controller
@RequiredArgsConstructor
public class ViewInfoController {
    private final SysClassService sysClassService;
    private final SysObjectService sysObjectService;

    private final SysAggregationService sysAggregationService;
    private final SysAssociationService sysAssociationService;

    private final SysTemplateService sysTemplateService;

    private final ViewMenu menu;

    @GetMapping("/")
    public String showStart(Model model) {
        model.addAttribute("title", "Старт");

        model.addAttribute("m", menu);
        return "start";
    }

    @GetMapping(VIEW_MODE)
    public String showViewMode(Model model) {
        return showStart(model);
    }

    @GetMapping(Info.GET_CLASS)
    public String showInfoClassList(Model model) {

        model.addAttribute("classes", sysClassService.getClasses());

        model.addAttribute("title", "Классы");
        model.addAttribute("detailPath", getPath(DetailInfo.GET_CLASS));

        model.addAttribute("m", menu);

        return "/view_mode/info/classes";
    }

    @GetMapping(Info.GET_AGGREGATION)
    public String showInfoAggregationList(Model model) {

        model.addAttribute("aggregations", sysAggregationService.getSysAggregations());

        model.addAttribute("title", "Агрегации");
        model.addAttribute("detailPath", getPath(DetailInfo.GET_AGGREGATION));

        model.addAttribute("m", menu);

        return "/view_mode/info/aggregations";
    }

    @GetMapping(Info.GET_ASSOCIATION)
    public String showInfoAssociationList(Model model) {

        model.addAttribute("associations", sysAssociationService.getSysAssociations());

        model.addAttribute("title", "Ассоциации");
        model.addAttribute("detailPath", getPath(DetailInfo.GET_ASSOCIATION));

        model.addAttribute("m", menu);
        return "/view_mode/info/associations";
    }

    @GetMapping(Info.GET_TEMPLATE)
    public String showInfoTemplatesList(Model model) {

        model.addAttribute("templates", sysTemplateService.getSysTemplates());

        model.addAttribute("title", "Шаблоны");
        model.addAttribute("detailPath", getPath(DetailInfo.GET_TEMPLATE));

        model.addAttribute("m", menu);
        return "/view_mode/info/templates";
    }

    @GetMapping(Info.GET_OBJECT)
    public String showInfoObjectList(Model model) {

        model.addAttribute("objects", sysObjectService.getSysObjects());

        model.addAttribute("title", "Объекты");
        model.addAttribute("detailPath", getPath(DetailInfo.GET_OBJECT));

        model.addAttribute("m", menu);
        return "/view_mode/info/objects";
    }

    @GetMapping(Info.GET_AGGREGATION_IMPL)
    public String showInfoAggregationImplList(Model model) {

        model.addAttribute("aggregationsImpl", sysAggregationService.getSysAggregationsImpl());

        model.addAttribute("title", "Связи");
        model.addAttribute("detailPath", getPath(DetailInfo.GET_AGGREGATION_IMPL));

        model.addAttribute("m", menu);

        return "/view_mode/info/aggregationsI_impl";
    }

    @GetMapping(Info.GET_ASSOCIATION_IMPL)
    public String showInfoAssociationImplList(Model model) {

        model.addAttribute("associationsImpl", sysAssociationService.getSysAssociationsImpl());

        model.addAttribute("title", "Гиперсвязи");
        model.addAttribute("detailPath", getPath(DetailInfo.GET_ASSOCIATION_IMPL));

        model.addAttribute("m", menu);

        return "/view_mode/info/associations_impl";
    }

    private String getPath(String str){
        return str.substring(0, str.length() - 4);
    }
}
