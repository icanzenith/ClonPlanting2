package sugarcaneselection.thaib.org.sugarcanselection.freetest;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

import java.util.HashMap;

import sugarcaneselection.thaib.org.sugarcanselection.BaseApplication;
import sugarcaneselection.thaib.org.sugarcanselection.Item.Code;
import sugarcaneselection.thaib.org.sugarcanselection.Item.ScoreItem;
import sugarcaneselection.thaib.org.sugarcanselection.R;

/**
 * Created by Jitpakorn on 2/23/15 AD.
 */
public class CheckScoreTestActivity extends ActionBarActivity{

    private BaseApplication b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testactivity_score);
        StringBuilder sb = new StringBuilder();
        TextView  score = (TextView) findViewById(R.id.tvScore);
        b = (BaseApplication) getApplication();
        HashMap<String,ScoreItem> dS = b.getCloneDataItem().getDataScore();
        sb.append("แมลงหวี่ขาว : "+dS.get(Code.WhiteFly).getTagName()+" "+ dS.get(Code.WhiteFly).getScore()+" "+dS.get(Code.WhiteFly).getRawScore() + "\n");
        sb.append("หนอนเจาะลำต้น : "+dS.get(Code.Borer).getTagName()+" "+ dS.get(Code.Borer).getScore()+" "+dS.get(Code.Borer).getRawScore()+"\n");
        sb.append("เพลี้ยอ่อน : "+dS.get(Code.Aphid).getTagName()+" "+ dS.get(Code.Aphid).getScore()+" "+dS.get(Code.Aphid).getRawScore()+"\n");
        sb.append("เพลี้ยแป้งสำลี : "+dS.get(Code.IceryaMealbug).getTagName()+" "+ dS.get(Code.IceryaMealbug).getScore()+" "+dS.get(Code.IceryaMealbug ).getRawScore()+"\n");
        sb.append("โรคยอดบิด : "+dS.get(Code.PokkahBoeng).getTagName()+" "+ dS.get(Code.PokkahBoeng).getScore()+" "+dS.get(Code.PokkahBoeng).getRawScore()+"\n");
        sb.append("ใบจุดเหลือง : "+dS.get(Code.YellowSpot).getTagName()+" "+ dS.get(Code.YellowSpot).getScore()+" "+dS.get(Code.YellowSpot).getRawScore()+"\n");
        sb.append("ใบจุดน้ำตาล : "+dS.get(Code.BrownSpot).getTagName()+" "+ dS.get(Code.BrownSpot).getScore()+" "+dS.get(Code.BrownSpot).getRawScore()+"\n");
        sb.append("ใบขุดวงแหวน : "+dS.get(Code.RingSpot).getTagName()+" "+ dS.get(Code.RingSpot).getScore()+" "+dS.get(Code.RingSpot).getRawScore()+"\n");
        sb.append("ราสนิม : "+dS.get(Code.Rust).getTagName()+" "+ dS.get(Code.Rust).getScore()+" "+dS.get(Code.Rust).getRawScore()+"\n");
        sb.append("ราน้ำค้าง : "+dS.get(Code.DownyMildew).getTagName()+" "+ dS.get(Code.DownyMildew).getScore()+" "+dS.get(Code.DownyMildew).getRawScore()+"\n");
        sb.append("โรคอืิ่น ๆ : "+dS.get(Code.OtherDisease).getTagName()+" "+ dS.get(Code.OtherDisease).getScore()+" "+dS.get(Code.OtherDisease).getRawScore()+"\n");
        sb.append("คะแนนภาพรวม : "+dS.get(Code.OverAll).getTagName()+" "+ dS.get(Code.OverAll).getScore()+" "+dS.get(Code.OverAll).getRawScore()+"\n");
        sb.append("การออกดอก : "+getStringbycode(Code.Flowering));
        sb.append("Brix : "+dS.get(Code.Brix).getTagName()+" "+ dS.get(Code.Brix).getScore()+" "+dS.get(Code.Brix).getRawScore()+"\n");
        sb.append("ความสูง : "+getStringbycode(Code.Height));
        sb.append("ภาพรวมทางสายตา : "+getStringbycode(Code.OverAll));
        sb.append("การลอกของกาบใบ : "+getStringbycode(Code.LeafSheath));
        sb.append("จำนวนลำต่อกอ : "+getStringbycode(Code.StalkAmount));
        sb.append("จำนวนปล้อง : "+getStringbycode(Code.InternodeAmount));
        sb.append("ขนาดลำ : "+getStringbycode(Code.StalkSize));
        sb.append("ทรงกอ : "+getStringbycode(Code.ClumpShape));
        sb.append("ลักษณะกอ : "+getStringbycode(Code.ClumpCharacteristic));
        sb.append("เนื้ออ้อยด้านโรค"+getStringbycode(Code.InternalSymtom));
        sb.append("เนื้ออ้วยด้านความหนาแน่น"+getStringbycode(Code.InternalFirmness));
        sb.append("ความยาวปล้อง : "+getStringbycode(Code.InternodeLength));
        sb.append("ลักษณะไส้"+getStringbycode(Code.Stuff));
        score.setText(sb.toString());

    }

    private String getStringbycode(String Code)
    {
        String s;
        HashMap<String,ScoreItem> dS = b.getCloneDataItem().getDataScore();
        s = dS.get(Code).getTagName()+" "+dS.get(Code).getData()+" "+dS.get(Code).getScore()+" "+dS.get(Code).getRawScore()+"\n";
        return  s;
    }}
