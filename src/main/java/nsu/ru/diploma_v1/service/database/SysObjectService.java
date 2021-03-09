package nsu.ru.diploma_v1.service.database;

import lombok.RequiredArgsConstructor;
import nsu.ru.diploma_v1.model.entity.SysObject;
import nsu.ru.diploma_v1.repository.SysObjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SysObjectService {

    private final SysObjectRepository sysObjectRepository;

    public List<SysObject> getSysObjects(){
        return sysObjectRepository.findAll();
    }

    public SysObject getSysObjectById(int id){
        return sysObjectRepository.getSysObjectById(id);
    }

    public SysObject saveSysObject(SysObject sysObject){
        return sysObjectRepository.save(sysObject);
    }

}
