package com.PortalGames.PortalDEX;

public class PdfListItem {
    private String name;
    private int iconId ;
    private String url;
    private boolean isDownloaded;

    public PdfListItem (String name, int iconId, String url, boolean isDownloaded){
        this.name = name;
        this.iconId = iconId;
        this.url = url;
        this.isDownloaded = isDownloaded;
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

    public boolean isDownloaded() {
        return isDownloaded;
    }

    public void setDownloaded(boolean downloaded) {
        isDownloaded = downloaded;
    }
}
