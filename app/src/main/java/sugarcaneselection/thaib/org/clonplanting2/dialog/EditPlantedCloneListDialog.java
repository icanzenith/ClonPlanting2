package sugarcaneselection.thaib.org.clonplanting2.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.zxing.qrcode.decoder.Mode;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import fr.castorflex.android.circularprogressbar.CircularProgressBar;
import sugarcaneselection.thaib.org.clonplanting2.R;
import sugarcaneselection.thaib.org.clonplanting2.databases.Columns;
import sugarcaneselection.thaib.org.clonplanting2.item.AppTag;
import sugarcaneselection.thaib.org.clonplanting2.item.CloneListItem;
import sugarcaneselection.thaib.org.clonplanting2.item.ParseClass;
import sugarcaneselection.thaib.org.clonplanting2.item.Upload_Status;
import sugarcaneselection.thaib.org.clonplanting2.util.UpdateDataBaseAsyncTask;
import sugarcaneselection.thaib.org.clonplanting2.util.onFinishClonePlanting;
import sugarcaneselection.thaib.org.clonplanting2.util.onFinishUpdate;

/**
 * Created by Jitpakorn on 5/9/15 AD.
 */
public class EditPlantedCloneListDialog extends DialogFragment {

    private ListView mListView;
    private ArrayAdapter<String> mAdapter;
    private CircularProgressBar circularProgressBar;
    private CloneListItem cloneListItem;
    private onFinishClonePlanting finish;


    public EditPlantedCloneListDialog() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        finish = (onFinishClonePlanting) activity;
    }

    public static DialogFragment newInstance() {
        DialogFragment d = new EditPlantedCloneListDialog();

        return d;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_edit_planted_clone, container, false);
        getDialog().getWindow().getAttributes().windowAnimations = R.style.mydialog;
        circularProgressBar = (CircularProgressBar) v.findViewById(R.id.progressBar);
        mListView = (ListView) v.findViewById(R.id.listView);
        mAdapter = new ArrayAdapter<String>(getActivity(), R.layout.single_edit_planted_list, R.id.tvMenu, new String[]{"ลบ","อัพโหลด","ยกเลิก"});
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 5:
                        break;
                    case 4:
                        break;
                    case 3:

                        break;
                    case 2:
                        //ยกเลิก
                        dismiss();
                        break;
                    case 1:
                        //อัพโหลด


                        break;
                    case 0:
                        //ลบ
                        mListView.setVisibility(View.INVISIBLE);
                        circularProgressBar.setVisibility(View.VISIBLE);
                        UpdateToDatabase(AppTag.MODE_DELETE);
//                        dismiss();

                        break;
                }
            }
        });
        return v;
    }

    private void UpdateToDatabase(Integer modeDelete) {
        if (modeDelete == AppTag.MODE_DELETE) {
            final ContentResolver resolver = getActivity().getContentResolver();
            final Uri uri = Uri.parse(getResources().getString(R.string.uri_planting_clone));
            final ContentValues v = new ContentValues();
            v.put(Columns.RowNumber, 0);
            v.put(Columns.UploadStatus, Upload_Status.NOT_UPLOADED);
            final String where = Columns.ObjectID + " = ?";
            final String[] args = {cloneListItem.getObjectID()};
            final int update = resolver.update(uri, v, where, args);
            if (update != 0) {
                Toast.makeText(getActivity(), "ลบสำเร็จ", Toast.LENGTH_SHORT).show();
                //Refresh List View
                //
                ParseQuery<ParseObject> query = new ParseQuery<>(ParseClass.Planting);
                query.whereEqualTo("objectId", cloneListItem.getObjectID());
                query.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> list, ParseException e) {
                        if (e == null) {
                            ParseObject o = list.get(0);
                            o.remove(Columns.RowNumber);
                            o.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if (e == null) {
                                        ContentValues v2 = new ContentValues();
                                        v2.put(Columns.UploadStatus, Upload_Status.UPLOADED);
                                        resolver.update(uri, v2, where, args);
                                    } else {
                                        Toast.makeText(getActivity(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                                    }
                                    finish.onFinishUpdate(true);
                                    dismiss();
                                }
                            });

                        } else {
                            Toast.makeText(getActivity(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }

        }
    }


    public void setCloneListItem(CloneListItem cloneListItem) {
        this.cloneListItem = cloneListItem;
    }
}
