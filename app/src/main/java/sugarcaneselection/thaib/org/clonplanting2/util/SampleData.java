package sugarcaneselection.thaib.org.clonplanting2.util;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;

import sugarcaneselection.thaib.org.clonplanting2.R;
import sugarcaneselection.thaib.org.clonplanting2.databases.Columns;

/**
 * Created by Jitpakorn on 4/25/15 AD.
 */
public class SampleData {

    private Context context;

    public SampleData(Context context) {
        this.context = context;
    }

    private String[] place = { "A", "B","C","D","E","G","H","I","J","K","L"};

    public void CreateSampleData(){
        ContentResolver c = context.getContentResolver();
        Uri uri = Uri.parse(context.getResources().getString(R.string.uri_clonetosent));

        for (String s : place){
            for (int i = 0 ;i < 20 ;i++){
                ContentValues v = new ContentValues();
                v.put(Columns.Sector,s);
                v.put(Columns.SentAmount,20);
                v.put(Columns.CloneCode,"A"+s+"14173-0"+i);
                v.put(Columns.UploadStatus,0);
                c.insert(uri,v);
            }
        }

    }
}
