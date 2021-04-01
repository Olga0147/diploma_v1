package nsu.ru.diploma_v1.model.enums.resource_types;

import nsu.ru.diploma_v1.configuration.urls.mode.UserMode;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

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

    public static Node getTag(SysResourceType type, Document doc, String id){

        Element tag;

        switch (type){
            case IMAGE:{
                tag = doc.createElement("img");
                tag.setAttribute("src", UserMode.GetFile.GET_RESOURCE.replace("{id}",id));
                tag.setAttribute("alt","картинка "+id);
                break;
            }
            case AUDIO:{
                tag = doc.createElement("audio");
                tag.setAttribute("controls","");
                tag.setAttribute("src", UserMode.GetFile.GET_RESOURCE.replace("{id}",id));
                tag.setAttribute("alt","аудио "+id);
                break;
            }
            case VIDEO:{
                tag = doc.createElement("video");
                tag.setAttribute("controls","");
                Element source = doc.createElement("source");
                source.setAttribute("src", UserMode.GetFile.GET_RESOURCE.replace("{id}",id));
                source.setAttribute("alt","видео "+id);
                tag.appendChild(source);
                break;
            }
            default:{
                tag = doc.createElement("a");
                tag.setAttribute("href", UserMode.GetFile.GET_RESOURCE.replace("{id}",id));
                tag.setAttribute("download", "");
                tag.setTextContent("Файл : "+id);
                break;
            }
        }
        return tag;
    }
}
