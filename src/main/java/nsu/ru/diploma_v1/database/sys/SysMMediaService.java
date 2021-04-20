package nsu.ru.diploma_v1.database.sys;

import lombok.RequiredArgsConstructor;
import nsu.ru.diploma_v1.model.entity.SysMmedia;
import nsu.ru.diploma_v1.template_parse.resource_types.SysResourceType;
import nsu.ru.diploma_v1.repository.SysMMediaRepository;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SysMMediaService {

    private final SysMMediaRepository sysMMediaRepository;

    public List<SysMmedia> getSysMMedia(){
        return sysMMediaRepository.findAll();
    }

    public List<SysMmedia> getSysMMediasByClassId(int objectId){
        return sysMMediaRepository.getSysMmediaByOwnerObjectId(objectId);
    }

    public SysMmedia getSysMMediaByMMediaId(int mmediaId){
        return sysMMediaRepository.getSysMmediaById(mmediaId);
    }

    public SysMmedia saveSysMmedia(SysMmedia sysMmedia){
        return sysMMediaRepository.save(sysMmedia);
    }

    public Node getTag(String resourceId, Document doc){
        SysMmedia resource = sysMMediaRepository.getSysMmediaById(Integer.parseInt(resourceId));
        return SysResourceType.getTagForResource(resource.getResourceType(), doc, resourceId);
    }

}
