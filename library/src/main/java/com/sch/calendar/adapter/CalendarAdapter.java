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

package com.sch.calendar.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.sch.calendar.listener.OnDateClickedListener;
import com.sch.calendar.MonthView;
import com.sch.calendar.annotation.Month;
import com.sch.calendar.entity.Date;
import com.sch.calendar.listener.OnVagueDataSetChangeListener;
import com.sch.calendar.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by StoneHui on 17/2/14.
 * <p>
 * Data adapter for calendar component. The data type is {@link Date}
 */
public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.MyViewHolder> {

    private final Context context;

    private int dateDividerColor;
    private float dateDividerSize;
    private boolean showOverflowDate = true;

    private VagueAdapter vagueAdapter;
    private OnDateClickedListener onDateClickedListener;

    private List<Date> dateList;

    /**
     * initialize adapter by {@link Date}
     *
     * @param context Activity context
     * @param date    first display date
     */
    public CalendarAdapter(Context context, Date date) {
        this.context = context;

        dateList = new ArrayList<>();
        dateList.add(DateUtil.lastMonth(date));
        dateList.add(date);
        dateList.add(DateUtil.nextMonth(date));
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MonthView monthView = new MonthView(context);
        monthView.setDateDividerColor(dateDividerColor);
        monthView.setDateDividerSize(dateDividerSize);
        monthView.setVagueAdapter(vagueAdapter);
        monthView.setOnDateClickedListener(onDateClickedListener);
        monthView.setShowOverflowDate(showOverflowDate);
        return new MyViewHolder(monthView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Date date = dateList.get(position);
        ((MonthView) holder.itemView).setMonth(date.getYear(), date.getMonth());
    }

    @Override
    public int getItemCount() {
        return dateList.size();
    }

    public List<Date> getDateList() {
        return dateList;
    }

    /**
     * Set {@link VagueAdapter} to handle custom data.
     *
     * @param vagueAdapter adapter
     */
    public void setVagueAdapter(VagueAdapter vagueAdapter) {
        this.vagueAdapter = vagueAdapter;
        vagueAdapter.addOnVagueDataSetChangeListener(new OnVagueDataSetChangeListener() {
            @Override
            public void onVagueDataSetChange() {
                notifyDataSetChanged();
            }

            @Override
            public void onVagueDataSetChange(int year, @Month int month) {
                notifyItemChanged(dateList.indexOf(new Date(year, month, 1)));
            }
        });
    }

    /**
     * Set listener for date be clicked.
     *
     * @param onDateClickedListener the listener will be call when date be clicked.
     */
    public void setOnDateClickedListener(OnDateClickedListener onDateClickedListener) {
        this.onDateClickedListener = onDateClickedListener;
    }

    /**
     * Add last month of {@link #dateList}'s  first month.
     */
    public void addNewLastMonth() {
        dateList.add(0, DateUtil.lastMonth(dateList.get(0)));
        notifyItemInserted(0);
    }

    /**
     * Add next month of {@link #dateList}'s  last month.
     */
    public void addNewNextMonth() {
        dateList.add(DateUtil.nextMonth(dateList.get(dateList.size() - 1)));
        notifyItemInserted(dateList.size() - 1);
    }

    /**
     * Set divider color for date.
     *
     * @param dateDividerColor the color
     */
    public void setDateDividerColor(int dateDividerColor) {
        this.dateDividerColor = dateDividerColor;
    }

    /**
     * Set divider size for date.
     *
     * @param dateDividerSize the size
     */
    public void setDateDividerSize(float dateDividerSize) {
        this.dateDividerSize = dateDividerSize;
    }

    /**
     * If true, show whole calendar.
     * e.g. showing date is April, if show whole calendar, 03/30 and 05/01 will show.
     */
    public void setShowOverflowDate(boolean showOverflowDate) {
        this.showOverflowDate = showOverflowDate;
    }

    public boolean isShowOverflowDate() {
        return showOverflowDate;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        MyViewHolder(View itemView) {
            super(itemView);
        }

    }
}