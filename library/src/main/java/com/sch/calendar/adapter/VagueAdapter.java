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

import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.widget.TextView;

import com.sch.calendar.R;
import com.sch.calendar.annotation.DayOfMonth;
import com.sch.calendar.annotation.Month;
import com.sch.calendar.entity.Date;
import com.sch.calendar.listener.OnVagueDataSetChangeListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by StoneHui on 17/2/16.
 * <p>
 * This adapter be used for custom data, e.g. checkin data.
 */
public class VagueAdapter<T> {

    private int dayLayoutRes;

    protected T data;
    private List<OnVagueDataSetChangeListener> onVagueDataSetChangeListenerList = new ArrayList<>();

    /**
     * Initialization adapter.
     *
     * @param dayLayout layout for day of month
     */
    public VagueAdapter(@LayoutRes int dayLayout) {
        dayLayoutRes = dayLayout;
    }

    /**
     * Return layout for day of month.
     */
    public int getDayLayoutRes() {
        return dayLayoutRes;
    }

    /**
     * Return {@link #data}
     */
    public T getData() {
        return data;
    }

    /**
     * Set the data for display.
     *
     * @param data data
     */
    public void setData(T data) {
        this.data = data;
    }

    /**
     * Notification adapter update data.
     */
    public void notifyDataSetChanged() {
        for (OnVagueDataSetChangeListener listener : onVagueDataSetChangeListenerList) {
            listener.onVagueDataSetChange();
        }
    }

    /**
     * Notification adapter update data.
     *
     * @param year  year of date
     * @param month month of date
     */
    public void notifyDataSetChanged(int year, @Month int month) {
        for (OnVagueDataSetChangeListener listener : onVagueDataSetChangeListenerList) {
            listener.onVagueDataSetChange(year, month);
        }
    }

    /**
     * Set listener for response {@link #data} change.
     *
     * @param listener listener for {@link #data} change
     */
    public void addOnVagueDataSetChangeListener(OnVagueDataSetChangeListener listener) {
        onVagueDataSetChangeListenerList.add(listener);
    }

    /**
     * Bind date.
     *
     * @param itemView   item view for day of month
     * @param year       year of date
     * @param month      month of date，［0 to 11］for［JANUARY to DECEMBER］
     * @param dayOfMonth day of month
     */
    public void onBindDate(View itemView, int year, @Month int month, @DayOfMonth int dayOfMonth) {
        try {
            // show day of month
            TextView view = (TextView) itemView.findViewById(R.id.tv_day_of_month);
            view.setText(String.valueOf(dayOfMonth));
            view.setAlpha(1f);
        } catch (NullPointerException e) {
            throw new RuntimeException("Missing ID is tv_day_of_month's TextView!");
        }
    }

    /**
     * Bind date.
     * e.g. showing date is April, param year is March, param dayOfMonth is end date of March.
     *
     * @param itemView   item view for day of month
     * @param year       year of date
     * @param month      month of date，［0 to 11］for［JANUARY to DECEMBER］
     * @param dayOfMonth day of month
     */
    public void onBindStartOverflowDate(View itemView, int year, @Month int month, @DayOfMonth int dayOfMonth) {
        try {
            // show day of month
            TextView view = (TextView) itemView.findViewById(R.id.tv_day_of_month);
            view.setText(String.valueOf(dayOfMonth));
            view.setAlpha(0.5f);
        } catch (NullPointerException e) {
            throw new RuntimeException("Missing ID is tv_day_of_month's TextView!");
        }
    }

    /**
     * Bind date.
     * e.g. showing date is April, param year is May, param dayOfMonth is end date of May.
     *
     * @param itemView   item view for day of month
     * @param year       year of date
     * @param month      month of date，［0 to 11］for［JANUARY to DECEMBER］
     * @param dayOfMonth day of month
     */
    public void onBindEndOverflowDate(View itemView, int year, @Month int month, @DayOfMonth int dayOfMonth) {
        try {
            // show day of month
            TextView view = (TextView) itemView.findViewById(R.id.tv_day_of_month);
            view.setText(String.valueOf(dayOfMonth));
            view.setAlpha(0.5f);
        } catch (NullPointerException e) {
            throw new RuntimeException("Missing ID is tv_day_of_month's TextView!");
        }
    }

    /**
     * Bind custom data.
     * e.g. showing date is April, param year is March, param dayOfMonth is end date of March.
     *
     * @param itemView   item view for day of month
     * @param year       year of date
     * @param month      month of date，［0 to 11］for［JANUARY to DECEMBER］
     * @param dayOfMonth day of month
     */
    public void onBindVague(View itemView, int year, @Month int month, @DayOfMonth int dayOfMonth) {
    }

    /**
     * Bind custom data.
     *
     * @param itemView   item view for day of month
     * @param year       year of date
     * @param month      month of date，［0 to 11］for［JANUARY to DECEMBER］
     * @param dayOfMonth day of month
     */
    public void onBindVagueOfStartOverflowDate(View itemView, int year, @Month int month, @DayOfMonth int dayOfMonth) {
    }

    /**
     * Bind custom data.
     * e.g. showing date is April, param year is May, param dayOfMonth is end date of May.
     *
     * @param itemView   item view for day of month
     * @param year       year of date
     * @param month      month of date，［0 to 11］for［JANUARY to DECEMBER］
     * @param dayOfMonth day of month
     */
    public void onBindVagueOfEndOverflowDate(View itemView, int year, @Month int month, @DayOfMonth int dayOfMonth) {
    }

    /**
     * Set a mark for today.
     *
     * @param todayView item view of today
     */
    public void flagToday(View todayView) {
        todayView.setBackgroundResource(R.drawable.bg_today);
    }

    /**
     * Set a mark for all date except today.
     *
     * @param dayView item view for date
     * @param date    date for flag
     */
    public void flagNotToday(View dayView, Date date) {
        dayView.setBackgroundColor(Color.TRANSPARENT);
    }

}
