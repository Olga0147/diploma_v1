package nsu.ru.diploma_v1.service.database;

import lombok.RequiredArgsConstructor;
import nsu.ru.diploma_v1.model.entity.SysAggregation;
import nsu.ru.diploma_v1.model.entity.SysAggregationImpl;
import nsu.ru.diploma_v1.repository.SysAggregationImplRepository;
import nsu.ru.diploma_v1.repository.SysAggregationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SysAggregationService {

    private final SysAggregationRepository sysAggregationRepository;
    private final SysAggregationImplRepository sysAggregationImplRepository;


    public List<SysAggregation> getSysAggregations(){
        return sysAggregationRepository.findAll();
    }

    public List<SysAggregationImpl> getSysAggregationsImpl(){
        return sysAggregationImplRepository.findAll();
    }

    public SysAggregation getSysAggregation(int id){
        return sysAggregationRepository.getSysAggregationById(id);
    }

    public SysAggregationImpl getSysAggregationImpl(int id){
        return sysAggregationImplRepository.getSysAggregationImplById(id);
    }
}
