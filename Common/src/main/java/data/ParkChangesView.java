package data;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.IntegerProperty;
import java.io.Serializable;

public class ParkChangesView implements Serializable {

    private String parkName;
    private int oldCapacity;
    private int oldAverageVisitTime;
    private int oldCapacityOffset;
    private int newCapacity;
    private int newAverageVisitTime;
    private int  newCapacityOffset;

    public ParkChangesView() {

    }

    public void setParkName(String parkName) {
        this.parkName = parkName;
    }

    public String getParkName() {
        return parkName;
    }

    public void setOldCapacity(Integer oldCapacity) {
        this.oldCapacity = oldCapacity;
    }

    public Integer getOldCapacity() {
        return oldCapacity;
    }

    public void setOldAverageVisitTime(Integer oldAverageVisitTime) {
        this.oldAverageVisitTime = oldAverageVisitTime;
    }

    public Integer getOldAverageVisitTime() {
        return oldAverageVisitTime;
    }

    public void setOldCapacityOffset(Integer oldCapacityOffset) {
        this.oldCapacityOffset = oldCapacityOffset;
    }

    public Integer getOldCapacityOffset() {
        return oldCapacityOffset;
    }

    public void setNewCapacity(Integer newCapacity) {
        this.newCapacity =newCapacity;
    }

    public Integer getNewCapacity() {
        return newCapacity;
    }

    public void setNewAverageVisitTime(Integer newAverageVisitTime) {
        this.newAverageVisitTime =newAverageVisitTime;
    }

    public Integer  getNewAverageVisitTime() {
        return newAverageVisitTime;
    }

    public void setNewCapacityOffset(Integer  newCapacityOffset) {
        this.newCapacityOffset= newCapacityOffset;
    }

    public Integer  getNewCapacityOffset() {
        return newCapacityOffset;
    }
}