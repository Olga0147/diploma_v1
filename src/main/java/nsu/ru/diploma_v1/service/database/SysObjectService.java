package nsu.ru.diploma_v1.service.database;

import lombok.RequiredArgsConstructor;
import nsu.ru.diploma_v1.model.entity.SysClass;
import nsu.ru.diploma_v1.model.entity.SysObject;
import nsu.ru.diploma_v1.repository.CustomRepository;
import nsu.ru.diploma_v1.repository.SysObjectRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SysObjectService {

    private final CustomRepository customRepository;
    private final SysObjectRepository sysObjectRepository;

    public List<SysObject> getObjectsInfo(){
        return sysObjectRepository.findAll();
    }

    public SysObject getObjectsInfoById(int id){
        return sysObjectRepository.getSysObjectById(id);
    }



    public Map<String, Object> getObjectDetailInfo(int id){

        SysObject sysObject = sysObjectRepository.getSysObjectById(id);
        SysClass sysClass = sysObject.getOwnerSysClass();

        Map<String,Object> param = new HashMap<>();
        param.put("ID",id);

        List<Map<String, Object>> list = customRepository.selectFromTable(sysClass.getSystemName(),null,param);
//        for (Map<String, Object> row : list) {
//            System.out.println("row : ");
//            for (Map.Entry<String, Object> column : row.entrySet()) {
//                System.out.println("key = "+column.getKey()+" val = "+column.getValue());
//            }
//            System.out.println("-------------");
//        }
        //TODO: исключение что не нашли
        return list.get(0);
    }

    public Map<String, Object> getObjectAssociationImpl(int id){
        SysObject sysObject = sysObjectRepository.getSysObjectById(id);
        SysClass sysClass = sysObject.getOwnerSysClass();

        //hz
        return null;
    }

    public SysObject saveObject(SysObject sysObject){
        return sysObjectRepository.save(sysObject);
    }

}
