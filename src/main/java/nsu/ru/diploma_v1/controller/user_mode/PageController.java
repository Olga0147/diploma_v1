package nsu.ru.diploma_v1.controller.user_mode;

import lombok.RequiredArgsConstructor;
import nsu.ru.diploma_v1.configuration.urls.mode.UserMode;
import nsu.ru.diploma_v1.service.database.SysObjectService;
import nsu.ru.diploma_v1.service.database.SysTemplateService;
import nsu.ru.diploma_v1.service.system.CustomService;
import nsu.ru.diploma_v1.service.system.TemplateService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PageController {

    private final SysObjectService sysObjectService;
    private final CustomService customService;

    private final SysTemplateService sysTemplateService;
    private final TemplateService templateService;

    @GetMapping(UserMode.Show.GET_OBJECT)
    public String getObjectInTemplate(@PathVariable Integer object_id,@PathVariable Integer id){
        //TODO error all
        return templateService.getObjectInTemplate(object_id, id);
    }

}
