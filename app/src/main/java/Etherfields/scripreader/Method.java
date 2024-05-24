package Etherfields.scripreader;

import java.io.File;
import Etherfields.scripreader.Constant;

public class Method {
    public static void loadDirectoryFiles(File directory){
        File[] fileList = directory.listFiles();
        if (fileList != null && fileList.length > 0) {
            for (int i = 0; i < fileList.length; i++) {
                if (fileList[i].isDirectory()) {
                    loadDirectoryFiles(fileList[i]);
                } else {
                    String name = fileList[i].getName().toLowerCase();
                    for (String extension: Constant.extensions) {
                        if (name.endsWith(extension)) {
                            Constant.allMediaList.add(fileList[i]);
                            break;
                        }
                    }
                }
            }
        }
    }
}
