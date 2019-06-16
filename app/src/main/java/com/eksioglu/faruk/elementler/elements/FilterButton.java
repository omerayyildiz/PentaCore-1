package com.eksioglu.faruk.elementler.elements;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.Checkable;

import com.eksioglu.faruk.elementler.R;

import static com.eksioglu.faruk.elementler.MainActivity.dataManager;

public class FilterButton extends AppCompatButton implements Checkable {

    private boolean isChecked;

    private String tur;

    public FilterButton(Context context) {
        this(context, null);
    }
    public FilterButton(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.buttonStyle);
    }
    public FilterButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setBackgroundColor(getResources().getColor(R.color.colorAccent));
        setPadding((int) convertDpToPixel(12,context), 0, (int)convertDpToPixel(12, context), 0);
    }

    public String getTur() {
        return tur;
    }
    public void setTur(String tur) {
        this.tur = tur;
        setBackgroundColor(getResources().getColor(dataManager.getTypeBackgrounColorId(tur)));
    }

    @Override
    public void setChecked(boolean checked) {
        if (checked){
            setBackgroundColor(getResources().getColor(R.color.colorAccentSecondary));
        }else {
            setBackgroundColor(getResources().getColor(dataManager.getTypeBackgrounColorId(tur)));
        }
        isChecked = checked;
    }

    @Override
    public boolean isChecked() {
        return isChecked;
    }

    @Override
    public void toggle() {
        setChecked(!isChecked);
    }

    public static float convertDpToPixel(float dp, Context context){
        return dp * ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }
}
