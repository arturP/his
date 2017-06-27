package sample.his.system.client;

/**
 *
 */

public class Holiday {
    private String name;
    private String date;
    private String observed;
    private Boolean isPublic;

    public String getName() {
        return name;
    }

    public Holiday setName(String name) {
        this.name = name;
        return this;
    }

    public String getDate() {
        return date;
    }

    public Holiday setDate(String date) {
        this.date = date;
        return this;
    }

    public String getObserved() {
        return observed;
    }

    public Holiday setObserved(String observed) {
        this.observed = observed;
        return this;
    }

    public Boolean getPublic() {
        return isPublic;
    }

    public Holiday setPublic(Boolean aPublic) {
        isPublic = aPublic;
        return this;
    }
}
