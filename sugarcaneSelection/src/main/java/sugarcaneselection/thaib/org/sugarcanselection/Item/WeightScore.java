package sugarcaneselection.thaib.org.sugarcanselection.Item;

import java.util.HashMap;

/**
 * Created by Jitpakorn on 2/24/15 AD.
 */
public class WeightScore {
    public static final int WhiteFly = 3;
    public static final int Borer = 2;
    public static final int Aphid = 1;
    public static final int IceryaMealbug = 1;
    public static final int Scale = 1;
    public static final int PokkahBoeng =1;
    public static final int YellowSpot = 2;
    public static final int BrownSpot = 1;
    public static final int RingSpot = 1;
    public static final int Rust = 1;
    public static final int DownyMildew = 1;
    public static final int OtherDisease = 1;

    public static final int Flowering = 5;
    public static final int Brix = 15;
    public static final int Heigth = 6;
    public static final int OverAll = 5;
    public static final int LeafSheath = 2;
    public static final int StalkAmount = 20;
    public static final int InternodeAmount = 0;
    public static final int StalkSize = 8;
    public static final int ClumpShape = 4;
    public static final int ClumpCharacteristic = 5;
    public static final int InternalStalkSymtom = 1;
    public static final int InternalFirmness = 1;
    public static final int InternodeLenght = 2;
    public static final int Stuff = 10;

    public static final HashMap<String,Integer> getWeigth(){
        HashMap<String,Integer> m = new HashMap<>();
        //OverAll
        m.put(Code.OverAll,OverAll);
        m.put(Code.Brix,Brix);
        m.put(Code.Flowering,Flowering);
        m.put(Code.LeafSheath,LeafSheath);
        m.put(Code.ClumpShape,ClumpShape);
        m.put(Code.ClumpCharacteristic,ClumpCharacteristic);

        //StalkSize

        //InternodeLength

        //Stalk Amount
        m.put(Code.StalkSize,StalkSize);
        m.put(Code.InternodeLength,InternodeLenght);
        m.put(Code.StalkAmount,StalkAmount);
        m.put(Code.Height,Heigth);
        m.put(Code.InternodeAmount,InternodeAmount);

        //Stuff
        m.put(Code.InternalSymtom,InternalStalkSymtom);
        m.put(Code.InternalFirmness,InternalFirmness);
        m.put(Code.Stuff,Stuff);

        //Diease

        m.put(Code.WhiteFly,WhiteFly);
        m.put(Code.Borer,Borer );
        m.put(Code.Aphid, Aphid);
        m.put(Code.IceryaMealbug,IceryaMealbug );
        m.put(Code.Scale,Scale );
        m.put(Code.PokkahBoeng, PokkahBoeng);
        m.put(Code.YellowSpot, YellowSpot);
        m.put(Code.BrownSpot, BrownSpot);
        m.put(Code.RingSpot, RingSpot);
        m.put(Code.Rust,Rust);
        m.put(Code.DownyMildew,DownyMildew );
        m.put(Code.OtherDisease, OtherDisease);
        return m;
    }
}
