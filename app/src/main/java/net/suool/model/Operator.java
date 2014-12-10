package net.suool.model;

public class Operator {
    private String title;
    private String name;
    private int imageId;
    public  Operator(String title, String name, int imageId){
        this.title = title;
        this.name = name;
        this.imageId = imageId;
    }
    public String getTitle(){
        return title;
    }
    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageId(){
        return imageId;
    }
}
