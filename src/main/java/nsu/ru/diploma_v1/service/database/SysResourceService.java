package nsu.ru.diploma_v1.service.database;

import lombok.RequiredArgsConstructor;
import nsu.ru.diploma_v1.model.entity.SysResource;
import nsu.ru.diploma_v1.model.enums.resource_types.SysResourceType;
import nsu.ru.diploma_v1.repository.SysResourceRepository;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SysResourceService {

    private final SysResourceRepository sysResourceRepository;

    public List<SysResource> getSysResources(){
        return sysResourceRepository.findAll();
    }

    public List<SysResource> getSysResourcesByClassId(int classId){
        return sysResourceRepository.getSysResourceByOwnerClassId(classId);
    }

    public SysResource getSysResourcesByResourceId(int resourceId){
        return sysResourceRepository.getSysResourceById(resourceId);
    }

    public SysResource saveSysResource(SysResource sysResource){
        return sysResourceRepository.save(sysResource);
    }

    public Node getTag(String resourceId, Document doc){
        SysResource resource = sysResourceRepository.getSysResourceById(Integer.parseInt(resourceId));
        return SysResourceType.getTagForResource(resource.getResourceType(), doc, resourceId);
    }

}
