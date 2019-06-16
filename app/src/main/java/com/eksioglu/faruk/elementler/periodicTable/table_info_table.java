package com.eksioglu.faruk.elementler.periodicTable;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;

import com.eksioglu.faruk.elementler.R;

public class table_info_table extends ConstraintLayout {
    public table_info_table(Context context) {
        this(context, null);
    }

    public table_info_table(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public table_info_table(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.table_info_table, this);
    }
}
