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
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;
import sugarcaneselection.thaib.org.clonplanting2.R;
import sugarcaneselection.thaib.org.clonplanting2.databases.Columns;
import sugarcaneselection.thaib.org.clonplanting2.item.AppTag;
import sugarcaneselection.thaib.org.clonplanting2.item.ParseClass;


public class ChartSentCloneActivity extends ActionBarActivity implements OnChartValueSelectedListener {
    private SmoothProgressBar progressBar;
    private String placeTest;
    private HashMap<String, Integer> mDataSetTray = new HashMap<>();
    private HashMap<String, Integer> mDataSetReceiveTray = new HashMap<>();

    private HashMap<String, Integer> mDataSetAmount = new HashMap<>();
    private HashMap<String, Integer> mDataSetReceiveAmount = new HashMap<>();

    private HorizontalBarChart mBarChart;
    private HorizontalBarChart mBarChartAmount;
    private String[] sentToList;
    //ตำแหน่งล่าสุดของ sentToList
    private Integer mCurrentPosition = 0;

    private Integer mTraySum = 0;
    private Integer mAmountSum = 0;
    private TextView tvTime;

    private TextView tvTitle;
    private TextView tvSubTitle;
    private TextView tvTraySum;
    private TextView tvAmountSum;
    private String FullPlaceName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        tvTime = (TextView) findViewById(R.id.tvTime);

        Calendar calendar = new GregorianCalendar();
        Date trialTime = new Date();
        calendar.setTime(trialTime);

        tvTime.setText("อัพเดทวันที่ " + calendar.getTime().toString());


        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvSubTitle = (TextView) findViewById(R.id.tvSubTitle);

        Typeface  tf = Typeface.createFromAsset(getAssets(), "RobotoCondensed-Light.ttf");
        tvTitle.setTypeface(tf);
        tvSubTitle.setTypeface(tf);
        tvTraySum = (TextView) findViewById(R.id.tvTraySum);
        tvAmountSum = (TextView) findViewById(R.id.tvAmountSum);

        placeTest = getIntent().getStringExtra(AppTag.PLACECODE);


