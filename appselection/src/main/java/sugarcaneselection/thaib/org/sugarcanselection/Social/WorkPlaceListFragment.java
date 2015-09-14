package sugarcaneselection.thaib.org.sugarcanselection.Social;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import sugarcaneselection.thaib.org.sugarcanselection.R;

/**
 * Created by Jitpakorn on 1/28/15 AD.
 */
public class WorkPlaceListFragment extends Fragment {

    private static final String ARG_PLACE_TEST = "PlaceTest";
    private ListView mListView;
    private MyListAdapter adapter;

    public WorkPlaceListFragment() {
    }

    public static Fragment newInstance() {
        Fragment fragment = new WorkPlaceListFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_network, container, false);
        mListView = (ListView) rootView.findViewById(R.id.mListView);
        adapter = new MyListAdapter();
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), WorkPlaceActivity.class);
                SectionItemData PlaceTest = (SectionItemData) parent.getItemAtPosition(position);
                intent.putExtra(ARG_PLACE_TEST, PlaceTest.getSection());
                startActivity(intent);
            }
        });

        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // ข้อมูลหน่วยงาน
    // Section Information
    class SectionItemData {

        private String WorkPlaceName;
        private String Section;

        public String getWorkPlaceName() {
            return WorkPlaceName;
        }

        public void setWorkPlaceName(String workPlaceName) {
            WorkPlaceName = workPlaceName;
        }

        public String getSection() {
            return Section;
        }

        public void setSection(String section) {
            Section = section;
        }
    }


    class MyListAdapter extends BaseAdapter {

        ArrayList<SectionItemData> sd = new ArrayList<>();
        String[] Code;
        String[] Name;

        MyListAdapter() {
            Code = getActivity().getResources().getStringArray(R.array.nameCode);
            Name = getActivity().getResources().getStringArray(R.array.namePlace);
            for (int i = 0; i < 11; i++) {
                SectionItemData s = new SectionItemData();
                s.setSection(Code[i]);
                s.setWorkPlaceName(Name[i]);
                sd.add(s);
            }
        }

        @Override
        public int getCount() {
            return sd.size();
        }

        @Override
        public Object getItem(int position) {
            return sd.get(position);
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
                convertView = View.inflate(getActivity(), R.layout.single_list_network, null);
                v.Code = (TextView) convertView.findViewById(R.id.tvCode);
                v.Name = (TextView) convertView.findViewById(R.id.tvName);
                convertView.setTag(v);
            } else {
                v = (ViewHolder) convertView.getTag();
            }

            v.Code.setText(sd.get(position).getSection());
            v.Name.setText(sd.get(position).getWorkPlaceName());

            return convertView;
        }

    }

    class ViewHolder {
        TextView Name;
        TextView Code;
    }
}
