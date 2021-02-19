package nsu.ru.diploma_v1.controller.util;

import lombok.RequiredArgsConstructor;
import nsu.ru.diploma_v1.configuration.SysUrl;
import nsu.ru.diploma_v1.service.database.*;
import nsu.ru.diploma_v1.utils.SysTypes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static nsu.ru.diploma_v1.configuration.SysUrl.GET_UTIL_ATTRIBUTE_FORM;

@Controller
@RequiredArgsConstructor
public class UtilController {
    private final SysClassService sysClassService;
    private final SysAggregationService sysAggregationService;
    private final SysAssociationService sysAssociationService;
    private final SysTemplateService sysTemplateService;
    private final SysObjectService sysObjectService;
    private final SysTypes sysTypes;
    private final SysUrl sysUrl;

    @GetMapping(GET_UTIL_ATTRIBUTE_FORM)
    public String editNewPage(Model model,@PathVariable String length) {
        model.addAttribute("attributeTypes",sysTypes.getTypes());
        model.addAttribute("id",Integer.parseInt(length)+1);
        return "/edit_mode/new_attribute";
    }
}
