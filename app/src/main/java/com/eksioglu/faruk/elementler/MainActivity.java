package com.eksioglu.faruk.elementler;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.eksioglu.faruk.elementler.dataProcess.DataDownloadOrRead;
import com.eksioglu.faruk.elementler.dataProcess.DataManager;
import com.eksioglu.faruk.elementler.elements.CustomElementRow;
import com.eksioglu.faruk.elementler.elements.FilterButton;
import com.eksioglu.faruk.elementler.elements.propartyDialog.MyCustomDialog;
import com.eksioglu.faruk.elementler.periodicTable.PeriodicTableActivity;

public class MainActivity extends AppCompatActivity {

    Context context = this;


    ScrollView scrollViewElements;
    HorizontalScrollView scrollViewFilterButtons;
    LinearLayout linearLayout;

    public static boolean dataIsNull = true;

    ImageView tableButton, settingsButton;

    FilterButton allB, metalsB, gecismetalB, ametalsB, yarimetalsB, soygazB, lantinitB, aktinitB;

    MyCustomDialog dialog;

    private boolean isOnCreate;

    public static int weight;
    public static int height;

    public static DataDownloadOrRead dataDownloadOrRead;
    public static DataManager dataManager;
    public static boolean isDownloaded;
    public static boolean isConnected;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tableButton = findViewById(R.id.mainActivty_table_button);
        settingsButton = findViewById(R.id.mainActivity_settingsButton);
        TextView titleText = findViewById(R.id.mainActivity_titleText);
        titleText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, SettingsActivity.class));
            }
        });
        tableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, PeriodicTableActivity.class));
            }
        });
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, SettingsActivity.class));
            }
        });
        tableButton.setClickable(false);
        linearLayout = findViewById(R.id.main_linearLayout);
        scrollViewElements = findViewById(R.id.main_scrollView);
        scrollViewFilterButtons = findViewById(R.id.filter);
        isOnCreate = true;

        dataDownloadOrRead = new DataDownloadOrRead(context);
        dataManager = new DataManager(dataDownloadOrRead.getData(), context);
        if (!dataDownloadOrRead.getData().equalsIgnoreCase("null")){
            loadPageElements();
        }

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        weight = size.x;
        height = size.y;

        initButtons();

        if (dataDownloadOrRead.getData() != null){
            System.out.println("data is not really null: "+ dataDownloadOrRead.getData());
        }

        if (dataIsNull) {
            AsyncClassForStartActivty asyncClass = new AsyncClassForStartActivty();
            asyncClass.execute();
        }else {
            tableButton.setClickable(true);
            loadPageElements();
        }

        dialog = new MyCustomDialog(this);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (isOnCreate) {
            Handler handler = new Handler();
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    setScrollAnim();
                }
            };
            handler.postDelayed(runnable, 0);
            isOnCreate = false;
        }
    }

    private void setScrollAnim(){
        scrollViewFilterButtons.scrollTo(aktinitB.getRight(), 0);
        scroolTo(0, 0);
    }

    public void loadPageElements(){
        for (int elementNumber = 1; elementNumber <= MainActivity.dataManager.getElementCount(); elementNumber++){
            CustomElementRow elementRow = new CustomElementRow(context, elementNumber);
            elementRow.setOnClickListener(onElementClickListener);
            linearLayout.addView(elementRow);
        }
        View view = new View(context);
        linearLayout.addView(view);
        setViewSize(view, 0, (int)convertDpToPixel(98, context));
    }
    public void clearPageElements(){
        linearLayout.removeAllViews();
    }

    private void loadPageWithFilterElements(String... filters){
        for (int elementNumber = 1; elementNumber <= MainActivity.dataManager.getElementCount(); elementNumber++){
            for (String filter : filters) {
                if (dataManager.getElementsProperty(elementNumber, "tur").equalsIgnoreCase(filter)){
                    CustomElementRow elementRow = new CustomElementRow(context, elementNumber);
                    elementRow.setOnClickListener(onElementClickListener);
                    linearLayout.addView(elementRow);
                    break;
                }
            }
        }
        View view = new View(context);
        linearLayout.addView(view);
        setViewSize(view, 0, (int)convertDpToPixel(98, context));
    }
    public void filterButtonClick(View view){
        int id = view.getId();

        uncheckAllButtons();
        ((FilterButton)view).setChecked(true);
        clearPageElements();

        if (id == R.id.main_fitter_metals){
            loadPageWithFilterElements("metal");
        }else if (id == R.id.main_fitter_gecismetal){
            loadPageWithFilterElements("gecismetal");
        }else if (id == R.id.main_fitter_ametals){
            loadPageWithFilterElements("ametal", "halojen");
        }else if (id == R.id.main_fitter_yarimetals){
            loadPageWithFilterElements("yarimetal");
        }else if (id == R.id.main_fitter_soygaz){
            loadPageWithFilterElements("soygaz");
        }else if (id == R.id.main_fitter_lantinit){
            loadPageWithFilterElements("lanthanoids");
        }else if (id == R.id.main_fitter_aktinit){
            loadPageWithFilterElements("actinoids");
        }else if (id == R.id.main_allButton){
            loadPageElements();
        }
    }
    private void initButtons(){
        allB        = findViewById(R.id.main_allButton);
        metalsB     = findViewById(R.id.main_fitter_metals);
        gecismetalB = findViewById(R.id.main_fitter_gecismetal);
        ametalsB    = findViewById(R.id.main_fitter_ametals);
        yarimetalsB = findViewById(R.id.main_fitter_yarimetals);
        soygazB     = findViewById(R.id.main_fitter_soygaz);
        lantinitB   = findViewById(R.id.main_fitter_lantinit);
        aktinitB    = findViewById(R.id.main_fitter_aktinit);

        allB.setTur(null);
        metalsB.setTur("metal");
        gecismetalB.setTur("gecismetal");
        ametalsB.setTur("ametal");
        yarimetalsB.setTur("yarimetal");
        soygazB.setTur("soygaz");
        lantinitB.setTur("lanthanoids");
        aktinitB.setTur("actinoids");


        allB.setChecked(true);
        setButtonsClickable(false);
    }
    private void uncheckAllButtons(){
        allB.setChecked(false);
        metalsB.setChecked(false);
        ametalsB.setChecked(false);
        yarimetalsB.setChecked(false);
        soygazB.setChecked(false);
        gecismetalB.setChecked(false);
        lantinitB.setChecked(false);
        aktinitB.setChecked(false);
    }
    private void setButtonsClickable(boolean clickable){
        allB.setClickable(clickable);
        metalsB.setClickable(clickable);
        gecismetalB.setClickable(clickable);
        ametalsB.setClickable(clickable);
        yarimetalsB.setClickable(clickable);
        soygazB.setClickable(clickable);
        lantinitB.setClickable(clickable);
        aktinitB.setClickable(clickable);
    }



    class AsyncClassForStartActivty extends AsyncTask<Void,Void,Void> {


        @Override
        protected Void doInBackground(Void... voids) {
            dataDownloadOrRead.downloadData();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dataManager.setData(dataDownloadOrRead.getData());
            if (isDownloaded){
                clearPageElements();
                loadPageElements();
                dataIsNull = false;
            }else if (dataDownloadOrRead.getData().equalsIgnoreCase("null")){
                Toast.makeText(context, "please connect to internet", Toast.LENGTH_SHORT).show();
                dataIsNull = false;
            }
            if (!dataDownloadOrRead.getData().equalsIgnoreCase("null")){
                tableButton.setClickable(true);
                dataIsNull = false;
            }
            if (!isConnected) Toast.makeText(context, "connected failed", Toast.LENGTH_SHORT).show();
            if (dataDownloadOrRead.getData().equalsIgnoreCase("null")){
                dataIsNull = true;
                clearPageElements();
            }
            setButtonsClickable(true);
        }
    }

    private void scroolTo(int y, int x) {
        System.out.println("position: " + scrollViewFilterButtons.getScrollY());
        ObjectAnimator xTranslate = ObjectAnimator.ofInt(scrollViewFilterButtons, "scrollX", x);
        ObjectAnimator yTranslate = ObjectAnimator.ofInt(scrollViewFilterButtons, "scrollY", y);

        AnimatorSet animators = new AnimatorSet();
        animators.setDuration(650L);
        animators.playTogether(xTranslate, yTranslate);
        animators.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animator arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animator arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationCancel(Animator arg0) {
                // TODO Auto-generated method stub

            }
        });
        animators.start();
    }

    private View.OnClickListener onElementClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showPropertyDialog(((CustomElementRow)v).elementNumber);
        }
    };

    private void showPropertyDialog(int elementNumber){
        dialog.show();
        dialog.setProperty(elementNumber);
    }

    public static float convertDpToPixel(float dp, Context context){
        return dp * ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }
    public void setViewSize(View view, int x, int y){
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (x > 0) params.width = x;
        if (y > 0) params.height = y;
    }
}
