package sugarcaneselection.thaib.org.clonplanting2.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

import fr.castorflex.android.circularprogressbar.CircularProgressBar;
import sugarcaneselection.thaib.org.clonplanting2.R;
import sugarcaneselection.thaib.org.clonplanting2.databases.Columns;
import sugarcaneselection.thaib.org.clonplanting2.item.AppTag;
import sugarcaneselection.thaib.org.clonplanting2.item.CloneListItem;
import sugarcaneselection.thaib.org.clonplanting2.item.OperationType;
import sugarcaneselection.thaib.org.clonplanting2.item.Upload_Status;
import sugarcaneselection.thaib.org.clonplanting2.util.EditSentDialogInterface;
import sugarcaneselection.thaib.org.clonplanting2.util.UpdateDataBaseAsyncTask;
import sugarcaneselection.thaib.org.clonplanting2.util.onFinishUpdate;

/**
 * Created by Jitpakorn on 4/24/15 AD.
 */
public class SentCloneEditDialog extends DialogFragment implements onFinishUpdate {

    private String CloneCode;
    private Integer SentAmount;
    private String PlaceCode;
    private Integer UploadStatus;
    private String ObjectID;
    private CheckBox cbDelete;
    private EditText edtSentAmount;
    private TextView tvUploadStatus;
    private Button btOK, btCancel;
    private TextView tvCloneCode;
    private CloneListItem item = new CloneListItem();
    private int operationtype;

    private View mContainer;
    private CircularProgressBar progressBar;



    public String getObjectID() {
        return ObjectID;
    }

    public void setObjectID(String objectID) {
        ObjectID = objectID;
    }

    public SentCloneEditDialog() {
    }

    EditSentDialogInterface myInterface;

    public static DialogFragment newInstance(String clonecode, Integer sentamount, String placecode,Integer uploadstatus,String objectID) {
        DialogFragment d = new SentCloneEditDialog();
        Bundle b = new Bundle();
        b.putString(AppTag.CLONECODE, clonecode);
        b.putInt(AppTag.SENTAMOUNT, sentamount);
        b.putString(AppTag.PLACECODE, placecode);
        b.putInt(AppTag.UPLOADSTATUS, uploadstatus);
        b.putString(Columns.ObjectID,objectID);
        d.setArguments(b);
        return d;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        myInterface = (EditSentDialogInterface) activity;
        if (myInterface == null) {
            Toast.makeText(getActivity(), "เกิดข้อผิดพลาด", Toast.LENGTH_LONG).show();
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void setStyle(int style, int theme) {
        super.setStyle(style, theme);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_edit_sent_clone, container, true);
//        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0x991122));
//        getDialog().getWindow().getAttributes().windowAnimations = R.style.dialog_animation;

        mContainer = v.findViewById(R.id.main_container);
        progressBar = (CircularProgressBar) v.findViewById(R.id.progressBar);

        cbDelete = (CheckBox) v.findViewById(R.id.checkBoxDelete);
        tvCloneCode = (TextView) v.findViewById(R.id.tvCloneCode);
        tvUploadStatus = (TextView) v.findViewById(R.id.tvUploadStatus);

        UploadStatus = getArguments().getInt(AppTag.UPLOADSTATUS,0);
        if (UploadStatus == Upload_Status.UPLOADED){
            tvUploadStatus.setText("อัพโหลดแล้ว");
        }else{
            tvUploadStatus.setText("ยังไม่ได้อัพโหลด");
        }

        edtSentAmount = (EditText) v.findViewById(R.id.edtSentAmount);
        edtSentAmount.setText(SentAmount + "");
        btOK = (Button) v.findViewById(R.id.btOK);
        btCancel = (Button) v.findViewById(R.id.btCancel);



        btOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbDelete.isChecked()) {
                    operationtype = OperationType.TYPE_DELETE;
                    item.setCloneCode(CloneCode);
                    item.setSentTo(PlaceCode);
                    item.setObjectID(ObjectID);
                    Log.d("Get ITEM ID", item.getObjectID() + 0);

                    mContainer.setVisibility(View.INVISIBLE);
                    progressBar.setVisibility(View.VISIBLE);

                    UpdateDataBaseAsyncTask a = new UpdateDataBaseAsyncTask(SentCloneEditDialog.this, item, getActivity(),OperationType.TYPE_DELETE);
                    a.execute();
                }else{
                    operationtype = OperationType.TYPE_UPDATE;

                    Integer SentAmount = Integer.valueOf(edtSentAmount.getText().toString());
                    item.setCloneCode(CloneCode);
                    item.setSentTo(PlaceCode);
                    item.setSentAmount(SentAmount);
                    item.setObjectID(ObjectID);
                    mContainer.setVisibility(View.INVISIBLE);
                    progressBar.setVisibility(View.VISIBLE);
                    UpdateDataBaseAsyncTask a = new UpdateDataBaseAsyncTask(SentCloneEditDialog.this, item, getActivity(),OperationType.TYPE_UPDATE);
                    a.execute();

                }
            }
        });

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        tvCloneCode.setText(CloneCode);
        final ColorStateList sentamount_color = edtSentAmount.getTextColors();
        final ColorStateList clonecode_color = tvCloneCode.getTextColors();
        cbDelete.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    edtSentAmount.setEnabled(false);
                    edtSentAmount.setAlpha(0.5f);
                    edtSentAmount.setTextColor(0xFF666666);
                    tvCloneCode.setTextColor(0xFF666666);

                } else {
                    edtSentAmount.setEnabled(true);
                    edtSentAmount.setAlpha(1.0f);
                    edtSentAmount.setTextColor(sentamount_color);
                    tvCloneCode.setTextColor(clonecode_color);
                }
            }
        });
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CloneCode = getArguments().getString(AppTag.CLONECODE);
        SentAmount = getArguments().getInt(AppTag.SENTAMOUNT);
        PlaceCode = getArguments().getString(AppTag.PLACECODE);
        ObjectID = getArguments().getString(Columns.ObjectID);
    }

    @Override
    public void onFinishUpdate() {
        item.setObjectID(ObjectID);
        myInterface.UpdateCloneItem(item,operationtype);
        dismiss();
    }
}
