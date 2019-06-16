package com.eksioglu.faruk.elementler.periodicTable;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.widget.TextView;

import com.eksioglu.faruk.elementler.R;

import static com.eksioglu.faruk.elementler.MainActivity.dataManager;

public class ElementViewOnTable extends ConstraintLayout {

    Context context;

    private int elementNumber;

    private TextView numberText, symbolText, nameText, indexText;


    public ElementViewOnTable(Context context, int elementNumber) {
        super(context);
        this.context = context;
        this.elementNumber = elementNumber;
        init();
        indexText.setVisibility(VISIBLE);
        if (elementNumber != 0){
            setTextViewsText(elementNumber);
        }
    }

    private void init(){
        inflate(context, R.layout.custom_element_view, this);
        numberText = findViewById(R.id.elementView_numberText);
        symbolText = findViewById(R.id.elementView_symbolText);
        nameText = findViewById(R.id.elementView_nameText);
        indexText = findViewById(R.id.elementView_index);
    }

    public void setTextViewsText(int elementNumber){
        setNumber(elementNumber);
        setSymbol(dataManager.getElementsProperty(elementNumber, "symbol"));
        setName(dataManager.getNameOnDeviceLanguage(elementNumber));
        setBackgroundColor(getResources().getColor(dataManager.getElementsBackgrounColor(elementNumber)));
    }

    public int getElementNumber(){
        return elementNumber;
    }

    public void setForEmpty(int index){
        setForEmpty();
        setIndex(index);
    }
    public void setForEmpty(){
        setSymbol("");
        numberText.setText("");
        setName("");
    }

    public void setIndex(int index){
        indexText.setText(""+ index);
    }

    public void setSymbol(String symbol){
        symbolText.setText(symbol);
    }
    public void setNumber(int number){
        numberText.setText(""+ number);
    }
    public void setNumber(String number){
        numberText.setText(number);
    }
    public void setName(String name){
        nameText.setText(name);
    }
}
