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

package com.sch.calendar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.sch.calendar.adapter.VagueAdapter;
import com.sch.calendar.entity.Date;
import com.sch.calendar.listener.OnDateClickedListener;
import com.sch.calendar.util.DateUtil;
import com.sch.calendar.annotation.Month;

/**
 * Created by StoneHui on 17/2/14.
 * <p>
 * view for month.
 */
public class MonthView extends GridLayout {

    public static final int ROW_MIN_COUNT = 5;
    public static final int ROW_MAX_COUNT = 6;
    public static final int COLUMN_COUNT = 7;

    private int year;
    @Month
    private int month;

    private int firstWeekOfMonth;
    private int dayCountOfMonth;

    private final int itemCount = ROW_MAX_COUNT * COLUMN_COUNT;

    private VagueAdapter vagueAdapter;
    private OnDateClickedListener onDateClickedListener;
    private int dateDividerColor;
    private float dateDividerSize;
    private boolean showOverflowDate = true;

    public MonthView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public MonthView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MonthView(Context context) {
        this(context, null);
    }

    private void init() {

        setWillNotDraw(false); // if not call this code, not call onDraw(canvas)

        setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));

        setRowCount(ROW_MAX_COUNT);
        setColumnCount(COLUMN_COUNT);
    }

    /**
     * Set adapter for custom data.
     *
     * @param vagueAdapter adapter
     */
    public void setVagueAdapter(final VagueAdapter vagueAdapter) {
        this.vagueAdapter = vagueAdapter;
    }

    /**
     * Set date to display.
     *
     * @param year  year of date
     * @param month month of date，0 to 11
     */
    public void setMonth(int year, @Month int month) {
        this.year = year;
        this.month = month;

        firstWeekOfMonth = DateUtil.getFirstWeekOfMonth(year, month);
        dayCountOfMonth = DateUtil.getDayCountOfMonth(year, month);

        initAllDayItem();
    }

    /**
     * Return listener for item clicked.
     */
    public OnDateClickedListener getOnDateClickedListener() {
        return onDateClickedListener;
    }

    /**
     * Set listener for item clicked.
     *
     * @param onDateClickedListener listener
     */
    public void setOnDateClickedListener(OnDateClickedListener onDateClickedListener) {
        this.onDateClickedListener = onDateClickedListener;
    }

    /**
     * Set divider's color.
     *
     * @param dateDividerColor color
     */
    public void setDateDividerColor(int dateDividerColor) {
        this.dateDividerColor = dateDividerColor;
    }

    /**
     * Set divider's size.
     *
     * @param dateDividerSize size
     */
    public void setDateDividerSize(float dateDividerSize) {
        this.dateDividerSize = dateDividerSize;
    }

    /**
     * Return item view of date
     *
     * @param dayOfMonth day of month，1 to 31
     */
    public View getDayItemView(int dayOfMonth) {
        if (dayOfMonth < 1 || dayOfMonth > dayCountOfMonth) {
            return null;
        }
        return getChildAt(firstWeekOfMonth + dayOfMonth - 1);
    }

    /**
     * If true, show whole calendar.
     * e.g. showing date is April, if show whole calendar, 03/30 and 05/01 will show.
     */
    public void setShowOverflowDate(boolean showOverflowDate) {
        this.showOverflowDate = showOverflowDate;
    }

    // initialize all item view
    private void initAllDayItem() {

        if (getChildCount() < itemCount) {
            removeAllViews();
            addAllDayItem();
        }

        int indexOfLastDay = firstWeekOfMonth + dayCountOfMonth - 1; // last day of month
        Date today = DateUtil.today();
        View itemView;
        for (int index = 0; index < itemCount; index++) {
            itemView = getChildAt(index);
            itemView.setBackgroundColor(Color.TRANSPARENT);
            if (index < firstWeekOfMonth) {
                handleStartOverflowDate(itemView, index);
            } else if (index > indexOfLastDay) {
                handleEndOverflowDate(itemView, index);
            } else {
                handleNormalDate(today, itemView, index);
            }
        }
    }

    // add all item view
    private void addAllDayItem() {
        for (int i = 0; i < itemCount; i++) {
            View dayView = createDayItemView();
            dayView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onDateClickedListener != null && view.getVisibility() == VISIBLE) {
                        int dayOfMonth = indexOfChild(view) - firstWeekOfMonth + 1;
                        onDateClickedListener.onDateClicked(view, year, month, dayOfMonth);
                    }
                }
            });
            addView(dayView);
        }
    }

    // create view for item
    @NonNull
    private View createDayItemView() {
        View dayView = LayoutInflater.from(getContext()).inflate(vagueAdapter.getDayLayoutRes(), this, false);
        LayoutParams params = new LayoutParams();
        // average distribution in horizontal direction
        params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
        dayView.setLayoutParams(params);
        return dayView;
    }

    private void handleStartOverflowDate(View itemView, int index) {
        // hide or show item view of date that isn't a part of current month
        itemView.setVisibility(showOverflowDate ? VISIBLE : INVISIBLE);
        Date lastMonth = DateUtil.lastMonth(new Date(year, month, 1));
        int dayOfMonth = DateUtil.getDayCountOfMonth(lastMonth) + index - firstWeekOfMonth + 1;
        // bind date and custom data
        vagueAdapter.onBindStartOverflowDate(itemView, lastMonth.getYear(), lastMonth.getMonth(), dayOfMonth);
        vagueAdapter.onBindVagueOfStartOverflowDate(itemView, lastMonth.getYear(), lastMonth.getMonth(), dayOfMonth);
    }

    private void handleEndOverflowDate(View itemView, int index) {
        // hide or show item view of date that isn't a part of current month
        itemView.setVisibility(showOverflowDate ? VISIBLE : INVISIBLE);
        Date nextMonth = DateUtil.nextMonth(new Date(year, month, 1));
        int dayOfMonth = index - dayCountOfMonth - firstWeekOfMonth + 1;
        // bind date and custom data
        vagueAdapter.onBindEndOverflowDate(itemView, nextMonth.getYear(), nextMonth.getMonth(), dayOfMonth);
        vagueAdapter.onBindVagueOfEndOverflowDate(itemView, nextMonth.getYear(), nextMonth.getMonth(), dayOfMonth);
    }

    private void handleNormalDate(Date today, View itemView, int index) {
        itemView.setVisibility(VISIBLE); // show item view of date that is a part of current month
        int dayOfMonth = index - firstWeekOfMonth + 1;
        // bind date and custom data
        vagueAdapter.onBindDate(itemView, year, month, dayOfMonth);
        vagueAdapter.onBindVague(itemView, year, month, dayOfMonth);
        if (dayOfMonth == today.getDayOfMonth() && month == today.getMonth() && year == today.getYear()) {
            vagueAdapter.flagToday(itemView); // set flag for today
        } else {
            vagueAdapter.flagNotToday(itemView, new Date(year, month, dayOfMonth)); // set flag for date except today
        }
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, heightSpec);
        setMeasuredDimension(getMeasuredWidth(), getChildAt(0).getMeasuredWidth() * ROW_MAX_COUNT);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {

        int totalWidth = right - left - getPaddingLeft() - getPaddingRight();
        int childSize = totalWidth / COLUMN_COUNT;

        for (int i = 0, N = getChildCount(); i < N; i++) {
            View c = getChildAt(i);
            int childLeft = i % COLUMN_COUNT * childSize + getPaddingLeft();
            int childTop = i / COLUMN_COUNT * childSize + getPaddingTop();
            c.layout(childLeft, childTop, childLeft + childSize, childTop + childSize);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawDriver(canvas);
    }

    // draw divider
    private void drawDriver(Canvas canvas) {

        if (dateDividerSize <= 0 || dateDividerColor == Color.TRANSPARENT) {
            return;
        }

        Paint paint = new Paint();
        paint.setStrokeWidth(dateDividerSize);
        paint.setColor(dateDividerColor);

        drawHDivider(canvas, paint);
        drawVDivider(canvas, paint);

    }

    // draw divider in horizontal direction
    private void drawHDivider(Canvas canvas, Paint paint) {
        int itemViewWidth = getChildAt(0).getWidth();
        int startX = getPaddingLeft();
        int stopX = canvas.getWidth() - getPaddingRight();
        for (int i = 1; i < ROW_MAX_COUNT; i++) {
            int startY = i % ROW_MAX_COUNT * itemViewWidth + getPaddingTop();
            canvas.drawLine(startX, startY, stopX, startY, paint);
        }
    }

    // draw divider in vertical direction
    private void drawVDivider(Canvas canvas, Paint paint) {
        int itemViewHeight = getChildAt(0).getHeight();
        int startY = getPaddingTop();
        int stopY = canvas.getHeight() - getPaddingBottom();
        for (int i = 1; i < COLUMN_COUNT; i++) {
            int startX = i % COLUMN_COUNT * itemViewHeight + getPaddingLeft();
            canvas.drawLine(startX, startY, startX, stopY, paint);
        }
    }

}
