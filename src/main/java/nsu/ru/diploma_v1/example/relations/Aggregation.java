package nsu.ru.diploma_v1.example.relations;

public class Aggregation {

    private int id;
    private String name;
    private int from_class_id;
    private int to_class_id;

    public Aggregation(int id, String name, int from_class_id, int to_class_id) {
        this.id = id;
        this.name = name;
        this.from_class_id = from_class_id;
        this.to_class_id = to_class_id;
    }
}
