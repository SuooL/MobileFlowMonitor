package net.suool.model;

public class Setting {
    private String name;
    private int imageId;
    private boolean isButton;
    private boolean checked;
    public Setting(String name, int imageId)
    {
        this.name = name;
        this.imageId = imageId;
    }
    public Setting(String name, boolean isButton){
        this.name = name;
        this.isButton = isButton;
    }
    public void setChecked(boolean checked){ this.checked = checked; }
    public boolean getChecked(){ return checked; }
    public String getName()
    {
        return name;
    }
    public boolean getIsButton(){ return isButton; }
    public int getImageId()
    {
        return imageId;
    }
}
