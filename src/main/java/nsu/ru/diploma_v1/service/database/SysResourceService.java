package nsu.ru.diploma_v1.service.database;

import lombok.RequiredArgsConstructor;
import nsu.ru.diploma_v1.model.entity.SysResource;
import nsu.ru.diploma_v1.repository.SysResourceRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SysResourceService {

    private final SysResourceRepository sysResourceRepository;

//    public List<SysTemplate> getSysTemplates(){
//        return sysResourceRepository.findAll();
//    }

//    public SysTemplate getSysResource(int resourceId){
//        return sysResourceRepository.getSysResourceById(resourceId);
//    }

    public void saveSysResource(SysResource sysResource){
        sysResourceRepository.save(sysResource);
    }

}
