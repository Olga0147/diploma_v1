package nsu.ru.diploma_v1.configuration.urls.mode;

import nsu.ru.diploma_v1.configuration.urls.EntityPath;

import static nsu.ru.diploma_v1.configuration.urls.ModePath.USER_MODE;

public class UserMode extends EntityPath {

    public static class Show{
        public static final String PREF = USER_MODE;

        public static final String GET_OBJECT = PREF + "/{object_id}/{id}";
    }
}
