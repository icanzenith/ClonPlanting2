package sugarcaneselection.thaib.org.sugarcanselection.Dialog;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
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

import sugarcaneselection.thaib.org.sugarcanselection.correctdata.FragmentMainCorrectData2;
import sugarcaneselection.thaib.org.sugarcanselection.Item.DataGroupItem;
import sugarcaneselection.thaib.org.sugarcanselection.Item.DiseaseListData;
import sugarcaneselection.thaib.org.sugarcanselection.Interface.OnClickItemListDialogListener;
import sugarcaneselection.thaib.org.sugarcanselection.MainActivity;
import sugarcaneselection.thaib.org.sugarcanselection.R;
import sugarcaneselection.thaib.org.sugarcanselection.correctdata.FragmentDiseaseCorrect2;

/**
 * Created by Jitpakorn on 2/13/15 AD.
 */
public class DialogDiseaseList extends DialogFragment {

    OnClickItemListDialogListener mlistener;
    ArrayList<DataGroupItem> mList;

    ListView mListView;

    public DialogDiseaseList() {

    }
     private Fragment fragment;

    public Fragment getD() {
        return fragment;
    }

    public void setD(Fragment d) {
        this.fragment = d;
    }

    public static DialogFragment newInstance(Fragment d){
        DialogDiseaseList dialogFragment  = new DialogDiseaseList();
        dialogFragment.setD(d);
        return dialogFragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
//        Log.d("Debug Null","OnAttach");
//        MainActivity main = (MainActivity) activity;
//        FragmentMainCorrectData2 s = (FragmentMainCorrectData2) main.getSupportFragmentManager().findFragmentByTag("Correct2");
//
//        FragmentDiseaseCorrect2 diseaseCorrect2 = (FragmentDiseaseCorrect2) s.getVectorFragment().get(3);
//        mlistener = (OnClickItemListDialogListener) d;
//        if (mlistener == null){
//            Toast.makeText(getActivity(),"MList = null",Toast.LENGTH_LONG).show();
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().setTitle("โปรดระบุโรค");
        mlistener = (OnClickItemListDialogListener) fragment;
        DiseaseListData d = new DiseaseListData();
        mList = d.getDiseaseList();

        View rootView = inflater.inflate(R.layout.disease_list_dialog, container, false);
        mListView = (ListView) rootView.findViewById(R.id.listView);
        myListAdapter adapter = new myListAdapter(getActivity());
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        DataGroupItem d = (DataGroupItem) parent.getItemAtPosition(position);
                        mlistener.onClickDialog(d);
                        dismiss();
                    }
                });

        return rootView;
    }

    @Override
    public void onDetach() {
        Log.d("Debug Null", "OnDetach");
        super.onDetach();
    }

    class myListAdapter extends BaseAdapter {


        private LayoutInflater inflater;


        myListAdapter(Context _context) {
            this.inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder v;
            if (convertView == null) {
                v = new ViewHolder();
                convertView = inflater.inflate(R.layout.disease_single_list, null);
                v.Name = (TextView) convertView.findViewById(R.id.tvName);
                convertView.setTag(v);
            } else {
                v = (ViewHolder) convertView.getTag();
            }
            v.Name.setText(mList.get(position).getGroupTitle());

            return convertView;
        }

    }

    class ViewHolder {
        TextView Name;
    }
}
