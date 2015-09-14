package item;

/**
 * Created by Rattanadiloknabuket on 10/8/14 AD.
 */
public class UserDataItem {

    private int UserID;
    private String Username;
    private int Authority;
    private String Password;
    private String Name;
    private String Sector;
    private String Tel;
    private String Email;
    private String Address;
    private String PicUrl;
    private String UUID;
    private String WorkPlace;
    private String LineID;
    private String FacebookID;

    public int getAuthority() {
        return Authority;
    }

    public void setAuthority(int authority) {
        Authority = authority;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }


    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSector() {
        return Sector;
    }

    public void setSector(String sector) {
        Sector = sector;
    }

    public String getTel() {
        return Tel;
    }

    public void setTel(String tel) {
        Tel = tel;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPicUrl() {
        return PicUrl;
    }

    public void setPicUrl(String picUrl) {
        PicUrl = picUrl;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public String getWorkPlace() {
        return WorkPlace;
    }

    public void setWorkPlace(String workPlace) {
        WorkPlace = workPlace;
    }

    public String getLineID() {
        return LineID;
    }

    public void setLineID(String lineID) {
        LineID = lineID;
    }

    public String getFacebookID() {
        return FacebookID;
    }

    public void setFacebookID(String facebookID) {
        FacebookID = facebookID;
    }
}
