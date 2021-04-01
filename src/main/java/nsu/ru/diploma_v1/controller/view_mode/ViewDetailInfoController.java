package nsu.ru.diploma_v1.controller.view_mode;

import lombok.RequiredArgsConstructor;
import nsu.ru.diploma_v1.configuration.urls.menu.ViewMenu;
import nsu.ru.diploma_v1.configuration.urls.mode.UserMode;
import nsu.ru.diploma_v1.model.entity.*;
import nsu.ru.diploma_v1.service.database.*;
import nsu.ru.diploma_v1.service.system.CustomService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

import static nsu.ru.diploma_v1.configuration.urls.mode.UserMode.Show;
import static nsu.ru.diploma_v1.configuration.urls.mode.ViewMode.DetailInfo;

@Controller
@RequiredArgsConstructor
public class ViewDetailInfoController {
    private final SysClassService sysClassService;
    private final SysAggregationService sysAggregationService;
    private final SysAssociationService sysAssociationService;
    private final SysTemplateService sysTemplateService;
    private final SysObjectService sysObjectService;
    private final CustomService customService;
    private final SysResourceService sysResourceService;

    private final ViewMenu menu;

    @GetMapping(DetailInfo.GET_CLASS)
    public String showInfoClass(Model model, @PathVariable String id) {

        //TODO: ERROR NOT FOUND
        SysClass sysClass = sysClassService.getClassById(Integer.parseInt(id));

        model.addAttribute("page", sysClass);
        model.addAttribute("attributes", sysClass.getAttributeList());
        model.addAttribute("aggregationsF", sysClass.getAggregationFromList());
        model.addAttribute("aggregationsT", sysClass.getAggregationToList());
        model.addAttribute("associationsF", sysClass.getAssociationFromList());
        model.addAttribute("associationsT", sysClass.getAssociationToList());
        model.addAttribute("templates", sysClass.getTemplateList());
        model.addAttribute("objects", sysClass.getObjectList());
        model.addAttribute("title", "Класс: детально");

        model.addAttribute("detailClassPath", getPath(DetailInfo.GET_CLASS));
        model.addAttribute("detailAssociationPath", getPath(DetailInfo.GET_ASSOCIATION));
        model.addAttribute("detailAggregationPath", getPath(DetailInfo.GET_AGGREGATION));
        model.addAttribute("detailTemplatePath", getPath(DetailInfo.GET_TEMPLATE));
        model.addAttribute("detailObjectPath", getPath(DetailInfo.GET_OBJECT));

        model.addAttribute("m", menu);

        return "/view_mode/detail_info/detail_class";
    }

    @GetMapping(DetailInfo.GET_AGGREGATION)
    public String showInfoAggregation(Model model, @PathVariable String id) {

        //TODO: ERROR NOT FOUND
        SysAggregation aggregation = sysAggregationService.getSysAggregation(Integer.parseInt(id));

        model.addAttribute("aggregation", aggregation);
        model.addAttribute("aggregationsImpl", aggregation.getSysAggregationList());

        model.addAttribute("title", "Агрегация: детально");
        model.addAttribute("detailObjectPath", getPath(DetailInfo.GET_OBJECT));
        model.addAttribute("detailClassPath", getPath(DetailInfo.GET_CLASS));
        model.addAttribute("detailAggregationImplPath", getPath(DetailInfo.GET_AGGREGATION_IMPL));

        model.addAttribute("m", menu);

        return "/view_mode/detail_info/detail_aggregation";
    }

    @GetMapping(DetailInfo.GET_ASSOCIATION)
    public String showInfoAssociation(Model model, @PathVariable String id) {

        //TODO: ERROR NOT FOUND
        SysAssociation association = sysAssociationService.getSysAssociation(Integer.parseInt(id));

        model.addAttribute("association", association);
        model.addAttribute("associationsImpl", association.getSysAssociationList());

        model.addAttribute("title", "Ассоциация: детально");
        model.addAttribute("detailObjectPath", getPath(DetailInfo.GET_OBJECT));
        model.addAttribute("detailClassPath", getPath(DetailInfo.GET_CLASS));
        model.addAttribute("detailAssociationImplPath", getPath(DetailInfo.GET_ASSOCIATION_IMPL));

        model.addAttribute("m", menu);

        return "/view_mode/detail_info/detail_association";
    }

