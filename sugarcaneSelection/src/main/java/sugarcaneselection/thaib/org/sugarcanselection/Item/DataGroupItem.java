package sugarcaneselection.thaib.org.sugarcanselection.Item;

import java.util.ArrayList;

/**
 * Created by Jitpakorn on 2/18/15 AD.
 */
public class DataGroupItem {

    private ArrayList<ScoreItem> ScoreItem = new ArrayList<>();
    private int OptionType;
    private String GroupTitle;
    private String GroupCode;


    public ArrayList<ScoreItem> getScoreItem() {
        return ScoreItem;
    }

    public void setScoreItem(ArrayList<ScoreItem> scoreItem) {
        ScoreItem = scoreItem;
    }

    public int getOptionType() {
        return OptionType;
    }

    public void setOptionType(int optionType) {
        OptionType = optionType;
    }

    public String getGroupTitle() {
        return GroupTitle;
    }

    public void setGroupTitle(String groupTitle) {
        GroupTitle = groupTitle;
    }

    public String getGroupCode() {
        return GroupCode;
    }

    public void setGroupCode(String groupCode) {
        GroupCode = groupCode;
    }
}
