package com.PortalGames.PortalDEX;

import android.provider.MediaStore;

public class PdfListItem {
    String name;
    MediaStore.Images.Media image = new MediaStore.Images.Media();

    public PdfListItem (String name, MediaStore.Images.Media image){
        this.name = name;
        this.image = image;
    }

    public String getName(){
        return name;
    }

    public MediaStore.Images.Media getImage() {
        return image;
    }
}
