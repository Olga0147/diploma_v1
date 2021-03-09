package nsu.ru.diploma_v1.example.notpages;


import java.util.List;

public class Menu {

    /**
     * Данные самого Класса
     */
    private static final int id = 2;
    private static final  String name = "Menu";
    private static final  String systemName = "class_2";
    private static final  boolean isPage = false;
    private static final  String description = "Верхнее меню";

    /**
     * Атрибуты Класса
     */
    public String logo;//URL to image in table resources

    /**
     * Доступные отношения : ассоциации
     */
    public List<String> have_hyperlinks_to;

    public Menu(String logo) {
        this.logo = logo;
    }

    public String toHTML() {

        String macros_1 = "";
        for (String hyperlink : have_hyperlinks_to) {
            macros_1 += "<li><a>linkToPage</a></li>\n".replace("linkToPage", hyperlink);
        }

        String template =
                "<!DOCTYPE html>\n" +
                "<div>\n" +
                        "<img src=\"URL\" alt=\"логотип\">".replace("URL",logo) +
                "    <ul class=\"menu\">\n" +
                        macros_1 +
                "    </ul>\n" +
                "</div>\n" +
                "</html>";

        return template;
    }
}
