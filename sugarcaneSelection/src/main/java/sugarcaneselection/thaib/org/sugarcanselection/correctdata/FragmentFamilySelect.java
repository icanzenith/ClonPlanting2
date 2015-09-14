package sugarcaneselection.thaib.org.sugarcanselection.correctdata;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import sugarcaneselection.thaib.org.sugarcanselection.BaseApplication;
import sugarcaneselection.thaib.org.sugarcanselection.Item.CloneDataItem;
import sugarcaneselection.thaib.org.sugarcanselection.Item.SelectStatus;
import sugarcaneselection.thaib.org.sugarcanselection.MainActivity;
import sugarcaneselection.thaib.org.sugarcanselection.R;
import sugarcaneselection.thaib.org.sugarcanselection.TakePictureActivity;
import sugarcaneselection.thaib.org.sugarcanselection.database.Columns;


public class FragmentFamilySelect extends Fragment {

    private static final int FRAGMENT_ENTRY = 1001;


    private static final String ARG_PARAM1 = "FromActivity";
    private static final String SELECT_FROM_TAKEPICMENU = "takpic";
    MainActivity mainActivity;
    private String fromActivity = "no from";
    private ListView mListView;

    BaseApplication b;

    private OnFragmentInteractionListener mListener;


    public FragmentFamilySelect() {
    }


    public static FragmentFamilySelect newInstance(String from, String param2) {
        FragmentFamilySelect fragment = new FragmentFamilySelect();
        Bundle args = new Bundle();
        if (from != null) {
            args.putString(ARG_PARAM1, from);
            fragment.setArguments(args);
        }

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Toast.makeText(getActivity(),"Create",Toast.LENGTH_LONG).show();
        b = (BaseApplication) getActivity().getApplication();
        if (getArguments() != null) {
            fromActivity = getArguments().getString(ARG_PARAM1);
            if (fromActivity.equals(SELECT_FROM_TAKEPICMENU)) {
                b.setCloneDataItem(new CloneDataItem());
            } else {
                mainActivity = (MainActivity) getActivity();
            }
        } else {
            mainActivity = (MainActivity) getActivity();
        }


        if (mListener != null) {

            mListener.setFragmentEntry(FRAGMENT_ENTRY);
        }



    }

