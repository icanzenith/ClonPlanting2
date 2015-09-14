package sugarcaneselection.thaib.org.clonplanting2.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.List;

import fr.castorflex.android.circularprogressbar.CircularProgressBar;
import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;
import sugarcaneselection.thaib.org.clonplanting2.BaseApplication;
import sugarcaneselection.thaib.org.clonplanting2.R;
import sugarcaneselection.thaib.org.clonplanting2.databases.Columns;
import sugarcaneselection.thaib.org.clonplanting2.item.CloneListItem;
import sugarcaneselection.thaib.org.clonplanting2.item.CloneStatus;
import sugarcaneselection.thaib.org.clonplanting2.item.ParseClass;
import sugarcaneselection.thaib.org.clonplanting2.item.Upload_Status;
import sugarcaneselection.thaib.org.clonplanting2.util.onUpdateData;

/**
 * Created by Jitpakorn on 4/28/15 AD.
 */
public class ReceiveCloneEditDialog extends DialogFragment {

    private BaseApplication b;

    private CheckBox cbDelete;
    private EditText edtReceiveAmount;
    private TextView tvUploadStatus;
    private Button btOK, btCancel;
    private TextView tvCloneCode;

    private CloneListItem item;
    private int operationtype;

    private onUpdateData mUpdate;
    private String SentFrom;

    private View mContainer;
    private CircularProgressBar progressBar;





    public ReceiveCloneEditDialog() {

    }

