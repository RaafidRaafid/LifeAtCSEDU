package edu.universitydhaka.cse2216.lifeatcsedu;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.apg.mobile.roundtextview.RoundTextView;

public class colorChangingRoundTextView extends com.apg.mobile.roundtextview.RoundTextView implements View.OnTouchListener {
    public colorChangingRoundTextView(Context context, AttributeSet attrs,
                                      int defStyle) {
        super(context, attrs, defStyle);
        setup();
    }

    public colorChangingRoundTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup();
    }

    public colorChangingRoundTextView(Context context, int checkableId) {
        super(context);
        setup();
    }

    public colorChangingRoundTextView(Context context) {
        super(context);
        setup();
    }

    private void setup() {
        setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (hasOnClickListeners()) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    setSelected(true);
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    setSelected(false);
                    break;
            }
        }

        // allow target view to handle click
        return false;
    }
}
