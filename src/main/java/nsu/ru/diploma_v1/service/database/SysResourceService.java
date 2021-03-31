package nsu.ru.diploma_v1.service.database;

import lombok.RequiredArgsConstructor;
import nsu.ru.diploma_v1.model.entity.SysResource;
import nsu.ru.diploma_v1.repository.SysResourceRepository;
import org.springframework.stereotype.Service;

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

    public void saveSysResource(SysResource sysResource){
        sysResourceRepository.save(sysResource);
    }

}