    public static DialogFragment newInstance() {
        DialogFragment d = new ReceiveCloneEditDialog();
        return d;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mUpdate = (onUpdateData) activity;
        if (mUpdate == null) {
            Toast.makeText(getActivity(), "เกิดข้อผิดพลาด", Toast.LENGTH_LONG).show();
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = (BaseApplication) getActivity().getApplication();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        
        View v = inflater.inflate(R.layout.dialog_edit_receive_clone, container, false);
        mContainer = v.findViewById(R.id.main_container);
        progressBar = (CircularProgressBar) v.findViewById(R.id.progressBar);
        cbDelete = (CheckBox) v.findViewById(R.id.checkBoxDelete);
        tvCloneCode = (TextView) v.findViewById(R.id.tvCloneCode);
        tvUploadStatus = (TextView) v.findViewById(R.id.tvUploadStatus);

        if (item.getUploadStatus() == Upload_Status.UPLOADED) {
            tvUploadStatus.setText("อัพโหลดแล้ว");
        } else {
            tvUploadStatus.setText("ยังไม่ได้อัพโหลด");
        }


        edtReceiveAmount = (EditText) v.findViewById(R.id.edtReceiveAmount);
        edtReceiveAmount.setText(item.getReceiveAmount() + "");
        btOK = (Button) v.findViewById(R.id.btOK);
        btCancel = (Button) v.findViewById(R.id.btCancel);

        tvCloneCode.setText(item.getCloneCode());
        btOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbDelete.isChecked()) {

                    item.setReceiveAmount(Integer.valueOf(edtReceiveAmount.getText().toString()));
                    mContainer.setVisibility(View.INVISIBLE);
                    progressBar.setVisibility(View.VISIBLE);
                    deleteReceiveClone();

                } else {

                    item.setReceiveAmount(Integer.valueOf(edtReceiveAmount.getText().toString()));
                    mContainer.setVisibility(View.INVISIBLE);
                    progressBar.setVisibility(View.VISIBLE);
                    UpdateData(false);

                }

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

    private void deleteOnDatabase(int ID) {
        ContentResolver resolver = getActivity().getContentResolver();
        Uri uri = Uri.parse(getResources().getString(R.string.uri_clone));
        String whereID = "_id = " + ID;
        resolver.delete(uri, whereID, null);
        mUpdate.updateData();
        dismiss();

    }

    private void deleteOnDatabase(String objectID) {
        ContentResolver resolver = getActivity().getContentResolver();
        Uri uri = Uri.parse(getResources().getString(R.string.uri_clone));
        String whereID = Columns.ObjectID + " = ?";
        String[] ObjectID = {objectID};
        resolver.delete(uri, whereID, ObjectID);
        mUpdate.updateData();
        dismiss();
    }


    private void deleteReceiveClone() {

        ContentResolver resolver = getActivity().getContentResolver();

        Uri uri = Uri.parse(getResources().getString(R.string.uri_clone));
        String where;
            String[] selectionArgs;
        if (item.getSentTo()!=null){
            where = Columns.CloneCode + "= ? AND " + Columns.SentTo + " = ?";
            selectionArgs = new String[]{item.getCloneCode(), item.getSentTo()};
        }else{
            where = Columns.CloneCode + "= ?";
            selectionArgs = new String[]{item.getCloneCode()};

        }

        Cursor c = resolver.query(uri, null, where, selectionArgs, null);

        Log.d("Debug Receive Clone","Cursor get Count "+c.getCount()+"");
//        Log.d("Debug Receive Clone","String Selection "+selectionArgs[0]+" "+selectionArgs[1]+"");
        if (c != null) {
            if (c.getCount() != 0) {
                if (c.moveToFirst()){
                    if (c.getString(c.getColumnIndex(Columns.ObjectID)) != null) {
                        //Delete on Server
                        Log.d("Debug Receive Clone","ObjectID != null");

                        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(ParseClass.Clone);
                        query.whereEqualTo("objectId", c.getString(c.getColumnIndex(Columns.ObjectID)));
                        query.findInBackground(new FindCallback<ParseObject>() {
                            @Override
                            public void done(final List<ParseObject> list, ParseException e) {
                                if (e == null) {
                                    // เจอข้อมูล จงมั่นใจว่า ID มัน Unique
                                    if (list.size() != 0) {
                                        final ParseObject object = list.get(0);
                                        object.put(Columns.CloneStatus, CloneStatus.Delete);
                                        object.put(Columns.ReceiveAmount,0);
                                        object.saveInBackground(new SaveCallback() {
                                            @Override
                                            public void done(ParseException e) {
                                                if (e == null) {
                                                    // เซฟได้ Delete บนฐานข้อมูลเราแม่งเลย
                                                    deleteOnDatabase(object.getObjectId());
                                                } else {
                                                    //เสือกเซฟไม่ได้ สราดดด ยอมเถอะ แล้วบอกไว้ เซฟคราวหน้า
                                                    Toast.makeText(getActivity(), "การอัพเดทมีปัญหา", Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        });
                                    }
                                } else {
                                    //Update ไม่สำเร็จ ให้แจ้ง และ Mask ไว้ ว่าจะลบ
                                    Toast.makeText(getActivity(), "เกิดข้อผิดพลาด", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    } else {
                        //ไม่มี Object ID ก็แสดงว่าไม่มีการเปลี่ยนแปลงข้อมูลบน Server เกิด ขึ้น
                        Log.d("Debug Receive Clone","ObjectID == null");
                        deleteOnDatabase(c.getInt(c.getColumnIndex("_id")));
                    }
                }
            } else {
                c.close();
            }
        }
    }

    private void UpdateData(final boolean isDelete) {
        //ถ้า มี Object ID แสดงว่า มีอยู่บน Server แล้ว
        if (isDelete) {
            deleteReceiveClone(isDelete);
        } else {
            if (item.getObjectID() != null) {
                // หมายถึงข้อมูลมีบน Server แล้ว
                // This Clone's Data is already added in to table
                ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(ParseClass.Clone);
                Log.d("Debug ObjectId", item.getObjectID());
                query.whereEqualTo("objectId", item.getObjectID());
                query.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> parseObjects, ParseException e) {
                        if (e == null) {
                            Log.d("Debug E=Null", "Done");
                            if (parseObjects.size() != 0) {
                                Log.d("Debug E!=Null", "SIZE != 0");
                                parseObjects.get(0).put(Columns.ReceiveAmount, item.getReceiveAmount());
                                parseObjects.get(0).saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(ParseException e) {
                                        if (e == null) {
                                            Log.d("Debug E2!=Null","DONE2");
                                            item.setUploadStatus(Upload_Status.UPLOADED);
                                            UpdateToDataBase(isDelete);
                                        } else {
                                            Log.d("Debug E2!=Null", e.getMessage());
                                            item.setUploadStatus(Upload_Status.NOT_UPLOADED);
                                            UpdateToDataBase(isDelete);
                                        }
                                    }
                                });
                            } else {
                                Log.d("Debug E!=Null", "SIZE == 0");
                                item.setUploadStatus(Upload_Status.NOT_UPLOADED);
                                UpdateToDataBase(isDelete);
                            }
                        } else {
                            Log.d("Debug E!=Null", e.getMessage());
                            item.setUploadStatus(Upload_Status.NOT_UPLOADED);
                            UpdateToDataBase(isDelete);
                        }


                    }
                });

            } else {
                // หมายถึงข้อมูลยังไม่มีบน Server
                // This Clone's Data is not add yet
                final ParseObject object = new ParseObject(ParseClass.Clone);
                object.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            item.setObjectID(object.getObjectId());
                            item.setReceiveAmount(object.getInt(Columns.ReceiveAmount));
                            item.setUploadStatus(Upload_Status.UPLOADED);
                            UpdateToDataBase(isDelete);
                        } else {
                            item.setReceiveAmount(object.getInt(Columns.ReceiveAmount));
                            item.setUploadStatus(Upload_Status.NOT_UPLOADED);
                            UpdateToDataBase(isDelete);


                        }

                    }
                });


            }
        }
    }

