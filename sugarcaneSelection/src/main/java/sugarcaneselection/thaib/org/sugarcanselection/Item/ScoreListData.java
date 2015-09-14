package sugarcaneselection.thaib.org.sugarcanselection.Item;

import java.util.ArrayList;

/**
 * Created by Jitpakorn on 2/24/15 AD.
 */
public class ScoreListData {



    public ScoreListData() {

    }

    public ArrayList<DataGroupItem> getDataList(){

        ArrayList<DataGroupItem> s = new ArrayList<>();

        DataGroupItem[] d = new DataGroupItem[12];
        d[0] = new DataGroupItem();
        d[0].setGroupCode(Code.Flowering);
        d[0].setGroupTitle("การออกดอก");
        d[0].setOptionType(1);
        d[0].setScoreItem(getChoiceItem(Code.Flowering));

        s.add(d[0]);

        d[1] = new DataGroupItem();
        d[1].setGroupCode(Code.LeafSheath);
        d[1].setGroupTitle("การร่วงของกาบใบ");
        d[1].setOptionType(1);
        d[1].setScoreItem(getChoiceItem(Code.LeafSheath));
        s.add(d[1]);

        d[2] = new DataGroupItem();
        d[2].setGroupCode(Code.ClumpShape);
        d[2].setGroupTitle("ทรงกอ");
        d[2].setOptionType(1);
        d[2].setScoreItem(getChoiceItem(Code.ClumpShape));
        s.add(d[2]);

        d[3] = new DataGroupItem();
        d[3].setGroupCode(Code.ClumpCharacteristic);
        d[3].setGroupTitle("ลักษณะกอ");
        d[3].setOptionType(1);
        d[3].setScoreItem(getChoiceItem(Code.ClumpCharacteristic));
        s.add(d[3]);








        return s;
    }

    public ArrayList<DataGroupItem> getStuffFragmentDataList(){
        ArrayList<DataGroupItem> s = new ArrayList<>();
        DataGroupItem[] d = new DataGroupItem[12];

        d[0] = new DataGroupItem();
        d[0].setGroupCode(Code.InternalSymtom);
        d[0].setGroupTitle("เนื้ออ้อยด้านโรค");
        d[0].setOptionType(1);
        d[0].setScoreItem(getChoiceItem(Code.InternalSymtom));
        s.add(d[0]);

        d[1] = new DataGroupItem();
        d[1].setGroupCode(Code.InternalFirmness);
        d[1].setGroupTitle("เนื้ออ้อยด้านความหนาแน่น");
        d[1].setOptionType(1);
        d[1].setScoreItem(getChoiceItem(Code.InternalFirmness));
        s.add(d[1]);

        d[2] = new DataGroupItem();
        d[2].setGroupCode(Code.Stuff);
        d[2].setGroupTitle("ไส้อ้อย");
        d[2].setOptionType(1);
        d[2].setScoreItem(getChoiceItem(Code.Stuff));
        s.add(d[2]);

        return s;
    }

