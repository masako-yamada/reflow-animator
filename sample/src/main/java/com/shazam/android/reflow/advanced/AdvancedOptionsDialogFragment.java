/*
 * Copyright 2017 Shazam Entertainment Limited.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.shazam.android.reflow.advanced;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import com.shazam.android.reflow.R;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnTextChanged;
import butterknife.Unbinder;

public final class AdvancedOptionsDialogFragment extends DialogFragment {
    public static final String TAG = "AdvancedOptionsDialogFragment";

    private static final String ARGS_TEXT = "args:text";

    private Unbinder binder;

    @BindView(R.id.content) TextView content;
    @BindView(R.id.source_bold) CheckBox sourceBoldStyle;
    @BindView(R.id.source_italic) CheckBox sourceItalicStyle;
    @BindView(R.id.target_bold) CheckBox targetBoldStyle;
    @BindView(R.id.target_italic) CheckBox targetItalicStyle;

    public static DialogFragment newInstance(CharSequence text) {
        Bundle args = new Bundle();
        args.putCharSequence(ARGS_TEXT, text);

        AdvancedOptionsDialogFragment fragment = new AdvancedOptionsDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public interface OnUpdateListener {
        void updateText(CharSequence text);
        void updateSourceTextAlignment(int alignment);
        void updateSourceTextStyle(boolean isBold, boolean isItalic);
        void updateTargetTextAlignment(int alignment);
        void updateTargetTextStyle(boolean isBold, boolean isItalic);
    }

    private OnUpdateListener onUpdateListener;

    @Override
    public void onAttach(Context context) {
        if (!(context instanceof OnUpdateListener)) {
            throw new IllegalArgumentException(context + " must implement OnUpdateListener");
        }

        onUpdateListener = (OnUpdateListener) context;
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        onUpdateListener = null;
        super.onDetach();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.Theme_AppCompat_Light_Dialog_Alert);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_options_dialog, container, false);
        binder = ButterKnife.bind(this, view);

        content.setText(getArguments().getCharSequence(ARGS_TEXT));

        return view;
    }

    @Override
    public void onDestroyView() {
        binder.unbind();
        super.onDestroyView();
    }

    @OnTextChanged(R.id.content)
    void onTextChanged(CharSequence text) {
        onUpdateListener.updateText(text);
    }

    @OnCheckedChanged({ R.id.source_bold, R.id.source_italic })
    void onSourceTextStyleChanged() {
        onUpdateListener.updateSourceTextStyle(sourceBoldStyle.isChecked(), sourceItalicStyle.isChecked());
    }

    @OnCheckedChanged({ R.id.source_align_start, R.id.source_align_center, R.id.source_align_end })
    void onSourceAlignmentChanged(RadioButton button, boolean isChecked) {
        if (isChecked) {
            int alignment = Gravity.NO_GRAVITY;
            switch (button.getId()) {
                case R.id.source_align_start:
                    alignment = Gravity.START;
                    break;
                case R.id.source_align_center:
                    alignment = Gravity.CENTER_HORIZONTAL;
                    break;
                case R.id.source_align_end:
                    alignment = Gravity.END;
                    break;
            }
            onUpdateListener.updateSourceTextAlignment(alignment);
        }
    }

    @OnCheckedChanged({ R.id.target_bold, R.id.target_italic })
    void onTargetTextStyleChanged() {
        onUpdateListener.updateTargetTextStyle(targetBoldStyle.isChecked(), targetItalicStyle.isChecked());
    }

    @OnCheckedChanged({ R.id.target_align_start, R.id.target_align_center, R.id.target_align_end })
    void onTargetAlignmentChanged(RadioButton button, boolean isChecked) {
        if (isChecked) {
            int alignment = Gravity.NO_GRAVITY;
            switch (button.getId()) {
                case R.id.target_align_start:
                    alignment = Gravity.START;
                    break;
                case R.id.target_align_center:
                    alignment = Gravity.CENTER_HORIZONTAL;
                    break;
                case R.id.target_align_end:
                    alignment = Gravity.END;
                    break;
            }
            onUpdateListener.updateTargetTextAlignment(alignment);
        }
    }
}
