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
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;

/**
 * Created by StoneHui on 17/2/24.
 * <p>
 * RecyclerView for page.
 */
public class PageRecyclerView extends RecyclerView {

    private boolean canDrag = true;
    private boolean canFling = false;

    public PageRecyclerView(Context context) {
        super(context);
    }

    /**
     * Set drag enable for page.
     */
    public void setCanDrag(boolean canDrag) {
        this.canDrag = canDrag;
    }

    /**
     * Return drag enable of page.
     */
    public boolean canDrag() {
        return canDrag;
    }

    /**
     * Set fling enable for page.
     */
    public void setCanFling(boolean canFling) {
        this.canFling = canFling;
    }

    /**
     * Return fling enable of page.
     */
    public boolean canFling() {
        return canFling;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        return canDrag && super.onInterceptTouchEvent(e);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        return !canDrag || super.onTouchEvent(e);
    }

    @Override
    public boolean fling(int velocityX, int velocityY) {
        // no fling
        return canFling && super.fling(velocityX, velocityY);
    }

}
