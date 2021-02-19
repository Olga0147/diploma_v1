package nsu.ru.diploma_v1.controller.view_mode;

import lombok.RequiredArgsConstructor;
import nsu.ru.diploma_v1.configuration.MenuUrlView;
import nsu.ru.diploma_v1.configuration.SysUrl;
import nsu.ru.diploma_v1.model.entity.*;
import nsu.ru.diploma_v1.service.database.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

import static nsu.ru.diploma_v1.configuration.SysUrl.*;

@Controller
@RequiredArgsConstructor
public class ViewDetailInfoController {
    private final SysClassService sysClassService;
    private final SysAggregationService sysAggregationService;
    private final SysAssociationService sysAssociationService;
    private final SysTemplateService sysTemplateService;
    private final SysObjectService sysObjectService;

    private final MenuUrlView menuUrlView;
    private final SysUrl sysUrl;

    @GetMapping(GET_DETAIL_INFO_PAGE)
    public String showInfoPage(Model model, @PathVariable String id) {

        SysClass sysClass = sysClassService.getClass(Integer.parseInt(id));
        List<SysAttribute> attributeList = sysClass.getAttributeList();

        List<SysAggregation> aggregationFromList = sysClass.getAggregationFromList();
        List<SysAggregation> aggregationToList = sysClass.getAggregationToList();

        List<SysAssociation> associationFromList = sysClass.getAssociationFromList();
        List<SysAssociation> associationToList = sysClass.getAssociationToList();

        List<SysTemplate> templateList = sysClass.getTemplateList();
        List<SysObject> objectList = sysClass.getObjectList();

        model.addAttribute("page", sysClass);
        model.addAttribute("attributes", attributeList);

        model.addAttribute("aggregationsF", aggregationFromList);
        model.addAttribute("aggregationsT", aggregationToList);

        model.addAttribute("associationsF", associationFromList);
        model.addAttribute("associationsT", associationToList);

        model.addAttribute("templates", templateList);
        model.addAttribute("objects", objectList);

        if(sysClass.isPage()){
            model.addAttribute("title", "Страница: детально");
        }
        else {
            model.addAttribute("title", "Класс: детально");
        }
        model.addAttribute("detailClassPath", GET_DETAIL_INFO_CLASS.substring(0, GET_DETAIL_INFO_CLASS.length() - 4));
        model.addAttribute("detailAssociationPath", GET_DETAIL_INFO_ASSOCIATION.substring(0, GET_DETAIL_INFO_ASSOCIATION.length() - 4));
        model.addAttribute("detailAggregationPath", GET_DETAIL_INFO_AGGREGATION.substring(0, GET_DETAIL_INFO_AGGREGATION.length() - 4));
        model.addAttribute("detailTemplatePath", GET_DETAIL_INFO_TEMPLATE.substring(0, GET_DETAIL_INFO_TEMPLATE.length() - 4));
        model.addAttribute("detailObjectPath", GET_DETAIL_INFO_OBJECT.substring(0, GET_DETAIL_INFO_OBJECT.length() - 4));

        model.addAttribute("menu_url", sysUrl);
        model.addAttribute("m", menuUrlView);

        return "/view_mode/detail_info/detail_pages";
    }

    @GetMapping(GET_DETAIL_INFO_CLASS)
    public String showInfoClass(Model model, @PathVariable String id) {
        return showInfoPage(model,id);
    }

    @GetMapping(GET_DETAIL_INFO_AGGREGATION)
    public String showInfoAggregation(Model model, @PathVariable String id) {

        SysAggregation aggregation = sysAggregationService.getSysAggregation(Integer.parseInt(id));

        List<SysAggregationImpl> aggregationImplList = aggregation.getSysAggregationList();

        model.addAttribute("aggregation", aggregation);
        model.addAttribute("aggregationsImpl", aggregationImplList);

        model.addAttribute("title", "Агрегация: детально");
        model.addAttribute("detailObjectPath", GET_DETAIL_INFO_OBJECT.substring(0, GET_DETAIL_INFO_OBJECT.length() - 4));
        model.addAttribute("detailClassPath", GET_DETAIL_INFO_CLASS.substring(0, GET_DETAIL_INFO_CLASS.length() - 4));
        model.addAttribute("detailAggregationImplPath", GET_DETAIL_INFO_AGGREGATION_IMPL.substring(0, GET_DETAIL_INFO_AGGREGATION_IMPL.length() - 4));

        model.addAttribute("menu_url", sysUrl);
        model.addAttribute("m", menuUrlView);

        return "/view_mode/detail_info/detail_aggregation";
    }