    @Override
    public void onResume() {
        super.onResume();
//        ReCheckConclusionData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Create Action Bar

        if (fromActivity.equals(SELECT_FROM_TAKEPICMENU)) {
            TakePictureActivity a = (TakePictureActivity) getActivity();
            a.getSupportActionBar().setTitle("เลือกคู่ผสม");
        } else {
            mainActivity = (MainActivity) getActivity();
            mainActivity.getSupportActionBar().removeAllTabs();
            mainActivity.getSupportActionBar().setDisplayOptions(ActionBar.NAVIGATION_MODE_STANDARD|ActionBar.DISPLAY_SHOW_TITLE);
            mainActivity.getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
            mainActivity.setActionBarTitle("เลือกคู่ผสม");
        }




        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        mListView = (ListView) rootView.findViewById(R.id.listView);
        myAdapter myAdapter = new myAdapter(getActivity(), getData());
        mListView.setAdapter(myAdapter);
//        Toast.makeText(getActivity(),"Create View",Toast.LENGTH_LONG).show();

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView v = (TextView) view.findViewById(R.id._tv_familycode);
                String familycode = v.getText().toString();
                if (fromActivity.equals(SELECT_FROM_TAKEPICMENU)) {
                    Log.d("Debug Family Code",familycode);
                    b.getCloneDataItem().setFamilycode(familycode);
                    b.setLastFamilyDo(familycode);
                    TakePictureActivity takePictureActivity = (TakePictureActivity) getActivity();
                    takePictureActivity.onFinishSelectFamily(familycode);
                } else {
                    b.getCloneDataItem().setFamilycode(familycode);
                    b.setLastFamilyDo(familycode);
                    Log.d("Debug Fam",familycode);
                    mainActivity.FinishSelectFamily(familycode);
                }


            }
        });

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
//        Toast.makeText(getActivity(),"Attach",Toast.LENGTH_LONG).show();
        try {
            mListener = (OnFragmentInteractionListener) activity;

        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
//        Toast.makeText(getActivity(),"Detach",Toast.LENGTH_LONG).show();
    }

    private ArrayList<ListItem> getData() {

        Uri uri = Uri.parse(getResources().getString(R.string.URI_MY_FAMILY));
        String[] projection = null;
        ArrayList<ListItem> a = new ArrayList<>();
        ContentResolver r = getActivity().getContentResolver();

        Cursor c = r.query(uri, projection, null, null, null);

        while (c != null && c.moveToNext()) {

            ListItem l = new ListItem();
            l.setFamily(c.getString(c.getColumnIndex(Columns.FAMILYCODE)));
            l.setRowNumber(c.getInt(c.getColumnIndex(Columns.ROWNUMBER)));
            l.setRejectedAmount(c.getInt(c.getColumnIndex(Columns.AMOUNT_OF_CLONE_REJECTED)));
            l.setSelectedAmount(c.getInt(c.getColumnIndex(Columns.AMOUNT_OF_CLONE_SELECTED)));
            l.setSaveAmount(c.getInt(c.getColumnIndex(Columns.AMOUNT_OF_CLONE_SAVED)) + l.getRejectedAmount()+l.getSelectedAmount());
            a.add(l);

        }

        c.close();
        return a;
    }

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(Uri uri);

        public void onChangeFragmentMenu();

        public void setFragmentEntry(int fragmentEntry);
    }

    public class myAdapter extends BaseAdapter {

        private final ArrayList<ListItem> array_fam;
        Context _context;
        LayoutInflater inflater;

        public myAdapter(Context context, ArrayList<ListItem> array) {

            _context = context;
            inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            array_fam = array;

        }

        @Override
        public int getCount() {
            return array_fam.size();
        }

        @Override
        public Object getItem(int position) {
            return array_fam.get(position);
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
                convertView = inflater.inflate(R.layout.family_single_list, null);
                v.tv_familycode = (TextView) convertView.findViewById(R.id._tv_familycode);
                v.tv_status = (TextView) convertView.findViewById(R.id.save_status);
                v.tv_RowNumber = (TextView) convertView.findViewById(R.id.tv_RowNumber);
                v.tv_LastDo = (TextView) convertView.findViewById(R.id.tvLastDo);
                convertView.setTag(v);
            } else {
                v = (ViewHolder) convertView.getTag();
            }
            v.tv_familycode.setText(array_fam.get(position).getFamily());



            if (b.getLastFamilyDo()!=null) {
                if (b.getLastFamilyDo().equals(array_fam.get(position).getFamily())) {
                    v.tv_LastDo.setVisibility(View.VISIBLE);
                    v.tv_LastDo.setText("• ล่าสุด");

                } else {
                    v.tv_LastDo.setVisibility(View.INVISIBLE);
                    v.tv_LastDo.setText("");
                }
            }else {
                v.tv_LastDo.setVisibility(View.INVISIBLE);
            }
            v.tv_RowNumber.setText("แถวที่ " + array_fam.get(position).getRowNumber());

            if (array_fam.get(position).getSaveAmount()!=0){
                v.tv_status.setVisibility(View.VISIBLE);
                v.tv_status.setText("คัดทิ้ง "+array_fam.get(position).getRejectedAmount()+"/คัดไว้  "+array_fam.get(position).getSelectedAmount()+"/ทั้งหมด "+array_fam.get(position).getSaveAmount());
            }else{
                v.tv_status.setVisibility(View.INVISIBLE);
            }

            return convertView;
        }


        class ViewHolder {
            TextView tv_familycode;
            TextView tv_status;
            TextView tv_RowNumber;
            TextView tv_LastDo;
        }
    }

    class ListItem {
        private String Family;
        private int RowNumber;
        private int status;
        private int SaveAmount;
        private int RejectedAmount;
        private int SelectedAmount;

        public int getSaveAmount() {
            return SaveAmount;
        }

        public void setSaveAmount(int saveAmount) {
            SaveAmount = saveAmount;
        }

        public int getRejectedAmount() {
            return RejectedAmount;
        }

        public void setRejectedAmount(int rejectedAmount) {
            RejectedAmount = rejectedAmount;
        }

        public int getSelectedAmount() {
            return SelectedAmount;
        }

        public void setSelectedAmount(int selectedAmount) {
            SelectedAmount = selectedAmount;
        }

        public String getFamily() {
            return Family;
        }

        public void setFamily(String family) {
            Family = family;
        }

        public int getRowNumber() {
            return RowNumber;
        }

        public void setRowNumber(int rowNumber) {
            RowNumber = rowNumber;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }

}
