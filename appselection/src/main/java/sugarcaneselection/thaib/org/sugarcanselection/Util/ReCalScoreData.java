package sugarcaneselection.thaib.org.sugarcanselection.Util;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import sugarcaneselection.thaib.org.sugarcanselection.Item.SelectStatus;
import sugarcaneselection.thaib.org.sugarcanselection.R;
import sugarcaneselection.thaib.org.sugarcanselection.database.Columns;

/**
 * Created by Jitpakorn on 4/7/15 AD.
 */
public class ReCalScoreData {

    private Context context;
    private ContentResolver contentResolver;

    public ReCalScoreData(Context context, ContentResolver contentResolver) {
        this.context = context;
        this.contentResolver = contentResolver;
    }

    public void onCaribrate() {

        Uri uri = Uri.parse(context.getResources().getString(R.string.URI_MYCLONE));
        String[] projection = null;
        String sortOrder = null;
        String selection = Columns.SELECT_STATUS+" != "+ SelectStatus.NOTHING;
        String[] selectionArgs = null;
        ContentResolver r = context.getContentResolver();
        Cursor c = r.query(uri, projection, selection, selectionArgs, sortOrder);
        while (c != null && c.moveToNext()) {
            ContentValues v = new ContentValues();

            String whitefly = c.getString(c.getColumnIndex(Columns.WHITE_FLY));
            String borer = c.getString(c.getColumnIndex(Columns.BORER));
            String aphid = c.getString(c.getColumnIndex(Columns.APHID));
            String mealbug = c.getString(c.getColumnIndex(Columns.ICERYA_MEAL_BUG));
            String scale = c.getString(c.getColumnIndex(Columns.SCALE));
            String boeng = c.getString(c.getColumnIndex(Columns.POKKAH_BOENG));
            String yellowspot = c.getString(c.getColumnIndex(Columns.YELLOW_SPOT));
            String brownspot = c.getString(c.getColumnIndex(Columns.BROWN_SPOT));
            String ringspot = c.getString(c.getColumnIndex(Columns.RING_SPOT));
            String rust = c.getString(c.getColumnIndex(Columns.RUST));
            String downymildew = c.getString(c.getColumnIndex(Columns.DOWNY_MILDEW));
            String otherdisease = c.getString(c.getColumnIndex(Columns.OTHER_DISEASE));


            if (whitefly != null) {
                v.putAll(WhiteFly(whitefly));
            }
            if (borer != null) {
                v.putAll(Bored(borer));
            }
            if (aphid != null) {
                v.putAll(Aphid(aphid));
            }
            if (mealbug != null) {
                v.putAll(IceryaMealBug(mealbug));
            }
            if (scale != null) {
                v.putAll(Scale(scale));
            }
            if (boeng != null) {
                v.putAll(Boeng(boeng));
            }
            if (yellowspot != null) {
                v.putAll(YellowSpot(yellowspot));
            }

            if (brownspot != null) {
                v.putAll(BrowSpot(brownspot));
            }
            if (ringspot != null) {
                v.putAll(RingSpot(ringspot));
            }
            if (rust != null) {
                v.putAll(Rust(rust));
            }
            if (downymildew != null) {
                v.putAll(DownyMildew(downymildew));
            }
            if (otherdisease != null) {
                v.putAll(OtherDisease(otherdisease));
            }


            String flowering = c.getString(c.getColumnIndex(Columns.FLOWERING));
            Float brix = c.getFloat(c.getColumnIndex(Columns.BRIX));
            Float height = c.getFloat(c.getColumnIndex(Columns.HEIGHT));
            Float overall = c.getFloat(c.getColumnIndex(Columns.OVERALL));
            String leafsheath = c.getString(c.getColumnIndex(Columns.LEAF_SHEATH));
            Integer stalkperclump = c.getInt(c.getColumnIndex(Columns.STALK_AMOUNT));
            Float stalksize = c.getFloat(c.getColumnIndex(Columns.STALK_SIZE_AVERAGE));
            String clumpshape = c.getString(c.getColumnIndex(Columns.CLUMP_SHAPE));
            String clumpcharecteristic = c.getString(c.getColumnIndex(Columns.CLUMP_CHARACTERISTIC));
            String internalsymtom = c.getString(c.getColumnIndex(Columns.INTERNAL_SYSTOM));
            String firmness = c.getString(c.getColumnIndex(Columns.INTERNAL_FIRMNESS));
            Float internodelength = c.getFloat(c.getColumnIndex(Columns.INTERNODE_LENGTH_AVERAGE));
            String stuff = c.getColumnName(c.getColumnIndex(Columns.STUFF));

            Log.d("Debug Load",whitefly+" : "+c.getString(c.getColumnIndex(Columns.CLONECODE)));

            if (flowering != null) {
                v.putAll(Flowering(flowering));
            }

            if (brix != null) {
                v.putAll(Brix(brix));
            }

            if (height != null) {
                v.putAll(Height(height));
            }

            if (overall != null) {
                v.putAll(OverAll(overall));
            }

            if (leafsheath != null) {
                v.putAll(LeafSheath(leafsheath));
            }

            if (stalkperclump != null) {
                v.putAll(StalkPerClump(stalkperclump));
            }

            if (stalksize != null) {
                v.putAll(StalkSize(stalksize));
            }

            if (clumpshape != null) {
                v.putAll(ClumpShape(clumpshape));
            }

            if (clumpcharecteristic != null) {
                v.putAll(ClumpCharecteristic(clumpcharecteristic));
            }
            //TODO FIX HERE
//            if (internalsymtom != null) {
//                v.putAll(StalkSymtom(internalsymtom));
//            }
//            if (firmness != null) {
//                v.putAll(StalkFirmness(firmness));
//            }
            if (internodelength != null) {
                v.putAll(InternodeLength(internodelength));
            }
//            if (stuff != null) {
//                v.putAll(Stuff(stuff));
//            }

             v.put(Columns.CHANGESTATUS,1);

            String where = Columns.CLONECODE+" = ?";
            String[] selectAgrs2={
                    c.getString(c.getColumnIndex(Columns.CLONECODE))
            };

            int update = r.update(uri, v, where, selectAgrs2);
            Log.d("Log Update",update+"");

        }

        c.close();

    }


