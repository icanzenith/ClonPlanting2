package sugarcaneselection.thaib.org.sugarcanselection.correctdata;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Type;
import java.util.ArrayList;

import sugarcaneselection.thaib.org.sugarcanselection.BaseApplication;
import sugarcaneselection.thaib.org.sugarcanselection.CalculateScore;
import sugarcaneselection.thaib.org.sugarcanselection.Dialog.DeleteDialog;
import sugarcaneselection.thaib.org.sugarcanselection.FragmentPictureManager;
import sugarcaneselection.thaib.org.sugarcanselection.Item.CloneDataItem;
import sugarcaneselection.thaib.org.sugarcanselection.Item.SelectStatus;
import sugarcaneselection.thaib.org.sugarcanselection.MainActivity;
import sugarcaneselection.thaib.org.sugarcanselection.R;
import sugarcaneselection.thaib.org.sugarcanselection.StandardSpecieData;
import sugarcaneselection.thaib.org.sugarcanselection.TakePictureActivity;
import sugarcaneselection.thaib.org.sugarcanselection.database.Columns;
import sugarcaneselection.thaib.org.sugarcanselection.database.SQLiteCommand;


public class FragmentCloneSelect extends Fragment implements DeleteDialog.DeleteDialogClick {

    private static final int Type_List = 0;
    private static final int Type_Head = 1;
    private static final int Type_Standard = 2;

