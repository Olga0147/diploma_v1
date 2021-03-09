package nsu.ru.diploma_v1.service.database;

import lombok.RequiredArgsConstructor;
import nsu.ru.diploma_v1.model.entity.SysAggregation;
import nsu.ru.diploma_v1.model.entity.SysAggregationImpl;
import nsu.ru.diploma_v1.model.entity.SysComposition;
import nsu.ru.diploma_v1.model.entity.SysCompositionImpl;
import nsu.ru.diploma_v1.repository.SysCompositionImplRepository;
import nsu.ru.diploma_v1.repository.SysCompositionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SysCompositionService {

    private final SysCompositionRepository sysCompositionRepository;
    private final SysCompositionImplRepository sysCompositionImplRepository;

    public List<SysComposition> getSysCompositions(){
        return sysCompositionRepository.findAll();
    }

    public List<SysCompositionImpl> getSysCompositionsImpl(){
        return sysCompositionImplRepository.findAll();
    }


    public SysComposition getSysComposition(int id){
        return sysCompositionRepository.getSysCompositionById(id);
    }

    public SysCompositionImpl getSysCompositionImpl(int id){
        return sysCompositionImplRepository.getSysCompositionImplById(id);
    }

    public void saveSysComposition(SysComposition sysComposition){
        sysCompositionRepository.save(sysComposition);
    }
}
