package sugarcaneselection.thaib.org.sugarcanselection.Item;


import java.util.ArrayList;

public class DiseaseListData {



    public DiseaseListData() {

    }

    public ArrayList<DataGroupItem> getDiseaseList(){
        ArrayList<DataGroupItem> s =  new ArrayList<>();

        //แมลงหวี่ขาว
        DataGroupItem[] d = new DataGroupItem[12];
        d[0] = new DataGroupItem();
        d[0].setGroupCode(Code.WhiteFly);
        d[0].setGroupTitle("แมลงหวี่ขาว");
        d[0].setOptionType(1);
        d[0].setScoreItem(getRadioOptionItems(1,Code.WhiteFly));
        s.add(d[0]);

        //หนอนเจาะลำต้น
        d[1] = new DataGroupItem();
        d[1].setGroupCode(Code.Borer);
        d[1].setGroupTitle("หนอนเจาะลำต้น");
        d[1].setOptionType(1);
        d[1].setScoreItem(getRadioOptionItems(1,Code.Borer));
        s.add(d[1]);

        //เพลี้ยอ่อน
        d[2] = new DataGroupItem();
        d[2].setGroupCode(Code.Aphid);
        d[2].setGroupTitle("เพลี้ยแป้ง");
        d[2].setOptionType(1);
        d[2].setScoreItem(getRadioOptionItems(1,Code.Aphid));
        s.add(d[2]);

        //เพลี้ยแป้งสำลี
        d[3] = new DataGroupItem();
        d[3].setGroupCode(Code.IceryaMealbug);
        d[3].setGroupTitle("เพลี้ยสำลี");
        d[3].setOptionType(1);
        d[3].setScoreItem(getRadioOptionItems(1,Code.IceryaMealbug));
        s.add(d[3]);

        //เพลี้ยหอย
        d[4] = new DataGroupItem();
        d[4].setGroupCode(Code.Scale);
        d[4].setGroupTitle("เพลี้ยหอย");
        d[4].setOptionType(1);
        d[4].setScoreItem(getRadioOptionItems(1,Code.Scale));
        s.add(d[4]);

        //โรคยอดบิด
        d[5] = new DataGroupItem();
        d[5].setGroupCode(Code.PokkahBoeng);
        d[5].setGroupTitle("ยอดบิด");
        d[5].setOptionType(1);
        d[5].setScoreItem(getRadioOptionItems(1,Code.PokkahBoeng));
        s.add(d[5]);

        //ใบจุดเหลือง
        d[6] = new DataGroupItem();
        d[6].setGroupCode(Code.YellowSpot);
        d[6].setGroupTitle("ใบจุดเหลือง");
        d[6].setOptionType(1);
        d[6].setScoreItem(getRadioOptionItems(1,Code.YellowSpot));
        s.add(d[6]);

        //ใบจุดสีน้ำตาล
        d[7] = new DataGroupItem();
        d[7].setGroupCode(Code.BrownSpot);
        d[7].setGroupTitle("ใบจุดสีน้ำตาล");
        d[7].setOptionType(1);
        d[7].setScoreItem(getRadioOptionItems(1,Code.BrownSpot));
        s.add(d[7]);

        //ใบจุดวงแหวน
        d[8] = new DataGroupItem();
        d[8].setGroupCode(Code.RingSpot);
        d[8].setGroupTitle("ใบจุดวงแหวน");
        d[8].setOptionType(1);
        d[8].setScoreItem(getRadioOptionItems(1,Code.RingSpot));
        s.add(d[8]);

        //โรคราสนิม
        d[9] = new DataGroupItem();
        d[9].setGroupCode(Code.Rust);
        d[9].setGroupTitle("ราสนิม");
        d[9].setOptionType(1);
        d[9].setScoreItem(getRadioOptionItems(1,Code.Rust));
        s.add(d[9]);

        //โรคราน้ำค้าง
        d[10] = new DataGroupItem();
        d[10].setGroupCode(Code.DownyMildew);
        d[10].setGroupTitle("ราน้ำค้าง");
        d[10].setOptionType(1);
        d[10].setScoreItem(getRadioOptionItems(1,Code.DownyMildew));
        s.add(d[10]);

        //โรคอื่นๆ
        d[11] = new DataGroupItem();
        d[11].setGroupCode(Code.OtherDisease);
        d[11].setGroupTitle("โรคอื่นๆ");
        d[11].setOptionType(2);
        d[11].setScoreItem(getRadioOptionItems(1,Code.OtherDisease));
        s.add(d[11]);

        return s;
    }


    /**
     *  Case 0 ประกอบด้ว
     * 2 พบน้อยกว่า 10 %
     * 3 พบมากกว่า 10 %
     * */

    private ArrayList<ScoreItem> getRadioOptionItems(int ListType,String Code){

        ArrayList<ScoreItem> arrayList = new ArrayList<>();

        switch (ListType){

            case 1:

                ScoreItem level2 = new ScoreItem();
                level2.setTagName(Code);
                level2.setData("< 10 %");
                level2.setRawScore(1f);
                level2.setScore(-3);

                ScoreItem level3 = new ScoreItem();
                level3.setTagName(Code);
                level3.setData("> 10 %");
                level3.setRawScore(3f);
                level3.setScore(-5);

                arrayList.add(level2);
                arrayList.add(level3);
                break;

        }

        return arrayList;
    }



}

