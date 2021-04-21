package nsu.ru.diploma_v1.controller.user_mode;

import lombok.RequiredArgsConstructor;
import nsu.ru.diploma_v1.configuration.urls.menu.ViewMenu;
import nsu.ru.diploma_v1.configuration.urls.mode.UserMode;
import nsu.ru.diploma_v1.exception.EntityNotFoundException;
import nsu.ru.diploma_v1.template_parse.TemplateService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PageController {

    private final TemplateService templateService;
    private final ViewMenu menu;

    @GetMapping(UserMode.Show.GET_OBJECT)
    public String getObjectInTemplate(Model model, @PathVariable Integer object_id, @PathVariable Integer id) throws EntityNotFoundException {
        //TODO error all
        String html;
        try {
            html = templateService.getObjectInTemplate(object_id, id);// throws EntityNotFoundException
        }catch (EntityNotFoundException e){
            return "<!DOCTYPE html>\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <title>Ошибка!</title>\n" +
                    "</head>\n" +
                    "<div th:replace=\"fragments/menu :: header\"/>\n" +
                    "<div class = \"inner_text\">\n" +
                    "    <h1>Ошибка!</h1>\n" +
                    "    <div>"+e.getMessage()+"</div>\n" +
                    "</div>\n" +
                    "</html>";
        }
        return html;
    }

}
