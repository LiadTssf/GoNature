package data;

import java.io.Serializable;

public class DataParkChange implements Serializable {
    public String parkName;
    public int newOffset;
    public int oldOffset;
    public int newStayingTime;
    public int oldStayingTime;

    public int newCapacity;
    public int oldCapacity;
    public boolean ChangesApply;

}
