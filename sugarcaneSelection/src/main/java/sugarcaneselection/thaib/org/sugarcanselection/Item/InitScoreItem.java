package sugarcaneselection.thaib.org.sugarcanselection.Item;

/**
 * Created by Jitpakorn on 3/11/15 AD.
 */
public class InitScoreItem {

    public static final ScoreItem getDiseaseDefult(String Code){
        ScoreItem item = new ScoreItem();
        item.setTagName(Code);
        item.setData("ไม่พบร่องรอยของโรค");
        item.setScore(0);
        item.setRawScore(0f);
        item.setUnit("คะแนน");
        return item;
    }

    public static final ScoreItem Overview() {
        ScoreItem item = new ScoreItem();
        item.setTagName(Code.OverAll);
        item.setData(0f);
        item.setScore(0);
        item.setRawScore(0f);
        item.setUnit("คะแนน");
        return item;
    }

    public static final ScoreItem Brix() {
        ScoreItem item = new ScoreItem();
        item.setTagName(Code.Brix);
        item.setData(0f);
        item.setScore(0);
        item.setRawScore(1f);
        item.setUnit("");
        return item;
    }

    public static final ScoreItem Flowering() {
        ScoreItem item = new ScoreItem();
        item.setTagName(Code.Flowering);
        item.setData("ไม่ออกดอก");
        item.setScore(0);
        item.setRawScore(1f);
        item.setUnit("");
        return item;
    }

    public static final ScoreItem LeafSheath() {
        ScoreItem item = new ScoreItem();
        item.setTagName(Code.LeafSheath);
        item.setData("ไม่ได้บันทึก");
        item.setRawScore(1f);
        item.setScore(-1);
        item.setUnit("");
        return item;
    }

    public static final ScoreItem ClumpShape() {
        ScoreItem item = new ScoreItem();
        item.setTagName(Code.ClumpShape);
        item.setData("ไม่ได้บันทึก");
        item.setRawScore(1f);
        item.setScore(0);
        item.setUnit("");
        return item;
    }

    public static final ScoreItem ClumpCharacteristic() {
        ScoreItem item = new ScoreItem();
        item.setTagName(Code.ClumpCharacteristic);
        item.setData("ไม่ได้บันทึก");
        item.setRawScore(1f);
        item.setScore(-1);
        item.setUnit("");
        return item;
    }

    public static final ScoreItem StalkSize() {
        ScoreItem item = new ScoreItem();
        item.setTagName(Code.StalkSize);
        item.setData(0f);
        item.setScore(0);
        item.setRawScore(0f);
        item.setUnit("cm");
        return item;
    }

    public static final ScoreItem InternodeLength() {
        ScoreItem item = new ScoreItem();
        item.setTagName(Code.InternodeLength);
        item.setData(0f);
        item.setScore(0);
        item.setRawScore(0f);
        item.setUnit("cm");
        return item;
    }

    public static final ScoreItem StalkAmount() {
        ScoreItem item = new ScoreItem();
        item.setTagName(Code.StalkAmount);
        item.setData(0);
        item.setScore(0);
        item.setRawScore(0f);
        item.setUnit("ลำ/กอ");
        return item;
    }

    public static final ScoreItem Height() {
        ScoreItem item = new ScoreItem();
        item.setTagName(Code.Height);
        item.setData(0);
        item.setScore(0);
        item.setRawScore(0f);
        item.setUnit("cm");
        return item;
    }

    public static final ScoreItem InternodeAmount() {
        ScoreItem item = new ScoreItem();
        item.setTagName(Code.InternodeAmount);
        item.setData(0);
        item.setScore(0);
        item.setRawScore(0f);
        item.setUnit("ปล้องต่อลำ");
        return item;
    }

    public static final ScoreItem InternalSymtom() {
        ScoreItem item = new ScoreItem();
        item.setTagName(Code.InternalSymtom);
        item.setData("ไม่ได้บันทึก");
        item.setRawScore(1f);
        item.setScore(-1);
        item.setUnit("");
        return item;
    }

