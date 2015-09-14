package sugarcaneselection.thaib.org.clonplanting2.item;

/**
 * Created by Jitpakorn on 4/23/15 AD.
 */
public class LandListItem {

    private String LandID;
    private String LandName;
    private Integer LandNumber;
    private Double Latitude;
    private Double Longitude;
    private Double LandSize;
    private Double LandWidth;
    private Double LandLength;
    private String LandAddress;
    private String Sector;
    private Integer UserID;
    private Integer RowAmount;

    public Double getLatitude() {
        return Latitude;
    }

    public void setLatitude(Double latitude) {
        Latitude = latitude;
    }

    public Double getLongitude() {
        return Longitude;
    }

    public void setLongitude(Double longitude) {
        Longitude = longitude;
    }

    public Double getLandSize() {
        return LandSize;
    }

    public void setLandSize(Double landSize) {
        LandSize = landSize;
    }

    public Double getLandWidth() {
        return LandWidth;
    }

    public void setLandWidth(Double landWidth) {
        LandWidth = landWidth;
    }

    public Double getLandLength() {
        return LandLength;
    }

    public void setLandLength(Double landLength) {
        LandLength = landLength;
    }

    public Integer getRowAmount() {
        return RowAmount;
    }

    public void setRowAmount(Integer rowAmount) {
        RowAmount = rowAmount;
    }

    public String getLandID() {
        return LandID;
    }

    public void setLandID(String landID) {
        LandID = landID;
    }

    public String getLandName() {
        return LandName;
    }

    public void setLandName(String landName) {
        LandName = landName;
    }

    public Integer getLandNumber() {
        return LandNumber;
    }

    public void setLandNumber(Integer landNumber) {
        LandNumber = landNumber;
    }
    public String getLandAddress() {
        return LandAddress;
    }

    public void setLandAddress(String landAddress) {
        LandAddress = landAddress;
    }

    public String getSector() {
        return Sector;
    }

    public void setSector(String sector) {
        Sector = sector;
    }

    public Integer getUserID() {
        return UserID;
    }

    public void setUserID(Integer userID) {
        UserID = userID;
    }
}