    @GetMapping(DetailInfo.GET_TEMPLATE)
    public String showInfoTemplate(Model model, @PathVariable Integer id) {

        //TODO: ERROR NOT FOUND
        SysTemplate template = sysTemplateService.getSysTemplate(id);

        model.addAttribute("template", template);

        model.addAttribute("title", "Шаблон: детально");
        model.addAttribute("detailClassPath", getPath(DetailInfo.GET_CLASS));

        model.addAttribute("m", menu);

        return "/view_mode/detail_info/detail_template";
    }

    @GetMapping(DetailInfo.GET_OBJECT)
    public String showInfoObject(Model model, @PathVariable Integer id) {

        //TODO: ERROR NOT FOUND
        SysObject sysObject = sysObjectService.getSysObjectById(id);
        SysClass sysClass = sysObject.getOwnerSysClass();
        Map<String, Object> object = customService.getObject(sysClass,id);

        Map<String, Object> objectMMediaAndXMemo = customService.getObjectMMediaAndXMemo(sysClass,id);

        model.addAttribute("id", String.valueOf(sysObject.getId()));
        model.addAttribute("object", object.entrySet());
        model.addAttribute("objectMMediaAndXMemo", objectMMediaAndXMemo);

        model.addAttribute("aggregationsImplF", sysObject.getAggregationImplFromList());
        model.addAttribute("aggregationsImplT", sysObject.getAggregationImplToList());

        model.addAttribute("associationsImplF", sysObject.getAssociationImplFromList());
        model.addAttribute("associationsImplT", sysObject.getAssociationImplToList());

        model.addAttribute("templates", sysClass.getTemplateList());
        model.addAttribute("class", sysClass);

        model.addAttribute("title", "Объект: детально");
        String objectInTemplatePath = Show.GET_OBJECT.replace("{object_id}", String.valueOf(sysObject.getId()));
        model.addAttribute("objectInTemplatePath", getPath(objectInTemplatePath));
        model.addAttribute("detailAssociationImplPath", getPath(DetailInfo.GET_ASSOCIATION_IMPL) );
        model.addAttribute("detailAggregationImplPath", getPath(DetailInfo.GET_AGGREGATION_IMPL) );
        model.addAttribute("detailTemplatePath", getPath(DetailInfo.GET_TEMPLATE) );
        model.addAttribute("detailClassPath", getPath(DetailInfo.GET_CLASS) );
        model.addAttribute("detailObjectPath", getPath(DetailInfo.GET_OBJECT) );

        model.addAttribute("m", menu);

        return "/view_mode/detail_info/detail_object";
        }

    @GetMapping(DetailInfo.GET_AGGREGATION_IMPL)
    public String showInfoAggregationImpl(Model model, @PathVariable Integer id) {

        //TODO: ERROR NOT FOUND
        SysAggregationImpl aggregationImpl = sysAggregationService.getSysAggregationImpl(id);

        model.addAttribute("aggregationImpl", aggregationImpl);

        model.addAttribute("title", "Связь: детально");
        model.addAttribute("detailObjectPath", getPath(DetailInfo.GET_OBJECT));
        model.addAttribute("detailAggregationPath", getPath(DetailInfo.GET_AGGREGATION));

        model.addAttribute("m", menu);

        return "/view_mode/detail_info/detail_aggregation_impl";
    }

    @GetMapping(DetailInfo.GET_ASSOCIATION_IMPL)
    public String showInfoAssociationImpl(Model model, @PathVariable Integer id) {

        //TODO: ERROR NOT FOUND
        SysAssociationImpl associationImpl = sysAssociationService.getSysAssociationImpl(id);

        model.addAttribute("associationImpl", associationImpl);

        model.addAttribute("title", " Гиперсвязь: детально");
        model.addAttribute("detailObjectPath", getPath(DetailInfo.GET_OBJECT));
        model.addAttribute("detailAssociationPath", getPath(DetailInfo.GET_ASSOCIATION));

        model.addAttribute("m", menu);

        return "/view_mode/detail_info/detail_association_impl";
    }

    @GetMapping(DetailInfo.GET_RESOURCE)
    public String showInfoResource(Model model, @PathVariable Integer id) {

        //TODO: ERROR NOT FOUND
        SysResource template = sysResourceService.getSysResourcesByResourceId(id);

        model.addAttribute("resource", template);

        model.addAttribute("title", "Ресурс: детально");
        model.addAttribute("detailClassPath", getPath(DetailInfo.GET_CLASS));
        model.addAttribute("downloadPath", getPath(UserMode.GetFile.GET_RESOURCE));

        model.addAttribute("m", menu);

        return "/view_mode/detail_info/detail_resource";
    }

    private String getPath(String str){
        return str.substring(0, str.length() - 4);
        }
}
