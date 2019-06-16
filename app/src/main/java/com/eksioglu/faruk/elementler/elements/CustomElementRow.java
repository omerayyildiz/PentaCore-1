package com.eksioglu.faruk.elementler.elements;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.widget.ImageView;
import android.widget.TextView;

import com.eksioglu.faruk.elementler.MainActivity;
import com.eksioglu.faruk.elementler.R;
import com.eksioglu.faruk.elementler.dataProcess.DataManager;

import java.util.Locale;

import static com.eksioglu.faruk.elementler.MainActivity.dataManager;

public class CustomElementRow extends CardView {

    Context context;
    public int elementNumber;

    TextView numberText, symbolText, nameText;
    ImageView lewisImage;
    ConstraintLayout layout;

    public CustomElementRow(@NonNull Context context, int elementNumber) {
        super(context);
        this.context = context;
        this.elementNumber = elementNumber;
        init();
        setBackgroundColor(getResources().getColor(R.color.dark_background));
        layout.setBackgroundColor(getResources().getColor(dataManager.getElementsBackgrounColor(elementNumber)));

        DataManager dm = MainActivity.dataManager;
        String language = Locale.getDefault().getDisplayLanguage();

        numberText.setText(dm.getElementsProperty(elementNumber, "number"));
        symbolText.setText(dm.getElementsProperty(elementNumber, "symbol"));
        if (language.equalsIgnoreCase("türkçe"))
            nameText.setText(dm.getElementsProperty(elementNumber, "turkishName"));
        else
            nameText.setText(dm.getElementsProperty(elementNumber, "name"));
    }


    private void init(){
        inflate(context, R.layout.element_row, this);
        layout = findViewById(R.id.itemRow_layout);
        numberText = findViewById(R.id.item_numberText);
        symbolText = findViewById(R.id.item_symbolTexxt);
        nameText = findViewById(R.id.item_nameText);
        lewisImage = findViewById(R.id.item_lewisImage);
    }

}
