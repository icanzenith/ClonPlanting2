package sugarcaneselection.thaib.org.sugarcanselection.Social;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.util.Progress;
import com.manuelpeinado.fadingactionbar.FadingActionBarHelper;

import java.util.ArrayList;
import java.util.HashMap;

import sugarcaneselection.thaib.org.sugarcanselection.GsonTransformer;
import sugarcaneselection.thaib.org.sugarcanselection.Params;
import sugarcaneselection.thaib.org.sugarcanselection.R;


public class WorkPlaceOverViewFragment extends Fragment {

    private static final String ARG_PLACE_TEST = "Placetest";
    AQuery aq;
    TextView tvPlace;
    private TableLayout mTable;
    private String mPlaceTest;
    private ArrayList<RowItem> mRowItem;
    private ProgressBar pb;

    public static Fragment newInstance(String placetest) {
        Fragment fragment = new WorkPlaceOverViewFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_PLACE_TEST, placetest);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WorkPlaceActivity s = (WorkPlaceActivity) getActivity();
        s.getSupportActionBar().setTitle("เครือข่ายนักปรับปรุงพันธุ์");

        if (getArguments() != null) {
            mRowItem = new ArrayList<>();
            mPlaceTest = getArguments().getString(ARG_PLACE_TEST);

            String URL = "http://210.1.60.178:6111/index.php/service/take8thmclonedata";

            HashMap<String, String> param = new HashMap<>();
            param.put(Params.LIST_TYPE, Params.PLACE_TEST_AMOUNT);
            param.put(Params.PLACE_TEST, mPlaceTest);

            Log.d("Debug",param.toString());

            aq = new AQuery(getActivity());
            GsonTransformer gsonTransformer = new GsonTransformer();
            aq.transformer(gsonTransformer)
                    .ajax(URL, param, CallBackItem.class, new AjaxCallback<CallBackItem>() {
                        @Override
                        public void callback(String url, CallBackItem object, AjaxStatus status) {
                            super.callback(url, object, status);
                            if (status.getCode() != AjaxStatus.NETWORK_ERROR) {

                                ArrayList<CallBackItem.ArrayItem> c = object.getPlacetestamount();
                                RowItem ih = new RowItem();
                                ih.setRowNumber("แถวที่");
                                ih.setFamilyCode("เบอร์ Family");
                                ih.setCloneAmount("จำนวนโคลนที่คัด");
                                ih.setMother("แม่");
                                ih.setFather("พ่อ");
                                mRowItem.add(ih);
                                if (object != null) {
                                    int j = 0;
                                    for (int i = 0; i < object.getPlacetestamount().size(); i++) {

                                        RowItem item = new RowItem();
                                        item.setRowNumber(c.get(i).getRowNumber());
                                        item.setFamilyCode(c.get(i).getFamilyCode());
                                        item.setCloneAmount(c.get(i).getCloneAmount());
                                        j = Integer.parseInt(c.get(i).getCloneAmount()) + j;
                                        item.setFather(c.get(i).getFather());
                                        item.setMother(c.get(i).getMother());
                                        mRowItem.add(item);

                                    }
                                    RowItem footer = new RowItem();
                                    footer.setRowNumber("สรุป");
                                    footer.setFamilyCode(object.getPlacetestamount().size() + " Family");
                                    footer.setCloneAmount(j + " โคลน");
                                    mRowItem.add(footer);

                                }

                                SetTable(mRowItem);
                                pb.setVisibility(View.INVISIBLE);


                            }
                        }

                    });
        }
    }

    private void SetTable(ArrayList<RowItem> datas) {


        for (int i = 0; i < datas.size(); i++) {

            TableRow tableRow = new TableRow(getActivity());
            tableRow.setLayoutParams(new TableLayout.LayoutParams
                    (TableLayout.LayoutParams.MATCH_PARENT,
                            TableLayout.LayoutParams.WRAP_CONTENT));
            tableRow.setGravity(Gravity.CENTER);
            tableRow.setWeightSum(5);
            tableRow.setPadding(10, 10, 10, 10);
            if (i == 0) {
                tableRow.setBackgroundColor(0xFF228b22);
            } else if (i % 2 == 1) {
                tableRow.setBackgroundColor(0x55228b22);
            } else if (i == (datas.size() - 1)) {
                tableRow.setBackgroundColor(0xFF228b22);
            }

            TextView tvRowNumber = new TextView(getActivity());
            tvRowNumber.setText(datas.get(i).getRowNumber());

            tvRowNumber.setLayoutParams(new TableRow.LayoutParams
                    (TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 0.5f));
            tvRowNumber.setTextColor(0xFF111111);
            tvRowNumber.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            tvRowNumber.setTypeface((Typeface.DEFAULT_BOLD));

            TextView tvFamilyCode = new TextView(getActivity());
            tvFamilyCode.setText(datas.get(i).getFamilyCode());
            tvFamilyCode.setLayoutParams(new TableRow.LayoutParams
                    (TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 1.25f));
            tvFamilyCode.setTextColor(0xFF111111);
            tvFamilyCode.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            tvFamilyCode.setTypeface((Typeface.DEFAULT_BOLD));


            TextView tvCloneAmount = new TextView(getActivity());
            tvCloneAmount.setText(datas.get(i).getCloneAmount());
            tvCloneAmount.setLayoutParams(new TableRow.LayoutParams
                    (TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 0.5f));
            tvCloneAmount.setTextColor(0xFF111111);
            tvCloneAmount.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            tvCloneAmount.setTypeface((Typeface.DEFAULT_BOLD));
            tvCloneAmount.setGravity(Gravity.CENTER);

            TextView tvCloneFather = new TextView(getActivity());
            tvCloneFather.setText(datas.get(i).getFather());
            tvCloneFather.setLayoutParams(new TableRow.LayoutParams
                    (TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 1.25f));
            tvCloneFather.setTextColor(0xFF111111);
            tvCloneFather.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            tvCloneFather.setTypeface((Typeface.DEFAULT_BOLD));
            tvCloneFather.setGravity(Gravity.CENTER);

            TextView tvMother = new TextView(getActivity());
            tvMother.setText(datas.get(i).getMother());
            tvMother.setLayoutParams(new TableRow.LayoutParams
                    (TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 1.25f));
            tvMother.setTextColor(0xFF111111);
            tvMother.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            tvMother.setTypeface((Typeface.DEFAULT_BOLD));
            tvMother.setGravity(Gravity.CENTER);


            tableRow.addView(tvRowNumber);
            tableRow.addView(tvFamilyCode);
            tableRow.addView(tvCloneAmount);
            tableRow.addView(tvMother);
            tableRow.addView(tvCloneFather);


            mTable.addView(tableRow);
            tvPlace.setVisibility(View.VISIBLE);


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_workplace, container, false);
        mTable = (TableLayout) rootView.findViewById(R.id.mTableLayout);
        pb = (ProgressBar) rootView.findViewById(R.id.progressBar);
        tvPlace = (TextView) rootView.findViewById(R.id.tvWorkPlaceName);


        return rootView;

    }

    class RowItem {
        private String RowNumber;
        private String FamilyCode;
        private String CloneAmount;
        private String Father;
        private String Mother;

        public String getRowNumber() {
            return RowNumber;
        }

        public void setRowNumber(String rowNumber) {
            RowNumber = rowNumber;
        }

        public String getFamilyCode() {
            return FamilyCode;
        }

        public void setFamilyCode(String familyCode) {
            FamilyCode = familyCode;
        }

        public String getCloneAmount() {
            return CloneAmount;
        }

        public void setCloneAmount(String cloneAmount) {
            CloneAmount = cloneAmount;
        }

        public String getFather() {
            return Father;
        }

        public void setFather(String father) {
            Father = father;
        }

        public String getMother() {
            return Mother;
        }

        public void setMother(String mother) {
            Mother = mother;
        }
    }

    class CallBackItem {
        private ArrayList<ArrayItem> placetestamount;

        public ArrayList<ArrayItem> getPlacetestamount() {
            return placetestamount;
        }

        public void setPlacetestamount(ArrayList<ArrayItem> placetestamount) {
            this.placetestamount = placetestamount;
        }

        class ArrayItem {
            private String RowNumber;
            private String CloneAmount;
            private String FamilyCode;
            private String Father;
            private String Mother;

            public String getRowNumber() {
                return RowNumber;
            }

            public void setRowNumber(String rowNumber) {
                RowNumber = rowNumber;
            }

            public String getCloneAmount() {
                return CloneAmount;
            }

            public void setCloneAmount(String cloneAmount) {
                CloneAmount = cloneAmount;
            }

            public String getFamilyCode() {
                return FamilyCode;
            }

            public void setFamilyCode(String familyCode) {
                FamilyCode = familyCode;
            }

            public String getFather() {
                return Father;
            }

            public void setFather(String father) {
                Father = father;
            }

            public String getMother() {
                return Mother;
            }

            public void setMother(String mother) {
                Mother = mother;
            }
        }
    }

}