        ParseQuery<ParseObject> p = new ParseQuery<ParseObject>(ParseClass.SectorInformation);
        p.whereEqualTo("PlaceCode", placeTest);
        p.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {
                    ParseObject o = list.get(0);
                    FullPlaceName = o.getString("FullName");
                    tvTitle.setText("สรุปการส่งอ้อยจาก " + placeTest);
                    tvSubTitle.setText("("+FullPlaceName + ")");
                } else {

                }
            }
        });

        sentToList = getResources().getStringArray(R.array.PlaceCode);
        Arrays.sort(sentToList);
        progressBar = (SmoothProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        //BarChart
        initBarChartTray();
        initBarChartAmount();

        //TODO Change Method Name!!
        SampleLog();

    }

    private void initBarChartAmount() {
        mBarChartAmount = (HorizontalBarChart) findViewById(R.id.barChartAmount);

        mBarChartAmount.setOnChartValueSelectedListener(this);
        mBarChartAmount.setDrawBarShadow(false);

        mBarChartAmount.setDrawValueAboveBar(true);

        mBarChartAmount.setDescription("");
        mBarChartAmount.setMaxVisibleValueCount(150);

        mBarChartAmount.setPinchZoom(false);

        mBarChartAmount.setDrawGridBackground(false);
        XAxis xl = mBarChartAmount.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xl.setTypeface(tf);
        xl.setDrawAxisLine(true);
        xl.setDrawGridLines(false);
        xl.setGridLineWidth(0.3f);

        YAxis yl = mBarChartAmount.getAxisLeft();
//        yl.setTypeface(tf);
        yl.setDrawAxisLine(true);
        yl.setDrawGridLines(false);
        yl.setGridLineWidth(0.3f);
//        yl.setInverted(true);

        YAxis yr = mBarChartAmount.getAxisRight();
//        yr.setTypeface(tf);
        yr.setDrawAxisLine(true);
        yr.setDrawGridLines(true);


    }

    private void initBarChartTray() {
        mBarChart = (HorizontalBarChart) findViewById(R.id.barChartTray);

        mBarChart.setOnChartValueSelectedListener(this);
        mBarChart.setDrawBarShadow(false);

        mBarChart.setDrawValueAboveBar(true);

        mBarChart.setDescription("");
        mBarChart.setMaxVisibleValueCount(150);

        mBarChart.setPinchZoom(false);

        mBarChart.setDrawGridBackground(false);
        XAxis xl = mBarChart.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xl.setTypeface(tf);
        xl.setDrawAxisLine(true);
        xl.setDrawGridLines(false);
        xl.setGridLineWidth(0.3f);

        YAxis yl = mBarChart.getAxisLeft();
//        yl.setTypeface(tf);
        yl.setDrawAxisLine(true);
        yl.setDrawGridLines(false);
        yl.setGridLineWidth(0.3f);
//        yl.setInverted(true);

        YAxis yr = mBarChart.getAxisRight();
//        yr.setTypeface(tf);
        yr.setDrawAxisLine(true);
        yr.setDrawGridLines(true);


    }

    private void mSum(String sentFrom, final String sentTo) {
        ParseQuery<ParseObject> p = new ParseQuery<ParseObject>(ParseClass.Clone);
        p.whereEqualTo(Columns.PlaceTest, sentFrom);
        p.whereEqualTo(Columns.SentTo, sentTo);
        p.setLimit(1000);
        p.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                Integer sum = 0;
                Integer sumReceive = 0;
                Integer receivedTray = 0;
                if (e == null) {
                    if (list.size() != 0) {
                        Log.d("Debug", "ส่งไปที่ " + sentTo + " จำนวน " + list.size() + " ถาด");
                        for (int i = 0; i < list.size(); i++) {
                            ParseObject p = list.get(i);
                            sum += p.getInt(Columns.SentAmount);
                            if (p.getInt(Columns.ReceiveAmount)!=0) {
                                sumReceive += p.getInt(Columns.ReceiveAmount);
                                receivedTray++;
                            }
                        }
                        mDataSetTray.put(sentTo, list.size());
                        mDataSetAmount.put(sentTo, sum);

                        mDataSetReceiveTray.put(sentTo, receivedTray);
                        mDataSetReceiveAmount.put(sentTo, sumReceive);
                        Log.d("Debug", "ส่งไปที่ " + sentTo + " จำนวน " + sum + " ต้น");
                        mTraySum += list.size();
                        mAmountSum += sum;
                    } else {
                        mDataSetTray.put(sentTo, 0);
                        mDataSetAmount.put(sentTo, sum);
                    }
                } else {
                    Log.d("Debug", e.getMessage().toString());
                }
                mCurrentPosition++;
                if (mCurrentPosition < sentToList.length) {
                    mSum(placeTest, sentToList[mCurrentPosition]);
                } else {
                    Log.d("Debug", "สำเร็จ");
                    progressBar.setVisibility(View.GONE);
                    setDataBarChart();
                    setDataAmountBarChart();
                    mCurrentPosition = 0;
                    NumberFormat formatter = NumberFormat.getNumberInstance();
                    String AmountString = formatter.format(mAmountSum);
                    String TrayString = formatter.format(mTraySum);
                    tvAmountSum.setText("จำนวนต้นทั้งหมด " + AmountString + " ต้น");
                    tvTraySum.setText("จำนวนถาดทั้งหมด " + TrayString + " ถาด");
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_chart, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            SaveToGallery("สรุปการส่งของ " + placeTest + "", 100);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
        if (e == null)
            return;
    }

    @Override
    public void onNothingSelected() {
    }

    private void setDataBarChart() {

        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < sentToList.length; i++) {
            xVals.add(sentToList[i]);
        }

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

        for (int i = 0; i < sentToList.length; i++) {
            int val = mDataSetTray.get(sentToList[i]);
            yVals1.add(new BarEntry(val, i));
        }

        ArrayList<BarEntry> yVals2 = new ArrayList<>();
        for (int i = 0; i < sentToList.length; i++) {
            int val = 0;
            if (mDataSetReceiveTray.get(sentToList[i]) == null) {
                val = 0;
            } else {
                val = mDataSetReceiveTray.get(sentToList[i]);
            }
            yVals2.add(new BarEntry(val, i));
        }

        BarDataSet set1 = new BarDataSet(yVals1, "จำนวนถาดจาก " + placeTest + " ไปที่ต่างๆ");
        set1.setBarSpacePercent(35f);
        BarDataSet set2 = new BarDataSet(yVals2, "จำนวนถาดที่รับแล้ว ");
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
        mBarChart.setData(data);
        mBarChart.animateY(2000);

    }

    private void setDataAmountBarChart() {

        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < sentToList.length; i++) {
            xVals.add(sentToList[i]);
        }

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

        for (int i = 0; i < sentToList.length; i++) {
            int val = mDataSetAmount.get(sentToList[i]);
            yVals1.add(new BarEntry(val, i));
        }
        ArrayList<BarEntry> yVals2= new ArrayList<BarEntry>();

        for (int i = 0; i < sentToList.length; i++) {
            int val;
            if (mDataSetReceiveAmount.get(sentToList[i])!=null){
                val = mDataSetReceiveAmount.get(sentToList[i]);
            }else {
                val=0;
            }
            yVals2.add(new BarEntry(val, i));
        }

        BarDataSet set1 = new BarDataSet(yVals1, "จำนวนต้นจาก " + placeTest + " ไปที่ต่างๆ");
        set1.setBarSpacePercent(35f);

        BarDataSet set2 = new BarDataSet(yVals2, "จำนวนต้นที่รับแล้ว");
        set2.setBarSpacePercent(35f);

        ArrayList<Integer> colors = new ArrayList<Integer>();
//
//        for (int c : ColorTemplate.PASTEL_COLORS)
//            colors.add(c);

        colors.add(ColorTemplate.VORDIPLOM_COLORS[2]);

        set1.setColor(ColorTemplate.PASTEL_COLORS[1]);
        set1.setColor(ColorTemplate.PASTEL_COLORS[2]);
        set1.setHighLightColor(ColorTemplate.VORDIPLOM_COLORS[3]);


        ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
        dataSets.add(set2);
        dataSets.add(set1);


        BarData data = new BarData(xVals, dataSets);
        data.setValueTextSize(10f);
//        data.setValueTypeface(tf);
        data.setValueFormatter(new ValueFormatter() {
                                   @Override
                                   public String getFormattedValue(float value) {
                                       String f = (int) value + "ต้น";
                                       return f;
                                   }
                               }
        );
        mBarChartAmount.setData(data);
        mBarChartAmount.animateY(2000);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    /**
     * Sample Log Query
     */


    private void SampleLog() {

        mSum(placeTest, sentToList[mCurrentPosition]);
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

        String filePath = file.getAbsolutePath() + "/" + fileName+"_"+currentTime+ ".png";
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
        Toast.makeText(this, "Export Finished", Toast.LENGTH_LONG).show();
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
