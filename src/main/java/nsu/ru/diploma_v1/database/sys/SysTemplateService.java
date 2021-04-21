package nsu.ru.diploma_v1.database.sys;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nsu.ru.diploma_v1.exception.EntityNotFoundException;
import nsu.ru.diploma_v1.model.entity.*;
import nsu.ru.diploma_v1.repository.SysTemplateRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SysTemplateService {

    private final SysTemplateRepository sysTemplateRepository;
    private final SysAggregationService sysAggregationService;

    public List<SysTemplate> getSysTemplates(){
        return sysTemplateRepository.findAll();
    }

    public SysTemplate getSysTemplate(int templateId) throws EntityNotFoundException{
        Optional<SysTemplate> template = sysTemplateRepository.getSysTemplateById(templateId);
        if(template.isPresent()){
            return template.get();
        }else {
            log.error(String.format("Шаблон %d не был найден.",templateId));
            throw new EntityNotFoundException(String.format("Шаблон %d не был найден.",templateId));
        }
    }

    public SysTemplate saveSysTemplate(SysTemplate sysTemplate){
        return sysTemplateRepository.save(sysTemplate);
    }

    @Transactional
    public String delete(int id){
        sysAggregationService.deleteAggregationImplByTemplateId(id);
        sysTemplateRepository.deleteById(id);
        return "Успешно удалено";
    }

}