    private ContentValues WhiteFly(String data) {
        ContentValues v = new ContentValues();
        if (data.contains(">") || data.contains("มากกว่า")) {
            v.put(Columns.WHITE_FLY, "> 10 %");
            v.put(Columns.WHITE_FLY_SCORE, -15);
        } else if (data.contains("<") || data.contains("น้อยกว่า")) {
            v.put(Columns.WHITE_FLY, "< 10 %");
            v.put(Columns.WHITE_FLY_SCORE, -10);
        } else {
            v.put(Columns.WHITE_FLY, "ไม่พบร่องรอยของโรค");
            v.put(Columns.WHITE_FLY_SCORE, 0);
        }
        return v;
    }

    private ContentValues Bored(String data) {
        ContentValues v = new ContentValues();
        if (data.contains(">") || data.contains("มากกว่า")) {
            v.put(Columns.BORER, ">10%");
            v.put(Columns.BORER_SCORE, -15);
        } else if (data.contains("<") || data.contains("น้อยกว่า")) {
            v.put(Columns.BORER, "<10%");
            v.put(Columns.BORER_SCORE, -10);
        } else {
            v.put(Columns.BORER, "ไม่พบร่องรอยของโรค");
            v.put(Columns.BORER_SCORE, 0);
        }
        return v;
    }

    private ContentValues Aphid(String data) {
        ContentValues v = new ContentValues();
        if (data.contains(">") || data.contains("มากกว่า")) {
            v.put(Columns.APHID, ">10%");
            v.put(Columns.APHID_SCORE, -15);
        } else if (data.contains("<") || data.contains("น้อยกว่า")) {
            v.put(Columns.APHID, "<10%");
            v.put(Columns.APHID_SCORE, -10);
        } else {
            v.put(Columns.APHID, "ไม่พบร่องรอยของโรค");
            v.put(Columns.APHID_SCORE, 0);
        }
        return v;
    }

