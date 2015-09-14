package sugarcaneselection.thaib.org.sugarcanselection.Social;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;



import java.util.ArrayList;

import sugarcaneselection.thaib.org.sugarcanselection.GsonTransformer;
import sugarcaneselection.thaib.org.sugarcanselection.R;


public class WorkPlaceSelectedFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    AQuery aq;
    ArrayList<RowItem> a = new ArrayList<>();
    private String mParam1;
    private String mParam2;
    private ListView mListView;
    private Spinner typeSpinner, placeSpinner, listAmountSpinner;
    private Button btSearch;
    private TextView tvHeadType;
    private MyListAdapter adapter;
    private String ListType = "BrixNoPlaceTest";
    private String PlaceCode = "ALL";
    private int ListAmount = 100;

    public WorkPlaceSelectedFragment() {

    }

    public static WorkPlaceSelectedFragment newInstance() {
        WorkPlaceSelectedFragment fragment = new WorkPlaceSelectedFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_work_place_selected, container, false);

        btSearch = (Button) rootView.findViewById(R.id.btRunScan);
        mListView = (ListView) rootView.findViewById(R.id.listView);

        typeSpinner = (Spinner) rootView.findViewById(R.id.typeSpinner);
        placeSpinner = (Spinner) rootView.findViewById(R.id.placeTestSpinner);
        listAmountSpinner = (Spinner) rootView.findViewById(R.id.ListAmountSpinner);

        tvHeadType = (TextView) rootView.findViewById(R.id.tvHeadType);

        adapter = new MyListAdapter(getActivity());

        ArrayAdapter<CharSequence> adapter_listtype = ArrayAdapter.createFromResource(getActivity(),
                R.array.listtype_array, R.layout.single_spinner);

        ArrayAdapter<CharSequence> adapter_placetest = ArrayAdapter.createFromResource(getActivity(),
                R.array.PlaceList, R.layout.single_spinner);

        ArrayAdapter<CharSequence> adapter_listAmount = ArrayAdapter.createFromResource(getActivity(),
                R.array.listAmount, R.layout.single_spinner);

        typeSpinner.setAdapter(adapter_listtype);
        placeSpinner.setAdapter(adapter_placetest);
        listAmountSpinner.setAdapter(adapter_listAmount);

        listAmountSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ListAmount = Integer.parseInt((String) parent.getItemAtPosition(position));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        placeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        PlaceCode = "ALL";
                        if (ListType.equals("Brix")) {
                            ListType = "BrixNoPlaceTest";
                        }
                        if (ListType.equals("StalkAmount")) {
                            ListType = "StalkAmountPlaceTest";
                        }
                        if (ListType.equals("Height")) {
                            ListType = "HeightNoPlaceTest";
                        }
                        if (ListType.equals("StalkSize")) {
                            ListType = "StalkSizeNoPlaceTest";
                        }
                        break;
                    default:
                        String[] Array = getResources().getStringArray(R.array.CodeList);

                        PlaceCode = Array[position];

                        typeSpinner.setSelection(0);

                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (PlaceCode.equals("ALL")) {
                    switch (position) {
                        case 0:
                            ListType = "BrixNoPlaceTest";
                            tvHeadType.setText("Brix");
                            break;
                        case 1:
                            ListType = "StalkAmountNoPlaceTest";
                            tvHeadType.setText("จำนวนลำ");
                            break;
                        case 2:
                            ListType = "HeightNoPlaceTest";
                            tvHeadType.setText("ความสูง");
                            break;
                        case 3:
                            ListType = "StalkSizeNoPlaceTest";
                            tvHeadType.setText("ขนาดลำ");
                            break;
                    }
                } else {
                    switch (position) {
                        case 0:
                            ListType = "Brix";
                            tvHeadType.setText("Brix");
                            break;
                        case 1:
                            ListType = "StalkAmount";
                            tvHeadType.setText("จำนวนลำ");
                            break;
                        case 2:
                            ListType = "Height";
                            tvHeadType.setText("ความสูง");
                            break;
                        case 3:
                            ListType = "StalkSize";
                            tvHeadType.setText("ขนาดลำ");
                            break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mListView.setAdapter(adapter);

        btSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RunScan();
            }
        });
        RunScan();
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void RunScan() {
        aq = new AQuery(getActivity());
        String URL;
        URL = "http://210.1.60.178:6111/index.php/service/take8thmclonedata?" + "ListType=TopList&&" + "TopType=" + ListType + "&&TopAmount=" + ListAmount;
        Log.d("URL", URL);
        if (!PlaceCode.equals("ALL")) {
            URL = "http://210.1.60.178:6111/index.php/service/take8thmclonedata?" + "ListType=TopList&&" + "TopType=" + ListType + "&&PlaceTest=" + PlaceCode + "&&TopAmount=" + ListAmount;
            Log.d("URL", URL);
        }


//        String URL = "http://210.1.60.178:6111/index.php/service/take8thmclonedata";
//        String URL = getResources().getString(R.string.url_dowloadlistdata);
//        HashMap<String, java.lang.Object> param = new HashMap<>();
//        param.put("ListType", "TopList");
//        param.put("TopType", "Brix");
//        param.put("PlaceTest", "L");
//        param.put("TopAmount",50);

//        Log.d("Network Debug",param.toString());


        GsonTransformer gsonTransformer = new GsonTransformer();


        ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Loading...");
        aq.progress(dialog).transformer(gsonTransformer).ajax(URL.trim(), null, JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject object, AjaxStatus status) {
                super.callback(url, object, status);
                Log.d("Network Status", status.getMessage());


                if (status.getCode() != AjaxStatus.NETWORK_ERROR) {
                    if (object != null) {

                        a.clear();
                        a = new ArrayList<RowItem>();
//                        RowItem head = new RowItem();
////                        head.setOrder("ลำดับที่");
//                        head.setCloneCode("เบอร์โคลน");
////                        head.setStalkAmount("จำนวนลำต่อกอ");
////                        head.setBrix("Brix");
////                        head.setHeight("ความสูง");
////                        head.setStalkSizeAverage("ขนาดลำ");
//                        head.setPlaceTest("สถานที่");

//

//                        a.add(head);
//                              Log.d("Debug", object.toString());
                        a.addAll(object.getToplist());
                        adapter.notifyDataSetChanged();


//                        for (int i = 0; i < object.getToplist().size(); i++) {
//                            RowItem item = new RowItem();
//                            item.setOrder(i+1);
//                            item.setBrix(a.get(i).getBrix());
//                            item.setCloneCode(a.get(i).getCloneCode());
//                            item.setHeight(a.get(i).getHeight());
//                            item.setInternodeAmount(a.get(i).getInternodeAmount());
//                            item.setInternodeLengthAverage(a.get(i).getInternodeLengthAverage());
//                            item.setLeafSheath(a.get(i).getLeafSheath());
//                            item.setStalkSizeAverage(a.get(i).getStalkSizeAverage());
//                            item.setStalkAmount(a.get(i).getStalkAmount());
//                            item.setPlaceTest(a.get(i).getPlaceTest());
//
//                            //TODO + MORE ITEM DATA
//
//                        }
                    }


                }
            }
        });


    }

    class MyListAdapter extends BaseAdapter {

        LayoutInflater inflater;

        MyListAdapter(Context context) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getItemViewType(int position) {
            return super.getItemViewType(position);
        }

        @Override
        public int getViewTypeCount() {
            return super.getViewTypeCount();
        }

        @Override
        public int getCount() {
            return a.size();
        }

        @Override
        public java.lang.Object getItem(int position) {
            return a.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder v;
            if (convertView == null) {
                v = new ViewHolder();
                convertView = inflater.inflate(R.layout.single_rank_list, null);
                v.tvCloneCode = (TextView) convertView.findViewById(R.id.tvCloneCode);
                v.tvPlace = (TextView) convertView.findViewById(R.id.tvPlace);
                v.tvRank = (TextView) convertView.findViewById(R.id.tvRank);
                v.tvType = (TextView) convertView.findViewById(R.id.tvType);
                convertView.setTag(v);
            } else {
                v = (ViewHolder) convertView.getTag();
            }

            v.tvCloneCode.setText(a.get(position).getCloneCode());
            v.tvPlace.setText(a.get(position).getPlaceTest());
            v.tvRank.setText("" + (position + 1));


            if (ListType.equals("Brix") || ListType.equals("BrixNoPlaceTest")) {
                v.tvType.setText("" + a.get(position).getBrix());
            } else if (ListType.equals("StalkAmount") || ListType.equals("StalkAmountNoPlaceTest")) {
//                v.tvType.setText("" + a.get(position).getStalkAmount());
            } else if (ListType.equals("Height") || ListType.equals("HeightNoPlaceTest")) {
                v.tvType.setText("" + a.get(position).getHeight());
            } else if (ListType.equals("StalkSize") || ListType.equals("StalkSizeNoPlaceTest")) {
                v.tvType.setText("" + a.get(position).getStalkSizeAverage());
            }
            return convertView;
        }


        class ViewHolder {
            TextView tvCloneCode;
            TextView tvRank;
            TextView tvPlace;
            TextView tvType;
        }
    }

    private class JSONObject {
        private ArrayList<RowItem> toplist;

        public ArrayList<RowItem> getToplist() {
            return toplist;
        }

        public void setToplist(ArrayList<RowItem> toplist) {
            this.toplist = toplist;
        }


    }

    private class RowItem {
        private String CloneCode;
        private String PlaceTest;
//        private int StalkAmount;
        private float StalkAmountScore;
        private String StalkShape;
        private float StalkShapeScore;
        private float StalkSizeAverage;
        private float StalkSizeAverageScore;

        private String ClumpShape;
        private float ClumpShapeScore;
        private String ClumpCharacteristic;
        private float ClumpCharacteristicScore;
        private String InternalSymtom;
        private float InternalSymtomScore;
        private String InternalFirmness;
        private float InternalFirmnessScore;
        private float InternodeAmount;
        private float InternodeAmountScore;
        private float InternodeLength;
        private float InternodeLengthScore;
        private float InternodeLengthAverage;
        private float InternodeLengthAverageScore;
        private float Brix;
        private float BrixScore;
        private float Height;
        private float HeightScore;
        private float OverAll;
        private float OverAllScore;
        private String LeafSheath;
        private float LeafSheathScore;
        private float TotalScore;
        private String SelectStatus;
        private String WhySelect;

        public float getInternalFirmnessScore() {
            return InternalFirmnessScore;
        }

        public void setInternalFirmnessScore(float internalFirmnessScore) {
            InternalFirmnessScore = internalFirmnessScore;
        }

        public float getInternodeAmount() {
            return InternodeAmount;
        }

        public void setInternodeAmount(float internodeAmount) {
            InternodeAmount = internodeAmount;
        }

        public float getInternodeAmountScore() {
            return InternodeAmountScore;
        }

        public void setInternodeAmountScore(float internodeAmountScore) {
            InternodeAmountScore = internodeAmountScore;
        }

        public float getInternodeLength() {
            return InternodeLength;
        }

        public void setInternodeLength(float internodeLength) {
            InternodeLength = internodeLength;
        }

        public float getInternodeLengthScore() {
            return InternodeLengthScore;
        }

        public void setInternodeLengthScore(float internodeLengthScore) {
            InternodeLengthScore = internodeLengthScore;
        }

        public float getInternodeLengthAverage() {
            return InternodeLengthAverage;
        }

        public void setInternodeLengthAverage(float internodeLengthAverage) {
            InternodeLengthAverage = internodeLengthAverage;
        }

        public float getInternodeLengthAverageScore() {
            return InternodeLengthAverageScore;
        }

        public void setInternodeLengthAverageScore(float internodeLengthAverageScore) {
            InternodeLengthAverageScore = internodeLengthAverageScore;
        }

        public float getBrix() {
            return Brix;
        }

        public void setBrix(float brix) {
            Brix = brix;
        }

        public float getBrixScore() {
            return BrixScore;
        }

        public void setBrixScore(float brixScore) {
            BrixScore = brixScore;
        }

        public float getHeightScore() {
            return HeightScore;
        }

        public void setHeightScore(float heightScore) {
            HeightScore = heightScore;
        }

        public float getOverAll() {
            return OverAll;
        }

        public void setOverAll(float overAll) {
            OverAll = overAll;
        }

        public float getOverAllScore() {
            return OverAllScore;
        }

        public void setOverAllScore(float overAllScore) {
            OverAllScore = overAllScore;
        }

        public float getLeafSheathScore() {
            return LeafSheathScore;
        }

        public void setLeafSheathScore(float leafSheathScore) {
            LeafSheathScore = leafSheathScore;
        }

        public float getTotalScore() {
            return TotalScore;
        }

        public void setTotalScore(float totalScore) {
            TotalScore = totalScore;
        }

        public String getSelectStatus() {
            return SelectStatus;
        }

        public void setSelectStatus(String selectStatus) {
            SelectStatus = selectStatus;
        }

        public String getWhySelect() {
            return WhySelect;
        }

        public void setWhySelect(String whySelect) {
            WhySelect = whySelect;
        }

        public String getLeafSheath() {
            return LeafSheath;
        }

        public void setLeafSheath(String leafSheath) {
            LeafSheath = leafSheath;
        }

        public String getInternalFirmness() {
            return InternalFirmness;
        }

        public void setInternalFirmness(String internalFirmness) {
            InternalFirmness = internalFirmness;
        }

        public float getInternalSymtomScore() {
            return InternalSymtomScore;
        }

        public void setInternalSymtomScore(float internalSymtomScore) {
            InternalSymtomScore = internalSymtomScore;
        }

        public String getInternalSymtom() {
            return InternalSymtom;
        }

        public void setInternalSymtom(String internalSymtom) {
            InternalSymtom = internalSymtom;
        }

        public float getClumpCharacteristicScore() {
            return ClumpCharacteristicScore;
        }

        public void setClumpCharacteristicScore(float clumpCharacteristicScore) {
            ClumpCharacteristicScore = clumpCharacteristicScore;
        }

        public String getClumpCharacteristic() {
            return ClumpCharacteristic;
        }

        public void setClumpCharacteristic(String clumpCharacteristic) {
            ClumpCharacteristic = clumpCharacteristic;
        }

        public float getHeight() {
            return Height;
        }

        public void setHeight(float height) {
            Height = height;
        }

        public float getClumpShapeScore() {
            return ClumpShapeScore;
        }

        public void setClumpShapeScore(float clumpShapeScore) {
            ClumpShapeScore = clumpShapeScore;
        }

        public String getClumpShape() {
            return ClumpShape;
        }

        public void setClumpShape(String clumpShape) {
            ClumpShape = clumpShape;
        }

        public float getStalkSizeAverageScore() {
            return StalkSizeAverageScore;
        }

        public void setStalkSizeAverageScore(float stalkSizeAverageScore) {
            StalkSizeAverageScore = stalkSizeAverageScore;
        }

        public float getStalkSizeAverage() {
            return StalkSizeAverage;
        }

        public void setStalkSizeAverage(float stalkSizeAverage) {
            StalkSizeAverage = stalkSizeAverage;
        }

        public float getStalkShapeScore() {
            return StalkShapeScore;
        }

        public void setStalkShapeScore(float stalkShapeScore) {
            StalkShapeScore = stalkShapeScore;
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

        public float getStalkAmountScore() {
            return StalkAmountScore;
        }

        public void setStalkAmountScore(float stalkAmountScore) {
            StalkAmountScore = stalkAmountScore;
        }

        public String getStalkShape() {
            return StalkShape;
        }

        public void setStalkShape(String stalkShape) {
            StalkShape = stalkShape;
        }
    }


}
