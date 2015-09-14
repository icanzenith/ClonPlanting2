package sugarcaneselection.thaib.org.clonplanting2.item;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jitpakorn on 4/24/15 AD.
 */

public class CloneListItem {

    private String ObjectID;
    private String CloneCode;
    private String LandID;
    private Integer RowNumber;
    private Integer SentAmount;
    private Integer ReceiveAmount;
    private Integer PlantAmount;
    private Integer SurviveAmount;
    private Integer PlantedID;
    private Integer CloneType;
    private String SentTo;
    private String SentFrom;
    private Integer UploadStatus;
    private long ID;

    public Integer getRowNumber() {
        return RowNumber;
    }

    public void setRowNumber(Integer rowNumber) {
        RowNumber = rowNumber;
    }

    public String getSentFrom() {
        return SentFrom;
    }

    public void setSentFrom(String sentFrom) {
        SentFrom = sentFrom;
    }

    public String getObjectID() {
        return ObjectID;
    }

    public void setObjectID(String objectID) {
        ObjectID = objectID;
    }

    public Integer getUploadStatus() {
        return UploadStatus;
    }


    public void setUploadStatus(Integer uploadStatus) {
        UploadStatus = uploadStatus;
    }

    public Integer getSentAmount() {
        return SentAmount;
    }

    public void setSentAmount(Integer sentAmount) {
        SentAmount = sentAmount;
    }

    public String getCloneCode() {
        return CloneCode;
    }

    public void setCloneCode(String cloneCode) {
        CloneCode = cloneCode;
    }

    public String getLandID() {
        return LandID;
    }

    public void setLandID(String landID) {
        LandID = landID;
    }

    public Integer getReceiveAmount() {
        return ReceiveAmount;
    }

    public void setReceiveAmount(Integer receiveAmount) {
        ReceiveAmount = receiveAmount;
    }

    public Integer getPlantAmount() {
        return PlantAmount;
    }

    public void setPlantAmount(Integer plantAmount) {
        PlantAmount = plantAmount;
    }

    public Integer getSurviveAmount() {
        return SurviveAmount;
    }

    public void setSurviveAmount(Integer surviveAmount) {
        SurviveAmount = surviveAmount;
    }

    public Integer getPlantedID() {
        return PlantedID;
    }

    public void setPlantedID(Integer plantedID) {
        PlantedID = plantedID;
    }

    public Integer getCloneType() {
        return CloneType;
    }

    public void setCloneType(Integer cloneType) {
        CloneType = cloneType;
    }

    public String getSentTo() {
        return SentTo;
    }

    public void setSentTo(String sentTo) {
        SentTo = sentTo;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }
}
