package nsu.ru.diploma_v1.database.sys;

import lombok.RequiredArgsConstructor;
import nsu.ru.diploma_v1.model.entity.SysAssociation;
import nsu.ru.diploma_v1.model.entity.SysAssociationImpl;
import nsu.ru.diploma_v1.repository.SysAssociationImplRepository;
import nsu.ru.diploma_v1.repository.SysAssociationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public SysAssociation saveSysAssociation(SysAssociation sysAssociation){
        return sysAssociationRepository.save(sysAssociation);
    }

    //------

    public List<SysAssociationImpl> getSysAssociationsImpl(){
        return sysAssociationImplRepository.findAll();
    }

    public SysAssociationImpl getSysAssociationImpl(int id){
        return sysAssociationImplRepository.getSysAssociationImplById(id);
    }

    public SysAssociationImpl saveSysAssociationImpl(SysAssociationImpl sysAssociation){
        return sysAssociationImplRepository.save(sysAssociation);
    }

    //------

    @Transactional
    public String deleteImpl(Integer id){
        sysAssociationImplRepository.deleteById(id);
        return "Успешно удалено";
    }

    @Transactional
    public String delete(Integer id){
        SysAssociation sysAssociation = sysAssociationRepository.getSysAssociationById(id);
        if (sysAssociation.getSysAssociationList().isEmpty()){
            sysAssociationRepository.deleteById(id);
            return "Успешно удалено.";
        } else{
            return "Удаление невозможно, существуют реализации данной ассоциации.";
        }
    }
}
