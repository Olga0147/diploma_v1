package nsu.ru.diploma_v1.service.database;

import lombok.RequiredArgsConstructor;
import nsu.ru.diploma_v1.model.entity.SysAssociation;
import nsu.ru.diploma_v1.model.entity.SysAssociationImpl;
import nsu.ru.diploma_v1.repository.SysAssociationImplRepository;
import nsu.ru.diploma_v1.repository.SysAssociationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SysAssociationService {
    private final SysAssociationRepository sysAssociationRepository;
    private final SysAssociationImplRepository sysAssociationImplRepository;


    public List<SysAssociation> getSysAssociations(){
        return sysAssociationRepository.findAll();
    }

    public List<SysAssociationImpl> getSysAssociationsImpl(){
        return sysAssociationImplRepository.findAll();
    }

    public SysAssociation getSysAssociation(int id){
        return sysAssociationRepository.getSysAssociationById(id);
    }

    public SysAssociationImpl getSysAssociationImpl(int id){
        return sysAssociationImplRepository.getSysAssociationImplById(id);
    }
}
