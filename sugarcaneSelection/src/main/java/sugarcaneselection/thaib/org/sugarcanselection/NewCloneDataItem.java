package sugarcaneselection.thaib.org.sugarcanselection;

/**
 * Created by Jitpakorn on 1/20/15 AD.
 */
public class NewCloneDataItem {

    private String FamilyCode;

    private String CloneCode;
    private String PlaceTest;
    private String UpperPicUrl;
    private String UpperUUID;
    private String LowerPicUrl;
    private String LowerUUID;
    private String FullPicUrl;
    private String FullPicUUID;
    private String CollectingTime;
    private int StalkAmount;
    private int StalkShape;
    private float StalkSize;
    private float Brix;
    private int Flowering;
    private int Height;
    private int Stuff;
    private int WhiteFly;
    private int Borer;
    private int YellowSpot;
    private int OtherDisease;
    private float OverView;


    private int HeightC1;
    private float BrixC1;
    private float StalkSizeC1;
    private int StalkAmountC1;
    private String NameC1;
    private int HeightC2;
    private float BrixC2;
    private float StalkSizeC2;

    private String NameC2;
    private int StalkAmountC2;
    private int InternodeAmount;
    private int LeafSheath;
    private float InternodeLenght;
    private int RowNumber;


    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Clonecode : " + getCloneCode() + '\n');
        builder.append("Observe : " + getOverView() + '\n');
        builder.append("Stalk per Clump :" + getStalkAmount() + '\n');
        builder.append("Stalk Type : " + getStalkShape() + '\n');
        builder.append("Stalk Size : " + getStalkSize() + '\n');
        builder.append("Brix : " + getBrix() + '\n');
        builder.append("Flowering : " + getFlowering() + '\n');
        builder.append("Height : " + getHeight() + '\n');
        builder.append("Texture : " + getStuff() + '\n');
        builder.append("แมลงหวี่ขาว : " + getWhiteFly() + '\n');
        builder.append("หนอนเจาะลำต้น : " + getBorer() + '\n');
        builder.append("โรคใบจุดเหลือง : " + getYellowSpot() + '\n');
        builder.append("โรคอื่นๆ : " + getOtherDisease() + '\n');
        builder.append("ที่อยู่รูปส่วนบน : " + getUpperPicUrl() + '\n');
        builder.append("ที่อยู่รูปส่วนล่าง : " + getLowerPicUrl() + '\n');
        builder.append("ที่อยู่รูปเต็ม : " + getFullPicUrl() + '\n');
        builder.append("การหลุดร่วงของใบ : " + getLeafSheath() + '\n');
        builder.append("จำนวนข้อปล้อง : " + getInternodeAmount() + '\n');
        builder.append("ความยาวข้อปล้อง : " + getInternodeLenght() + '\n');


