package nsu.ru.diploma_v1.model.enums.resource_types;

public enum SysResourceType {
    FILE,
    IMAGE,
    AUDIO,
    TEXT_FILE,
    VIDEO;

    public static SysResourceType getByMime(String mime){

        if(mime.contains("application")){
            return FILE;
        }
        else if(mime.contains("image")){
            return IMAGE;
        }
        else if(mime.contains("audio")){
            return AUDIO;
        }
        else if(mime.contains("text")){
            return TEXT_FILE;
        }
        else if(mime.contains("video")){
            return VIDEO;
        }
        else  {return FILE;}
    }
}
