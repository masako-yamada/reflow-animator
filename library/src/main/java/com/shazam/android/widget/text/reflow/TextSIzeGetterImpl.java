package com.shazam.android.widget.text.reflow;

import android.widget.TextView;

public class TextSIzeGetterImpl implements TextSizeGetter {
    @Override
    public float get(TextView view) {
        return view.getTextSize();
    }
}