    private static final String SELECT_FROM_TAKEPICMENU = "takpic";
    private static final String SELECT_FROM_CORRECT ="correct";
    private static final String ARG_LISTDATA = "Listdata";
    private static final String ARG_FROMACTIVITY = "from";
    private static final String ARG_FamilyCode = "FamilyCode";
    private ArrayList<CloneItem> allClone = new ArrayList<>();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER]
    private ListView mListView;
    private BaseApplication baseApplication;
    private MainActivity mainActivity;
    private Button bt_changefamily;
    // TODO: Rename and change types of parameters
    private String fromActivity = null;
    private String mFamilyCode;
    private mAdapter adapter;
    private OnFragmentInteractionListener mListener;

    public FragmentCloneSelect() {

    }

    public static FragmentCloneSelect newInstance(String fromactivity, String FamilyCode) {
        FragmentCloneSelect fragment = new FragmentCloneSelect();
        Bundle args = new Bundle();
        args.putString(ARG_FROMACTIVITY, fromactivity);
        args.putString(ARG_FamilyCode, FamilyCode);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onConfirm() {
        RefreshList();

    }

    private void RefreshList() {
        getAllClone(mFamilyCode);
        adapter.setData(allClone);
        adapter.notifyDataSetChanged();
    }

    private void getStandardClone(String mfamilycode) {

        ContentResolver cr = getActivity().getContentResolver();

        String uri = getResources().getString(R.string.URI_MY_STANDARDCLONE);

        String[] projection = null;
        String selection = Columns.FAMILYCODE+" = ?";
        String[] selectionagrs = {mfamilycode};

        Cursor c;
        c = cr.query(Uri.parse(uri), projection, selection, selectionagrs, null);
        if (c != null) {
            if (c.getCount() != 0) {
                CloneItem TitleCheckSpecie = new CloneItem();
                TitleCheckSpecie.setStatus("พันธุ์เช็ค");
                TitleCheckSpecie.setListViewType(Type_Head);

                if (c.getCount() <2){
                    //TODO มีค่าในตารางแต่ไม่ถึง 2 ค่า
//                    Toast.makeText(getActivity(), "มีพันธุ์เช็ค< 2", Toast.LENGTH_LONG).show();
                    while (c.moveToNext()) {
                        if (c.getInt(c.getColumnIndex(Columns.PLANTORDER)) == CloneDataItem.STANDARD_FIRST) {
                            allClone.add(CreateHeaderStandardRow());
                           allClone.add(CreateStandardList(CloneDataItem.STANDARD_FIRST,c));
                           allClone.add(CreateStandardList(CloneDataItem.STANDARD_LAST));
                            allClone.add(CreateHeaderCloneRow());
                        } else if (c.getInt(c.getColumnIndex(Columns.PLANTORDER)) == CloneDataItem.STANDARD_LAST) {
                            allClone.add(CreateHeaderStandardRow());
                           allClone.add( CreateStandardList(CloneDataItem.STANDARD_FIRST));
                           allClone.add(CreateStandardList(CloneDataItem.STANDARD_LAST,c));
                            allClone.add(CreateHeaderCloneRow());
                        }
                    }
                }else if (c.getCount() ==2){
                    //TODO มีพันธุ์เช็คสองต้น
//                    Toast.makeText(getActivity(), "มีพันธุ์เช็ค 2", Toast.LENGTH_LONG).show();
                    allClone.add(CreateHeaderStandardRow());
                    while (c.moveToNext()) {
                        if (c.getInt(c.getColumnIndex(Columns.PLANTORDER)) == CloneDataItem.STANDARD_FIRST) {
                            allClone.add(CreateStandardList(CloneDataItem.STANDARD_FIRST,c));
                        } else if (c.getInt(c.getColumnIndex(Columns.PLANTORDER)) == CloneDataItem.STANDARD_LAST) {
                            allClone.add(CreateStandardList(CloneDataItem.STANDARD_LAST,c));
                        }
                    }
                    allClone.add(CreateHeaderCloneRow());
                }else{
                    Toast.makeText(getActivity(), "Cusor Count"+c.getCount(), Toast.LENGTH_LONG).show();
                    Toast.makeText(getActivity(), "What the Fuck", Toast.LENGTH_LONG).show();
                }
            } else {
                //TODO  INSERT NEW SPECIES DATA HERE!!!! ไม่มีค่าต้นเช็คในตารางข้อมูล
//                Toast.makeText(getActivity(), "Cursor Count = 0", Toast.LENGTH_LONG).show();

                CloneItem TitleCheckSpecie = new CloneItem();
                TitleCheckSpecie.setStatus("พันธุ์เช็ค");
                TitleCheckSpecie.setListViewType(Type_Head);

                allClone.add(CreateHeaderStandardRow());
                allClone.add(CreateStandardList(CloneDataItem.STANDARD_FIRST));
                allClone.add(CreateStandardList(CloneDataItem.STANDARD_LAST));
                allClone.add(CreateHeaderCloneRow());
            }

        } else {
//            Toast.makeText(getActivity(), "Cursor Count = NULL", Toast.LENGTH_LONG).show();

        }


    }

    private CloneItem CreateStandardList(int standardLast) {
        CloneItem d = new CloneItem();
        d.setListViewType(Type_Standard);
        d.setPlantNumber(standardLast);
        //TODO Move Static String To XML TYPE
        d.setCloneCode("ยังไม่ได้ระบุชื่อพันธุ์");
        d.setStatus(""+SelectStatus.NOTHING);
        return d;

    }

    private CloneItem CreateHeaderStandardRow(){
        CloneItem TitleCheckSpecie = new CloneItem();
        TitleCheckSpecie.setStatus("พันธุ์เช็ค");
        TitleCheckSpecie.setListViewType(Type_Head);
        return  TitleCheckSpecie;
    }

    private CloneItem CreateHeaderCloneRow(){
        CloneItem TitleClone = new CloneItem();
        TitleClone.setStatus("เบอร์โคลน");
        TitleClone.setListViewType(Type_Head);
        return TitleClone;
    }


    private CloneItem CreateStandardList(int standardLast,Cursor c) {
        CloneItem d = new CloneItem();
        d.setListViewType(Type_Standard);
        d.setPlantNumber(standardLast);
        //TODO Move Static String To XML TYPE
        d.setCloneCode(c.getString(c.getColumnIndex(Columns.SPECIENAME)));
        d.setStatus(""+c.getInt(c.getColumnIndex(Columns.SAVEDSTATUS)));
        return d;

    }

    private void getAllClone(String mfamilycode) {
        allClone = new ArrayList<>();
        getStandardClone(mfamilycode);

        ContentResolver cr = getActivity().getContentResolver();

        String uri = getResources().getString(R.string.URI_MYCLONE);

        String[] projection = null;
        String selection = "familycode = ?";
        String[] selectionagrs = {mfamilycode};

        Cursor c;
        c = cr.query(Uri.parse(uri), projection, selection, selectionagrs, null);


        // Clone in List
        while (c != null && c.moveToNext()) {

            CloneItem item = new CloneItem();
            item.setPlantNumber(c.getInt(c.getColumnIndex(Columns.PLANTORDER)));
            item.setStatus("" + c.getInt(c.getColumnIndex(Columns.SELECT_STATUS)));
            item.setCloneCode("" + c.getString(c.getColumnIndex(Columns.CLONECODE)));
            item.setListViewType(Type_List);

            allClone.add(item);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.changefamily) {
            if (fromActivity != null) {
                if (fromActivity.equals(SELECT_FROM_TAKEPICMENU)) {
                    TakePictureActivity takePictureActivity = (TakePictureActivity) getActivity();
                    takePictureActivity.onChangeFamily();
                } else {
                    mainActivity.onChangeFamily();
                }
            } else {
                mainActivity.onChangeFamily();
            }

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.reselect_family, menu);
    }

    ActionBar actionBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            fromActivity = getArguments().getString(ARG_FROMACTIVITY);
            mFamilyCode = getArguments().getString(ARG_FamilyCode);
            getAllClone(mFamilyCode);
        }
        if (fromActivity != null) {
            if (fromActivity.equals(SELECT_FROM_TAKEPICMENU)) {

            } else {
                mainActivity = (MainActivity) getActivity();
                mainActivity.setActionBarTitle("เลือกเบอร์โคลน");
                actionBar = mainActivity.getSupportActionBar();


            }

        } else {
            mainActivity = (MainActivity) getActivity();
            mainActivity.setActionBarTitle("เลือกเบอร์โคลน");
            actionBar = mainActivity.getSupportActionBar();
        }


        baseApplication = (BaseApplication) getActivity().getApplication();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Create Action Bar
        if (fromActivity.equals(SELECT_FROM_TAKEPICMENU)) {

        } else {
            mainActivity = (MainActivity) getActivity();
            mainActivity.setActionBarTitle("เลือกเบอร์โคลน");
            actionBar = mainActivity.getSupportActionBar();
            mainActivity.getSupportActionBar().removeAllTabs();
            mainActivity.getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
            mainActivity.getSupportActionBar().setDisplayOptions(ActionBar.NAVIGATION_MODE_STANDARD | ActionBar.DISPLAY_SHOW_TITLE);
            mainActivity.setActionBarTitle("เลือกเบอร์โคลน");

        }



        // Create Action Bar

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_clone_select, container, false);
        mListView = (ListView) rootView.findViewById(R.id.listView_clonelist);
        adapter = new mAdapter(getActivity(), allClone);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (fromActivity != null) {

                    if (fromActivity.equals(SELECT_FROM_TAKEPICMENU)) {


//                        baseApplication.setCalculateScore(new CalculateScore());
                        CloneItem c = (CloneItem) parent.getItemAtPosition(position);
                        if (c.getListViewType() == Type_Head) {

                        } else {
                            if (c.getListViewType() == Type_List) {

                                baseApplication.getCloneDataItem().setClonecode(c.getCloneCode());
                                TakePictureActivity takePictureActivity = (TakePictureActivity) getActivity();
                                takePictureActivity.onFinishCorrectData(FragmentPictureManager.PIC_CLONE,0);
                                takePictureActivity.getSupportActionBar().setTitle("บันทึกข้อมูลของ " + c.getCloneCode());
                            }else if(c.getListViewType() == Type_Standard){
                                baseApplication.getCloneDataItem().setClonecode(c.getCloneCode());
                                TakePictureActivity takePictureActivity = (TakePictureActivity) getActivity();
                                takePictureActivity.onFinishCorrectData(FragmentPictureManager.PIC_STANDARD,c.getPlantNumber());
                                takePictureActivity.getSupportActionBar().setTitle("บันทึกข้อมูลพันธุ์เช็ค "+c.getPlantNumber()+ baseApplication.getCloneDataItem().getFamilycode()+" ");
                            }


                        }

                    }else {
                        CloneItem c = (CloneItem) parent.getItemAtPosition(position);

                        if (c.getListViewType() == Type_Head) {

                        } else {
                            if (c.getListViewType() == Type_Standard) {
                                /**
                                 * GetCloneData is method to query cloneDataItem*/
                                baseApplication.getCloneDataItem().setClonecode(c.getCloneCode());
                                baseApplication.getCloneDataItem().setDataType(CloneDataItem.DATATYPE_STANDARD);
                                baseApplication.getCloneDataItem().setSTANDARD_ROW(c.getPlantNumber());
                                mainActivity.onFinishCloneSelect(Type_Standard);
                                mainActivity.setActionBarTitle("บันทึกข้อมูลพันธุ์เช็ค " + c.getCloneCode());
                            } else if (c.getListViewType() == Type_List) {
                                /**
                                 * GetCloneData is method to query cloneDataItem*/
                                baseApplication.getCloneDataItem().setClonecode(c.getCloneCode());
                                baseApplication.getCloneDataItem().setDataType(CloneDataItem.DATATYPE_CLONE);
                                mainActivity.onFinishCloneSelect(Type_List);
                                mainActivity.setActionBarTitle("บันทึกข้อมูลของ " + c.getCloneCode());
                            }


                        }
                    }
                }
            }
        });

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                CloneItem c = (CloneItem) parent.getItemAtPosition(position);
                DeleteDialog.newInstance("deleteCloneData", c.getCloneCode(),FragmentCloneSelect.this);
                DeleteDialog.newInstance("deleteCloneData", c.getCloneCode(),FragmentCloneSelect.this)
                        .show(getActivity().getSupportFragmentManager(), "Delete");
                return true;
            }
        });

        bt_changefamily = (Button) rootView.findViewById(R.id.bt_changefamily);
        bt_changefamily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.onChangeFamily();
            }
        });
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
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
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (baseApplication == null){

            baseApplication = (BaseApplication) mainActivity.getApplication();
        }
        if (baseApplication.getCloneDataItem() != null){

        }else{

        }
