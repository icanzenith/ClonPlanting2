package sugarcaneselection.thaib.org.sugarcanselection;

import java.util.ArrayList;

/**
 * Created by Rattanadiloknabuket on 10/13/14 AD.
 */
public class PersonalDataCallbackJson {
    private ArrayList<userdata> userdata;

    public ArrayList<PersonalDataCallbackJson.userdata> getUserdata() {
        return userdata;
    }

    public void setUserdata(ArrayList<PersonalDataCallbackJson.userdata> userdata) {
        this.userdata = userdata;
    }

    public class userdata {
        private String PicURL;
        private String UUID;
        private String Name;
        private String UserID;
        private String WorkPlace;
        private String Tel;
        private String Email;
        private String LineID;
        private String FacebookID;

        public String getPicURL() {
            return PicURL;
        }

        public void setPicURL(String picURL) {
            PicURL = picURL;
        }

        public String getUUID() {
            return UUID;
        }

        public void setUUID(String UUID) {
            this.UUID = UUID;
        }

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public String getUserID() {
            return UserID;
        }

        public void setUserID(String userID) {
            UserID = userID;
        }

        public String getWorkPlace() {
            return WorkPlace;
        }

        public void setWorkPlace(String workPlace) {
            WorkPlace = workPlace;
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
}
