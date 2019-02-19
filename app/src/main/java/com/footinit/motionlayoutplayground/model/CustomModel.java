package com.footinit.motionlayoutplayground.model;

public class CustomModel {

    private String brief;
    private String description;
    private int colorCode;

    public CustomModel(String brief, String description, int colorCode) {
        this.brief = brief;
        this.description = description;
        this.colorCode = colorCode;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getColorCode() {
        return colorCode;
    }

    public void setColorCode(int colorCode) {
        this.colorCode = colorCode;
    }
}
