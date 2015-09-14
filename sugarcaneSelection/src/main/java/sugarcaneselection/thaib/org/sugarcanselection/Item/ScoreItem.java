package sugarcaneselection.thaib.org.sugarcanselection.Item;

/**
 * Created by Jitpakorn on 2/18/15 AD.
 */
public class ScoreItem {
    /**
     * Score สำหรับเก็บคะแนน
     * */
    private float Score;
    /**
     * name สำเก็บความหมายของคะแนน
     *
     * */

    private String TagName;
    private Float RawScore;
    private Object Data;
    private String Unit;

    public String getUnit() {
        return Unit;
    }

    public void setUnit(String unit) {
        Unit = unit;
    }

    public String getTagName() {
        return TagName;
    }

    public void setTagName(String tagName) {
        TagName = tagName;
    }

    public float getScore() {
        return Score;
    }

    public void setScore(float score) {
        Score = score;
    }

    public Float getRawScore() {
        return RawScore;
    }

    public void setRawScore(Float rawScore) {
        RawScore = rawScore;
    }

    public Object getData() {
        return Data;
    }

    public void setData(Object data) {
        Data = data;
    }
}
