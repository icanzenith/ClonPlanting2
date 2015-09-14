package sugarcaneselection.thaib.org.sugarcanselection;

import java.util.HashMap;

import sugarcaneselection.thaib.org.sugarcanselection.database.Columns;

/**
 * Created by Jitpakorn on 1/10/15 AD.
 */
public class StandardSpecieData {
    private String name;
    private int height;
    private Float size = .2f;
    private Float brix = .2f;
    private int stalkperclump;

    public Object get(String Key) {

        HashMap<String, Object> v = new HashMap<>();


        return v.get(Key);
    }

    public String toString() {

        StringBuilder s = new StringBuilder();
        s.append("Standard Name : " + getName() + '\n');
        s.append("Standard Height : " + getHeight() + '\n');
        s.append("Standard Size : " + getSize() + '\n');
        s.append("Standard Brix : " + getBrix() + '\n');
        s.append("Standard StalkPerClump : " + getStalkperclump());

        return s.toString();
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Float getSize() {
        return size;
    }

    public void setSize(Float size) {
        this.size = size;
    }

    public Float getBrix() {
        return brix;
    }

    public void setBrix(Float brix) {
        this.brix = brix;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStalkperclump() {
        return stalkperclump;
    }

    public void setStalkperclump(int stalkperclump) {
        this.stalkperclump = stalkperclump;
    }
}
