package sugarcaneselection.thaib.org.sugarcanselection.Item;

import java.util.ArrayList;
import java.util.HashMap;

import sugarcaneselection.thaib.org.sugarcanselection.Item.ScoreItem;
import sugarcaneselection.thaib.org.sugarcanselection.database.Columns;

/**
 * Created by Jitpakorn on 1/6/15 AD.
 */
public class CloneDataItem {

    public static final int DATATYPE_CLONE = 1;
    public static final int DATATYPE_STANDARD = 2;

    public static final int STANDARD_FIRST = 1;
    public static final int STANDARD_LAST = 2;

    private HashMap<String,ScoreItem> DataScore;
    private HashMap<String,Float> StalkSize;
    private HashMap<String,Float> InternodeLength;
    private String WhySelect;
    private String SaveStatus;
    private String SelectStatus;

    private String OtherDiseaseName;

    private int DataType;
    private String SpecieName;
    private String Familycode;
    private int STANDARD_ROW;
    private String clonecode;

    private String picupper;
    private String piclower;
    private String picfull;

    public String getOtherDiseaseName() {
        return OtherDiseaseName;
    }

    public void setOtherDiseaseName(String otherDiseaseName) {
        OtherDiseaseName = otherDiseaseName;
    }

    public int getSTANDARD_ROW() {
        return STANDARD_ROW;
    }

    public void setSTANDARD_ROW(int STANDARD_ROW) {
        this.STANDARD_ROW = STANDARD_ROW;
    }

    public String getFamilycode() {
        return Familycode;
    }

    public void setFamilycode(String familycode) {
        Familycode = familycode;
    }

    public int getDataType() {
        return DataType;
    }

    public void setDataType(int dataType) {
        DataType = dataType;
    }

    public String getWhySelect() {
        return WhySelect;
    }

    public void setWhySelect(String whySelect) {
        WhySelect = whySelect;
    }

    public String getSaveStatus() {
        return SaveStatus;
    }

    public void setSaveStatus(String saveStatus) {
        SaveStatus = saveStatus;
    }

    public String getSelectStatus() {
        return SelectStatus;
    }

    public void setSelectStatus(String selectStatus) {
        SelectStatus = selectStatus;
    }

    public HashMap<String, ScoreItem> getDataScore() {
        return DataScore;
    }

    public void setDataScore(HashMap<String, ScoreItem> dataScore) {
        DataScore = dataScore;
    }


    public String getSpecieName() {
        return SpecieName;
    }

    public void setSpecieName(String specieName) {
        SpecieName = specieName;
    }


    public Object get(String Key) {
        //TODO RECHECK THIS METHOD
        HashMap<String, Object> v = new HashMap<>();
        v.put(Columns.CLONECODE, getClonecode());


        return v.get(Key);
    }


    public String getClonecode() {
        return clonecode;
    }

    public void setClonecode(String clonecode) {
        this.clonecode = clonecode;
    }

    public String getPicupper() {
        return picupper;
    }

    public void setPicupper(String picupper) {
        this.picupper = picupper;
    }

    public String getPiclower() {
        return piclower;
    }

    public void setPiclower(String piclower) {
        this.piclower = piclower;
    }


    public String getPicfull() {
        return picfull;
    }

    public void setPicfull(String picfull) {
        this.picfull = picfull;
    }



    public HashMap<String, Float> getStalkSize() {
        return StalkSize;
    }

    public void setStalkSize(HashMap<String, Float> stalkSize) {
        StalkSize = stalkSize;
    }

    public HashMap<String, Float> getInternodeLength() {
        return InternodeLength;
    }

    public void setInternodeLength(HashMap<String, Float> internodeLength) {
        InternodeLength = internodeLength;
    }
}

