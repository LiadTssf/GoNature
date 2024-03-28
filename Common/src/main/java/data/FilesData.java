package data;

import java.io.Serializable;

public class FilesData implements Serializable {
    private String fileName;
    public FilesData(){}

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