    @GetMapping(GET_DETAIL_INFO_ASSOCIATION)
    public String showInfoAssociation(Model model, @PathVariable String id) {

        SysAssociation association = sysAssociationService.getSysAssociation(Integer.parseInt(id));

        List<SysAssociationImpl> associationImplList = association.getSysAssociationList();

        model.addAttribute("association", association);
        model.addAttribute("associationsImpl", associationImplList);

        model.addAttribute("title", "Ассоциация: детально");
        model.addAttribute("detailObjectPath", GET_DETAIL_INFO_OBJECT.substring(0, GET_DETAIL_INFO_OBJECT.length() - 4));
        model.addAttribute("detailClassPath", GET_DETAIL_INFO_CLASS.substring(0, GET_DETAIL_INFO_CLASS.length() - 4));
        model.addAttribute("detailAssociationImplPath", GET_DETAIL_INFO_ASSOCIATION_IMPL.substring(0, GET_DETAIL_INFO_ASSOCIATION_IMPL.length() - 4));

        model.addAttribute("menu_url", sysUrl);
        model.addAttribute("m", menuUrlView);

        return "/view_mode/detail_info/detail_association";
    }

    @GetMapping(GET_DETAIL_INFO_TEMPLATE)
    public String showInfoTemplate(Model model, @PathVariable String id) {

        SysTemplate template = sysTemplateService.getSysTemplate(Integer.parseInt(id));

        model.addAttribute("template", template);

        model.addAttribute("title", "Шаблон: детально");
        model.addAttribute("detailClassPath", GET_DETAIL_INFO_CLASS.substring(0, GET_DETAIL_INFO_CLASS.length() - 4));

        model.addAttribute("menu_url", sysUrl);
        model.addAttribute("m", menuUrlView);

        return "/view_mode/detail_info/detail_template";
    }

    @GetMapping(GET_DETAIL_INFO_OBJECT)
    public String showInfoObject(Model model, @PathVariable String id) {

        SysObject sysObject = sysObjectService.getObjectsInfoById(Integer.parseInt(id));
        SysClass sysClass = sysObject.getOwnerSysClass();

        //todo : если ресурс - ммедиа, то ссылка на него прямая
        Map<String, Object> object = sysObjectService.getObjectDetailInfo(Integer.parseInt(id));


        List<SysAggregationImpl> aggregationImplFromList = sysObject.getAggregationImplFromList();
        List<SysAggregationImpl> aggregationImplToList = sysObject.getAggregationImplToList();

        List<SysAssociationImpl> associationImplFromList = sysObject.getAssociationImplFromList();
        List<SysAssociationImpl> associationImplToList = sysObject.getAssociationImplToList();

        List<SysTemplate> templateList = sysClass.getTemplateList();

        model.addAttribute("object", object.entrySet());

        model.addAttribute("aggregationsImplF", aggregationImplFromList);
        model.addAttribute("aggregationsImplT", aggregationImplToList);

        model.addAttribute("associationsImplF", associationImplFromList);
        model.addAttribute("associationsImplT", associationImplToList);

        model.addAttribute("templates", templateList);

        model.addAttribute("class", sysClass);

        model.addAttribute("title", "Объект: детально");

        model.addAttribute("objectInTemplatePath", getPath(GET_SHOW_OBJECT.replace("{object_id}",String.valueOf(sysObject.getId()))) );
        model.addAttribute("detailAssociationImplPath", getPath(GET_DETAIL_INFO_ASSOCIATION_IMPL) );
        model.addAttribute("detailAggregationImplPath", getPath(GET_DETAIL_INFO_AGGREGATION_IMPL) );
        model.addAttribute("detailTemplatePath", getPath(GET_DETAIL_INFO_TEMPLATE) );
        model.addAttribute("detailClassPath", getPath(GET_DETAIL_INFO_CLASS) );
        model.addAttribute("detailObjectPath", getPath(GET_DETAIL_INFO_OBJECT) );

        model.addAttribute("menu_url", sysUrl);
        model.addAttribute("m", menuUrlView);

        return "/view_mode/detail_info/detail_object";
        }

    @GetMapping(GET_DETAIL_INFO_AGGREGATION_IMPL)
    public String showInfoAggregationImpl(Model model, @PathVariable String id) {

        SysAggregationImpl aggregationImpl = sysAggregationService.getSysAggregationImpl(Integer.parseInt(id));

        model.addAttribute("aggregationImpl", aggregationImpl);

        model.addAttribute("title", "Связь: детально");
        model.addAttribute("detailObjectPath", getPath(GET_DETAIL_INFO_OBJECT));
        model.addAttribute("detailAggregationPath", getPath(GET_DETAIL_INFO_AGGREGATION));

        model.addAttribute("menu_url", sysUrl);
        model.addAttribute("m", menuUrlView);

        return "/view_mode/detail_info/detail_aggregation_impl";
    }

    @GetMapping(GET_DETAIL_INFO_ASSOCIATION_IMPL)
    public String showInfoAssociationImpl(Model model, @PathVariable String id) {

        SysAssociationImpl associationImpl = sysAssociationService.getSysAssociationImpl(Integer.parseInt(id));

        model.addAttribute("associationImpl", associationImpl);

        model.addAttribute("title", " Гипервязь: детально");
        model.addAttribute("detailObjectPath", getPath(GET_DETAIL_INFO_OBJECT));
        model.addAttribute("detailAssociationPath", getPath(GET_DETAIL_INFO_ASSOCIATION));

        model.addAttribute("menu_url", sysUrl);
        model.addAttribute("m", menuUrlView);

        return "/view_mode/detail_info/detail_association_impl";
    }

        private String getPath(String str){
        return str.substring(0, str.length() - 4);
        }
}
