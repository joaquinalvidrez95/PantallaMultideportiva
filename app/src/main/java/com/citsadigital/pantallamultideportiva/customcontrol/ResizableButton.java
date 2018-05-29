package com.citsadigital.pantallamultideportiva.customcontrol;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by Diseno4 on 23/02/2018.
 */

public class ResizableButton extends android.support.v7.widget.AppCompatButton {

    public ResizableButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measure = MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(measure, measure);
    }
}
