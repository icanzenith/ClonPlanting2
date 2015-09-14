package sugarcaneselection.thaib.org.sugarcanselection.Dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import sugarcaneselection.thaib.org.sugarcanselection.BaseApplication;
import sugarcaneselection.thaib.org.sugarcanselection.Params;
import sugarcaneselection.thaib.org.sugarcanselection.database.Columns;
import sugarcaneselection.thaib.org.sugarcanselection.GsonTransformer;
import sugarcaneselection.thaib.org.sugarcanselection.R;

/**
 * Created by Jitpakorn on 1/15/15 AD.
 */
public class UploadingPicture extends DialogFragment {

    private static final String PIC_UPPER = "upper";
    private static final String PIC_LOWER = "lower";
    private static final String PIC_FULL = "full";

    BaseApplication baseApplication;
    TextView Clonename;
    ImageView imagelower;
    ImageView imagefull;
    ImageView imageupper;
    ProgressBar progressBar;
    AQuery aq;
    GsonTransformer gsonTransformer = new GsonTransformer();
    HashMap<String, Object> param = new HashMap<String, Object>();
    String url;
    int current = 0;

    public UploadingPicture() {
    }

    public UploadingPicture newInstance(Context context) {
        UploadingPicture d = new UploadingPicture();
        Bundle bundle = new Bundle();
        d.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
        d.setArguments(bundle);
        return d;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.dialog_pic_upload, null);
        Clonename = (TextView) v.findViewById(R.id.clone_name);

        progressBar = (ProgressBar) v.findViewById(R.id.progressBar);

        imagefull = (ImageView) v.findViewById(R.id.image_full);
        imagelower = (ImageView) v.findViewById(R.id.image_lower);
        imageupper = (ImageView) v.findViewById(R.id.image_upper);

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        new Thread(new Runnable() {
            @Override
            public void run() {

                baseApplication = (BaseApplication) getActivity().getApplicationContext();
                aq = new AQuery(getActivity().getApplicationContext());
                url = getResources().getString(R.string.url_uploadimage);
                String uri = getResources().getString(R.string.URI_MYCLONE);
                String[] projection = null;
                String selection = Columns.SAVEDSTATUS+" = 1";
                String[] selectionArgs = null;
                String sortOrder = null;
                Cursor c = getActivity().getContentResolver().query(Uri.parse(uri), projection, selection, selectionArgs, sortOrder);
                progressBar.setMax(c.getCount());
                Log.d("Cursor Count",c.getCount()+"");
                if (c.getCount() != 0) {
                    while (c.moveToNext()) {
                        final String CloneCode = c.getString(1);
                        ArrayList<HashMap<String, Object>> arrayList = new ArrayList<HashMap<String, Object>>();
                        if (c.getInt(c.getColumnIndex(Columns.UPLOADPICFULLSTATUS)) == 1 || c.getInt(c.getColumnIndex(Columns.UPLOADPICLOWSTATUS)) == 1
                                || c.getInt(c.getColumnIndex(Columns.UPLOADPICUPSTATUS)) == 1) {

                            if (c.getInt(c.getColumnIndex(Columns.UPLOADPICUPSTATUS)) == 1) {
                                if (c.getString(c.getColumnIndex(Columns.UPPERPICURL)) != null) {
                                    if (!c.getString(c.getColumnIndex(Columns.UPPERPICURL)).contains("http")) {
                                        HashMap<String, Object> ob = new HashMap<String, Object>();
                                        ob.put(Params.CLONECODE, CloneCode);
                                        ob.put(Params.PLACE_TEST, baseApplication.getUserdata().getSector());
                                        ob.put(Params.USERID, baseApplication.getUserdata().getUserID());
                                        ob.put(Params.UPLOAD, new File(c.getString(c.getColumnIndex(Columns.UPPERPICURL))));
                                        ob.put(Params.PicType, "Upper");
                                        arrayList.add(ob);
                                    }
                                }
                            }
                            if (c.getInt(c.getColumnIndex(Columns.UPLOADPICLOWSTATUS)) == 1) {
                                if (c.getString(c.getColumnIndex(Columns.LOWERPICURL)) != null) {
                                    if (!c.getString(c.getColumnIndex(Columns.LOWERPICURL)).contains("http")) {
                                        HashMap<String, Object> ob = new HashMap<String, Object>();
                                        ob.put(Params.CLONECODE, CloneCode);
                                        ob.put(Params.PLACE_TEST, baseApplication.getUserdata().getSector());
                                        ob.put(Params.USERID, baseApplication.getUserdata().getUserID());
                                        ob.put(Params.UPLOAD, new File(c.getString(c.getColumnIndex(Columns.LOWERPICURL))));
                                        ob.put(Params.PicType, "Lower");
                                        arrayList.add(ob);
                                    }
                                }

                            }
                            if (c.getInt(c.getColumnIndex(Columns.UPLOADPICFULLSTATUS )) == 1) {

                                if (c.getString(c.getColumnIndex(Columns.FULLPICURL)) != null) {
                                    if (!c.getString(c.getColumnIndex(Columns.FULLPICURL)).contains("http")) {
                                        HashMap<String, Object> ob = new HashMap<String, Object>();
                                        ob.put(Params.CLONECODE, CloneCode);
                                        ob.put(Params.PLACE_TEST, baseApplication.getUserdata().getSector());
                                        ob.put(Params.USERID, baseApplication.getUserdata().getUserID());
                                        ob.put(Params.UPLOAD, new File(c.getString(c.getColumnIndex(Columns.FULLPICURL))));
                                        ob.put(Params.PicType, "Full");
                                        arrayList.add(ob);
                                    }
                                }
                            }
                            for (HashMap<String, Object> ob : arrayList) {
                                Log.d("Object Debug", ob.toString());
                                aq.sync(getCallback(CloneCode, ob.get(Params.UPLOAD), ob.get(Params.PicType)).type(JSONObject.class).transformer(gsonTransformer).url(url).params(ob));
                            }
                        } else {
                            progressBar.setProgress(current);
                            if (progressBar.getMax() == current) {
                                Clonename.setText("Finish");
                            }
                        }
                        current++;


                    }
                }


            }
        }).start();

    }

    public AjaxCallback<JSONObject> getCallback(final String CloneCode, final Object file, final Object Type) {
        AjaxCallback<JSONObject> callback = new AjaxCallback<JSONObject>() {


            @Override
            public void run() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        getActivity().runOnUiThread(new Runnable() {
                            public void run() {
                                Clonename.setText("Uploading " + CloneCode);
                                if (Type == "Upper") {
                                    Picasso.with(getActivity()).load((File) file).fit().centerCrop().into(imageupper);
                                } else if (Type == "Lower") {
                                    Picasso.with(getActivity()).load((File) file).fit().centerCrop().into(imagelower);
                                } else if (Type == "Full") {
                                    Picasso.with(getActivity()).load((File) file).fit().centerCrop().into(imagefull);
                                }
                            }
                        });
                    }
                }).start();
                super.run();

            }

            @Override
            public void callback(String url, JSONObject object, AjaxStatus status) {
                super.callback(url, object, status);
                Log.d("object url", url);
                Log.d("object network status", status.getMessage());
                if (object != null) {


                        Log.d("object", object.toString());



                }


                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        getActivity().runOnUiThread(new Runnable() {
                            public void run() {
                                progressBar.setProgress(current);
                                if (progressBar.getMax() == current) {
                                    Clonename.setText("Finish");
                                }

                            }
                        });
                    }
                }).start();


            }

        };
        return callback;
    }


}
