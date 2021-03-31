package nsu.ru.diploma_v1.configuration.urls.mode;

import nsu.ru.diploma_v1.configuration.urls.EntityPath;

import static nsu.ru.diploma_v1.configuration.urls.ModePath.DOWNLOAD_MODE;
import static nsu.ru.diploma_v1.configuration.urls.ModePath.EDIT_MODE;

public class DownloadMode extends EntityPath {

    public static class GetFile{
        public static final String PREF = DOWNLOAD_MODE;

        public static final String GET_MMEDIA =   PREF + "/mmedia"   + "/{id}";;
        public static final String GET_RESOURCE = PREF + "/resource" + "/{id}";
    }
}