//        if (fromActivity != null) {
//            if (fromActivity.equals(SELECT_FROM_TAKEPICMENU)) {
//
//            } else {
//                actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_CUSTOM);
//                actionBar.removeAllTabs();
//                actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
//            }
//        }

        if (savedInstanceState != null) {

        } else {

        }
        getAllClone(mFamilyCode);
        mAdapter adapter = (mAdapter) mListView.getAdapter();
        adapter.setData(allClone);
        adapter.notifyDataSetChanged();

    }

    public interface OnFragmentInteractionListener {

        public void onFragmentInteraction(Uri uri);
    }

    class mAdapter extends BaseAdapter {

        Context _context;
        LayoutInflater inflater;
        private ArrayList<CloneItem> data;

        mAdapter(Context context, ArrayList<CloneItem> data) {
            _context = context;
            this.data = data;
            inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public ArrayList<CloneItem> getData() {
            return data;
        }

        @Override
        public int getViewTypeCount() {
            return 3;
        }

        @Override
        public int getItemViewType(int position) {
            return data.get(position).getListViewType();
        }

        public void setData(ArrayList<CloneItem> data) {
            this.data = data;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder v;
            int getItemViewType = getItemViewType(position);

            if (convertView == null) {
                v = new ViewHolder();
                if (getItemViewType == Type_Head) {
                    convertView = inflater.inflate(R.layout.single_list_familyheader, null);
                } else {
                    convertView = inflater.inflate(R.layout.single_clone_list, null);
                }
                if (getItemViewType == Type_Head) {
                    v.headerTitle = (TextView) convertView.findViewById(R.id.tvFamilycode);
                } else {
                    v.clonecode = (TextView) convertView.findViewById(R.id.tv_clonecode);
                    v.order = (TextView) convertView.findViewById(R.id.tv_order);
                    v.tvSaveStatus = (TextView) convertView.findViewById(R.id.tvSavesStatus);
                }


                convertView.setTag(v);
            } else {
                v = (ViewHolder) convertView.getTag();
            }

            if (getItemViewType == Type_Head) {
                v.headerTitle.setText(data.get(position).getStatus());
            } else {
                v.clonecode.setText(data.get(position).getCloneCode());
                if (getItemViewType == Type_Standard) {
                    if (data.get(position).getCloneCode()==null){
                    v.clonecode.setText("ยังไม่ได้ระบุชื่อพันธุ์");
                    }else {
                    v.clonecode.setText(data.get(position).getCloneCode());

                    }


                    if (data.get(position).getPlantNumber()==CloneDataItem.STANDARD_FIRST){
                        v.order.setText("หัวแถว");
                    }else if (data.get(position).getPlantNumber()==CloneDataItem.STANDARD_LAST){
                        v.order.setText("ท้ายแถว");
                    }
                } else {
                    v.order.setText("กอที่ " + data.get(position).getPlantNumber());
                }

                if (!data.get(position).getStatus().equals("0")) {
                    v.tvSaveStatus.setVisibility(View.VISIBLE);
                    v.tvSaveStatus.setText("บันทึกแล้ว");
                } else {
                    v.tvSaveStatus.setVisibility(View.INVISIBLE);
                    v.tvSaveStatus.setText("");

                }
            }


            return convertView;
        }

        class ViewHolder {
            TextView clonecode;
            TextView order;
            TextView tvSaveStatus;
            TextView headerTitle;
        }
    }

    class CloneItem {
        private String CloneCode;
        private String Status;
        private Integer PlantNumber;
        private Integer ListViewType;

        public String getCloneCode() {
            return CloneCode;
        }

        public void setCloneCode(String cloneCode) {
            CloneCode = cloneCode;
        }

        public String getStatus() {
            return Status;
        }

        public void setStatus(String status) {
            Status = status;
        }

        public Integer getPlantNumber() {
            return PlantNumber;
        }

        public void setPlantNumber(Integer plantNumber) {
            PlantNumber = plantNumber;
        }

        public void setListViewType(Integer listViewType) {
            ListViewType = listViewType;
        }

        public int getListViewType() {
            return ListViewType;
        }

        public void setListViewType(int listViewType) {
            ListViewType = listViewType;
        }
    }
}
