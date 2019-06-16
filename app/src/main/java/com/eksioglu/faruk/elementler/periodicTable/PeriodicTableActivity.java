package com.eksioglu.faruk.elementler.periodicTable;

import android.content.Context;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.View;
import android.widget.GridLayout;
import android.widget.LinearLayout;

import com.eksioglu.faruk.elementler.R;
import com.eksioglu.faruk.elementler.elements.propartyDialog.MyCustomDialog;

import java.util.ArrayList;

import static com.eksioglu.faruk.elementler.MainActivity.dataManager;
import static com.eksioglu.faruk.elementler.MainActivity.height;
import static com.eksioglu.faruk.elementler.MainActivity.weight;

public class PeriodicTableActivity extends AppCompatActivity {

    Context context = this;

    ConstraintLayout constraintLayout;
    GridLayout gridLayout, gridLayoutIcGecis;
    LinearLayout groupNumberLayout, periodNumberLayout;

    MyCustomDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_periodic_table);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        gridLayout = findViewById(R.id.periodic_grid);
        gridLayoutIcGecis = findViewById(R.id.periodic_icgecisGrid);
        groupNumberLayout = findViewById(R.id.periodic_groupNumberLayout);
        periodNumberLayout = findViewById(R.id.periodic_periodNumberLayout);
        constraintLayout = findViewById(R.id.periodic_constraintOnScroll);
        dialog = new MyCustomDialog(this);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        weight = size.x;
        height = size.y;

        gridLayout.setColumnCount(18);

        AsyncProcess asyncProcess = new AsyncProcess();
        asyncProcess.execute();

    }

    private void showPropertyDialog(int elementNumber){
        dialog.show();
        dialog.setProperty(elementNumber);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showPropertyDialog(((ElementViewOnTable)v).getElementNumber());
        }
    };

    private class AsyncProcess extends AsyncTask<Void,Void,Void>{

        ArrayList<ElementViewOnTable> elementViewList = new ArrayList<>();
        ArrayList<ElementViewOnTable> icGecisMetalViewList = new ArrayList<>();

        @Override
        protected Void doInBackground(Void... voids) {
            int elementNumber = 1;
            int index = 0;
            while (elementNumber<= dataManager.getElementCount()){
                if ((index>0 && index<17) || (index>19 && index<30) || (index>37 && index<48)) {
                    ElementViewOnTable spaceView = new ElementViewOnTable(context, 0);
                    spaceView.setForEmpty();
                    elementViewList.add(spaceView);
                }else if (index == 92 || index == 125){
                    ElementViewOnTable spaceView = new ElementViewOnTable(context, 0);
                    if (index == 92) spaceView.setBackgroundColor(getResources().getColor(dataManager.getTypeBackgrounColorId("lanthanoids")));
                    else spaceView.setBackgroundColor(getResources().getColor(dataManager.getTypeBackgrounColorId("actinoids")));
                    spaceView.setForEmpty();
                    elementViewList.add(spaceView);
                }else if (elementNumber>=57 && elementNumber<=71){
                    ElementViewOnTable icGecisView = new ElementViewOnTable(context, elementNumber);
                    icGecisMetalViewList.add(icGecisView);
                    elementNumber++;
                }else if (elementNumber >=89  &&  elementNumber <= 103){
                    ElementViewOnTable icGecisView = new ElementViewOnTable(context, elementNumber);
                    icGecisMetalViewList.add(icGecisView);
                    elementNumber++;
                }else {
                    ElementViewOnTable elementView = new ElementViewOnTable(context, elementNumber);
                    elementViewList.add(elementView);
                    elementNumber++;
                }
                index++;
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            for (ElementViewOnTable m:elementViewList){
                gridLayout.addView(m);
                if (m.getElementNumber() != 0)
                    m.setOnClickListener(onClickListener);
            }
            for (ElementViewOnTable m:icGecisMetalViewList){
                gridLayoutIcGecis.addView(m);
                m.setOnClickListener(onClickListener);
            }
            constraintLayout.setVisibility(View.VISIBLE);
        }
    }
}