    private void deleteReceiveClone(final boolean isDelete) {
        if (item.getObjectID() != null) {
            // หมายถึงข้อมูลมีบน Server แล้ว
            // This Clone's Data is already added in to table
            ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(ParseClass.Clone);
            query.whereEqualTo("objectId", item.getObjectID());
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> parseObjects, ParseException e) {
                    if (e == null) {
                        if (parseObjects.size() != 0) {
                            parseObjects.get(0).put(Columns.ReceiveAmount, 0);
                            //TODO Define -1 = Delete remove
                            parseObjects.get(0).put("CloneStatus", -1);
                            parseObjects.get(0).saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if (e == null) {
                                        item.setUploadStatus(Upload_Status.UPLOADED);
                                        UpdateToDataBase(isDelete);
                                    } else {
                                        item.setUploadStatus(Upload_Status.NOT_UPLOADED);
                                        UpdateToDataBase(isDelete);
                                    }
                                }
                            });

                        } else {
                            item.setUploadStatus(Upload_Status.NOT_UPLOADED);
                            UpdateToDataBase(isDelete);
                        }
                    } else {
                        item.setUploadStatus(Upload_Status.NOT_UPLOADED);
                        UpdateToDataBase(isDelete);
                    }


                }
            });

        } else {
            // หมายถึงข้อมูลยังไม่มีบน Server
            // This Clone's Data is not add yet
            final ParseObject object = new ParseObject(ParseClass.Clone);
            object.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {

                        item.setObjectID(object.getObjectId());
                        item.setReceiveAmount(object.getInt(Columns.ReceiveAmount));
                        item.setUploadStatus(Upload_Status.UPLOADED);
                        UpdateToDataBase(isDelete);
                    } else {

                        item.setReceiveAmount(object.getInt(Columns.ReceiveAmount));
                        item.setUploadStatus(Upload_Status.NOT_UPLOADED);
                        UpdateToDataBase(isDelete);

                    }
                }
            });
        }
    }


    private void UpdateToDataBase(boolean isDelete) {
        if (isDelete) {


            ContentResolver resolver = getActivity().getContentResolver();
            Uri uri = Uri.parse(getResources().getString(R.string.uri_clone));

            String where = Columns.CloneCode + "= ? AND " + Columns.SentTo + " = ?";
            String[] selectionArgs = {item.getCloneCode(), item.getSentTo()};
            ContentValues v = new ContentValues();
            v.put(Columns.ObjectID, item.getObjectID());
            v.put(Columns.UploadStatus, item.getUploadStatus());
            resolver.delete(uri, where, selectionArgs);

            Log.d("Debug Value", where);
            Log.d("Debug Value", v.toString());

            mUpdate.updateData();
            progressBar.setVisibility(View.INVISIBLE);
            mContainer.setVisibility(View.VISIBLE);
            ReceiveCloneEditDialog.this.dismiss();

        } else {

            ContentResolver resolver = getActivity().getContentResolver();
            Uri uri = Uri.parse(getResources().getString(R.string.uri_clone));
            String where;
            String[] selectionArgs;
            if (item.getObjectID()!=null){
                where = Columns.ObjectID + "= ?";
                selectionArgs = new String[]{item.getObjectID()};

            }else {
                if (item.getSentTo() == null) {
                    where = Columns.CloneCode + "= ? AND " + Columns.SentTo + " = ?";
                    selectionArgs = new String[]{item.getCloneCode(), item.getSentTo()};
                } else {
                    where = Columns.CloneCode + "= ?";
                    selectionArgs = new String[]{item.getCloneCode()};

                }
            }
            ContentValues v = new ContentValues();
            v.put(Columns.ObjectID, item.getObjectID());
            v.put(Columns.UploadStatus, item.getUploadStatus());
            v.put(Columns.ReceiveAmount,item.getReceiveAmount());
            resolver.update(uri, v, where, selectionArgs);

            Log.d("Debug Value NOT DELETE", where);
            Log.d("Debug Value NOT DELETE", v.toString());

            mUpdate.updateData();
            progressBar.setVisibility(View.INVISIBLE);
            mContainer.setVisibility(View.VISIBLE);
            ReceiveCloneEditDialog.this.dismiss();
        }

    }

    public CloneListItem getItem() {
        return item;
    }

    public void setItem(CloneListItem item) {
        this.item = item;
    }
}
