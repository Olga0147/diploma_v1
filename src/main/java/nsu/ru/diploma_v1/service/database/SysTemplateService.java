package nsu.ru.diploma_v1.service.database;

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

    public List<SysTemplate> getSysTemplates(){
        return sysTemplateRepository.findAll();
    }

    public SysTemplate getSysTemplate(int templateId){
        return sysTemplateRepository.getSysTemplateById(templateId);
    }

    public void saveSysTemplate(SysTemplate sysTemplate){
        sysTemplateRepository.save(sysTemplate);
    }

}
