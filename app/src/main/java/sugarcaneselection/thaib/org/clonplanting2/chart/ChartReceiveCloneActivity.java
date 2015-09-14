package sugarcaneselection.thaib.org.clonplanting2.chart;

import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.text.style.TtsSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Highlight;
import com.github.mikephil.charting.utils.ValueFormatter;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;
import sugarcaneselection.thaib.org.clonplanting2.BaseApplication;
import sugarcaneselection.thaib.org.clonplanting2.R;
import sugarcaneselection.thaib.org.clonplanting2.databases.Columns;
import sugarcaneselection.thaib.org.clonplanting2.item.AppTag;
import sugarcaneselection.thaib.org.clonplanting2.item.ParseClass;

public class ChartReceiveCloneActivity extends ActionBarActivity implements OnChartValueSelectedListener {

    private HorizontalBarChart mTrayChart;
    private HorizontalBarChart mAmountChart;
    private String[] mPlaceTest;
    private SmoothProgressBar progressBar;

    private HashMap<String, Integer> mReceivedTrayData = new HashMap<>();
    private HashMap<String, Integer> mReceivedAmountData = new HashMap<>();
    private HashMap<String, Integer> mReceivedWrongTrayData = new HashMap<>();
    private HashMap<String, Integer> mSentTrayData = new HashMap<>();
    private HashMap<String, Integer> mSentAmountData = new HashMap<>();
    private HashMap<String, Integer> mMissTrayAmountData = new HashMap<>();
    private HashMap<String, Integer> mMissCloneAmountData = new HashMap<>();

    private int mCurrentPosition = 0;
    private TextView tvTitle;
    private TextView tvSubTitle;
    private String FullPlaceName;
    private Typeface tf;
    private String mSentTo;

    private TextView tvTraySum;
    private TextView tvCloneSum;
    private TextView tvTime;

    private Integer mTraySum = 0;
    private Integer mAmountSum = 0;

    private BaseApplication baseApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        baseApplication = (BaseApplication) getApplication();
        setContentView(R.layout.activity_chart_receive_clone);
        tvTraySum = (TextView) findViewById(R.id.tvTraySum);
        tvCloneSum = (TextView) findViewById(R.id.tvAmountSum);
        tvTime = (TextView) findViewById(R.id.tvTime);
        Calendar calendar = new GregorianCalendar();
        Date trialTime = new Date();
        calendar.setTime(trialTime);
        tvTime.setText("อัพเดทวันที่ " + calendar.getTime().toString());
        tf = Typeface.createFromAsset(getAssets(), "RobotoCondensed-Light.ttf");
        mPlaceTest = getResources().getStringArray(R.array.PlaceCode);
        initTrayChart();
        initAmountChart();
        progressBar = (SmoothProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvSubTitle = (TextView) findViewById(R.id.tvSubTitle);
        tvTitle.setTypeface(tf);
        tvSubTitle.setTypeface(tf);
        mSentTo = getIntent().getStringExtra(AppTag.PLACECODE);
//        if (savedInstanceState==null){
        ParseQuery<ParseObject> p = new ParseQuery<ParseObject>(ParseClass.SectorInformation);
        p.whereEqualTo("PlaceCode", mSentTo);
        p.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {
                    ParseObject o = list.get(0);
                    FullPlaceName = o.getString("FullName");
                    tvTitle.setText("สรุปการรับอ้อยของ " + mSentTo);
                    tvSubTitle.setText("(" + FullPlaceName + ")");
                } else {

                }
            }
        });
        mSum(mPlaceTest[0], mSentTo);
//        }else{
//            mReceivedTrayData= (HashMap<String, Integer>) baseApplication.getTempChartMap().get("mReceivedTrayData");
//            mReceivedAmountData= (HashMap<String, Integer>) baseApplication.getTempChartMap().get("mReceivedAmountData");
//            mReceivedWrongTrayData= (HashMap<String, Integer>) baseApplication.getTempChartMap().get("mReceivedWrongTrayData");
//            mSentAmountData= (HashMap<String, Integer>) baseApplication.getTempChartMap().get("mSentAmountData");
//            mMissTrayAmountData= (HashMap<String, Integer>) baseApplication.getTempChartMap().get("mMissTrayAmountData");
//            mMissCloneAmountData= (HashMap<String, Integer>) baseApplication.getTempChartMap().get("mMissCloneAmountData");
//            setDataCloneChart();
//            setDataTrayChart();
//        }


    }

