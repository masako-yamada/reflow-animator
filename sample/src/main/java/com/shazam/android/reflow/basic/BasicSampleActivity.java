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

package com.shazam.android.reflow.basic;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.shazam.android.reflow.R;
import com.shazam.android.widget.text.reflow.ReflowTextAnimatorHelper;

public class BasicSampleActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_basic);

        final TextView fromView = findViewById(R.id.fromText);
        final TextView toView = findViewById(R.id.toText);

        // Both views need to have the same text for the transformation to make sense.
        String text = "Some text that is about to transform";
        fromView.setText(text);
        toView.setText(text);

        // Views have to be laid out so the helper can calculate the transformation needed.
        fromView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                fromView.getViewTreeObserver().removeOnPreDrawListener(this);

                Animator animator = new ReflowTextAnimatorHelper.Builder(fromView, toView)
                        .withDuration(2000, 3000)
                        .buildAnimator();

                animator.setStartDelay(500);
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        // Swap out the original view now that it looks the same as the target
                        fromView.setVisibility(View.GONE);
                        toView.setVisibility(View.VISIBLE);
                    }
                });
                animator.start();

                return true;
            }
        });
    }
}
