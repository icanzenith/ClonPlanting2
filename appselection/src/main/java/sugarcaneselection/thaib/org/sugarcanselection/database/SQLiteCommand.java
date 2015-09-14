package sugarcaneselection.thaib.org.sugarcanselection.database;

public class SQLiteCommand {

    public static final String CREATE_TABLE_MYFAMIY = "CREATE TABLE myfamily(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            Columns.FAMILYCODE+" TEXT," +
            Columns.ROWNUMBER+" INTEGER," +
            Columns.AMOUNT_OF_CLONE_SAVED+" INTEGER," +
            Columns.AMOUNT_OF_CLONE_REJECTED+" INTEGER," +
            Columns.AMOUNT_OF_CLONE_SELECTED+" INTEGER" +
            ")";
    public static final String CREATE_TABLE_STANDARD =
            "CREATE TABLE mystandardclone(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    Columns.SPECIENAME + " TEXT," +
                    Columns.PLANTORDER + " INTEGER," +
                    Columns.FAMILYCODE + " TEXT," +
                    Columns.PLACETEST + " TEXT," +
                    Columns.UPPERPICURL + " TEXT," +
                    Columns.UPPERUUID + " TEXT," +
                    Columns.LOWERPICURL + " TEXT," +
                    Columns.LOWERUUDI + " TEXT," +
                    Columns.FULLPICURL + " TEXT," +
                    Columns.FULLPICUUID + " TEXT," +
                    Columns.COLLECTINGTIME + " TEXT," +
                    Columns.WHITE_FLY + " TEXT," +
                    Columns.WHITE_FLY_SCORE + " REAL," +
                    Columns.BORER + " TEXT," +
                    Columns.BORER_SCORE + " REAL," +
                    Columns.APHID + " TEXT," +
                    Columns.APHID_SCORE + " REAL," +
                    Columns.ICERYA_MEAL_BUG + " TEXT," +
                    Columns.ICERYA_MEAL_BUG_SCORE + " REAL," +
                    Columns.SCALE + " TEXT," +
                    Columns.SCALE_SCORE + " REAL," +
                    Columns.POKKAH_BOENG + " TEXT," +
                    Columns.POKKAH_BOENG_SCORE + " REAL," +
                    Columns.YELLOW_SPOT + " TEXT," +
                    Columns.YELLOW_SPOT_SCORE + " REAL," +
                    Columns.BROWN_SPOT + " TEXT," +
                    Columns.BROWN_SPOT_SCORE + " REAL," +
                    Columns.RING_SPOT + " TEXT," +
                    Columns.RING_SPOT_SCORE + " REAL," +
                    Columns.RUST + " TEXT," +
                    Columns.RUST_SCORE + " REAL," +
                    Columns.DOWNY_MILDEW + " TEXT," +
                    Columns.DOWNY_MILDEW_SCORE + " REAL," +
                    Columns.OTHER_DISEASE + " TEXT," +
                    Columns.OTHER_DISEASE_SCORE + " REAL," +
                    Columns.OTHER_DISEASE_NAME + " TEXT," +
                    Columns.FLOWERING + " TEXT," +
                    Columns.FLOWERING_SCORE + " REAL," +
                    Columns.BRIX + " REAL," +
                    Columns.BRIX_SCORE + " REAL," +
                    Columns.HEIGHT + " INTEGER," +
                    Columns.HEIGHT_SCORE + " REAL," +
                    Columns.OVERALL + " REAL," +
                    Columns.OVERALL_SCORE + " REAL," +
                    Columns.LEAF_SHEATH + " TEXT," +
                    Columns.LEAF_SHEATH_SCORE + " REAL," +
                    Columns.STALK_AMOUNT + " INTEGER," +
                    Columns.STALK_AMOUNT_SCORE + " REAL," +
                    Columns.INTERNODE_AMOUNT + " INTEGER," +
                    Columns.INTERNODE_AMOUNT_SCORE + " REAL," +
                    Columns.CLUMP_SHAPE + " TEXT," +
                    Columns.CLUMP_SHAPE_SCORE + " REAL," +
                    Columns.CLUMP_CHARACTERISTIC + " TEXT," +
                    Columns.CLUMP_CHARACTERISTIC_SCORE + " REAL," +
                    Columns.INTERNAL_SYSTOM + " TEXT," +
                    Columns.INTERNAL_SYSTOM_SCORE + " REAL," +
                    Columns.INTERNAL_FIRMNESS + " TEXT," +
                    Columns.INTERNAL_FIRMNESS_SCORE + " REAL," +
                    Columns.STUFF + " TEXT," +
                    Columns.STUFF_SCORE + " REAL," +
                    Columns.STALK_SIZE_1 + " REAL," +
                    Columns.STALK_SIZE_2 + " REAL," +
                    Columns.STALK_SIZE_3 + " REAL," +
                    Columns.STALK_SIZE_4 + " REAL," +
                    Columns.STALK_SIZE_5 + " REAL," +
                    Columns.STALK_SIZE_AVERAGE + " REAL," +
                    Columns.STALK_SIZE_AVERAGE_SCORE + " REAL," +
                    Columns.INTERNODE_LENGTH1 + " REAL," +
                    Columns.INTERNODE_LENGTH2 + " REAL," +
                    Columns.INTERNODE_LENGTH3 + " REAL," +
                    Columns.INTERNODE_LENGTH4 + " REAL," +
                    Columns.INTERNODE_LENGTH5 + " REAL," +
                    Columns.INTERNODE_LENGTH_AVERAGE + " REAL," +
                    Columns.INTERNODE_LENGTH_AVERAGE_SCORE + " REAL," +
                    Columns.CHANGESTATUS + " REAL," +
                    Columns.UPLOADSTATUS + " INTEGER," +
                    Columns.UPLOADPICUPSTATUS + " INTEGER," +
                    Columns.UPLOADPICLOWSTATUS + " INTEGER," +
                    Columns.UPLOADPICFULLSTATUS + " INTEGER," +
                    Columns.ROWNUMBER + " INTEGER," +
                    Columns.SAVEDSTATUS + " INTEGER" +
                    Columns.SELECT_STATUS + " INTEGER" +
                    ")";


