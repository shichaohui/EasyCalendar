/*
 * Copyright 2017 StoneHui
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
 * See the License for the specific language governing permissions and limitations under the License.
 */

package com.sch.calendar.recyclerview;

import android.content.Context;
import android.graphics.PointF;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;

/**
 * Created by StoneHui on 17/2/24.
 * <p>
 * A linear layout manager for control scroll speed.
 */
public class SpeedScrollLinearLayoutManager extends LinearLayoutManager {

    private final float MILLISECONDS_PER_INCH = 25f;
    private float millisecondsPerInch = MILLISECONDS_PER_INCH;

    public SpeedScrollLinearLayoutManager(Context context) {
        super(context);
    }

    public SpeedScrollLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public SpeedScrollLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
        LinearSmoothScroller linearSmoothScroller = new LinearSmoothScroller(recyclerView.getContext()) {

            @Override
            public PointF computeScrollVectorForPosition(int targetPosition) {
                return SpeedScrollLinearLayoutManager.this.computeScrollVectorForPosition(targetPosition);
            }

            //if returned value is 2 ms, it means scrolling 1000 pixels with LinearInterpolation should take 2 seconds.
            @Override
            protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                //该方法返回滑动一个pixel需要多少毫秒。
                /*
                     控制单位速度,  毫秒/像素, 滑动1像素需要多少毫秒.
                     默认为 (25F/densityDpi) 毫秒/像素
                     mdpi上, 1英寸有160个像素点, 25/160,
                     xxhdpi,1英寸有480个像素点, 25/480,
                  */

                //return 10F / displayMetrics.densityDpi;//可以减少时间，默认25F，加快滚动速度
                //return 50F / displayMetrics.densityDpi;//可以增加时间，默认25F，减缓滚动速度
                //return super.calculateSpeedPerPixel(displayMetrics);
                return millisecondsPerInch / displayMetrics.densityDpi;
            }

            //Calculates the time it should take to scroll the given distance (in pixels)
            @Override
            protected int calculateTimeForScrolling(int dx) {
                // 该方法计算滑动所需时间。dx 值越大，需要的时间越长。
               /*
                   控制距离, 然后根据上面那个方(calculateSpeedPerPixel())提供的速度算出时间,
                   默认一次 滚动 TARGET_SEEK_SCROLL_DISTANCE_PX = 10000个像素,
                   在此处可以减少该值来达到减少滚动时间的目的.
                   在此处可以增大该值来达到增加滚动时间的目的.
                */

                // 间接计算时修改速度，也可以直接在calculateSpeedPerPixel修改
                // dx += 5000;

                return super.calculateTimeForScrolling(dx);
            }
        };

        linearSmoothScroller.setTargetPosition(position);
        startSmoothScroll(linearSmoothScroller);
    }

    /**
     * scrolling fast
     */
    public void fastScroll() {
        millisecondsPerInch = MILLISECONDS_PER_INCH / 5f;
    }

    /**
     * scrolling slow
     */
    public void slowScroll() {
        millisecondsPerInch = MILLISECONDS_PER_INCH * 5f;
    }

    /**
     * scrolling normal
     */
    public void normalScroll() {
        millisecondsPerInch = MILLISECONDS_PER_INCH;
    }

}