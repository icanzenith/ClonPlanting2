package sugarcaneselection.thaib.org.sugarcanselection.Util;

import sugarcaneselection.thaib.org.sugarcanselection.Item.WeightValue;

/**
 * Created by Jitpakorn on 4/7/15 AD.
 */
public class ReCalculateScore {

    public ReCalculateScore() {
    }

    //
    public static Float ClumpCharecteristic(String data) {
        Float score = null;
        if (data.contains("ไม่ล้ม")) {
            score = Float.valueOf(5 * WeightValue.ClumpCharacteristic);
        } else if (data.equals("ล้มเพราะดินยุบ")) {
            score = Float.valueOf(3 * WeightValue.ClumpCharacteristic);
        } else if (data
                .equals("ล้ม")) {
            score = Float.valueOf(0 * WeightValue.ClumpCharacteristic);
        } else if (data.equals("ไม่ได้บันทึก")) {
            score = -1f;
        }
        return score;
    }
}
