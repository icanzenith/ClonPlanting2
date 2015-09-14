package sugarcaneselection.thaib.org.sugarcanselection;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import sugarcaneselection.thaib.org.sugarcanselection.Item.CloneDataItem;
import sugarcaneselection.thaib.org.sugarcanselection.Item.SelectStatus;
import sugarcaneselection.thaib.org.sugarcanselection.database.Columns;
import sugarcaneselection.thaib.org.sugarcanselection.review.ImageReviewActivity;


public class FragmentPictureManager extends Fragment {
    private static final int willChange = 3303;
    private static final int isUpperImage = 100;
    private static final int isLowerImage = 101;
    private static final int isFullImage = 102;

    private static final String IMAGEVIEW_VISIBILITY_STORAGE_KEY = "imageviewvisibility";
    private static final String BITMAP_STORAGE_KEY = "viewbitmap";
    private static final String JPEG_FILE_PREFIX = "IMG_";
    private static final String JPEG_FILE_SUFFIX = ".jpg";

    public static final int PIC_STANDARD=101;
    public static final int PIC_CLONE = 102;

    //ถ่ายรูปอะไร พันธุ์เช็ค หรือ โคลนที่ปลูก
    private static final String WhichClone = "WhichClone";
    private static final String RowNumber = "RowNumber";
    private int WhatPic;
    private int StandardRowNumber;


    private static final String ARG_PARAM2 = "param2";
    private static final String PART_FULL = "full";
    private static final String PART_UPPER = "upper";
    private static final String PART_LOWER = "lower";
    BaseApplication b;
    private MainActivity mainActivity;
    private CloneDataItem cloneDataItem;
    private ImageView ImageUpper;
    private ImageView ImageLower;
    private ImageView ImageFULL;
    private String CloneCode;
    private Bitmap mImageBitmap;
    private int whichpic = 0;
    private String UpperImagePath;
    private String LowerImagePath;
    private String FullImagePath;
    private String mCurrentPhotoPath;
    private AlbumStorageDirFactory mAlbumStorageDirFactory = null;
    private OnFragmentInteractionListener mListener;

    public FragmentPictureManager() {
        // Required empty public constructor
    }

    public static FragmentPictureManager newInstance(int whichclone,int Rownumber) {
        FragmentPictureManager fragment = new FragmentPictureManager();
        Bundle args = new Bundle();
        args.putInt(WhichClone, whichclone);
        if (whichclone!=0){
            args.putInt(RowNumber,Rownumber);
        }
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments()!=null){
            WhatPic = getArguments().getInt(WhichClone);
            StandardRowNumber = getArguments().getInt(RowNumber);
        }

