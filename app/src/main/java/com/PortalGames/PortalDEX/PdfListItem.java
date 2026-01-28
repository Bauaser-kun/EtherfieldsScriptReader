package com.PortalGames.PortalDEX;

public class PdfListItem {
    private String name;
    private int iconId ;

    public PdfListItem (String name, int iconId){
        this.name = name;
        this.iconId = iconId;
    }

    public String getName(){
        return name;
    }

    public int getIconId() {
        return iconId;
    }
}
