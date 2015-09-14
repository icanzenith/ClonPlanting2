package sugarcaneselection.thaib.org.sugarcanselection;

import java.util.HashMap;

import sugarcaneselection.thaib.org.sugarcanselection.database.Columns;

/**
 * Created by Jitpakorn on 1/24/15 AD.
 */
public class TableRowName {
    public static final String get(String Key) {
        HashMap<String, String> v = new HashMap<>();
        v.put(Columns.CLONECODE, "ชื่อโคลน");
//        v.put(Columns.STALKPERCLUMP, "ลำต่อกอ");
//        v.put(Columns.STALKSIZE, "ขนาดลำ");
//        v.put(Columns.STALKHEIGHT, "ความสูง");
//        v.put(Columns.STALKSHAPE, "ทรงกอ");
//        v.put(Columns.STALKTEXTURE, "เนื้ออ้อย");
        v.put(Columns.BRIX, "Brix");
//        v.put(Columns.INTERNODELENGHT, "ความยาวข้อปล้อง");
//        v.put(Columns.INTERNODEPERSTALK, "จำนวนข้อปล้องต่อลำ");
        v.put(Columns.WHITE_FLY, "แมลงหวี่ขาว");
        v.put(Columns.BORER, "หนอนเจาะลำต้น");
//        v.put(Columns.YELLOWSPOT, "ใบจุดเหลือง");
//        v.put(Columns.OTHERDISEASE, "โรคอื่นๆ");
        v.put(Columns.FLOWERING, "การออกดอก");
        v.put(Columns.OVERALL, "คะแนนภาพรวม");
//        v.put(Columns.LEAFFALL, "การหลุดร่วงของกาบใบ");

        return v.get(Key);

    }

}