    private ContentValues IceryaMealBug(String data) {
        ContentValues v = new ContentValues();
        if (data.contains(">") || data.contains("มากกว่า")) {
            v.put(Columns.ICERYA_MEAL_BUG, ">10%");
            v.put(Columns.ICERYA_MEAL_BUG_SCORE, -15);
        } else if (data.contains("<") || data.contains("น้อยกว่า")) {
            v.put(Columns.ICERYA_MEAL_BUG, "<10%");
            v.put(Columns.ICERYA_MEAL_BUG_SCORE, -10);
        } else {
            v.put(Columns.ICERYA_MEAL_BUG, "ไม่พบร่องรอยของโรค");
            v.put(Columns.ICERYA_MEAL_BUG_SCORE, 0);
        }
        return v;
    }

    private ContentValues Scale(String data) {
        ContentValues v = new ContentValues();
        if (data.contains(">") || data.contains("มากกว่า")) {
            v.put(Columns.SCALE, ">10%");
            v.put(Columns.SCALE_SCORE, -15);
        } else if (data.contains("<") || data.contains("น้อยกว่า")) {
            v.put(Columns.SCALE, "<10%");
            v.put(Columns.SCALE_SCORE, -10);
        } else {
            v.put(Columns.SCALE, "ไม่พบร่องรอยของโรค");
            v.put(Columns.SCALE_SCORE, 0);
        }
        return v;
    }

    private ContentValues Boeng(String data) {
        ContentValues v = new ContentValues();
        if (data.contains(">") || data.contains("มากกว่า")) {
            v.put(Columns.POKKAH_BOENG, ">10%");
            v.put(Columns.POKKAH_BOENG_SCORE, -15);
        } else if (data.contains("<") || data.contains("น้อยกว่า")) {
            v.put(Columns.POKKAH_BOENG, "<10%");
            v.put(Columns.POKKAH_BOENG_SCORE, -10);
        } else {
            v.put(Columns.POKKAH_BOENG, "ไม่พบร่องรอยของโรค");
            v.put(Columns.POKKAH_BOENG_SCORE, 0);
        }
        return v;
    }

    private ContentValues YellowSpot(String data) {
        ContentValues v = new ContentValues();
        if (data.contains(">") || data.contains("มากกว่า")) {
            v.put(Columns.YELLOW_SPOT, ">10%");
            v.put(Columns.YELLOW_SPOT_SCORE, -15);
        } else if (data.contains("<") || data.contains("น้อยกว่า")) {
            v.put(Columns.YELLOW_SPOT, "<10%");
            v.put(Columns.YELLOW_SPOT_SCORE, -10);
        } else {
            v.put(Columns.YELLOW_SPOT, "ไม่พบร่องรอยของโรค");
            v.put(Columns.YELLOW_SPOT_SCORE, 0);
        }
        return v;
    }

    private ContentValues BrowSpot(String data) {
        ContentValues v = new ContentValues();
        if (data.contains(">") || data.contains("มากกว่า")) {
            v.put(Columns.BROWN_SPOT, ">10%");
            v.put(Columns.BROWN_SPOT_SCORE, -15);
        } else if (data.contains("<") || data.contains("น้อยกว่า")) {
            v.put(Columns.BROWN_SPOT, "<10%");
            v.put(Columns.BROWN_SPOT_SCORE, -10);
        } else {
            v.put(Columns.BROWN_SPOT, "ไม่พบร่องรอยของโรค");
            v.put(Columns.BROWN_SPOT_SCORE, 0);
        }
        return v;
    }

