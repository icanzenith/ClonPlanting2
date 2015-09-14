package sugarcaneselection.thaib.org.sugarcanselection;

import sugarcaneselection.thaib.org.sugarcanselection.Item.Code;
import sugarcaneselection.thaib.org.sugarcanselection.Item.ScoreItem;
import sugarcaneselection.thaib.org.sugarcanselection.Item.WeightValue;

/**
 * Created by Jitpakorn on 2/26/15 AD.
 */
public class ScoreOption {

    public static ScoreItem OverViewScore(float rating) {

        ScoreItem item = new ScoreItem();
        float score = rating * 5;
        item.setTagName(Code.OverAll);
        item.setData(rating);
        item.setScore(score);
        item.setRawScore(0f);


        return item;
    }

    public static ScoreItem BrixScore(float brix) {

        ScoreItem item = new ScoreItem();
        float score;

        item.setTagName(Code.Brix);
        item.setData(brix);

        if (brix >= 21) {
            score = 5 * 15;
            item.setScore(score);
            item.setRawScore(5f);
        } else if (brix >= 20) {
            score = 4 * 15;
            item.setScore(score);
            item.setRawScore(4f);
        } else if (brix >= 19) {
            score = 3 * 15;
            item.setScore(score);
            item.setRawScore(3f);
        } else if (brix >= 18) {
            score = 2 * 15;
            item.setScore(score);
            item.setRawScore(2f);
        } else if (brix >= 17) {
            score = 1 * 15;
            item.setScore(score);
            item.setRawScore(1f);
        } else if (brix < 17) {
            score = 0;
            item.setScore(score);
            item.setRawScore(0f);
        }

        return item;
    }

    public static ScoreItem StalkPerClumpScore(int StalkperClump) {

        ScoreItem item = new ScoreItem();

        item.setTagName(Code.StalkAmount);
        item.setData(StalkperClump);

        if (StalkperClump > 10) {
            item.setScore(5 * 20);
            item.setRawScore(5f);

        } else if (StalkperClump >= 7 && StalkperClump <= 9) {
            item.setScore(4 * 20);
            item.setRawScore(4f);
        } else if (StalkperClump >= 5 && StalkperClump <= 6) {
            item.setScore(3 * 20);
            item.setRawScore(3f);
        } else if (StalkperClump >= 3 && StalkperClump <= 4) {
            item.setScore(2 * 20);
            item.setRawScore(2f);
        } else if (StalkperClump >= 1 && StalkperClump <= 2) {
            item.setScore(1 * 20);
            item.setRawScore(1f);
        }


        return item;
    }

    public static ScoreItem StalkSize(float StalkSizeAverage) {

        ScoreItem item = new ScoreItem();

        float score ;
        item.setTagName(Code.StalkSize);
        item.setData(StalkSizeAverage);

        if (StalkSizeAverage >= 3.0) {
            score = 5 * WeightValue.StalkSize;
            item.setScore(score);
            item.setRawScore(5f);
        } else if (StalkSizeAverage >= 2.7 || StalkSizeAverage < 3.0) {
            score = 4 * WeightValue.StalkSize;
            item.setScore(score);
            item.setRawScore(4f);
        } else if (StalkSizeAverage >= 2.5 || StalkSizeAverage < 2.7) {
            score = 3 * WeightValue.StalkSize;
            item.setScore(score);
            item.setRawScore(3f);
        } else if (StalkSizeAverage < 2.5) {
            score = 0 * WeightValue.StalkSize;
            item.setScore(score);
            item.setRawScore(0f);
        }
        return item;
    }

    public static ScoreItem InternodeLengthScore(float InternodeLegthAverage) {

        ScoreItem item = new ScoreItem();
        float score;
        item.setTagName(Code.InternodeLength);
        item.setData(InternodeLegthAverage);

        if (InternodeLegthAverage >= 13) {
            score = 5 * WeightValue.InternodeLength;
            item.setScore((float)score);
            item.setRawScore(5f);
        } else if (InternodeLegthAverage >= 11 || InternodeLegthAverage < 13) {
            score = 3 * WeightValue.InternodeLength;
            item.setScore(score);
            item.setRawScore(3f);
        } else if (InternodeLegthAverage < 11) {
            score = 1 * WeightValue.InternodeLength;
            item.setScore(score);
            item.setRawScore(1f);
        }
        return item;
    }

    public static ScoreItem InternodeAmountScore(int InternodeAmount) {

        /**
         *
         * ไมมี การให้คะแนน ในจุดนี้
         *
         * */

        ScoreItem item = new ScoreItem();
        item.setTagName(Code.InternodeAmount);
        item.setRawScore(0f);
        item.setScore(0);
        item.setData(InternodeAmount);

        return item;
    }

    public static ScoreItem Height(int height, int StandardHeight) {
        int different = height - StandardHeight;
        ScoreItem item = new ScoreItem();
        item.setTagName(Code.Height);
        item.setData(height);
        if (different > 3) {
            item.setScore(5 * WeightValue.Height);
            item.setRawScore(5f);


        } else if (different <= 3 && different >= -3) {
            item.setScore(4 * WeightValue.Height);
            item.setRawScore(4f);

        } else if (different < -3) {
            item.setScore(2 * WeightValue.Height);
            item.setRawScore(2f);

        }
        return item;

    }
}