//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        HashMap<String,Object> a = new HashMap<>();
//        a.put("mReceivedTrayData",mReceivedTrayData);
//        a.put("mReceivedAmountData",mReceivedAmountData);
//        a.put("mReceivedWrongTrayData",mReceivedWrongTrayData);
//        a.put("mSentAmountData",mSentAmountData);
//        a.put("mMissTrayAmountData",mMissTrayAmountData);
//        a.put("mMissCloneAmountData",mMissCloneAmountData);
//        baseApplication.setTempChartMap(a);
//    }

    private void initTrayChart() {
        mTrayChart = (HorizontalBarChart) findViewById(R.id.barChartTray);

        mTrayChart.setOnChartValueSelectedListener(this);
        mTrayChart.setDrawBarShadow(false);

        mTrayChart.setDrawValueAboveBar(true);

        mTrayChart.setDescription("");
        mTrayChart.setMaxVisibleValueCount(150);

        mTrayChart.setPinchZoom(false);

        mTrayChart.setDrawGridBackground(false);
        XAxis xl = mTrayChart.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xl.setTypeface(tf);
        xl.setDrawAxisLine(true);
        xl.setDrawGridLines(false);
        xl.setGridLineWidth(0.3f);

        YAxis yl = mTrayChart.getAxisLeft();
//        yl.setTypeface(tf);
        yl.setDrawAxisLine(true);
        yl.setDrawGridLines(false);
        yl.setGridLineWidth(0.3f);
//        yl.setInverted(true);

        YAxis yr = mTrayChart.getAxisRight();
//        yr.setTypeface(tf);
        yr.setDrawAxisLine(true);
        yr.setDrawGridLines(true);
    }

    private void initAmountChart() {
        mAmountChart = (HorizontalBarChart) findViewById(R.id.barChartAmount);

        mAmountChart.setOnChartValueSelectedListener(this);
        mAmountChart.setDrawBarShadow(false);

        mAmountChart.setDrawValueAboveBar(true);

        mAmountChart.setDescription("");
        mAmountChart.setMaxVisibleValueCount(150);

        mAmountChart.setPinchZoom(false);

        mAmountChart.setDrawGridBackground(false);
        XAxis xl = mAmountChart.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xl.setTypeface(tf);
        xl.setDrawAxisLine(true);
        xl.setDrawGridLines(false);
        xl.setGridLineWidth(0.3f);

        YAxis yl = mAmountChart.getAxisLeft();
//        yl.setTypeface(tf);
        yl.setDrawAxisLine(true);
        yl.setDrawGridLines(false);
        yl.setGridLineWidth(0.3f);
//        yl.setInverted(true);

        YAxis yr = mAmountChart.getAxisRight();
//        yr.setTypeface(tf);
        yr.setDrawAxisLine(true);
        yr.setDrawGridLines(true);

    }

    private void mSum(final String sentFrom, final String sentTo) {
        ParseQuery<ParseObject> p = new ParseQuery<ParseObject>(ParseClass.Clone);
        p.whereEqualTo(Columns.PlaceTest, sentFrom);
        p.whereEqualTo(Columns.SentTo, sentTo);
        p.setLimit(1000);
        p.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                Integer sumSentCloneAmount = 0;
                Integer sumReceiveCloneAmount = 0;
                Integer SentTrayAmount = 0;
                Integer ReceivedTrayAmount = 0;
                Integer wrongTray = 0;
                Integer missTray = 0;
                Integer missClone = 0;

                if (e == null) {
                    //List Size != 0
                    if (list.size() != 0) {
                        for (int i = 0; i < list.size(); i++) {
                            ParseObject object = list.get(i);
                            if (object.getInt(Columns.SentAmount) != 0) {
                                //จำนวนที่ส่งจริง
                                sumSentCloneAmount += object.getInt(Columns.SentAmount);
                                SentTrayAmount++;

                            } else {
                                //รับเกิน รับแต่ไม่ได้ส่ง
                                wrongTray++;
                            }
                            // จำนวนที่ส่งมาให้เป็นถาด
                            mSentTrayData.put(sentFrom, SentTrayAmount);
                            //จำนวนที่ส่งมาให้เป็นต้น
                            mSentAmountData.put(sentFrom, sumSentCloneAmount);
                            //จำนวนรับผิด ไม่ได้ส่งให้แต่รับมา
                            mReceivedWrongTrayData.put(sentFrom, wrongTray);

                            if (object.getInt(Columns.ReceiveAmount) != 0) {
                                //จำนวนที่รับมาจริง
                                sumReceiveCloneAmount += object.getInt(Columns.ReceiveAmount);
                                ReceivedTrayAmount++;

                            } else {
                                //ไม่ได้สแกนรับ ส่งแต่ไม่รับ
                                missTray++;
                            }

                            if (object.getInt(Columns.SentAmount) != 0 && object.getInt(Columns.ReceiveAmount) != 0) {
                                missClone += object.getInt(Columns.SentAmount) - object.getInt(Columns.ReceiveAmount);

                            }


                        }
                        //จำนวนถาดที่รับจริง ทั้งหมด
                        mReceivedTrayData.put(sentFrom, ReceivedTrayAmount);
                        //จำนวนต้นที่รับจริงทั้งหมด
                        mReceivedAmountData.put(sentFrom, sumReceiveCloneAmount);

                        //จำนวนถาดที่ไม่ได้รับ
                        mMissTrayAmountData.put(sentFrom, missTray);
                        //จำนวนโคลนที่หายไประหว่างการส่ง
                        mMissCloneAmountData.put(sentFrom, missClone);

                        Log.d("Debug", "------------------------------------------------------------");
                        Log.d("Debug", "-------------------     Debug    ---------------------");
                        Log.d("Debug", "จำนวนถาดที่ " + sentFrom + " ส่งมาให้ " + SentTrayAmount + " ถาด");
                        Log.d("Debug", "จำนวนโคลนที่ " + sentFrom + " ส่งมาให้ " + sumSentCloneAmount + " ต้น");
                        Log.d("Debug", "จำนวนถาดที่รับผิดจาก " + sentFrom + " ส่งมาให้ " + wrongTray + " ถาด");
                        Log.d("Debug", "จำนวนถาดที่รับจาก " + sentFrom + " จริง " + ReceivedTrayAmount + " ถาด");
                        Log.d("Debug", "จำนวนโคลนที่รับจาก " + sentFrom + " จริง " + sumReceiveCloneAmount + " ต้น");
                        Log.d("Debug", "จำนวนถาด " + sentFrom + " หายไป " + missTray + " ถาด");
                        Log.d("Debug", "จำนวนโคลนที่จาก " + sentFrom + " หายไป " + (sumSentCloneAmount - sumReceiveCloneAmount) + "ต้น");
                        Log.d("Debug", "จำนวนโคลนจาก " + sentFrom + "มาแล้วแต่หายไป " + missClone + " ต้น");
                        Log.d("Debug", "------------------------------------------------------------");
                        Log.d("Debug", "------------------------------------------------------------");

                        mTraySum += ReceivedTrayAmount;
                        mAmountSum += sumReceiveCloneAmount;

                    } else {
                        //size = 0
                    }
                } else {
                    Log.d("Debug", e.getMessage());
                }
                if (mCurrentPosition < mPlaceTest.length) {
                    mSum(mPlaceTest[mCurrentPosition], sentTo);
                    mCurrentPosition++;
                } else {
                    mCurrentPosition = 0;
                    //TODO Set Graph DataHere
                    progressBar.setVisibility(View.GONE);
                    setDataCloneChart();
                    setDataTrayChart();


                    NumberFormat formatter = NumberFormat.getNumberInstance();
                    String AmountString = formatter.format(mAmountSum);
                    String TrayString = formatter.format(mTraySum);
                    tvCloneSum.setText("จำนวนต้นที่รับทั้งหมด " + AmountString + " ต้น");
                    tvTraySum.setText("จำนวนถาดที่รับทั้งหมด " + TrayString + " ถาด");
                }
            }
        });
    }

    private void setDataTrayChart() {

        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < mPlaceTest.length; i++) {
            xVals.add(mPlaceTest[i]);
        }

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

        for (int i = 0; i < mPlaceTest.length; i++) {
            int val;
            if (mSentTrayData.get(mPlaceTest[i]) == null) {
                val = 0;
            } else {
                val = mSentTrayData.get(mPlaceTest[i]);
            }
            yVals1.add(new BarEntry(val, i));
        }

        ArrayList<BarEntry> yVals2 = new ArrayList<>();
        for (int i = 0; i < mPlaceTest.length; i++) {
            int val = 0;
            if (mReceivedTrayData.get(mPlaceTest[i]) == null) {
                val = 0;
            } else {
                val = mReceivedTrayData.get(mPlaceTest[i]);
            }
            yVals2.add(new BarEntry(val, i));
        }

        BarDataSet set1 = new BarDataSet(yVals1, "จำนวนถาดที่ส่งมาจากที่ต่างๆ");
        set1.setBarSpacePercent(35f);
        BarDataSet set2 = new BarDataSet(yVals2, "จำนวนถาดที่รับแล้ว");
        set2.setBarSpacePercent(35f);


        set1.setColor(ColorTemplate.VORDIPLOM_COLORS[1]);
        set2.setColor(ColorTemplate.VORDIPLOM_COLORS[3]);

        ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
        dataSets.add(set2);
        dataSets.add(set1);

        BarData data = new BarData(xVals, dataSets);
        data.setValueTextSize(10f);
