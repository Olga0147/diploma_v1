package nsu.ru.diploma_v1.service.system;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TemplateService {

    public String parseTemplate(Map<String, Object> object, String template){
        String html = "<html><head><title>First parse</title></head>"
                + "<body><p>Parsed HTML into a doc.</p></body></html>";



        Document doc = Jsoup.parse(html);
        return template;
    }
}