    public static final String CREATE_TABLE_MYCLONE = "CREATE TABLE myclone(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            //ข้อมูลโคลน
            Columns.CLONECODE + " TEXT," +
            Columns.FAMILYCODE + " TEXT," +
            Columns.ROWNUMBER + " INTEGER," +
            Columns.PLACETEST + " TEXT," +
            Columns.PLANTORDER + " INTEGER," +
            Columns.UPPERPICURL + " TEXT," +
            Columns.UPPERUUID + " TEXT," +
            Columns.LOWERPICURL + " TEXT," +
            Columns.LOWERUUDI + " TEXT," +
            Columns.FULLPICURL + " TEXT," +
            Columns.FULLPICUUID + " TEXT," +
            Columns.COLLECTINGTIME + " TEXT," +
            Columns.WHITE_FLY + " TEXT," +
            Columns.WHITE_FLY_SCORE + " REAL," +
            Columns.BORER + " TEXT," +
            Columns.BORER_SCORE + " REAL," +
            Columns.APHID + " TEXT," +
            Columns.APHID_SCORE + " REAL," +
            Columns.ICERYA_MEAL_BUG + " TEXT," +
            Columns.ICERYA_MEAL_BUG_SCORE + " REAL," +
            Columns.SCALE + " TEXT," +
            Columns.SCALE_SCORE + " REAL," +
            Columns.POKKAH_BOENG + " TEXT," +
            Columns.POKKAH_BOENG_SCORE + " REAL," +
            Columns.YELLOW_SPOT + " TEXT," +
            Columns.YELLOW_SPOT_SCORE + " REAL," +
            Columns.BROWN_SPOT + " TEXT," +
            Columns.BROWN_SPOT_SCORE + " REAL," +
            Columns.RING_SPOT + " TEXT," +
            Columns.RING_SPOT_SCORE + " REAL," +
            Columns.RUST + " TEXT," +
            Columns.RUST_SCORE + " REAL," +
            Columns.DOWNY_MILDEW + " TEXT," +
            Columns.DOWNY_MILDEW_SCORE + " REAL," +
            Columns.OTHER_DISEASE + " TEXT," +
            Columns.OTHER_DISEASE_SCORE + " REAL," +
            Columns.OTHER_DISEASE_NAME + " TEXT," +
            Columns.FLOWERING + " TEXT," +
            Columns.FLOWERING_SCORE + " REAL," +
            Columns.BRIX + " REAL," +
            Columns.BRIX_SCORE + " REAL," +
            Columns.HEIGHT + " INTEGER," +
            Columns.HEIGHT_SCORE + " REAL," +
            Columns.OVERALL + " REAL," +
            Columns.OVERALL_SCORE + " REAL," +
            Columns.LEAF_SHEATH + " TEXT," +
            Columns.LEAF_SHEATH_SCORE + " REAL," +
            Columns.STALK_AMOUNT + " INTEGER," +
            Columns.STALK_AMOUNT_SCORE + " REAL," +
            Columns.INTERNODE_AMOUNT + " INTEGER," +
            Columns.INTERNODE_AMOUNT_SCORE + " REAL," +
            Columns.CLUMP_SHAPE + " TEXT," +
            Columns.CLUMP_SHAPE_SCORE + " REAL," +
            Columns.CLUMP_CHARACTERISTIC + " TEXT," +
            Columns.CLUMP_CHARACTERISTIC_SCORE + " REAL," +
            Columns.INTERNAL_SYSTOM + " TEXT," +
            Columns.INTERNAL_SYSTOM_SCORE + " REAL," +
            Columns.INTERNAL_FIRMNESS + " TEXT," +
            Columns.INTERNAL_FIRMNESS_SCORE + " REAL," +
            Columns.STUFF + " TEXT," +
            Columns.STUFF_SCORE + " REAL," +
            Columns.STALK_SIZE_1 + " REAL," +
            Columns.STALK_SIZE_2 + " REAL," +
            Columns.STALK_SIZE_3 + " REAL," +
            Columns.STALK_SIZE_4 + " REAL," +
            Columns.STALK_SIZE_5 + " REAL," +
            Columns.STALK_SIZE_AVERAGE + " REAL," +
            Columns.STALK_SIZE_AVERAGE_SCORE + " REAL," +
            Columns.INTERNODE_LENGTH1 + " REAL," +
            Columns.INTERNODE_LENGTH2 + " REAL," +
            Columns.INTERNODE_LENGTH3 + " REAL," +
            Columns.INTERNODE_LENGTH4 + " REAL," +
            Columns.INTERNODE_LENGTH5 + " REAL," +
            Columns.INTERNODE_LENGTH_AVERAGE + " REAL," +
            Columns.INTERNODE_LENGTH_AVERAGE_SCORE + " REAL," +
            Columns.TOTAL_SCORE+" REAL," +
            Columns.CHANGESTATUS + " REAL," +
            Columns.UPLOADSTATUS + " INTEGER," +
            Columns.UPLOADPICUPSTATUS + " INTEGER," +
            Columns.UPLOADPICLOWSTATUS + " INTEGER," +
            Columns.UPLOADPICFULLSTATUS + " INTEGER," +
            Columns.SAVEDSTATUS + " INTEGER," +
            Columns.SELECT_STATUS + " INTEGER," +
            Columns.WHY_SELECT + " TEXT" +
            ")";
    public static final String DROP_TABLE_MYCLONE = "DROP TABLE IF EXISTS myclone";
    public static final String DROP_TABLE_MYFAMILY = "DROP TABLE IF EXISTS myfamily";
    public static final String DROP_TABLE_MYSTANDARDCLONE = "DROP TABLE IF EXISTS mystandardclone";

    SQLiteCommand() {
        super();

    }
}
