package com.eksioglu.faruk.elementler.elements.propartyDialog;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ScrollView;
import android.widget.TextView;

import com.eksioglu.faruk.elementler.R;

import static com.eksioglu.faruk.elementler.MainActivity.dataManager;
import static com.eksioglu.faruk.elementler.MainActivity.height;
import static com.eksioglu.faruk.elementler.MainActivity.weight;

public class MyCustomDialog extends Dialog {

    private Activity activity;

    public ConstraintLayout constraintLayout;

    private View dividerOnBottom;

    private ScrollView scrollView    ;
    private TextView   nameText      ;
    private TextView   symbolText    ;
    private TextView   protonText    ;
    private TextView   turText       ;
    private TextView   massText      ;
    private TextView   eDizilimiText ;
    private TextView   fazText       ;
    private TextView   konumText     ;


    public MyCustomDialog(Activity activity) {
        super(activity);
        this.activity = activity;
        System.out.println("dialog constructor");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("dialog onCreate");
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.property_dialog);
        constraintLayout = findViewById(R.id.propertyDialog_constraintLayout);
        dividerOnBottom  = findViewById(R.id.propertyDialog_BottomDivider);
        scrollView    = findViewById(R.id.propertyDialog_scrollView);
        nameText      = findViewById(R.id.property_name);
        symbolText    = findViewById(R.id.property_value_symbol);
        protonText    = findViewById(R.id.property_value_protonsayisi);
        turText       = findViewById(R.id.property_value_tur);
        massText      = findViewById(R.id.property_value_mass);
        eDizilimiText = findViewById(R.id.property_value_eDizilimi);
        fazText       = findViewById(R.id.property_value_Faz);
        konumText     = findViewById(R.id.property_value_konum);

        getWindow().setBackgroundDrawable(new ColorDrawable(0x00000000));
        setViewSize(constraintLayout, weight/6*5, height/3*2);
    }


    public void setProperty(int elementNumber){
        nameText.setText(dataManager.getNameOnDeviceLanguage(elementNumber));
        symbolText.setText(dataManager.getElementsProperty(elementNumber, "symbol"));
        protonText.setText(""+ elementNumber);
        turText.setText(dataManager.getElementsType(elementNumber));
        massText.setText(dataManager.getElementMass(elementNumber));
        eDizilimiText.setText(dataManager.getElementsProperty(elementNumber, "e-dizilimi"));
        fazText.setText(activity.getResources().getString(dataManager.getElementPhase(elementNumber)));
        konumText.setText(dataManager.getElementsProperty(elementNumber, "konum"));
        dividerOnBottom.setBackgroundColor(activity.getResources().getColor(dataManager.getElementsBackgrounColor(elementNumber)));
    }


    private void setViewSize(View view, int x, int y){
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (x > 0) params.width = x;
        if (y > 0) params.height = y;
    }
}