        return builder.toString();

    }

    public String getFamilyCode() {
        return FamilyCode;
    }

    public void setFamilyCode(String familyCode) {
        FamilyCode = familyCode;
    }

    public String getCloneCode() {
        return CloneCode;
    }

    public void setCloneCode(String cloneCode) {
        CloneCode = cloneCode;
    }

    public String getPlaceTest() {
        return PlaceTest;
    }

    public void setPlaceTest(String placeTest) {
        PlaceTest = placeTest;
    }

    public String getUpperPicUrl() {
        return UpperPicUrl;
    }

    public void setUpperPicUrl(String upperPicUrl) {
        UpperPicUrl = upperPicUrl;
    }

    public String getUpperUUID() {
        return UpperUUID;
    }

    public void setUpperUUID(String upperUUID) {
        UpperUUID = upperUUID;
    }

    public String getLowerPicUrl() {
        return LowerPicUrl;
    }

    public void setLowerPicUrl(String lowerPicUrl) {
        LowerPicUrl = lowerPicUrl;
    }

    public String getLowerUUID() {
        return LowerUUID;
    }

    public void setLowerUUID(String lowerUUID) {
        LowerUUID = lowerUUID;
    }

    public String getFullPicUrl() {
        return FullPicUrl;
    }

    public void setFullPicUrl(String fullPicUrl) {
        FullPicUrl = fullPicUrl;
    }

    public String getFullPicUUID() {
        return FullPicUUID;
    }

    public void setFullPicUUID(String fullPicUUID) {
        FullPicUUID = fullPicUUID;
    }

    public String getCollectingTime() {
        return CollectingTime;
    }

    public void setCollectingTime(String collectingTime) {
        CollectingTime = collectingTime;
    }

    public int getStalkAmount() {
        return StalkAmount;
    }

    public void setStalkAmount(int stalkAmount) {
        StalkAmount = stalkAmount;
    }

    public int getStalkShape() {
        return StalkShape;
    }

    public void setStalkShape(int stalkShape) {
        StalkShape = stalkShape;
    }

    public float getStalkSize() {
        return StalkSize;
    }

    public void setStalkSize(float stalkSize) {
        StalkSize = stalkSize;
    }

    public float getBrix() {
        return Brix;
    }

    public void setBrix(float brix) {
        Brix = brix;
    }

    public int getFlowering() {
        return Flowering;
    }

    public void setFlowering(int flowering) {
        Flowering = flowering;
    }

    public int getHeight() {
        return Height;
    }

    public void setHeight(int height) {
        Height = height;
    }

    public int getStuff() {
        return Stuff;
    }

    public void setStuff(int stuff) {
        Stuff = stuff;
    }

    public int getWhiteFly() {
        return WhiteFly;
    }

    public void setWhiteFly(int whiteFly) {
        WhiteFly = whiteFly;
    }

    public int getBorer() {
        return Borer;
    }

    public void setBorer(int borer) {
        Borer = borer;
    }

    public int getYellowSpot() {
        return YellowSpot;
    }

    public void setYellowSpot(int yellowSpot) {
        YellowSpot = yellowSpot;
    }

    public int getOtherDisease() {
        return OtherDisease;
    }

    public void setOtherDisease(int otherDisease) {
        OtherDisease = otherDisease;
    }

    public float getOverView() {
        return OverView;
    }

    public void setOverView(float overView) {
        OverView = overView;
    }

    public int getHeightC1() {
        return HeightC1;
    }

    public void setHeightC1(int heightC1) {
        HeightC1 = heightC1;
    }

    public float getBrixC1() {
        return BrixC1;
    }

    public void setBrixC1(float brixC1) {
        BrixC1 = brixC1;
    }

    public float getStalkSizeC1() {
        return StalkSizeC1;
    }

    public void setStalkSizeC1(float stalkSizeC1) {
        StalkSizeC1 = stalkSizeC1;
    }

    public int getStalkAmountC1() {
        return StalkAmountC1;
    }

    public void setStalkAmountC1(int stalkAmountC1) {
        StalkAmountC1 = stalkAmountC1;
    }

    public String getNameC1() {
        return NameC1;
    }

    public void setNameC1(String nameC1) {
        NameC1 = nameC1;
    }

    public int getHeightC2() {
        return HeightC2;
    }

    public void setHeightC2(int heightC2) {
        HeightC2 = heightC2;
    }

    public float getBrixC2() {
        return BrixC2;
    }

    public void setBrixC2(float brixC2) {
        BrixC2 = brixC2;
    }

    public float getStalkSizeC2() {
        return StalkSizeC2;
    }

    public void setStalkSizeC2(float stalkSizeC2) {
        StalkSizeC2 = stalkSizeC2;
    }

    public String getNameC2() {
        return NameC2;
    }

    public void setNameC2(String nameC2) {
        NameC2 = nameC2;
    }

    public int getStalkAmountC2() {
        return StalkAmountC2;
    }

    public void setStalkAmountC2(int stalkAmountC2) {
        StalkAmountC2 = stalkAmountC2;
    }

    public int getInternodeAmount() {
        return InternodeAmount;
    }

    public void setInternodeAmount(int internodeAmount) {
        InternodeAmount = internodeAmount;
    }

    public int getLeafSheath() {
        return LeafSheath;
    }

    public void setLeafSheath(int leafSheath) {
        LeafSheath = leafSheath;
    }

    public float getInternodeLenght() {
        return InternodeLenght;
    }

    public void setInternodeLenght(float internodeLenght) {
        InternodeLenght = internodeLenght;
    }

    public int getRowNumber() {
        return RowNumber;
    }

    public void setRowNumber(int rowNumber) {
        RowNumber = rowNumber;
    }
}
