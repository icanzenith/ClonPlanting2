package sugarcaneselection.thaib.org.sugarcanselection;

import android.util.Log;

import sugarcaneselection.thaib.org.sugarcanselection.Item.CloneDataItem;

/**
 * Created by Jitpakorn on 1/26/15 AD.
 */
public class OnCalculateScore {

    private CloneDataItem cd;
    private CalculateScore cal;
    private BaseApplication baseApplication;

    public OnCalculateScore(CloneDataItem cd, BaseApplication baseApplication) {
        this.cd = cd;
        this.baseApplication = baseApplication;
        cal = new CalculateScore();


//        cal.setScore_stalktype(cd.getStalktype());


//        cal.setScore_texture(cd.getTexture());


//        setHeight_Score(cd.getHeight());
//        setBrix_Score((int) (cd.getBrix() * 10));
//        setObserve_Score(cd.getObserve_score());
//        setStalkperClump_Score(cd.getStalkperclump());

//        Log.d("GetCalType Score",""+cal.getScore_stalktype());
//        Log.d("GetCalSryff Score",""+cal.getScore_texture());
//        this.baseApplication.setCalculateScore(cal);
//        this.baseApplication.getCalculateScore().getTotalScore(cd);
//        Log.d("GetCalType Score2",""+cal.getScore_stalktype());
//        Log.d("GetCalSryff Score2",""+cal.getScore_texture());

    }

    private void setHeight_Score(int height) {

//        int standard = baseApplication.getStandardSpecieData().getHeight();
//        baseApplication.getCloneDataItem().setHeight(height);
//        int result = height - standard;
//
//        Log.d("Debug", "" + result);
//
//        if (result > 3) {
//            สูงกว่าพันธุ์ตรวจสอบมากกว่า 3
//            cal.setScore_height(5);
//
//
//        } else if (result <= 3 && result >= -3) {
//            บวก ลบ 3
//            cal.setScore_height(4);
//        } else if (result < -3) {
//            ต่ำกว่าพันธฺุ์มากกว่า 3 cm
//            cal.setScore_height(2);
//        }

    }

    private void setBrix_Score(int brix) {

        if (brix > 210) {
            cal.setScore_Brix(5);


        } else if (brix > 200 && brix <= 210) {
            cal.setScore_Brix(4);


        } else if (brix > 190 && brix <= 200) {
            cal.setScore_Brix(3);


        } else if (brix > 180 && brix <= 190) {
            cal.setScore_Brix(2);


        } else if (brix > 170 && brix <= 180) {
            cal.setScore_Brix(1);


        } else if (brix <= 170) {
            cal.setScore_Brix(0);


        }
    }

    private void setObserve_Score(float observe) {
        cal.setScore_observe_score(observe);
//        baseApplication.getCloneDataItem().setObserve_score(observe);
    }

    private void setStalkperClump_Score(int stalkperclump) {
//        baseApplication.getCloneDataItem().setStalkperclump(stalkperclump);
        if (stalkperclump > 10) {
            cal.setScore_stalkperclump(5);

        } else if (stalkperclump <= 9 && stalkperclump >= 7) {
            cal.setScore_stalkperclump(4);


        } else if (stalkperclump >= 5 && stalkperclump <= 6) {
            cal.setScore_stalkperclump(3);

        } else if (stalkperclump == 4) {
            cal.setScore_stalkperclump(2);

        } else if (stalkperclump >= 1 && stalkperclump <= 3) {
            cal.setScore_stalkperclump(1);
        }

    }

    private void setStalkSize_Score(int size) {
        if (size >= 30) {
            Log.d("Debug Size", "> 30");
            cal.setScore_stalksize(5);

        } else if (size >= 27 && size <= 29) {
            cal.setScore_stalksize(4);
        } else if (size >= 25 && size <= 26) {
            cal.setScore_stalksize(3);
        } else {
            cal.setScore_stalksize(0);
        }
    }

    public CloneDataItem getCd() {
        return cd;
    }

    public void setCd(CloneDataItem cd) {
        this.cd = cd;
    }

    public CalculateScore getCal() {
        return cal;
    }

    public void setCal(CalculateScore cal) {
        this.cal = cal;
    }
}

