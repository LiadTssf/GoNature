package data;

import java.io.Serializable;
import java.sql.Blob;

public class FilesData implements Serializable {
    private String fileName;
    private byte[] file_data;
    private int size = 0;
    public FilesData(){}

    public String getFileName() {
        return fileName;
    }

    public void setFile_data(byte[] file_data) {
        this.file_data = file_data;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public byte[] getFile_data() {
        return file_data;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
