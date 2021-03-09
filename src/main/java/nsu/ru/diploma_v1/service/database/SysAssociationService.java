package nsu.ru.diploma_v1.service.database;

import lombok.RequiredArgsConstructor;
import nsu.ru.diploma_v1.model.entity.SysAggregation;
import nsu.ru.diploma_v1.model.entity.SysAggregationImpl;
import nsu.ru.diploma_v1.model.entity.SysAssociation;
import nsu.ru.diploma_v1.model.entity.SysAssociationImpl;
import nsu.ru.diploma_v1.repository.SysAssociationImplRepository;
import nsu.ru.diploma_v1.repository.SysAssociationRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SysAssociationService {

    private final SysAssociationRepository sysAssociationRepository;
    private final SysAssociationImplRepository sysAssociationImplRepository;

    public List<SysAssociation> getSysAssociations(){
        return sysAssociationRepository.findAll();
    }

    public SysAssociation getSysAssociation(int id){
        return sysAssociationRepository.getSysAssociationById(id);
    }

    public List<String> getSysAssociationsIdsAndNames(){
        List<String> result = new LinkedList<>();

        List<SysAssociation> list = sysAssociationRepository.findAll();
        for (SysAssociation sysAssociation : list) {
            result.add(String.format("%d-%s",sysAssociation.getId(),sysAssociation.getName()));
        }
        return result;
    }

    public void saveSysAssociation(SysAssociation sysAssociation){
        sysAssociationRepository.save(sysAssociation);
    }

    //------

    public List<SysAssociationImpl> getSysAssociationsImpl(){
        return sysAssociationImplRepository.findAll();
    }

    public SysAssociationImpl getSysAssociationImpl(int id){
        return sysAssociationImplRepository.getSysAssociationImplById(id);
    }

    public void saveSysAssociationImpl(SysAssociationImpl sysAssociation){
        sysAssociationImplRepository.save(sysAssociation);
    }
}