    public static final ScoreItem InternalFirmness() {
        ScoreItem item = new ScoreItem();
        item.setTagName(Code.InternalFirmness);
        item.setData("ไม่ได้บันทึก");
        item.setRawScore(1f);
        item.setScore(-1);
        item.setUnit("");
        return item;
    }

    public static final ScoreItem Stuff() {
        ScoreItem item = new ScoreItem();
        item.setTagName(Code.Stuff);
        item.setData("ไม่ได้บันทึก");
        item.setRawScore(1f);
        item.setScore(-1);
        item.setUnit("");
        return item;
    }

    public static final ScoreItem WhiteFly() {
        ScoreItem item = new ScoreItem();
        item.setTagName(Code.WhiteFly);
        item.setData("ไม่พบร่องรอยของโรค");
        item.setRawScore(0f);
        item.setScore(0);
        item.setUnit("");
        return item;
    }

    public static final ScoreItem Borer() {
        ScoreItem item = new ScoreItem();
        item.setTagName(Code.Borer);
        item.setData("ไม่พบร่องรอยของโรค");
        item.setRawScore(0f);
        item.setScore(0);
        item.setUnit("");
        return item;
    }

    public static final ScoreItem Aphid() {
        ScoreItem item = new ScoreItem();
        item.setTagName(Code.Aphid);
        item.setData("ไม่พบร่องรอยของโรค");
        item.setRawScore(0f);
        item.setScore(0);
        item.setUnit("");
        return item;
    }

    public static final ScoreItem IceryaMealbug() {
        ScoreItem item = new ScoreItem();
        item.setTagName(Code.IceryaMealbug);
        item.setData("ไม่พบร่องรอยของโรค");
        item.setRawScore(0f);
        item.setScore(0);
        item.setUnit("");
        return item;
    }

    public static final ScoreItem Scale() {
        ScoreItem item = new ScoreItem();
        item.setTagName(Code.Scale);
        item.setData("ไม่พบร่องรอยของโรค");
        item.setRawScore(0f);
        item.setScore(0);
        item.setUnit("");
        return item;
    }

    public static final ScoreItem PokkahBoeng() {
        ScoreItem item = new ScoreItem();
        item.setTagName(Code.PokkahBoeng);
        item.setData("ไม่พบร่องรอยของโรค");
        item.setRawScore(0f);
        item.setScore(0);
        item.setUnit("");
        return item;
    }

    public static final ScoreItem YellowSpot() {
        ScoreItem item = new ScoreItem();
        item.setTagName(Code.YellowSpot);
        item.setData("ไม่พบร่องรอยของโรค");
        item.setRawScore(0f);
        item.setScore(0);
        item.setUnit("");
        return item;
    }
    public static final ScoreItem BrownSpot() {
        ScoreItem item = new ScoreItem();
        item.setTagName(Code.BrownSpot);
        item.setData("ไม่พบร่องรอยของโรค");
        item.setRawScore(0f);
        item.setScore(0);
        item.setUnit("");
        return item;
    }
    public static final ScoreItem RingSpot() {
        ScoreItem item = new ScoreItem();
        item.setTagName(Code.RingSpot);
        item.setData("ไม่พบร่องรอยของโรค");
        item.setRawScore(0f);
        item.setScore(0);
        item.setUnit("");
        return item;
    }
    public static final ScoreItem Rust() {
        ScoreItem item = new ScoreItem();
        item.setTagName(Code.Rust);
        item.setData("ไม่พบร่องรอยของโรค");
        item.setRawScore(0f);
        item.setScore(0);
        item.setUnit("");
        return item;
    }
    public static final ScoreItem DownyMildew() {
        ScoreItem item = new ScoreItem();
        item.setTagName(Code.DownyMildew);
        item.setData("ไม่พบร่องรอยของโรค");
        item.setRawScore(0f);
        item.setScore(0);
        item.setUnit("");
        return item;
    }
    public static final ScoreItem OtherDisease() {
        ScoreItem item = new ScoreItem();
        item.setTagName(Code.OtherDisease);
        item.setData("ไม่พบร่องรอยของโรค");
        item.setRawScore(0f);
        item.setScore(0);
        item.setUnit("");
        return item;
    }



}