    private ContentValues RingSpot(String data) {
        ContentValues v = new ContentValues();
        if (data.contains(">") || data.contains("มากกว่า")) {
            v.put(Columns.RING_SPOT, ">10%");
            v.put(Columns.RING_SPOT_SCORE, -15);
        } else if (data.contains("<") || data.contains("น้อยกว่า")) {
            v.put(Columns.RING_SPOT, "<10%");
            v.put(Columns.RING_SPOT_SCORE, -10);
        } else {
            v.put(Columns.RING_SPOT, "ไม่พบร่องรอยของโรค");
            v.put(Columns.RING_SPOT_SCORE, 0);
        }
        return v;
    }

    private ContentValues Rust(String data) {
        ContentValues v = new ContentValues();
        if (data.contains(">") || data.contains("มากกว่า")) {
            v.put(Columns.RUST, ">10%");
            v.put(Columns.RUST_SCORE, -15);
        } else if (data.contains("<") || data.contains("น้อยกว่า")) {
            v.put(Columns.RUST, "<10%");
            v.put(Columns.RUST_SCORE, -10);
        } else {
            v.put(Columns.RUST, "ไม่พบร่องรอยของโรค");
            v.put(Columns.RUST_SCORE, 0);
        }
        return v;
    }

    private ContentValues DownyMildew(String data) {
        ContentValues v = new ContentValues();
        if (data.contains(">") || data.contains("มากกว่า")) {
            v.put(Columns.DOWNY_MILDEW, ">10%");
            v.put(Columns.DOWNY_MILDEW_SCORE, -15);
        } else if (data.contains("<") || data.contains("น้อยกว่า")) {
            v.put(Columns.DOWNY_MILDEW, "<10%");
            v.put(Columns.DOWNY_MILDEW_SCORE, -10);
        } else {
            v.put(Columns.DOWNY_MILDEW, "ไม่พบร่องรอยของโรค");
            v.put(Columns.DOWNY_MILDEW_SCORE, 0);
        }
        return v;
    }

    private ContentValues OtherDisease(String data) {
        ContentValues v = new ContentValues();
        if (data.contains(">") || data.contains("มากกว่า")) {
            v.put(Columns.OTHER_DISEASE, ">10%");
            v.put(Columns.OTHER_DISEASE_SCORE, -15);
        } else if (data.contains("<") || data.contains("น้อยกว่า")) {
            v.put(Columns.OTHER_DISEASE, "<10%");
            v.put(Columns.OTHER_DISEASE_SCORE, -10);
        } else {
            v.put(Columns.OTHER_DISEASE, "ไม่พบร่องรอยของโรค");
            v.put(Columns.OTHER_DISEASE_SCORE, 0);
        }
        return v;
    }

    private ContentValues Flowering(String data) {
        ContentValues v = new ContentValues();
        if (data.equals("ออกดอก")) {
            v.put(Columns.FLOWERING, "< 50 %");
            v.put(Columns.FLOWERING_SCORE, 15);
        } else if (data.equals("ไม่ออกดอก")) {
            v.put(Columns.FLOWERING, "ไม่ออกดอก");
            v.put(Columns.FLOWERING_SCORE, 25);
        } else if (data.contains("> 50 %")) {
            v.put(Columns.FLOWERING, "> 50 %");
            v.put(Columns.FLOWERING_SCORE, 0);
        } else if (data.contains("< 50 %")) {
            v.put(Columns.FLOWERING, "< 50 %");
            v.put(Columns.FLOWERING_SCORE, 15);
        }
        return v;
    }

    private ContentValues Brix(Float data) {
        ContentValues v = new ContentValues();
        if (data >= 21) {
            v.put(Columns.BRIX, data);
            v.put(Columns.BRIX_SCORE, 75);
        } else if (data >= 20 && data < 21) {
            v.put(Columns.BRIX, data);
            v.put(Columns.BRIX_SCORE, 60);
        } else if (data >= 19 && data < 20) {
            v.put(Columns.BRIX, data);
            v.put(Columns.BRIX_SCORE, 45);
        } else if (data >= 18 && data < 19) {
            v.put(Columns.BRIX, data);
            v.put(Columns.BRIX_SCORE, 30);
        } else if (data >= 17 && data < 18) {
            v.put(Columns.BRIX, data);
            v.put(Columns.BRIX_SCORE, 15);
        } else if (data < 17) {
            v.put(Columns.BRIX, data);
            v.put(Columns.BRIX_SCORE, 0);
        }
        return v;
    }

