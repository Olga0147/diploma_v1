package nsu.ru.diploma_v1.example.notpages;

public class StudentCard {
    /**
     * Данные самого Класса
     */
    private static final int id = 3;
    private static final  String name = "TeacherCard";
    private static final  String systemName = "class_3";
    private static final  boolean isPage = false;
    private static final  String description = "Информация об учителе";

    /**
     * Атрибуты Класса
     */
    public String photo;//URL to image in table resources
    public String firstName;
    public String lastName;

    /**
     * Отношение :
     */
    public String teacher;

    public StudentCard(String photo, String firstName, String lastName) {
        this.photo = photo;
        this.firstName = firstName;
        this.lastName = lastName;
    }


    public String toHTML() {
        return "TeacherCard{" +
                "<img src=\"URL\" alt=\"фото учителя\">".replace("URL",photo) +
                ", name='" + firstName + '\'' +
                ", surname='" + lastName + '\'' +
                '}';
    }
}
