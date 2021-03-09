package nsu.ru.diploma_v1.example.pages;

import nsu.ru.diploma_v1.example.notpages.StudentCard;
import nsu.ru.diploma_v1.example.notpages.TeacherCard;
import nsu.ru.diploma_v1.example.notpages.Menu;

public class InfoPage {

    /**
     * Данные самого Класса
     */
    private static final int class_id = 1;
    private static final  String name = "InfoPage";
    private static final  String systemName = "class_1";
    private static final  boolean isPage = true;
    private static final  String description = "Каркас страницы для сайта - информационной странички";

    /**
     * Атрибуты Класса
     */
    public int object_id;
    public String title;

    /**
     * Доступные отношения :
     */
    public Menu menu;
    public TeacherCard teacherCard;
    public StudentCard studentCard;

    public InfoPage(int object_id, String title, Menu menu, TeacherCard teacherCard) {
        this.object_id = object_id;
        this.title = title;
        this.menu = menu;
        this.teacherCard = teacherCard;
    }

    public InfoPage(int object_id, String title, Menu menu, StudentCard studentCard) {
        this.object_id = object_id;
        this.title = title;
        this.menu = menu;
        this.studentCard = studentCard;
    }

    public String toHTML() {
        String template =
                "<!DOCTYPE html>\n" +
                        "<head>\n" +
                        "    <meta charset=\"UTF-8\">\n" +
                        "    <title>" + title + "</title>\n" +
                        "</head>" +
                        "<div>\n" + menu.toHTML() + "</div>\n" +
                        "<div>\n" + teacherCard.toHTML() + "</div>\n" +
                        "</html>";
        return template;
    }
}
