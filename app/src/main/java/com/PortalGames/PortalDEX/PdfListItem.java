package com.PortalGames.PortalDEX;

import android.graphics.drawable.Drawable;

public class PdfListItem {
    private String name;
    private int iconId ;
    private String url;

    public PdfListItem (String name, int iconId, String url){
        this.name = name;
        this.iconId = iconId;
        this.url = url;
    }

    public String getName(){
        return name;
    }

    public int getIconId() {
        return iconId;
    }

    public String getUrl() {
        return url;
    }
}
