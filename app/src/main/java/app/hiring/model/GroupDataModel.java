package app.hiring.model;

/*
 * model/GroupDataModel.java
 * basically what each group can have (id, name)
 */

public class GroupDataModel {
    String id;
    String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
