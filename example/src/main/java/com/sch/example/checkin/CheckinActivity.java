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

package com.sch.example.checkin;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sch.calendar.CalendarView;
import com.sch.calendar.adapter.VagueAdapter;
import com.sch.calendar.annotation.DayOfMonth;
import com.sch.calendar.annotation.Month;
import com.sch.calendar.entity.Date;
import com.sch.calendar.listener.OnMonthChangedListener;
import com.sch.calendar.util.DateUtil;
import com.sch.example.R;
import com.sch.example.util.ResourcesHelper;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by StoneHui on 17/4/26.
 * <p>
 * Calendar for checkin.
 */
public class CheckinActivity extends Activity {

    private final String MONTH_FORMAT = "yyyyMM";
    private final String DAY_OF_MONTH_FORMAT = "yyyyMMdd";

    @BindView(R.id.calendar_view)
    CalendarView calendarView;

    private VagueAdapter<Map<String, Map<String, Checkin>>> vagueAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkin);

        ButterKnife.bind(this);

        defineDialogStyle();

        initCalendarView();

        calendarView.post(new Runnable() {
            @Override
            public void run() {
                vagueAdapter.setData(createCheckinData());
                vagueAdapter.notifyDataSetChanged();
            }
        });
    }

    @OnClick(R.id.root_layout)
    public void hide() {
        finish();
    }

    @OnClick(R.id.btn_checkin)
    public void checkin(View btnCheckin) {
        Date today = DateUtil.today();
        int year = today.getYear();
        int month = today.getMonth();
        int dayOfMonth = today.getDayOfMonth();

        Map<String, Checkin> checkinMap = vagueAdapter.getData().get(DateUtil.formatDate(year, month, dayOfMonth, MONTH_FORMAT));
        checkinMap.put(DateUtil.formatDate(year, month, dayOfMonth, DAY_OF_MONTH_FORMAT), new Checkin());
        vagueAdapter.notifyDataSetChanged(year, month);

        btnCheckin.setEnabled(false);
        ((Button)btnCheckin).setText(R.string.checkin_already);
    }

    // Define the style for calendar dialog,
    private void defineDialogStyle() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // Set status bar transparent
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // Set status bar transparent
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }

        // Cancel the default in and out animation of Activity.
        overridePendingTransition(0, 0);
    }

    // Initialize view for calendar
    private void initCalendarView() {
        calendarView.setCanDrag(false); // can't change month by slide
        calendarView.setScaleEnable(true); // can auto scale calendar when month changed.
        calendarView.setShowOverflowDate(false); // hide overflow date of showing month.
        // Set a listener，callback when month changed.
        calendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(Date date) {
            }
        });
        // create adapter
        vagueAdapter = new MyVagueAdapter(R.layout.layout_checkin_calendar_item);
        vagueAdapter.setData(new HashMap<String, Map<String, Checkin>>());
        calendarView.setVagueAdapter(vagueAdapter);
    }

    // Generate some data.
    private Map<String, Map<String, Checkin>> createCheckinData() {

        Map<String, Map<String, Checkin>> checkinMap = new HashMap<>();
        Map<String, Checkin> monthCheckinMap = new HashMap<>();

        Date today = DateUtil.today();
        int year = today.getYear();
        int month = today.getMonth();
        int dayOfMonth = today.getDayOfMonth();

        checkinMap.put(DateUtil.formatDate(year, month, dayOfMonth, MONTH_FORMAT), monthCheckinMap);

        for (int i = 1, N = today.getDayOfMonth() - 1; i < N; i++) {
            if (i % 3 == 0) {
                monthCheckinMap.put(DateUtil.formatDate(year, month, i + 1, DAY_OF_MONTH_FORMAT), new Checkin());
            }
        }
        return checkinMap;
    }

    private class MyVagueAdapter extends VagueAdapter<Map<String, Map<String, Checkin>>> {

        MyVagueAdapter(@LayoutRes int dayLayout) {
            super(dayLayout);
        }

        @Override
        public void onBindVague(View itemView, int year, @Month int month, @DayOfMonth int dayOfMonth) {
            ImageView ivCheckinAlready = itemView.findViewById(R.id.iv_checkin_already);
            if (data == null) return;
            // Get the data of current month.
            Map<String, Checkin> monthMap = data.get(DateUtil.formatDate(year, month, dayOfMonth, MONTH_FORMAT));
            // No data of current month.
            if (monthMap == null) {
                ivCheckinAlready.setVisibility(View.GONE);
                return;
            }
            // Get the data of today.
            Checkin history = monthMap.get(DateUtil.formatDate(year, month, dayOfMonth, DAY_OF_MONTH_FORMAT));
            // Show action finished or not.
            ivCheckinAlready.setVisibility(history == null ? View.GONE : View.VISIBLE);
        }

        @Override
        public void flagToday(View todayView) {
            // Highlight today.
            TextView tvDayView = todayView.findViewById(R.id.tv_day_of_month);
            tvDayView.setBackgroundResource(R.mipmap.ic_flag_checkin_calendar_today);
            tvDayView.setTextColor(Color.WHITE);
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
