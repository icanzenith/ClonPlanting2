package sugarcaneselection.thaib.org.sugarcanselection.Item;

import java.util.HashMap;

import sugarcaneselection.thaib.org.sugarcanselection.database.Columns;

/**
 * Created by Jitpakorn on 3/12/15 AD.
 */
public class MatchCodeAndColumn {

    private HashMap<String,String> ScoreColumn;

    public static HashMap<String, String> getTagColumn() {

        HashMap<String, String> a = new HashMap<>();

        //OverAll
        a.put(Code.OverAll, Columns.OVERALL);
        a.put(Code.Brix,Columns.BRIX);
        a.put(Code.Flowering,Columns.FLOWERING);
        a.put(Code.LeafSheath,Columns.LEAF_SHEATH);
        a.put(Code.ClumpShape,Columns.CLUMP_SHAPE);
        a.put(Code.ClumpCharacteristic,Columns.CLUMP_CHARACTERISTIC);

        //StalkSize

        //InternodeLength

        //Stalk Amount
        a.put(Code.StalkSize,Columns.STALK_SIZE_AVERAGE);
        a.put(Code.InternodeLength,Columns.INTERNODE_LENGTH_AVERAGE);
        a.put(Code.StalkAmount,Columns.STALK_AMOUNT);
        a.put(Code.Height,Columns.HEIGHT);
        a.put(Code.InternodeAmount,Columns.INTERNODE_AMOUNT);

        //Stuff
        a.put(Code.InternalSymtom,Columns.INTERNAL_SYSTOM);
        a.put(Code.InternalFirmness,Columns.INTERNAL_FIRMNESS);
        a.put(Code.Stuff,Columns.STUFF);

        //Diease

        a.put(Code.WhiteFly,Columns.WHITE_FLY);
        a.put(Code.Borer, Columns.BORER);
        a.put(Code.Aphid, Columns.APHID);
        a.put(Code.IceryaMealbug,Columns.ICERYA_MEAL_BUG);
        a.put(Code.Scale, Columns.SCALE);
        a.put(Code.PokkahBoeng, Columns.POKKAH_BOENG);
        a.put(Code.YellowSpot,Columns.YELLOW_SPOT );
        a.put(Code.BrownSpot,Columns.BROWN_SPOT);
        a.put(Code.RingSpot,Columns.RING_SPOT);
        a.put(Code.Rust,Columns.RUST);
        a.put(Code.DownyMildew,Columns.DOWNY_MILDEW);
        a.put(Code.OtherDisease,Columns.OTHER_DISEASE);

        a.put(StalkSize.StalkSize1.name(),Columns.STALK_SIZE_1);
        a.put(StalkSize.StalkSize2.name(),Columns.STALK_SIZE_2);
        a.put(StalkSize.StalkSize3.name(),Columns.STALK_SIZE_3);
        a.put(StalkSize.StalkSize4.name(),Columns.STALK_SIZE_4);
        a.put(StalkSize.StalkSize5.name(),Columns.STALK_SIZE_5);

        a.put(InternodeLength.InternodeLength1.name(),Columns.INTERNODE_LENGTH1);
        a.put(InternodeLength.InternodeLength2.name(),Columns.INTERNODE_LENGTH2);
        a.put(InternodeLength.InternodeLength3.name(),Columns.INTERNODE_LENGTH3);
        a.put(InternodeLength.InternodeLength4.name(),Columns.INTERNODE_LENGTH4);
        a.put(InternodeLength.InternodeLength5.name(),Columns.INTERNODE_LENGTH5);
        return a;
    }

    public static HashMap<String, String> getScoreColumn() {
        HashMap<String, String> a = new HashMap<>();

        //OverAll
        a.put(Code.OverAll,Columns.OVERALL_SCORE);
        a.put(Code.Brix,Columns.BRIX_SCORE);
        a.put(Code.Flowering,Columns.FLOWERING_SCORE);
        a.put(Code.LeafSheath,Columns.LEAF_SHEATH_SCORE);
        a.put(Code.ClumpShape,Columns.CLUMP_SHAPE_SCORE);
        a.put(Code.ClumpCharacteristic,Columns.CLUMP_CHARACTERISTIC_SCORE);

        //StalkSize

        //InternodeLength

        //Stalk Amount
        a.put(Code.StalkSize,Columns.STALK_SIZE_AVERAGE_SCORE);
        a.put(Code.InternodeLength,Columns.INTERNODE_LENGTH_AVERAGE_SCORE);
        a.put(Code.StalkAmount,Columns.STALK_AMOUNT_SCORE);
        a.put(Code.Height,Columns.HEIGHT_SCORE);
        a.put(Code.InternodeAmount,Columns.INTERNODE_AMOUNT_SCORE);

        //Stuff
        a.put(Code.InternalSymtom,Columns.INTERNAL_SYSTOM_SCORE);
        a.put(Code.InternalFirmness,Columns.INTERNAL_FIRMNESS_SCORE);
        a.put(Code.Stuff,Columns.STUFF_SCORE);

        //Diease

        a.put(Code.WhiteFly,Columns.WHITE_FLY_SCORE);
        a.put(Code.Borer, Columns.BORER_SCORE);
        a.put(Code.Aphid, Columns.APHID_SCORE);
        a.put(Code.IceryaMealbug,Columns.ICERYA_MEAL_BUG_SCORE);
        a.put(Code.Scale, Columns.SCALE_SCORE);
        a.put(Code.PokkahBoeng, Columns.POKKAH_BOENG_SCORE);
        a.put(Code.YellowSpot,Columns.YELLOW_SPOT_SCORE );
        a.put(Code.BrownSpot,Columns.BROWN_SPOT_SCORE);
        a.put(Code.RingSpot,Columns.RING_SPOT_SCORE);
        a.put(Code.Rust,Columns.RUST_SCORE);
        a.put(Code.DownyMildew,Columns.DOWNY_MILDEW_SCORE);
        a.put(Code.OtherDisease,Columns.OTHER_DISEASE_SCORE);
        return a;
    }

}