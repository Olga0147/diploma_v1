package nsu.ru.diploma_v1.controller.user_mode;

import lombok.RequiredArgsConstructor;
import nsu.ru.diploma_v1.configuration.urls.mode.UserMode;
import nsu.ru.diploma_v1.model.entity.SysClass;
import nsu.ru.diploma_v1.model.entity.SysObject;
import nsu.ru.diploma_v1.model.entity.SysTemplate;
import nsu.ru.diploma_v1.service.database.SysObjectService;
import nsu.ru.diploma_v1.service.database.SysTemplateService;
import nsu.ru.diploma_v1.service.system.CustomService;
import nsu.ru.diploma_v1.service.system.TemplateService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class PageController {

    private final SysObjectService sysObjectService;
    private final CustomService customService;
    private final SysTemplateService sysTemplateService;

    private final TemplateService templateService;

    @GetMapping(UserMode.Show.GET_OBJECT)
    public String getObjectInTemplate(@PathVariable Integer object_id,@PathVariable Integer id){
        SysObject sysObject = sysObjectService.getSysObjectById(object_id);
        SysClass sysClass = sysObject.getOwnerSysClass();
        Map<String, Object> object = customService.getObject(sysClass.getSystemName(),object_id);

        //TODO: сравнить что шаблон принадлежит классу

        SysTemplate template = sysTemplateService.getSysTemplate(id);

        return templateService.parseTemplate(object,template.getBody());
    }
}
