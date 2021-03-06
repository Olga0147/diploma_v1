package nsu.ru.diploma_v1.template_parse.resource_types;

import nsu.ru.diploma_v1.configuration.urls.mode.UserMode;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public enum SysResourceType {
    FILE,
    IMAGE,
    AUDIO,
    TEXT_FILE,
    VIDEO,
    NOT_FOUND;

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

    public static Node getTagForResource(SysResourceType type, Document doc, String id){
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
            case NOT_FOUND:{
                tag = doc.createElement("a");
                tag.setAttribute("href", UserMode.GetFile.GET_RESOURCE.replace("{id}",id));
                tag.setTextContent("404");
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

    public static String getTagForMMedia(Integer id,SysResourceType type){

        switch (type){
            case IMAGE:{
                return  String.format("<img src=\"%s\" alt=\"картинка %d\"></img>",
                        UserMode.GetFile.GET_MMEDIA.replace("{id}",String.valueOf(id)),id);
            }
            case AUDIO:{
                return String.format("<audio src=\"%s\" controls=\"\" alt=\"аудио %d\"></audio>",
                        UserMode.GetFile.GET_MMEDIA.replace("{id}",String.valueOf(id)),id);
            }
            case VIDEO:{
                return  String.format("<video controls=\"\" ><source src=\"%s\" alt=\"видео %d\"></source></video>",
                        UserMode.GetFile.GET_MMEDIA.replace("{id}",String.valueOf(id)),id);
            }
            default:{
                return  String.format("<a href=\"%s\" download=\"\" alt=\"аудио %d\">Файл : %d</a>",
                        UserMode.GetFile.GET_MMEDIA.replace("{id}",String.valueOf(id)),id,id);
            }
        }
    }
}
