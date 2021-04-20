package nsu.ru.diploma_v1.controller.user_mode;

import lombok.RequiredArgsConstructor;
import nsu.ru.diploma_v1.configuration.urls.mode.UserMode;
import nsu.ru.diploma_v1.exception.EntityNotFoundException;
import nsu.ru.diploma_v1.template_parse.TemplateService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PageController {

    private final TemplateService templateService;

    @GetMapping(UserMode.Show.GET_OBJECT)
    public String getObjectInTemplate(@PathVariable Integer object_id,@PathVariable Integer id) throws EntityNotFoundException {
        //TODO error all
        return templateService.getObjectInTemplate(object_id, id);// throws EntityNotFoundException
    }

}
