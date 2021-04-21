package nsu.ru.diploma_v1.database.sys;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nsu.ru.diploma_v1.exception.EntityNotFoundException;
import nsu.ru.diploma_v1.model.entity.SysResource;
import nsu.ru.diploma_v1.template_parse.resource_types.SysResourceType;
import nsu.ru.diploma_v1.repository.SysResourceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.util.List;
import java.util.Optional;

@Slf4j
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

    public SysResource getSysResourcesByResourceId(int resourceId)throws EntityNotFoundException{
        Optional<SysResource> resource = sysResourceRepository.getSysResourceById(resourceId);
        if(resource.isPresent()){
            return resource.get();
        }else{
        log.error(String.format("Ресурс %d не был найден.",resourceId));
        throw new EntityNotFoundException(String.format("Ресурс %d не был найден.",resourceId));
        }
    }

    public SysResource saveSysResource(SysResource sysResource){
        return sysResourceRepository.save(sysResource);
    }

    public Node getTag(String resourceId, Document doc){
        Optional<SysResource> resource = sysResourceRepository.getSysResourceById(Integer.parseInt(resourceId));
        if(resource.isPresent()){
            return SysResourceType.getTagForResource(resource.get().getResourceType(), doc, resourceId);
        }else{
            log.error(String.format("Ресурс %s не был найден.",resourceId));
            return SysResourceType.getTagForResource(SysResourceType.NOT_FOUND, doc, resourceId);
        }
    }

    @Transactional
    public String delete(int id){
        sysResourceRepository.deleteById(id);
        return "Успешно удалено";
    }

}
