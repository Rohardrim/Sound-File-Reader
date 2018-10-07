package additionalListSoundClass;

public class additionalClass {

    private int id;
    private String name, path;

    public additionalClass(int id, String name, String path) {
        this.id = id;
        this.name = name;
        this.path = path;
    }

    public int getId() { return id; }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }
}