//        data.setValueTypeface(tf);
        data.setValueFormatter(new ValueFormatter() {
                                   @Override
                                   public String getFormattedValue(float value) {
                                       String f = (int) value + " ถาด";
                                       return f;
                                   }
                               }
        );
        mTrayChart.setData(data);
        mTrayChart.animateY(2000);
    }

    private void setDataCloneChart() {
        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < mPlaceTest.length; i++) {
            xVals.add(mPlaceTest[i]);
        }

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

        for (int i = 0; i < mPlaceTest.length; i++) {
            int val;
            if (mSentAmountData.get(mPlaceTest[i]) == null) {
                val = 0;
            } else {
                val = mSentAmountData.get(mPlaceTest[i]);
            }
            yVals1.add(new BarEntry(val, i));
        }

        ArrayList<BarEntry> yVals2 = new ArrayList<>();
        for (int i = 0; i < mPlaceTest.length; i++) {
            int val = 0;
            if (mReceivedAmountData.get(mPlaceTest[i]) == null) {
                val = 0;
            } else {
                val = mReceivedAmountData.get(mPlaceTest[i]);
            }
            yVals2.add(new BarEntry(val, i));
        }

        BarDataSet set1 = new BarDataSet(yVals1, "จำนวนต้นที่ส่งมาจากที่ต่างๆ");
        set1.setBarSpacePercent(35f);
        BarDataSet set2 = new BarDataSet(yVals2, "จำนวนต้นที่รับแล้ว");
        set2.setBarSpacePercent(35f);


        set1.setColor(ColorTemplate.VORDIPLOM_COLORS[1]);
        set2.setColor(ColorTemplate.VORDIPLOM_COLORS[3]);

        ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
        dataSets.add(set2);
        dataSets.add(set1);

        BarData data = new BarData(xVals, dataSets);
        data.setValueTextSize(10f);