    private ContentValues Height(Float data) {
        ContentValues v = new ContentValues();
        //TODO กลับมาแก้ไขจัดนี้ ต้องใช้ค่าเฉลี่ยพันธุ์เช็ค
        Float result = 200 - data;
        if (result > 3) {
            v.put(Columns.HEIGHT, data);
            v.put(Columns.HEIGHT_SCORE, 30);
        } else if (result >= -3 && result <= 3) {
            v.put(Columns.HEIGHT, data);
            v.put(Columns.HEIGHT_SCORE, 24);
        } else if (result < -3) {
            v.put(Columns.HEIGHT, data);
            v.put(Columns.HEIGHT_SCORE, 12);
        }
        return v;
    }

    private ContentValues OverAll(Float data) {
        ContentValues v = new ContentValues();
        v.put(Columns.OVERALL, data);
        v.put(Columns.OVERALL_SCORE, data * 5);
        return v;
    }

    private ContentValues LeafSheath(String data) {
        ContentValues v = new ContentValues();
        if (data.equals("ง่าย")) {
            v.put(Columns.LEAF_SHEATH, data);
            v.put(Columns.LEAF_SHEATH_SCORE, 10);
        } else if (data.equals("ปานกลาง")) {
            v.put(Columns.LEAF_SHEATH, data);
            v.put(Columns.LEAF_SHEATH_SCORE, 8);
        } else if (data.equals("หลุดเอง")) {
            v.put(Columns.LEAF_SHEATH, data);
            v.put(Columns.LEAF_SHEATH_SCORE, 6);
        } else if (data.equals("ยาก")) {
            v.put(Columns.LEAF_SHEATH, data);
            v.put(Columns.LEAF_SHEATH_SCORE, 4);
        } else {
            v.put(Columns.LEAF_SHEATH, "ปานกลาง");
            v.put(Columns.LEAF_SHEATH_SCORE, 8);
        }
        return v;
    }

    private ContentValues StalkPerClump(Integer data) {
        ContentValues v = new ContentValues();
        if (data > 10) {
            v.put(Columns.STALK_AMOUNT, data);
            v.put(Columns.STALK_AMOUNT_SCORE, 100);
        } else if (data <= 10 || data >= 7) {
            v.put(Columns.STALK_AMOUNT, data);
            v.put(Columns.STALK_AMOUNT_SCORE, 80);
        } else if (data <= 6 || data >= 5) {
            v.put(Columns.STALK_AMOUNT, data);
            v.put(Columns.STALK_AMOUNT_SCORE, 60);
        } else if (data <= 4 || data >= 3) {
            v.put(Columns.STALK_AMOUNT, data);
            v.put(Columns.STALK_AMOUNT_SCORE, 40);
        } else if (data <= 2 || data >= 1) {
            v.put(Columns.STALK_AMOUNT, data);
            v.put(Columns.STALK_AMOUNT_SCORE, 20);
        }
        return v;
    }

    private ContentValues InternodeAmount(Integer data) {
        ContentValues v = new ContentValues();
        v.put(Columns.INTERNODE_AMOUNT, data);
        v.put(Columns.INTERNODE_AMOUNT_SCORE, 0);
        return v;
    }

