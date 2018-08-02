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

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.shazam.android.reflow.R;
import com.shazam.android.widget.text.reflow.ReflowTextAnimatorHelper;
import butterknife.BindView;
import butterknife.ButterKnife;

public final class AdvancedSampleActivity extends AppCompatActivity
        implements AdvancedOptionsDialogFragment.OnUpdateListener {

    private static final long MIN_DURATION = 1_000L;
    private static final long MAX_DURATION = 1_200L;
    public static final float SOURCE_ALPHA = 1F;
    public static final float TARGET_ALPHA = .125F;

    @BindView(R.id.topText) TextView topText;
    @BindView(R.id.bottomText) TextView bottomText;
    private TextView sourceView;
    private TextView targetView;
    private Animator animator;
    private boolean showLayers = false;

    private final Animator.AnimatorListener animatorListener = new AnimatorListenerAdapter() {
        @Override
        public void onAnimationStart(Animator animation) {
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            setupForReverse();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_advanced);
        ButterKnife.bind(this);

        sourceView = topText;
        targetView = bottomText;
        sourceView.setAlpha(SOURCE_ALPHA);
        targetView.setAlpha(TARGET_ALPHA);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_advanced, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        int layersIcon = showLayers ? R.drawable.ic_layers_visible : R.drawable.ic_layers_hidden;
        menu.findItem(R.id.action_layers).setIcon(layersIcon);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_play:
                animate();
                return true;
            case R.id.action_edit:
                edit();
                return true;
            case R.id.action_layers:
                showLayers = !showLayers;
                invalidateOptionsMenu();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void updateText(CharSequence text) {
        topText.setText(text);
        bottomText.setText(text);
    }

    @Override
    public void updateSourceTextAlignment(int alignment) {
        sourceView.setGravity(alignment);
    }

    @Override
    public void updateSourceTextStyle(boolean isBold, boolean isItalic) {
        int style = getTextStyle(isBold, isItalic);
        topText.setTypeface(Typeface.DEFAULT, style);
    }

    @Override
    public void updateTargetTextAlignment(int alignment) {
        bottomText.setGravity(alignment);
    }

    @Override
    public void updateTargetTextStyle(boolean isBold, boolean isItalic) {
        int style = getTextStyle(isBold, isItalic);
        bottomText.setTypeface(Typeface.DEFAULT, style);
    }

    private void animate() {
        if (animator == null || !animator.isRunning()) {
            animator = new ReflowTextAnimatorHelper.Builder(sourceView, targetView)
                    .debug(showLayers)
                    .withDuration(MIN_DURATION, MAX_DURATION)
                    .buildAnimator();
            animator.addListener(animatorListener);
            animator.start();
        }
    }

    private void edit() {
        CharSequence text = topText.getText();
        AdvancedOptionsDialogFragment.newInstance(text)
                .show(getSupportFragmentManager(), AdvancedOptionsDialogFragment.TAG);
    }

    private void setupForReverse() {
        TextView tmp = sourceView;
        sourceView = targetView;
        targetView = tmp;

        sourceView.setAlpha(SOURCE_ALPHA);
        targetView.setAlpha(TARGET_ALPHA);
    }

    private static int getTextStyle(boolean isBold, boolean isItalic) {
        int style = Typeface.NORMAL;
        if (isBold && isItalic) {
            style = Typeface.BOLD_ITALIC;
        } else if (isBold) {
            style = Typeface.BOLD;
        } else if (isItalic) {
            style = Typeface.ITALIC;
        }
        return style;
    }
}