//        data.setValueTypeface(tf);
        data.setValueFormatter(new ValueFormatter() {
                                   @Override
                                   public String getFormattedValue(float value) {
                                       String f = (int) value + " ต้น";
                                       return f;
                                   }
                               }
        );
        mAmountChart.setData(data);
        mAmountChart.animateY(2000);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_chart_receive_clone, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            SaveToGallery("สรุปการรับของ " + mSentTo + "", 100);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }

    private boolean SaveToGallery(String fileName, int quality) {
        if (quality < 0 || quality > 100)
            quality = 50;

        long currentTime = System.currentTimeMillis();

        File extBaseDir = Environment.getExternalStorageDirectory();
        File file = new File(extBaseDir.getAbsolutePath() + "/DCIM");
        if (!file.exists()) {
            if (!file.mkdirs()) {
                return false;
            }
        }

        String filePath = file.getAbsolutePath() + "/" + fileName + "_" + currentTime + ".png";
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(filePath);

            Bitmap b = getChartBitmap();

            b.compress(Bitmap.CompressFormat.PNG, quality, out); // control
            // the jpeg
            // quality

            out.flush();
            out.close();

        } catch (IOException e) {
            e.printStackTrace();

            return false;
        }

        long size = new File(filePath).length();

        ContentValues values = new ContentValues(8);

        // store the details
        values.put(MediaStore.Images.Media.TITLE, fileName);
        values.put(MediaStore.Images.Media.DISPLAY_NAME, fileName);
        values.put(MediaStore.Images.Media.DATE_ADDED, currentTime);
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/png");
        values.put(MediaStore.Images.Media.DESCRIPTION, "ClonePlanting Save");
        values.put(MediaStore.Images.Media.ORIENTATION, 0);
        values.put(MediaStore.Images.Media.DATA, filePath);
        values.put(MediaStore.Images.Media.SIZE, size);

        Toast.makeText(this,"Export Finished",Toast.LENGTH_LONG).show();


        return this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values) == null
                ? false : true;
    }

    private Bitmap getChartBitmap() {

        ScrollView z = (ScrollView) findViewById(R.id.container);
        int totalHeight = z.getChildAt(0).getHeight();
        int totalWidth = z.getChildAt(0).getWidth();

        // Define a bitmap with the same size as the view
        Bitmap returnedBitmap = Bitmap.createBitmap(totalWidth, totalHeight, Bitmap.Config.RGB_565);
        // Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        // Get the view's background
        Drawable bgDrawable = z.getBackground();
        if (bgDrawable != null)
            // has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        else
            // does not have background drawable, then draw white background on
            // the canvas
            canvas.drawColor(Color.WHITE);
        // draw the view on the canvas
        z.draw(canvas);
        // return the bitmap
        return returnedBitmap;
    }


}