    private ContentValues StalkSize(Float data) {
        ContentValues v = new ContentValues();
        if (data > 3.0) {
            v.put(Columns.STALK_SIZE_AVERAGE, data);
            v.put(Columns.STALK_SIZE_AVERAGE_SCORE, 40);
        } else if (data >= 2.7 || data < 3.0) {
            v.put(Columns.STALK_SIZE_AVERAGE, data);
            v.put(Columns.STALK_SIZE_AVERAGE_SCORE, 32);
        } else if (data >= 2.5 || data < 2.7) {
            v.put(Columns.STALK_SIZE_AVERAGE, data);
            v.put(Columns.STALK_SIZE_AVERAGE_SCORE, 24);
        } else if (data < 2.5) {
            v.put(Columns.STALK_SIZE_AVERAGE, data);
            v.put(Columns.STALK_SIZE_AVERAGE_SCORE, 0);
        }

        return v;
    }

    private ContentValues ClumpShape(String data) {
        ContentValues v = new ContentValues();
        Log.d("Data",data);
        if (data.equals("แคบ")) {
            v.put(Columns.CLUMP_SHAPE,data);
            v.put(Columns.CLUMP_SHAPE_SCORE,20);
        } else if (data.equals("กว้าง")) {
            v.put(Columns.CLUMP_SHAPE,data);
            v.put(Columns.CLUMP_SHAPE_SCORE,20);
        } else if (data.equals("แบะน้อย")) {
            v.put(Columns.CLUMP_SHAPE,data);
            v.put(Columns.CLUMP_SHAPE_SCORE,12);
        } else if (data.equals("แบะมาก")) {
            v.put(Columns.CLUMP_SHAPE,data);
            v.put(Columns.CLUMP_SHAPE_SCORE,20);
        }else{
            v.put(Columns.CLUMP_SHAPE,"แบะน้อย");
            v.put(Columns.CLUMP_SHAPE_SCORE,12);
        }
        Log.d("Data",v.get(Columns.CLUMP_SHAPE).toString());
        Log.d("Data",v.get(Columns.CLUMP_SHAPE_SCORE).toString());
        return v;
    }

    private ContentValues ClumpCharecteristic(String data) {
        ContentValues v = new ContentValues();
        if (data.contains("ไม่ล้ม")){
            v.put(Columns.CLUMP_CHARACTERISTIC,"ไม่ล้ม");
            v.put(Columns.CLUMP_CHARACTERISTIC_SCORE,25);
        }else if (data.equals("ล้มเพราะดินยุบ")){
            v.put(Columns.CLUMP_CHARACTERISTIC,"ล้มเพราะดินยุบ");
            v.put(Columns.CLUMP_CHARACTERISTIC_SCORE,15);
        }else if (data.equals("ล้ม")) {
            v.put(Columns.CLUMP_CHARACTERISTIC,"ล้ม");
            v.put(Columns.CLUMP_CHARACTERISTIC_SCORE,0);
        }else {
            v.put(Columns.CLUMP_CHARACTERISTIC,"ไม่ล้ม");
            v.put(Columns.CLUMP_CHARACTERISTIC_SCORE,25);
        }
        return v;
    }

    //TODO Complete This Caribreate Mode
    private ContentValues StalkSymtom(String data) {
        ContentValues v = new ContentValues();
        return v;
    }

    private ContentValues StalkFirmness(String data) {
        ContentValues v = new ContentValues();
        return v;
    }

    private ContentValues InternodeLength(Float data) {
        ContentValues v = new ContentValues();
        if (data >13){
            v.put(Columns.INTERNODE_LENGTH_AVERAGE,data);
            v.put(Columns.INTERNODE_LENGTH_AVERAGE_SCORE,10);
        }else if(data >=11 || data <=13){
            v.put(Columns.INTERNODE_LENGTH_AVERAGE,data);
            v.put(Columns.INTERNODE_LENGTH_AVERAGE_SCORE,6);
        }else if (data<11){
            v.put(Columns.INTERNODE_LENGTH_AVERAGE,data);
            v.put(Columns.INTERNODE_LENGTH_AVERAGE_SCORE,2);
        }
        return v;
    }

    private ContentValues Stuff(String data) {
        ContentValues v = new ContentValues();
        return v;
    }


}