    private ArrayList<ScoreItem> getChoiceItem(String type){
        ArrayList<ScoreItem> arrayList = new ArrayList<>();

        if (type.equals(Code.Flowering)){

            ScoreItem level[] = new ScoreItem[5];
            level[0] = new ScoreItem();

            level[0].setTagName(Code.Flowering);
            level[0].setData("ไม่ออกดอก");
            level[0].setRawScore(5f);
            level[0].setScore(level[0].getRawScore()*WeightValue.Flowering);
            level[0].setUnit(Code.getUnit().get(Code.Flowering));

            arrayList.add(level[0]);

            level[1] = new ScoreItem();

            level[1].setTagName(Code.Flowering);
            level[1].setData("< 50 %");
            level[1].setRawScore(3f);
            level[1].setScore(level[1].getRawScore()*WeightValue.Flowering);
            level[1].setUnit(Code.getUnit().get(Code.Flowering));


            arrayList.add(level[1]);

            level[2] = new ScoreItem();

            level[2].setTagName(Code.Flowering);
            level[2].setData("> 50 %");
            level[2].setRawScore(0f);
            level[2].setScore(level[2].getRawScore()*WeightValue.Flowering);
            level[2].setUnit(Code.getUnit().get(Code.Flowering));


            arrayList.add(level[2]);

            return arrayList;
        }

        if (type.equals(Code.ClumpShape)){

            ScoreItem level[] = new ScoreItem[5];

            level[0] = new ScoreItem();
            level[0].setTagName(Code.ClumpShape);
            level[0].setData("ไม่ได้บันทึก");
            level[0].setRawScore(0f);
            level[0].setScore(0);
            arrayList.add(level[0]);

            level[1] = new ScoreItem();
            level[1].setTagName(Code.ClumpShape);
            level[1].setData("แคบ");
            level[1].setRawScore(5f);
            level[1].setScore(5*WeightValue.ClumpShape);
            arrayList.add(level[1]);

            level[2] = new ScoreItem();
            level[2].setTagName(Code.ClumpShape);
            level[2].setData("กว้าง");
            level[2].setRawScore(5f);
            level[2].setScore(5*WeightValue.ClumpShape);
            arrayList.add(level[2]);

            level[3] = new ScoreItem();
            level[3].setTagName(Code.ClumpShape);
            level[3].setData("แบะน้อย");
            level[3].setRawScore(3f);
            level[3].setScore(3*WeightValue.ClumpShape);
            arrayList.add(level[3]);

            level[4] = new ScoreItem();
            level[4].setTagName(Code.ClumpShape);
            level[4].setData("แบะมาก");
            level[4].setRawScore(0f);
            level[4].setScore(0*WeightValue.ClumpShape);
            arrayList.add(level[4]);

            return arrayList;
        }

        if (type.equals(Code.ClumpCharacteristic)){

            ScoreItem level[] = new ScoreItem[5];

            level[0] = new ScoreItem();
            level[0].setTagName(Code.ClumpCharacteristic);
            level[0].setData("ไม่ได้บันทึก");
            level[0].setRawScore(1f);
            level[0].setScore(-1);
            arrayList.add(level[0]);

            level[1] = new ScoreItem();
            level[1].setTagName(Code.ClumpCharacteristic);
            level[1].setData("ไม่ล้ม");
            level[1].setRawScore(2f);
            level[1].setScore(5*WeightValue.ClumpCharacteristic);
            arrayList.add(level[1]);

            level[2] = new ScoreItem();
            level[2].setTagName(Code.ClumpCharacteristic);
            level[2].setData("ล้มเพราะดินยุบ");
            level[2].setRawScore(3f);
            level[2].setScore(3*WeightValue.ClumpCharacteristic);
            arrayList.add(level[2]);

            level[3] = new ScoreItem();
            level[3].setTagName(Code.ClumpCharacteristic);
            level[3].setData("ล้ม");
            level[3].setRawScore(4f);
            level[3].setScore(0*WeightValue.ClumpCharacteristic);
            arrayList.add(level[3]);

            return arrayList;
        }

        if (type.equals(Code.InternalSymtom)){

            ScoreItem level[] = new ScoreItem[5];

            level[0] = new ScoreItem();
            level[0].setTagName(Code.InternalSymtom);
            level[0].setData("ไม่ได้บันทึก");
            level[0].setRawScore(1f);
            level[0].setScore(-1);
            arrayList.add(level[0]);

            level[1] = new ScoreItem();
            level[1].setTagName(Code.InternalSymtom);
            level[1].setData("สะอาด");
            level[1].setRawScore(2f);
            level[1].setScore(5*WeightValue.InternalSymtom);
            arrayList.add(level[1]);

            level[2] = new ScoreItem();
            level[2].setTagName(Code.InternalSymtom);
            level[2].setData("มีร่องรอยของโรคน้อย");
            level[2].setRawScore(3f);
            level[2].setScore(3*WeightValue.InternalSymtom);
            arrayList.add(level[2]);

            level[3] = new ScoreItem();
            level[3].setTagName(Code.InternalSymtom);
            level[3].setData("มีร่อยรอยของโรคมาก");
            level[3].setRawScore(4f);
            level[3].setScore(0);
            arrayList.add(level[3]);

            return arrayList;
        }
        if (type.equals(Code.InternalFirmness)){

            ScoreItem level[] = new ScoreItem[5];

            level[0] = new ScoreItem();
            level[0].setTagName(Code.InternalFirmness);
            level[0].setData("ไม่ได้บันทึก");
            level[0].setRawScore(1f);
            level[0].setScore(-1);
            arrayList.add(level[0]);

            level[1] = new ScoreItem();
            level[1].setTagName(Code.InternalFirmness);
            level[1].setData("ไม่ฟ่าม");
            level[1].setRawScore(2f);
            level[1].setScore(5*WeightValue.InternalFirmness);
            arrayList.add(level[1]);

            level[2] = new ScoreItem();
            level[2].setTagName(Code.InternalFirmness);
            level[2].setData("ฟ่ามเล็กน้อย");
            level[2].setRawScore(3f);
            level[2].setScore(2*WeightValue.InternalFirmness);
            arrayList.add(level[2]);

            level[3] = new ScoreItem();
            level[3].setTagName(Code.InternalFirmness);
            level[3].setData("ฟ่าม");
            level[3].setRawScore(4f);
            level[3].setScore(0*WeightValue.InternalFirmness);
            arrayList.add(level[3]);


            return arrayList;
        }
        if (type.equals(Code.Stuff)){

            ScoreItem level[] = new ScoreItem[5];

            level[0] = new ScoreItem();
            level[0].setTagName(Code.Stuff);
            level[0].setData("ไม่ได้บันทึก");
            level[0].setRawScore(1f);
            level[0].setScore(-1);
            arrayList.add(level[0]);

            level[1] = new ScoreItem();
            level[1].setTagName(Code.Stuff);
            level[1].setData("ตัน");
            level[1].setRawScore(2f);
            level[1].setScore(5*WeightValue.Stuff);
            arrayList.add(level[1]);

            level[2] = new ScoreItem();
            level[2].setTagName(Code.Stuff);
            level[2].setData("กลวงเล็กน้อย");
            level[2].setRawScore(3f);
            level[2].setScore(4*WeightValue.Stuff);
            arrayList.add(level[2]);

            level[3] = new ScoreItem();
            level[3].setTagName(Code.Stuff);
            level[3].setData("กลวงปานกลาง");
            level[3].setRawScore(4f);
            level[3].setScore(2*WeightValue.Stuff);
            arrayList.add(level[3]);

            level[4] = new ScoreItem();
            level[4].setTagName(Code.Stuff);
            level[4].setData("กลวงมาก");
            level[4].setRawScore(5f);
            level[4].setScore(0*WeightValue.Stuff);
            arrayList.add(level[4]);

            return arrayList;


        }

        if (type.equals(Code.LeafSheath)){

            ScoreItem level[] = new ScoreItem[5];

            level[0] = new ScoreItem();
            level[0].setTagName(Code.LeafSheath);
            level[0].setData("ไม่ได้บันทึก");
            level[0].setRawScore(1f);
            level[0].setScore(-1);
            arrayList.add(level[0]);

            level[1] = new ScoreItem();
            level[1].setTagName(Code.LeafSheath);
            level[1].setData("ง่าย");
            level[1].setRawScore(2f);
            level[1].setScore(5*WeightValue.LeafSheath);
            arrayList.add(level[1]);

            level[2] = new ScoreItem();
            level[2].setTagName(Code.LeafSheath);
            level[2].setData("ปานกลาง");
            level[2].setRawScore(3f);
            level[2].setScore(4*WeightValue.LeafSheath);
            arrayList.add(level[2]);

            level[3] = new ScoreItem();
            level[3].setTagName(Code.LeafSheath);
            level[3].setData("หลุดเอง");
            level[3].setRawScore(4f);
            level[3].setScore(3*WeightValue.LeafSheath);
            arrayList.add(level[3]);

            level[4] = new ScoreItem();
            level[4].setTagName(Code.LeafSheath);
            level[4].setData("ยาก");
            level[4].setRawScore(5f);
            level[4].setScore(2*WeightValue.LeafSheath);
            arrayList.add(level[4]);

            return  arrayList;
        }


        return null;


    }
}
