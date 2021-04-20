package nsu.ru.diploma_v1.database.sys;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nsu.ru.diploma_v1.exception.EditException;
import nsu.ru.diploma_v1.exception.EntityNotFoundException;
import nsu.ru.diploma_v1.model.entity.SysAssociation;
import nsu.ru.diploma_v1.model.entity.SysAssociationImpl;
import nsu.ru.diploma_v1.repository.SysAssociationImplRepository;
import nsu.ru.diploma_v1.repository.SysAssociationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SysAssociationService {

    private final SysAssociationRepository sysAssociationRepository;
    private final SysAssociationImplRepository sysAssociationImplRepository;

    public List<SysAssociation> getSysAssociations(){
        return sysAssociationRepository.findAll();
    }

    public SysAssociation getSysAssociation(int id) throws EntityNotFoundException{
        Optional<SysAssociation> association = sysAssociationRepository.getSysAssociationById(id);
        if(association.isPresent()){
            return association.get();
        }else{
            log.error(String.format("Ассоциация %d не была найдена",id));
            throw new EntityNotFoundException(String.format("Ассоциация %d не была найдена",id));
        }
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

    public SysAssociationImpl getSysAssociationImpl(int id) throws EntityNotFoundException{
        Optional<SysAssociationImpl> association = sysAssociationImplRepository.getSysAssociationImplById(id);
        if(association.isPresent()){
            return association.get();
        }else{
            log.error(String.format("Реализация ассоциации %d не была найдена",id));
            throw new EntityNotFoundException(String.format("Реализация ассоциации %d не была найдена",id));
        }

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
    public String delete(Integer id) throws EditException,EntityNotFoundException{
        Optional<SysAssociation> association = sysAssociationRepository.getSysAssociationById(id);
        if(association.isEmpty()){
            log.error(String.format("Ассоциация %d не была найдена",id));
            throw new EntityNotFoundException(String.format("Ассоциация %d не была найдена",id));
        }
        if (association.get().getSysAssociationList().isEmpty()){
            sysAssociationRepository.deleteById(id);
            return "Успешно удалено.";
        } else{
            log.info("Удаление невозможно, существуют реализации данной ассоциации.");
            throw new EditException("Удаление невозможно, существуют реализации данной ассоциации.");
        }
    }
}
