package nsu.ru.diploma_v1.database.sys;

import lombok.RequiredArgsConstructor;
import nsu.ru.diploma_v1.model.entity.*;
import nsu.ru.diploma_v1.repository.SysTemplateRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SysTemplateService {

    private final SysTemplateRepository sysTemplateRepository;
    private final SysAggregationService sysAggregationService;

    public List<SysTemplate> getSysTemplates(){
        return sysTemplateRepository.findAll();
    }

    public SysTemplate getSysTemplate(int templateId){
        return sysTemplateRepository.getSysTemplateById(templateId);
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
