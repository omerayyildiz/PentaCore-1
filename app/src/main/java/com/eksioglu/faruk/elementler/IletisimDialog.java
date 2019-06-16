package com.eksioglu.faruk.elementler;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Px;
import android.support.v4.util.LogWriter;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.eksioglu.faruk.divisiblegridlayout.DivisibleGridLayout;

import static com.eksioglu.faruk.elementler.MainActivity.weight;

public class IletisimDialog extends Dialog {

    Context context;

    private DivisibleGridLayout divisibleGridLayout;
    private ImageView imageWeb, imageGithub, imageDiscord, imageLinkedin;
    private TextView textWeb, textGithub, textDiscord, textLinkedin;

    public IletisimDialog(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.iletisim_dialog);
        getWindow().setBackgroundDrawable(new ColorDrawable(0x00000000));
        divisibleGridLayout = findViewById(R.id.iletisimDialog_ConstraintLayout);
        imageWeb      = findViewById(R.id.iletisimImage_web);
        imageGithub   = findViewById(R.id.iletisimImage_github);
        imageDiscord  = findViewById(R.id.iletisimImage_discord);
        imageLinkedin = findViewById(R.id.iletisimImage_linkedin);
        textWeb       = findViewById(R.id.iletisimText_web);
        textGithub    = findViewById(R.id.iletisimText_github);
        textDiscord   = findViewById(R.id.iletisimText_discord);
        textLinkedin  = findViewById(R.id.iletisimText_linkedin);

        imageWeb.setOnClickListener(onWebClickListener);
        textWeb.setOnClickListener(onWebClickListener);

        imageGithub.setOnClickListener(onGithubClickListener);
        textGithub .setOnClickListener(onGithubClickListener);

        imageDiscord.setOnClickListener(onDiscordClickListener);
        textDiscord .setOnClickListener(onDiscordClickListener);

        imageLinkedin.setOnClickListener(onLinkedinClickListener);
        textLinkedin .setOnClickListener(onLinkedinClickListener);

        int layoutWidth = weight/6*5;
        int dividerWeight = layoutWidth/40;
        setViewSize(divisibleGridLayout, layoutWidth, 0);
        divisibleGridLayout.setAllViewsSizePx(layoutWidth/2-dividerWeight/2*3, layoutWidth/20*11);
        divisibleGridLayout.setPadding(dividerWeight, dividerWeight, dividerWeight, dividerWeight);
        divisibleGridLayout.setDividerWeightPx(dividerWeight);

        textDiscord.setTextSize(TypedValue.COMPLEX_UNIT_PX, ((float)layoutWidth)/25);
        textWeb.setTextSize(TypedValue.COMPLEX_UNIT_PX, ((float)layoutWidth)/25);
        textGithub.setTextSize(TypedValue.COMPLEX_UNIT_PX, ((float)layoutWidth)/25);
        textLinkedin.setTextSize(TypedValue.COMPLEX_UNIT_PX, ((float)layoutWidth)/25);

        textDiscord.setPadding(0, layoutWidth/50, 0, layoutWidth/50);
        textWeb.setPadding(0, layoutWidth/50, 0, layoutWidth/50);
        textGithub.setPadding(0, layoutWidth/50, 0, layoutWidth/50);
        textLinkedin.setPadding(0, layoutWidth/50, 0, layoutWidth/50);

    }

    private View.OnClickListener onWebClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            openWebURL(context.getResources().getString(R.string.link_web));
        }
    };
    private View.OnClickListener onGithubClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            openWebURL(context.getResources().getString(R.string.link_github));
        }
    };
    private View.OnClickListener onDiscordClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            openWebURL(context.getResources().getString(R.string.link_discord));
        }
    };
    private View.OnClickListener onLinkedinClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            openWebURL(context.getResources().getString(R.string.link_linkedin));
        }
    };



    private void openWebURL( String inURL ) {
        Intent browse = new Intent( Intent.ACTION_VIEW , Uri.parse( inURL ) );

        context.startActivity( browse );
    }

    private void setViewSize(View view, int x, int y){
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (x > 0) params.width = x;
        if (y > 0) params.height = y;
    }
}
