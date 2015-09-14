package sugarcaneselection.thaib.org.sugarcanselection.Item;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;

import java.util.HashMap;

import sugarcaneselection.thaib.org.sugarcanselection.R;

/**
 * Created by Jitpakorn on 3/4/15 AD.
 */
public class ProgressScoreView {


    public static View ProgreesContainerView(Context context, int ResLayoutID, ScoreItem item) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(ResLayoutID, null);
        TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        for (String title : Code.getTAGName().keySet()) {
            if (item.getTagName()!=null){
                if (item.getTagName().equals(title)) {
                    tvTitle.setText(Code.getTAGName().get(item.getTagName()));
                }
            }


        }

        TextView data = (TextView) view.findViewById(R.id.tvData);
        for (String unit : Unit().keySet()) {
            if (unit.equals(item.getTagName())) {
                data.setText(item.getData().toString() + " " + Unit().get(unit).toString());
            }
        }

        RoundCornerProgressBar progressBar = (RoundCornerProgressBar) view.findViewById(R.id.progressBar);
        for (String unit : MaxScore().keySet()) {
            if (unit.equals(item.getTagName())) {
                progressBar.setMax(MaxScore().get(unit));
                progressBar.setProgress(item.getScore());
                if (item.getRawScore() > 4) {
                    progressBar.setProgressColor(0xFF88CC00);
                } else if (item.getRawScore() > 3 || item.getRawScore() <= 4) {
                    progressBar.setProgressColor(0xFF7F9900);
                } else if (item.getRawScore() > 2 || item.getRawScore() <= 3) {
                    progressBar.setProgressColor(0xFFFFAA00);

                } else if (item.getRawScore() > 1 || item.getRawScore() <= 2) {
                    progressBar.setProgressColor(0xFFFF8000);
                } else if (item.getRawScore() < 1) {
                    progressBar.setProgressColor(0xFFCC2200);
                }

            }
        }

        return view;

    }

    private static HashMap<String, String> Unit() {

        HashMap<String, String> map = new HashMap<>();
        map.put(Code.WhiteFly, "");
        map.put(Code.Borer, "");
        map.put(Code.Aphid, "");
        map.put(Code.IceryaMealbug, "");
        map.put(Code.Scale, "");
        map.put(Code.PokkahBoeng, "");
        map.put(Code.YellowSpot, "");
        map.put(Code.BrownSpot, "");
        map.put(Code.RingSpot, "");
        map.put(Code.Rust, "");
        map.put(Code.DownyMildew, "");
        map.put(Code.OtherDisease, "");

        map.put(Code.Flowering, "");
        map.put(Code.Brix, "");
        map.put(Code.Height, "cm");
        map.put(Code.OverAll, "คะแนน");
        map.put(Code.LeafSheath, "");
        map.put(Code.StalkAmount, "ลำ/กอ");
        map.put(Code.InternodeAmount, "ปล้อง/ลำ");
        map.put(Code.StalkSize, "cm");
        map.put(Code.ClumpShape, "");
        map.put(Code.ClumpCharacteristic, "");
        map.put(Code.InternalSymtom, "");
        map.put(Code.InternalFirmness, "");
        map.put(Code.InternodeLength, "cm");
        map.put(Code.Stuff, "");
        return map;
    }

    private static HashMap<String, Integer> MaxScore() {

        HashMap<String, Integer> map = new HashMap<>();
        map.put(Code.WhiteFly, 15);
        map.put(Code.Borer, 10);
        map.put(Code.Aphid, 5);
        map.put(Code.IceryaMealbug, 5);
        map.put(Code.Scale, 5);
        map.put(Code.PokkahBoeng, 5);
        map.put(Code.YellowSpot, 10);
        map.put(Code.BrownSpot, 5);
        map.put(Code.RingSpot, 5);
        map.put(Code.Rust, 5);
        map.put(Code.DownyMildew, 5);
        map.put(Code.OtherDisease, 5);

        /**
         * Data
         * */

        map.put(Code.Flowering, 25);
        map.put(Code.Brix, 75);
        map.put(Code.Height, 30);
        map.put(Code.OverAll, 25);
        map.put(Code.LeafSheath, 10);
        map.put(Code.StalkAmount, 100);
        map.put(Code.InternodeAmount, 0);
        map.put(Code.StalkSize, 40);
        map.put(Code.ClumpShape, 20);
        map.put(Code.ClumpCharacteristic, 25);
        map.put(Code.InternalSymtom, 5);
        map.put(Code.InternalFirmness, 5);
        map.put(Code.InternodeLength, 10);
        map.put(Code.Stuff, 50);
        return map;
    }

}
