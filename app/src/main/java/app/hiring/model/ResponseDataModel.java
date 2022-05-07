package app.hiring.model;


/*
 * model/ResponseDataModel.java
 * basically what json response will be from api call (id, listId, name)
 */

public class ResponseDataModel {

    private String id;
    private String listId;
    private String name;

    public String getId() {
        return id;
    }

    public String getListId() {
        return listId;
    }

    public String getName() {
        return name;
    }

}
