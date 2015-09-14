package sugarcaneselection.thaib.org.sugarcanselection.Item;

import java.util.HashMap;

/**
 * Created by Jitpakorn on 2/20/15 AD.
 */

public class Code {


    /**
    *  Disease Code
    * */
    public static final String WhiteFly = "Whitefly";
    public static final String Borer = "Borer";
    public static final String Aphid = "Aphid";
    public static final String IceryaMealbug ="IceryaMealbug";
    public static final String Scale = "Scale";
    public static final String PokkahBoeng ="PokkahBoeng";
    public static final String YellowSpot = "YellowSpot";
    public static final String BrownSpot = "BrownSpot";
    public static final String RingSpot = "RingSpot";
    public static final String Rust = "Rust";
    public static final String DownyMildew = "DownyMildew";
    public static final String OtherDisease = "OtherDisease";

    /**
     * Data
    * */

    public static final String Flowering = "Flowering";
    public static final String Brix = "Brix";
    public static final String Height = "Height";
    public static final String OverAll = "OverAll";
    public static final String LeafSheath = "LeafSheath";
    public static final String StalkAmount = "StalkAmount";
    public static final String InternodeAmount = "InternodeAmount";
    public static final String StalkSize = "StalkSize";
    public static final String ClumpShape = "ClumpShape";
    public static final String ClumpCharacteristic = "ClumpCharacteristic";
    public static final String InternalSymtom = "InternalSymtom";
    public static final String InternalFirmness = "InternalFirmness";
    public static final String InternodeLength = "InternodeLength";
    public static final String Stuff = "Stuff";

    public static String[] KeySet ={
            WhiteFly,Borer,Aphid,IceryaMealbug,Scale,PokkahBoeng,YellowSpot,BrownSpot,RingSpot,Rust
            ,DownyMildew,OtherDisease,Flowering,Brix
            ,Height,OverAll,LeafSheath,StalkAmount,StalkSize,ClumpShape,ClumpCharacteristic,InternalSymtom,
            InternalFirmness,InternodeLength,Stuff,InternodeAmount
    };

    public static HashMap<String,String> getTAGName(){
        HashMap<String,String> map = new HashMap<>();


        map.put(WhiteFly , "แมลงหวี่ขาว");
        map.put(Borer , "หนอนเจาะลำต้น");
        map.put(Aphid , "เพลี้ยอ่อน");
        map.put(IceryaMealbug ,"เพลี้ยแป้งสำลี");
        map.put(Scale , "เพลี้ยหอย");
        map.put(PokkahBoeng ,"โรคยอดบิด");
        map.put(YellowSpot , "โรคใบจุดเหลือง");
        map.put(BrownSpot , "โรคใบจุดสีน้ำตาล");
        map.put(RingSpot , "โรคใบจุดวงแหวน");
        map.put(Rust , "โรคราสนิม");
        map.put(DownyMildew , "โรคราน้ำค้าง");
        map.put(OtherDisease , "โรคอื่นๆ");
        map.put(Flowering , "การออกดอก");
        map.put(Brix , "Brix");
        map.put(Height , "ความสูง");
        map.put(OverAll , "ภาพรวมด้วยสายตา");
        map.put(LeafSheath , "การลอกของกาบใบ");
        map.put(StalkAmount , "จำนวนลำ");
        map.put(InternodeAmount , "จำนวข้อปล้องต่าลำ");
        map.put(StalkSize , "ขนาดลำ");
        map.put(ClumpShape , "ทรงกอ");
        map.put(ClumpCharacteristic , "ลักษณะกอ");
        map.put(InternalSymtom , "เนื้ออ้อยด้านโรค");
        map.put(InternalFirmness , "ความแน่นของเนื้ออ้อย");
        map.put(InternodeLength , "ความยาวข้อปล้อง");
        map.put(Stuff , "ไส้");

        return map;
    }
    public static HashMap<String,String> getUnit(){
        HashMap<String,String> map = new HashMap<>();


        map.put(WhiteFly , "");
        map.put(Borer , "");
        map.put(Aphid , "");
        map.put(IceryaMealbug ,"");
        map.put(Scale , "");
        map.put(PokkahBoeng ,"");
        map.put(YellowSpot , "");
        map.put(BrownSpot , "");
        map.put(RingSpot , "");
        map.put(Rust , "");
        map.put(DownyMildew , "");
        map.put(OtherDisease , "");
        map.put(Flowering , "");
        map.put(Brix , "");
        map.put(Height , "cm");
        map.put(OverAll , "คะแนน");
        map.put(LeafSheath , "");
        map.put(StalkAmount , "ลำ/กอ");
        map.put(InternodeAmount , "ปล้อง/ลำ");
        map.put(StalkSize , "cm");
        map.put(ClumpShape , "");
        map.put(ClumpCharacteristic , "");
        map.put(InternalSymtom , "");
        map.put(InternalFirmness , "");
        map.put(InternodeLength , "cm");
        map.put(Stuff , "");

        return map;
    }

 }
