package sugarcaneselection.thaib.org.sugarcanselection;

import java.util.ArrayList;

/**
 * Created by Rattanadiloknabuket on 10/13/14 AD.
 */
public class LoginCallbackJson {
    private ArrayList<tempva> tempva;

    public ArrayList<tempva> getTempva() {
        return tempva;
    }

    public void setTempva(ArrayList<tempva> tempva) {

        this.tempva = tempva;
    }

    public class tempva {
        private int UserID;
        private String Username;
        private int Authority;
        private String Passsword;
        private String Name;
        private String Sector;
        private String Tel;
        private String Email;
        private String Address;

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

        public int getAuthority() {
            return Authority;
        }

        public void setAuthority(int authority) {
            Authority = authority;
        }

        public String getPasssword() {
            return Passsword;
        }

        public void setPasssword(String passsword) {
            Passsword = passsword;
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
    }

}
