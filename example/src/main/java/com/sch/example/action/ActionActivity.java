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

package com.sch.example.action;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sch.calendar.CalendarView;
import com.sch.calendar.adapter.VagueAdapter;
import com.sch.calendar.annotation.DayOfMonth;
import com.sch.calendar.annotation.Month;
import com.sch.calendar.entity.Date;
import com.sch.calendar.util.DateUtil;
import com.sch.example.R;
import com.sch.example.util.ResourcesHelper;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by StoneHui on 17/4/26.
 * <p>
 * Calendar for action.
 */
public class ActionActivity extends Activity {

    private final String MONTH_FORMAT = "yyyyMM";
    private final String DAY_OF_MONTH_FORMAT = "yyyyMMdd";

    @BindView(R.id.calendar_view)
    CalendarView calendarView;

    private VagueAdapter<Map<String, Map<String, Action>>> vagueAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action);

        ButterKnife.bind(this);

        initCalendarView();

        calendarView.post(new Runnable() {
            @Override
            public void run() {
                vagueAdapter.setData(createCheckinData());
                vagueAdapter.notifyDataSetChanged();
            }
        });
    }

    // Initialize view for calendar.
    private void initCalendarView() {
        // Create adapter
        vagueAdapter = new MyVagueAdapter(R.layout.layout_action_calendar_item);
        vagueAdapter.setData(new HashMap<String, Map<String, Action>>());
        calendarView.setVagueAdapter(vagueAdapter);
    }

    // Generate some data.
    private Map<String, Map<String, Action>> createCheckinData() {

        Map<String, Map<String, Action>> checkinMap = new HashMap<>();
        Map<String, Action> monthCheckinMap = new HashMap<>();

        Date today = DateUtil.today();
        int year = today.getYear();
        int month = today.getMonth();
        int dayOfMonth = today.getDayOfMonth();

        checkinMap.put(DateUtil.formatDate(year, month, dayOfMonth, MONTH_FORMAT), monthCheckinMap);

        for (int i = 1, N = today.getDayOfMonth() - 1; i < N; i++) {
            if (i % 3 == 0) {
                monthCheckinMap.put(DateUtil.formatDate(year, month, i + 1, DAY_OF_MONTH_FORMAT), new Action());
            }
        }
        return checkinMap;
    }

    private class MyVagueAdapter extends VagueAdapter<Map<String, Map<String, Action>>> {

        MyVagueAdapter(@LayoutRes int dayLayout) {
            super(dayLayout);
        }

        @Override
        public void onBindVague(View itemView, int year, @Month int month, @DayOfMonth int dayOfMonth) {
            ImageView ivActionFinished = itemView.findViewById(R.id.iv_action_finished);
            if (data == null) return;
            // Get the data of current month.
            Map<String, Action> monthMap = data.get(DateUtil.formatDate(year, month, dayOfMonth, MONTH_FORMAT));
            // No data of current month.
            if (monthMap == null) {
                ivActionFinished.setVisibility(View.GONE);
                return;
            }
            // Get the data of today.
            Action history = monthMap.get(DateUtil.formatDate(year, month, dayOfMonth, DAY_OF_MONTH_FORMAT));
            // Show action finished or not.
            ivActionFinished.setVisibility(history == null ? View.GONE : View.VISIBLE);
        }

        @Override
        public void flagToday(View todayView) {
            // Highlight today.
            TextView tvDayView = todayView.findViewById(R.id.tv_day_of_month);
            tvDayView.setTextColor(Color.WHITE);
            todayView.setBackgroundColor(getResources().getColor(R.color.blue_light));
        }

        @Override
        public void flagNotToday(View dayView, Date date) {
            // Reset the view of not today.
            TextView tvDayView = dayView.findViewById(R.id.tv_day_of_month);
            tvDayView.setBackgroundColor(Color.TRANSPARENT);
            tvDayView.setTextColor(ResourcesHelper.getColor(getApplicationContext(), R.color.contentTextHintColor));
        }
    }

}
