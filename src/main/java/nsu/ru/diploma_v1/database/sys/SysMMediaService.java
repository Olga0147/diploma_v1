package nsu.ru.diploma_v1.database.sys;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nsu.ru.diploma_v1.exception.EntityNotFoundException;
import nsu.ru.diploma_v1.model.entity.SysMmedia;
import nsu.ru.diploma_v1.template_parse.resource_types.SysResourceType;
import nsu.ru.diploma_v1.repository.SysMMediaRepository;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.util.List;
import java.util.Optional;

@Slf4j
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

    public SysMmedia getSysMMediaByMMediaId(int id) throws EntityNotFoundException{
        Optional<SysMmedia> mmedia = sysMMediaRepository.getSysMmediaById(id);
        if(mmedia.isPresent()){
            return mmedia.get();
        }else{
            log.error(String.format("Ммедиа %d не был найден.",id));
            throw new EntityNotFoundException(String.format("Ммедиа %d не был найден.",id));
        }
    }

    public SysMmedia saveSysMmedia(SysMmedia sysMmedia){
        return sysMMediaRepository.save(sysMmedia);
    }

    public Node getTag(String id, Document doc) throws EntityNotFoundException{
        Optional<SysMmedia> mmedia = sysMMediaRepository.getSysMmediaById(Integer.parseInt(id));
        if(mmedia.isPresent()){
            return SysResourceType.getTagForResource(mmedia.get().getResourceType(), doc, id);
        }else{
            log.error(String.format("Ммедиа %d не был найден.",id));
            throw new EntityNotFoundException(String.format("Ммедиа %d не был найден.",id));
        }
    }

}
