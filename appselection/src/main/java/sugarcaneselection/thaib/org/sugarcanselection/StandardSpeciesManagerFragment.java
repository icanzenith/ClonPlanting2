package sugarcaneselection.thaib.org.sugarcanselection;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import sugarcaneselection.thaib.org.sugarcanselection.Interface.OnFragmentInteractionListener;
import sugarcaneselection.thaib.org.sugarcanselection.Item.CloneDataItem;
import sugarcaneselection.thaib.org.sugarcanselection.correctdata.FragmentCloneSelect;
import sugarcaneselection.thaib.org.sugarcanselection.database.Columns;


public class StandardSpeciesManagerFragment extends Fragment implements View.OnClickListener {

    private static final String ARG_FamilyCode = "FamilyCode";
    private static final String ARG_PLANTORDER = "PlantOrder";
    private MyListAdapter adapter;
    private Button btADD;
    private Button btDelete;
    private Button btOKDelete;
    private ListView mListView;
    private String mFamilyCode;

    private TextView tvBrix, tvHeight, tvStalkperClump, tvStalkSize;


    private OnFragmentInteractionListener mListener;

    private MainActivity mainActivity;


    public StandardSpeciesManagerFragment() {
        // Required empty public constructor
    }

    public static StandardSpeciesManagerFragment newInstance(String param1) {
        StandardSpeciesManagerFragment fragment = new StandardSpeciesManagerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_FamilyCode, param1);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mFamilyCode = getArguments().getString(ARG_FamilyCode);

        }
        setHasOptionsMenu(true);
        mainActivity = (MainActivity) getActivity();

        adapter = new MyListAdapter(mainActivity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_standard_species_manager, container, false);

        tvBrix = (TextView) rootView.findViewById(R.id.tv_brix);
        tvStalkperClump = (TextView) rootView.findViewById(R.id.tvStalkPerClump);
        tvHeight = (TextView) rootView.findViewById(R.id.tvHeight);
        tvStalkSize = (TextView) rootView.findViewById(R.id.tvStalkSize);


        btADD = (Button) rootView.findViewById(R.id.btAdd);
        btDelete = (Button) rootView.findViewById(R.id.btDelete);
        btOKDelete = (Button) rootView.findViewById(R.id.btOkDelete);

        mListView = (ListView) rootView.findViewById(R.id.mListView);

        btADD.setOnClickListener(this);
        btDelete.setOnClickListener(this);
        btOKDelete.setOnClickListener(this);

        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String[] plantOrder = (String[]) parent.getItemAtPosition(position);
                Integer p = Integer.parseInt(plantOrder[0]);
                mainActivity.OpenStandardSpecieForm(mFamilyCode, p, false);
            }
        });
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_default, menu);

    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        adapter.Query();
        Refresh();

    }

    private void Refresh() {
        CloneDataItem s = CalculateAverageStandardCloneScore(null);
//        tvBrix.setText(s.getBrix() + "");
//        tvStalkperClump.setText(s.getStalkperclump() + "");
//        tvStalkSize.setText(s.getStalkSizeAverage() + "");
//        tvHeight.setText(s.getHeight() + "");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.next) {

            BaseApplication baseApplication = (BaseApplication) getActivity().getApplication();
            baseApplication.setStandardSpecieData(CalculateAverageStandardCloneScore(mFamilyCode));

            mainActivity.FinishedStandardDataManager(mFamilyCode);
        }
        return true;

    }

    private CloneDataItem CalculateAverageStandardCloneScore(String FamilyCode) {


        Uri uri = Uri.parse(getResources().getString(R.string.URI_MY_STANDARDCLONE));
        String[] projection = null;
        String sortOrder = null;
        String selection = null;
        String[] selectionArgs = null;
        ContentResolver r = getActivity().getContentResolver();
        Cursor c = r.query(uri, projection, selection, selectionArgs, sortOrder);
        int stalkperclump = 0;
        Float brix = 0f;
        int height = 0;
        Float size = 0f;
        CloneDataItem sd = null;
        if (c.getCount() != 0) {
            while (c != null && c.moveToNext()) {
//                stalkperclump = stalkperclump + c.getInt(c.getColumnIndex(Columns.STALKPERCLUMP));
                brix = brix + c.getFloat(c.getColumnIndex(Columns.BRIX));
//                height = height + c.getInt(c.getColumnIndex(Columns.STALKHEIGHT));
//                size = size + c.getFloat(c.getColumnIndex(Columns.AVERAGE_STALK_SIZE));
            }

            c.close();


            sd = new CloneDataItem();
//            sd.setStalkperclump(stalkperclump / c.getCount());
//            sd.setBrix(brix / c.getCount());
//            sd.setHeight(height / c.getCount());
//            sd.setStalkSizeAverage(size / c.getCount());
        } else {
//            sd = new CloneDataItem();
//            sd.setStalkperclump(0);
//            sd.setBrix(0.0f);
//            sd.setHeight(0);
//            sd.setStalkSizeAverage(0f);
//
        }
        return sd;

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btAdd:
                ContentResolver resolver = getActivity().getContentResolver();
                Uri uri = Uri.parse(getResources().getString(R.string.URI_MY_STANDARDCLONE));
                String[] projection = null;
                String selection = Columns.FAMILYCODE + " = ?";
                String[] selectionArgs = {mFamilyCode};
                String sortOrder = Columns.FAMILYCODE + " ASC ";

                Cursor c = resolver.query(uri, projection, selection, selectionArgs, sortOrder);


                int plantOrder = 1;
                if (c.moveToLast()) {
                    plantOrder = (c.getInt(c.getColumnIndex(Columns.PLANTORDER)) + 1);
                }
                mainActivity.OpenStandardSpecieForm(mFamilyCode, plantOrder, true);
                break;
            case R.id.btDelete:

                btADD.setVisibility(View.INVISIBLE);
                btDelete.setVisibility(View.INVISIBLE);
                if (btOKDelete.getVisibility() == View.INVISIBLE) {
                    btOKDelete.setVisibility(View.VISIBLE);
                    PrePairMenuDeleteStandardData();
                } else {
                    btOKDelete.setVisibility(View.INVISIBLE);
                    PrePairMenuDeleteStandardData();
                }

                break;
            case R.id.btOkDelete:
                OKDelete();
                PrePairMenuDeleteStandardData();
                btOKDelete.setVisibility(View.INVISIBLE);
                Refresh();
                break;


        }

    }

    private void OKDelete() {
        btADD.setVisibility(View.VISIBLE);
        btDelete.setVisibility(View.VISIBLE);
        for (int i = 0; i < adapter.getData().size(); i++) {
            if (adapter.getData().get(i)[2] == "1") {
                Uri uri = Uri.parse(getResources().getString(R.string.URI_MY_STANDARDCLONE));
                String where = Columns.FAMILYCODE + " = ? AND " + Columns.PLANTORDER + " = " + adapter.getData().get(i)[0];
                String[] selectionArgs = {mFamilyCode};
                getActivity().getContentResolver().delete(uri, where, selectionArgs);

            } else {

            }

        }
        adapter.Query();
    }

    private void PrePairMenuDeleteStandardData() {
        adapter.onShowCheckList();
    }

    class StandardCloneItem {
        private String FamilyName;
        private String SpeciName;
        private String PlantOrder;
    }

    class MyListAdapter extends BaseAdapter {

        Context _context;
        LayoutInflater inflater;
        ViewHolder v = new ViewHolder();
        private boolean onDeleteMode = false;
        private ArrayList<String[]> data;

        MyListAdapter(Context _context) {
            this._context = _context;
            this.data = new ArrayList<>();
            inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            Query();


        }

        public ArrayList<String[]> getData() {
            return data;
        }

        public void setData(ArrayList<String[]> data) {
            this.data = data;
        }

        public void Query() {
            data.clear();
            ContentResolver resolver = getActivity().getContentResolver();
            Uri uri = Uri.parse(getResources().getString(R.string.URI_MY_STANDARDCLONE));
            String[] projection = null;
            String selection = Columns.FAMILYCODE + " = ?";
            String[] selectionArgs = {mFamilyCode};
            String sortOrder = Columns.FAMILYCODE + " ASC ";

            Cursor c = resolver.query(uri, projection, selection, selectionArgs, sortOrder);
            while (c.moveToNext()) {
//                data.add(new String[]{c.getString(c.getColumnIndex(Columns.PLANTORDER)), c.getString(c.getColumnIndex(Columns.STANDARDSPECIENAME)), "0"});
            }

            notifyDataSetChanged();
        }

        public void onShowCheckList() {
            if (onDeleteMode) {
                onDeleteMode = false;
            } else {
                onDeleteMode = true;
            }
            Log.d("Debug", "ShowCheckList");
            MyListAdapter.this.notifyDataSetChanged();
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
        public View getView(final int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                v = new ViewHolder();
                convertView = inflater.inflate(R.layout.single_standard_clone_list, null);
                v.clonecode = (TextView) convertView.findViewById(R.id.tvSpecieName);
                v.order = (TextView) convertView.findViewById(R.id.tvSavesStatus);

                v.checkBox = (CheckBox) convertView.findViewById(R.id.checkbox);

                convertView.setTag(v);
            } else {
                v = (ViewHolder) convertView.getTag();
            }
            v.order.setText("ต้นที่ " + data.get(position)[0]);
            v.clonecode.setText(data.get(position)[1]);
            if (onDeleteMode) {
                v.checkBox.setVisibility(View.VISIBLE);
            } else {

                v.checkBox.setVisibility(View.INVISIBLE);
                v.checkBox.setChecked(false);
            }
            v.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        data.get(position)[2] = "1";
                        Log.d("Position", " " + position);
                    } else {
                        data.get(position)[2] = "0";
                        Log.d("Position", " " + position);
                    }
                }
            });
            return convertView;
        }


        class ViewHolder {
            TextView clonecode;
            TextView order;

            CheckBox checkBox;
        }
    }
}
