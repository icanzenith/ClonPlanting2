package sugarcaneselection.thaib.org.clonplanting2.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.List;

import fr.castorflex.android.circularprogressbar.CircularProgressBar;
import sugarcaneselection.thaib.org.clonplanting2.R;
import sugarcaneselection.thaib.org.clonplanting2.databases.Columns;
import sugarcaneselection.thaib.org.clonplanting2.item.CloneListItem;
import sugarcaneselection.thaib.org.clonplanting2.item.ParseClass;
import sugarcaneselection.thaib.org.clonplanting2.item.Upload_Status;
import sugarcaneselection.thaib.org.clonplanting2.util.onFinishUpdate;

/**
 * Created by Jitpakorn on 5/11/15 AD.
 */
public class CountClonePlantedDialog extends DialogFragment{

    private CloneListItem item;
    private EditText edtAmount;
    private Button btOK;
    private Button btCancel;
    private Integer mAmount;
    private CircularProgressBar circularProgressBar;
    private onFinishUpdate onFinish;
    private LinearLayout mContainer;

    public CloneListItem getItem() {
        return item;
    }

    public void setItem(CloneListItem item) {
        this.item = item;
    }

    public CountClonePlantedDialog() {

    }

    public static DialogFragment newInstance() {
        DialogFragment d = new CountClonePlantedDialog();
        return d;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        onFinish = (onFinishUpdate) activity;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = View.inflate(getActivity(), R.layout.dialog_count_clone_planted,container);

        circularProgressBar = (CircularProgressBar) v.findViewById(R.id.progressBar);
        mContainer= (LinearLayout) v.findViewById(R.id.container);
        edtAmount = (EditText) v.findViewById(R.id.edtAmount);
        btOK = (Button) v.findViewById(R.id.btOK);
        btCancel = (Button) v.findViewById(R.id.btCancel);

        btOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                circularProgressBar.setVisibility(View.VISIBLE);
                mContainer.setVisibility(View.INVISIBLE);
                mAmount=Integer.valueOf(edtAmount.getText().toString());
                //TODO Update to Server;
                ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(ParseClass.Planting);
                query.whereEqualTo("objectId",item.getObjectID());
                query.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> list, ParseException e) {
                        if (e==null){
                            ParseObject o = list.get(0);
                            o.put(Columns.PlantedAmount,mAmount);
                            o.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if (e==null){
                                        onSaveToDatabase(Upload_Status.UPLOADED);
                                    }else{
                                        onSaveToDatabase(Upload_Status.NOT_UPLOADED);
                                    }
                                }
                            });
                        }else{
                            onSaveToDatabase(Upload_Status.NOT_UPLOADED);
                        }
                    }
                });


            }
        });

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return v;
    }

    private void onSaveToDatabase(int uploaded) {
        ContentResolver c = getActivity().getContentResolver();
        Uri uri = Uri.parse(getResources().getString(R.string.uri_planting_clone));
        ContentValues v= new ContentValues();
        v.put(Columns.UploadStatus,uploaded);
        v.put(Columns.PlantedAmount,mAmount);
        String where = Columns.ObjectID+" = ?";
        String[] args = {item.getObjectID()};
        int update = c.update(uri,v,where,args);
        if (update!=0){
            Toast.makeText(getActivity(),"อัพเดทสำเร็จ",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getActivity(),"อัพเดทล้มเหลว",Toast.LENGTH_LONG).show();
        }

        onFinish.onFinishUpdate();
        circularProgressBar.setVisibility(View.INVISIBLE);
        mContainer.setVisibility(View.VISIBLE);
        dismiss();
    }


}
