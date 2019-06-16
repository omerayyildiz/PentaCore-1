package com.eksioglu.faruk.elementler;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.eksioglu.faruk.elementler.elements.FilterButton;

public class SettingsActivity extends AppCompatActivity {

    Context context;

    FilterButton buttonPentacore, buttonUbiko;
    LinearLayout linearLayout;
    ImageView iletisimButton;
    ScrollView scrollView;

    IletisimDialog dialog;

    private int weight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        context = this;
        Toolbar toolbar = findViewById(R.id.settingsActivty_toolbar);
        setSupportActionBar(toolbar);
        buttonPentacore = findViewById(R.id.settingsActivty_pentacoreButton);
        buttonUbiko = findViewById(R.id.settingsActivty_ubikoButton);
        linearLayout = findViewById(R.id.settingsActivty_linearLayout);
        iletisimButton = findViewById(R.id.iletisim_button);
        scrollView = findViewById(R.id.settingsActivity_scrollView);
        dialog = new IletisimDialog(context);
        linearLayout.setGravity(Gravity.CENTER_HORIZONTAL);
        buttonPentacore.setChecked(true);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        weight = size.x;

        setPage(true);
        iletisimButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });
    }

    public void onSettingsButtonClickListener(View view){
        int id = view.getId();

        if (id == R.id.settingsActivty_pentacoreButton){
            buttonPentacore.setChecked(true);
            buttonUbiko.setChecked(false);
            setPage(true);
        }else if (id == R.id.settingsActivty_ubikoButton){
            buttonPentacore.setChecked(false);
            buttonUbiko.setChecked(true);
            setPage(false);
        }
    }

    private void setPage(boolean isPentacore){
        if (isPentacore){
            linearLayout.removeAllViews();

            ImageView iconImage = new ImageView(context);
            iconImage.setImageResource(R.drawable.app_icon);
            linearLayout.addView(iconImage);
            setViewSize(iconImage, weight/3*2, weight/3*2);

            TextView textView = new TextView(context);
            textView.setText(getResources().getText(R.string.pentaCoreTanıtımıBaşlık_1));
            textView.setTextColor(getResources().getColor(R.color.light_text));
            textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 28);
            linearLayout.addView(textView);

            TextView textView1 = new TextView(context);
            textView1.setText(getResources().getText(R.string.pentaCoreTanıtımı_1));
            textView1.setTextColor(getResources().getColor(R.color.light_text));
            linearLayout.addView(textView1);

            TextView textView2 = new TextView(context);
            textView2.setText(getResources().getText(R.string.pentaCoreTanıtımıBaşlık_2));
            textView2.setTextColor(getResources().getColor(R.color.light_text));
            textView2.setTypeface(textView2.getTypeface(), Typeface.BOLD);
            textView2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 28);
            linearLayout.addView(textView2);

            TextView textView3 = new TextView(context);
            textView3.setText(getResources().getText(R.string.pentaCoreTanıtımı_2));
            textView3.setTextColor(getResources().getColor(R.color.light_text));
            linearLayout.addView(textView3);
            scroolTo(0, 0);
        }else {
            linearLayout.removeAllViews();

            ImageView ubikoImage = new ImageView(context);
            ubikoImage.setImageResource(R.drawable.ubiko_icon_red_circle);
            linearLayout.addView(ubikoImage);
            setViewSize(ubikoImage, weight/3*2, weight/3*2);

            TextView textView = new TextView(context);
            textView.setText(getResources().getText(R.string.ubikoTanıtımı));
            textView.setTextColor(getResources().getColor(R.color.light_text));
            linearLayout.addView(textView);
            scroolTo(0, 0);
        }
    }
    private void scroolTo(int y, int x) {
        System.out.println("position: " + scrollView.getScrollY());
        ObjectAnimator xTranslate = ObjectAnimator.ofInt(scrollView, "scrollX", x);
        ObjectAnimator yTranslate = ObjectAnimator.ofInt(scrollView, "scrollY", y);

        AnimatorSet animators = new AnimatorSet();
        animators.setDuration(250L);
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

    public void setViewSize(View view, int x, int y){
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (x > 0) params.width = x;
        if (y > 0) params.height = y;
    }
}