        if (WhatPic == PIC_CLONE){
            b = (BaseApplication) getActivity().getApplication();
            mAlbumStorageDirFactory = new BaseAlbumDirFactory();
            cloneDataItem = b.getCloneDataItem();
            CloneCode = b.getCloneDataItem().getClonecode();

        }else{
            b = (BaseApplication) getActivity().getApplication();
            mAlbumStorageDirFactory = new BaseAlbumDirFactory();
            cloneDataItem = b.getCloneDataItem();

        }




    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_save, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.save) {

            if (WhatPic == PIC_STANDARD)
            {
                ContentResolver cr = getActivity().getContentResolver();
                ContentValues v = new ContentValues();
                v.put(Columns.FAMILYCODE,b.getCloneDataItem().getFamilycode());
                v.put(Columns.PLANTORDER,StandardRowNumber);
                v.put(Columns.CHANGESTATUS, 1);
                v.put(Columns.SAVEDSTATUS, 1);
                v.put(Columns.UPLOADPICFULLSTATUS, 1);
                v.put(Columns.UPLOADPICUPSTATUS, 1);
                v.put(Columns.UPLOADPICLOWSTATUS, 1);
                v.put(Columns.UPPERPICURL, b.getCloneDataItem().getPicupper());
                v.put(Columns.LOWERPICURL, b.getCloneDataItem().getPiclower());
                v.put(Columns.FULLPICURL, b.getCloneDataItem().getPicfull());

                Uri uri = Uri.parse(getResources().getString(R.string.URI_MY_STANDARDCLONE));
                String where = Columns.FAMILYCODE+" = ? AND "+Columns.PLANTORDER+" = "+StandardRowNumber;
                String[] selection = {b.getCloneDataItem().getFamilycode()};
                int update = cr.update(uri, v, where, selection);
                Log.d("Debug Update",update+"");
                if (update > 0) {
                    Toast.makeText(getActivity(), "บันทึกข้อมูลเรียบร้อย", Toast.LENGTH_LONG).show();
                }else{
                    cr.insert(uri,v);
                }
                TakePictureActivity takePictureActivity = (TakePictureActivity) getActivity();
                takePictureActivity.onFinishTakingPicture();
            }else if (WhatPic == PIC_CLONE){
                ContentResolver cr = getActivity().getContentResolver();
                ContentValues v = new ContentValues();
                v.put(Columns.SELECT_STATUS,SelectStatus.SAVED);
                v.put(Columns.CHANGESTATUS, 1);
                v.put(Columns.SAVEDSTATUS, SelectStatus.SAVED);
                v.put(Columns.UPLOADPICFULLSTATUS, 1);
                v.put(Columns.UPLOADPICUPSTATUS, 1);
                v.put(Columns.UPLOADPICLOWSTATUS, 1);
                v.put(Columns.UPPERPICURL, b.getCloneDataItem().getPicupper());
                v.put(Columns.LOWERPICURL, b.getCloneDataItem().getPiclower());
                v.put(Columns.FULLPICURL, b.getCloneDataItem().getPicfull());

                Uri uri = Uri.parse(getResources().getString(R.string.URI_MYCLONE));
                String where = Columns.CLONECODE+" = ?";
                String[] selection = {b.getCloneDataItem().getClonecode()};
                int update = cr.update(uri, v, where, selection);
                if (update >= 0) {
                    Toast.makeText(getActivity(), "บันทึกข้อมูลเรียบร้อย", Toast.LENGTH_LONG).show();
                }
                TakePictureActivity takePictureActivity = (TakePictureActivity) getActivity();
                takePictureActivity.onFinishTakingPicture();
            }

        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            mCurrentPhotoPath = savedInstanceState.getString("CurrentPath");
            if (b.getCloneDataItem().getPicupper() != null) {
                Picasso.with(getActivity()).load(new File(b.getCloneDataItem().getPicupper())).fit().centerCrop().into(ImageUpper);
            }
            if (b.getCloneDataItem().getPiclower() != null) {
                Picasso.with(getActivity()).load(new File(b.getCloneDataItem().getPiclower())).fit().centerCrop().into(ImageLower);
            }
            if (b.getCloneDataItem().getPicfull() != null) {
                Picasso.with(getActivity()).load(new File(b.getCloneDataItem().getPiclower())).fit().centerCrop().into(ImageFULL);
            }

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_picture_manager, container, false);
        ImageUpper = (ImageView) rootView.findViewById(R.id.image_upper);
        ImageLower = (ImageView) rootView.findViewById(R.id.image_lower);
        ImageFULL = (ImageView) rootView.findViewById(R.id.image_full);


        Uri uri = null;
        String[] projection = null;
        String sortOrder = null;
        String selection =null;
        String[] selectionArgs =null;
        ContentResolver r = getActivity().getContentResolver();
        Cursor c;

        if (WhatPic== PIC_CLONE){
            Log.d("DeBug Debug","Pic Clone");
            uri = Uri.parse(getResources().getString(R.string.URI_MYCLONE));
            selection = Columns.CLONECODE + " = ?";
            selectionArgs = new String[]{CloneCode};
        }else if (WhatPic == PIC_STANDARD){
            uri = Uri.parse(getResources().getString(R.string.URI_MY_STANDARDCLONE));
            selection = Columns.FAMILYCODE + " = ? AND "+Columns.PLANTORDER+" = "+StandardRowNumber;
            selectionArgs = new String[]{b.getCloneDataItem().getFamilycode()};
            Log.d("DeBug Debug","Pic Standard");
            Log.d("DeBug Debug","Pic Standard Selection : "+selection);
            Log.d("DeBug Debug","Pic Standard SelectionArgs : "+selectionArgs[0]);


        }
        c = r.query(uri, projection, selection, selectionArgs, sortOrder);
        Log.d("DeBug Debug","Cursor Count: "+c.getCount());
        while (c != null && c.moveToNext()) {

            if (c.getString(c.getColumnIndex(Columns.FULLPICURL)) != null) {
                b.getCloneDataItem().setPicfull((c.getString(c.getColumnIndex(Columns.FULLPICURL))));
                Picasso.with(getActivity()).load(new File(b.getCloneDataItem().getPicfull())).fit().centerCrop().into(ImageFULL);
            }

            if (c.getString(c.getColumnIndex(Columns.UPPERPICURL)) != null) {
                b.getCloneDataItem().setPicupper(c.getString(c.getColumnIndex(Columns.UPPERPICURL)));
                Picasso.with(getActivity()).load(new File(b.getCloneDataItem().getPicupper())).fit().centerCrop().into(ImageUpper);
            }

            if (c.getString(c.getColumnIndex(Columns.LOWERPICURL)) != null) {
                b.getCloneDataItem().setPiclower(c.getString(c.getColumnIndex(Columns.LOWERPICURL)));
                Picasso.with(getActivity()).load(new File(b.getCloneDataItem().getPiclower())).fit().centerCrop().into(ImageLower);
            }

        }

        c.close();
        ImageUpper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (b.getCloneDataItem().getPicupper()!=null) {
                    Intent intent = new Intent(getActivity(), ImageReviewActivity.class);
                    intent.putExtra("Path",b.getCloneDataItem().getPicupper().toString());
                    intent.putExtra("ResultCode",isUpperImage);
                    startActivityForResult(intent,willChange);
                }else {
                    whichpic = isUpperImage;
                    dispatchTakePictureIntent(whichpic);
                }
            }
        });

        ImageLower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (b.getCloneDataItem().getPiclower()!=null){
                    Intent intent = new Intent(getActivity(), ImageReviewActivity.class);
                    intent.putExtra("Path",b.getCloneDataItem().getPiclower().toString());
                    intent.putExtra("ResultCode",isLowerImage);
                    startActivityForResult(intent,willChange);
                }else{
                    whichpic = isLowerImage;
                    dispatchTakePictureIntent(whichpic);

                }
            }
        });

        ImageFULL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (b.getCloneDataItem().getPicfull()!=null){
                    Intent intent = new Intent(getActivity(), ImageReviewActivity.class);
                    intent.putExtra("Path",b.getCloneDataItem().getPicfull().toString());
                    intent.putExtra("ResultCode",isFullImage);
                    startActivityForResult(intent,willChange);
                }else{
                    whichpic = isFullImage;
                    dispatchTakePictureIntent(whichpic);
                }
            }
        });
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        Log.d("Debug", "SavedState");
        outState.putParcelable(BITMAP_STORAGE_KEY, mImageBitmap);
        outState.putBoolean(IMAGEVIEW_VISIBILITY_STORAGE_KEY, (mImageBitmap != null));
        outState.putString("CurrentPath", mCurrentPhotoPath);
        outState.putString("UpperPath", UpperImagePath);
        outState.putString("LowerPath", LowerImagePath);
        outState.putString("Fullpath", FullImagePath);

        super.onSaveInstanceState(outState);
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

    private String getAlbumName() {
        return getString(R.string.album_name);
    }

    private File getAlbumDir() {
        File storageDir = null;

        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {

            storageDir = mAlbumStorageDirFactory.getAlbumStorageDir(getAlbumName());

            if (storageDir != null) {
                if (!storageDir.mkdirs()) {
                    if (!storageDir.exists()) {
                        Log.d("CameraSample", "failed to create directory");
                        return null;
                    }
                }
            }

        } else {
            Log.v(getString(R.string.app_name), "External storage is not mounted READ/WRITE.");
        }

        return storageDir;
    }

    private File createImageFile(String whichpart) throws IOException {
        // Create an image file name
        File albumF = getAlbumDir();
        File imageF = File.createTempFile(createClonePictureName(whichpart), JPEG_FILE_SUFFIX, albumF);
        return imageF;
    }

    private File setUpPhotoFile(String whichpart) throws IOException {


        File f = createImageFile(whichpart);

        return f;
    }

    private String createClonePictureName(String whichpart) {
        String timestamp;

        Calendar calendar = Calendar.getInstance();
        TimeZone tz = TimeZone.getDefault();
        calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HHmmSS");
        Date currentTimeZone = (Date) calendar.getTime();

        timestamp = sdf.format(currentTimeZone);
//        Toast.makeText(get, timestamp, Toast.LENGTH_LONG).show();
        String PictureName = timestamp + CloneCode + "_" + whichpart;

        return PictureName;
    }

    private void dispatchTakePictureIntent(int whichpic) {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        File f = null;
        switch (whichpic) {
            case isUpperImage:
                try {
                    f = setUpPhotoFile(PART_UPPER);
                    mCurrentPhotoPath = f.getAbsolutePath();
                    cloneDataItem.setPicupper(mCurrentPhotoPath);
                    Log.d("ImagePahtUpper", mCurrentPhotoPath);
                    Log.d("Debug", "Dispatch");
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                } catch (IOException e) {
                    e.printStackTrace();
                    f = null;
                    mCurrentPhotoPath = null;
                    cloneDataItem.setPicupper(null);
                }
                break;
            case isLowerImage:
                try {
                    f = setUpPhotoFile(PART_LOWER);
                    mCurrentPhotoPath = f.getAbsolutePath();
                    cloneDataItem.setPiclower(mCurrentPhotoPath);
                    Log.d("ImagePahtLower", mCurrentPhotoPath);
                    Log.d("Debug", "Dispatch");
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                } catch (IOException e) {
                    e.printStackTrace();
                    f = null;
                    mCurrentPhotoPath = null;
                    cloneDataItem.setPiclower(null);
                }
                break;
            case isFullImage:
                try {
                    f = setUpPhotoFile(PART_FULL);
                    mCurrentPhotoPath = f.getAbsolutePath();
                    cloneDataItem.setPicfull(mCurrentPhotoPath);
                    Log.d("ImagePathFull", mCurrentPhotoPath);
                    Log.d("Debug", "Dispatch");
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                } catch (IOException e) {
                    e.printStackTrace();
                    f = null;
                    mCurrentPhotoPath = null;
                    cloneDataItem.setPicfull(null);
                }
                break;
        }


        startActivityForResult(takePictureIntent, whichpic);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == willChange) {
            switch (resultCode){

                case isUpperImage:
                    whichpic = isUpperImage;
                    dispatchTakePictureIntent(whichpic);
                    break;
                case isLowerImage:
                    whichpic = isLowerImage;
                    dispatchTakePictureIntent(whichpic);
                    break;
                case isFullImage:
                    whichpic = isFullImage;
                    dispatchTakePictureIntent(whichpic);
                     break;

            }
        }else{

            switch (requestCode) {
                case isUpperImage: {
                    if (resultCode == getActivity().RESULT_OK) {
                        mCurrentPhotoPath = b.getCloneDataItem().getPicupper();
                        galleryAddPic(isUpperImage);
                        Log.d("Result Upper", b.getCloneDataItem().getPicupper());
                        Log.d("Debug", "Result");
                        Picasso.with(getActivity()).load(new File(b.getCloneDataItem().getPicupper())).fit().centerCrop().into(ImageUpper);
                    }
                    break;

                }
                case isLowerImage: {
                    if (resultCode == getActivity().RESULT_OK) {
                        mCurrentPhotoPath = b.getCloneDataItem().getPiclower();
                        galleryAddPic(isLowerImage);
                        Log.d("Result Lower", b.getCloneDataItem().getPiclower());
                        Log.d("Debug", "Result");
                        Picasso.with(getActivity()).load(new File(b.getCloneDataItem().getPiclower())).fit().centerCrop().into(ImageLower);
                    }
                    break;

                }
                case isFullImage: {
                    if (resultCode == getActivity().RESULT_OK) {
                        mCurrentPhotoPath = b.getCloneDataItem().getPicfull();
                        galleryAddPic(isFullImage);
                        Picasso.with(getActivity()).load(new File(b.getCloneDataItem().getPicfull())).fit().centerCrop().into(ImageFULL);
                    }
                    break;

                }
            }
        }
    }

    private void galleryAddPic(int whichpic) {
        Intent mediaScanIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");

        File f = null;
        if (whichpic == isUpperImage) {
            f = new File(b.getCloneDataItem().getPicupper());
        } else if (whichpic == isLowerImage) {
            f = new File(b.getCloneDataItem().getPiclower());

        } else if (whichpic == isFullImage) {
            f = new File(b.getCloneDataItem().getPicfull());
        }

        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        getActivity().sendBroadcast(mediaScanIntent);
    }

    private void showPath() {

        Log.d("Debug", b.getCloneDataItem().toString());
        mainActivity = (MainActivity) getActivity();
        mainActivity.onFinishTakepicture();

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
