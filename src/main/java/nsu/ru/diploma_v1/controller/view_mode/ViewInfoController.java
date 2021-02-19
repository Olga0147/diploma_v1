package nsu.ru.diploma_v1.controller.view_mode;

import lombok.RequiredArgsConstructor;
import nsu.ru.diploma_v1.configuration.MenuUrlView;
import nsu.ru.diploma_v1.configuration.SysUrl;
import nsu.ru.diploma_v1.model.entity.*;
import nsu.ru.diploma_v1.repository.CustomRepository;
import nsu.ru.diploma_v1.service.database.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

import static nsu.ru.diploma_v1.configuration.PathsModeConstants.GET_VIEW_MODE_PREF;
import static nsu.ru.diploma_v1.configuration.SysUrl.*;

@Controller
@RequiredArgsConstructor
public class ViewInfoController {
    private final SysClassService sysClassService;
    private final SysObjectService sysObjectService;

    private final SysAggregationService sysAggregationService;
    private final SysAssociationService sysAssociationService;

    private final SysTemplateService sysTemplateService;
    private final CustomRepository customRepository;

    private final MenuUrlView menuUrlView;
    private final SysUrl sysUrl;

    @GetMapping("/")
    public String showStart(Model model) {
        model.addAttribute("title", "Старт");

        model.addAttribute("menu_url", sysUrl);
        model.addAttribute("m", menuUrlView);
        return "start";
    }

    @GetMapping(GET_VIEW_MODE_PREF)
    public String showViewMode(Model model) {
        return showStart(model);
    }

    @GetMapping(GET_INFO_PAGE)
    public String showInfoPageList(Model model) {
        List<SysClass> sysClassList = sysClassService.getPages();

        model.addAttribute("pages", sysClassList);
        model.addAttribute("title", "Страницы");
        model.addAttribute("detailPath", GET_DETAIL_INFO_PAGE.substring(0, GET_DETAIL_INFO_PAGE.length() - 4));

        model.addAttribute("menu_url", sysUrl);
        model.addAttribute("m", menuUrlView);
        return "/view_mode/info/pages";
    }

    @GetMapping(GET_INFO_CLASS)
    public String showInfoClassList(Model model) {
        List<SysClass> sysClassList = sysClassService.getClasses();

        model.addAttribute("classes", sysClassList);
        model.addAttribute("title", "Классы");
        model.addAttribute("detailPath", GET_DETAIL_INFO_CLASS.substring(0, GET_DETAIL_INFO_CLASS.length() - 4));

        model.addAttribute("menu_url", sysUrl);
        model.addAttribute("m", menuUrlView);

        return "/view_mode/info/classes";
    }

    @GetMapping(GET_INFO_AGGREGATION)
    public String showInfoAggregationList(Model model) {
        List<SysAggregation> sysAggregations = sysAggregationService.getSysAggregations();

        model.addAttribute("aggregations", sysAggregations);
        model.addAttribute("title", "Агрегации");
        model.addAttribute("detailPath", GET_DETAIL_INFO_AGGREGATION.substring(0, GET_DETAIL_INFO_AGGREGATION.length() - 4));

        model.addAttribute("menu_url", sysUrl);
        model.addAttribute("m", menuUrlView);

        return "/view_mode/info/aggregations";
    }

    @GetMapping(GET_INFO_ASSOCIATION)
    public String showInfoAssociationList(Model model) {
        List<SysAssociation> sysAssociations = sysAssociationService.getSysAssociations();

        model.addAttribute("associations", sysAssociations);
        model.addAttribute("title", "Ассоциации");
        model.addAttribute("detailPath", GET_DETAIL_INFO_ASSOCIATION.substring(0, GET_DETAIL_INFO_ASSOCIATION.length() - 4));

        model.addAttribute("menu_url", sysUrl);
        model.addAttribute("m", menuUrlView);
        return "/view_mode/info/associations";
    }

    @GetMapping(GET_INFO_TEMPLATE)
    public String showInfoTemplatesList(Model model) {
        List<SysTemplate> sysTemplateList = sysTemplateService.getSysTemplates();

        model.addAttribute("templates", sysTemplateList);
        model.addAttribute("title", "Шаблоны");
        model.addAttribute("detailPath", GET_DETAIL_INFO_TEMPLATE.substring(0, GET_DETAIL_INFO_TEMPLATE.length() - 4));

        model.addAttribute("menu_url", sysUrl);
        model.addAttribute("m", menuUrlView);
        return "/view_mode/info/templates";
    }

    @GetMapping(GET_INFO_OBJECT)
    public String showInfoObjectList(Model model) {
        List<SysObject> sysTemplateList = sysObjectService.getObjectsInfo();

        model.addAttribute("objects", sysTemplateList);
        model.addAttribute("title", "Объекты");
        model.addAttribute("detailPath", GET_DETAIL_INFO_OBJECT.substring(0, GET_DETAIL_INFO_OBJECT.length() - 4));

        model.addAttribute("menu_url", sysUrl);
        model.addAttribute("m", menuUrlView);
        return "/view_mode/info/objects";
    }

    @GetMapping(GET_INFO_AGGREGATION_IMPL)
    public String showInfoAggregationImplList(Model model) {
        List<SysAggregationImpl> sysAggregations = sysAggregationService.getSysAggregationsImpl();

        model.addAttribute("aggregationsImpl", sysAggregations);
        model.addAttribute("title", "Связи");
        model.addAttribute("detailPath", getPath(GET_DETAIL_INFO_AGGREGATION_IMPL));

        model.addAttribute("menu_url", sysUrl);
        model.addAttribute("m", menuUrlView);

        return "/view_mode/info/aggregationsI_impl";
    }

    @GetMapping(GET_INFO_ASSOCIATION_IMPL)
    public String showInfoAssociationImplList(Model model) {
        List<SysAssociationImpl> sysAssociations = sysAssociationService.getSysAssociationsImpl();

        model.addAttribute("associationsImpl", sysAssociations);
        model.addAttribute("title", "Гиперсвязи");
        model.addAttribute("detailPath", getPath(GET_DETAIL_INFO_ASSOCIATION_IMPL));

        model.addAttribute("menu_url", sysUrl);
        model.addAttribute("m", menuUrlView);

        return "/view_mode/info/associations_impl";
    }

    private String getPath(String str){
        return str.substring(0, str.length() - 4);
    }
}
