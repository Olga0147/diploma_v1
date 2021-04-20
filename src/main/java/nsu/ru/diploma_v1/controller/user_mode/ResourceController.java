package nsu.ru.diploma_v1.controller.user_mode;

import lombok.RequiredArgsConstructor;
import nsu.ru.diploma_v1.configuration.urls.mode.UserMode;
import nsu.ru.diploma_v1.model.entity.SysMmedia;
import nsu.ru.diploma_v1.model.entity.SysResource;
import nsu.ru.diploma_v1.database.sys.SysMMediaService;
import nsu.ru.diploma_v1.database.sys.SysResourceService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ResourceController {

    private final SysResourceService sysResourceService;
    private final SysMMediaService sysMMediaService;

    @GetMapping(UserMode.GetFile.GET_RESOURCE)
    public ResponseEntity<byte[]> getResource(@PathVariable Integer id){
        //TODO error all
        SysResource resource = sysResourceService.getSysResourcesByResourceId(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getName() + "\"")
                .body(resource.getData());
    }

    @GetMapping(UserMode.GetFile.GET_MMEDIA)
    public ResponseEntity<byte[]> getMMedia(@PathVariable Integer id){
        SysMmedia media = sysMMediaService.getSysMMediaByMMediaId(id);//EntityNotFoundException
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + media.getName() + "\"")
                .body(media.getData());
    }
}
