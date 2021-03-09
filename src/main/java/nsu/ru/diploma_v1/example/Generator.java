package nsu.ru.diploma_v1.example;

import nsu.ru.diploma_v1.example.notpages.Menu;
import nsu.ru.diploma_v1.example.notpages.StudentCard;
import nsu.ru.diploma_v1.example.notpages.TeacherCard;
import nsu.ru.diploma_v1.example.pages.InfoPage;

public class Generator {
    
    public String generatePage(){

        Menu object_menu = new Menu("/img/2");//создали объект Меню

        TeacherCard object_teacherCard = new TeacherCard(
                "/img/1",
                "Иван",
                "Иванов",
                "Математика");//создали объект Карточка учителя

        StudentCard object_studentCard = new StudentCard(
                "/img/3",
                "Петр",
                "Петров"
        );//создали объект Карточка ученика


        InfoPage object_infoPage1 = new InfoPage(1, "Страница учителя",
                object_menu, object_teacherCard);//Агрегация
        InfoPage object_infoPage2 = new InfoPage(2, "Страница ученика",
                object_menu, object_studentCard);//Агрегация

        String link1 = "/page/"+object_infoPage1.object_id;
        String link2 = "/page/"+object_infoPage2.object_id;

        object_studentCard.teacher = link1;//Ассоциация
        object_teacherCard.student = link2;//Ассоциация

        object_menu.have_hyperlinks_to.add(link1);//Ассоциация
        object_menu.have_hyperlinks_to.add(link2);//Ассоциация
        
        return object_infoPage1.toHTML();
    }
}
