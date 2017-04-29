/*
 * Copyright 2017 Stone辉
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

package com.sch.example;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.sch.calendar.CalendarView;
import com.sch.calendar.adapter.SampleVagueAdapter;
import com.sch.calendar.annotation.DayOfMonth;
import com.sch.calendar.annotation.Month;
import com.sch.calendar.entity.Date;
import com.sch.calendar.listener.OnDateClickedListener;
import com.sch.calendar.listener.OnMonthChangedListener;
import com.sch.example.checkin.CheckinActivity;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Create by Stone辉 on 17/04/10.
 * <p>
 * 示例 Activity
 */
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.calendar_view)
    CalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initCalendarView();

    }

    @OnClick(R.id.btn_goto_checkin)
    public void gotoCheckin() {
        startActivity(new Intent(this, CheckinActivity.class));
    }

    // 初始化日历
    private void initCalendarView() {
        calendarView.setCanDrag(true); // 可拖动
        calendarView.setScaleEnable(false); // 不可伸缩
        calendarView.setShowOverflowDate(true); // 显示溢出的日期
        calendarView.setTitleFormat("yyyy-MM", Locale.CHINA); // 设置标题的格式
        // 设置月份改变监听
        calendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(Date date) {
            }
        });
        // 日期点击监听
        calendarView.setOnDateClickedListener(new OnDateClickedListener() {
            @Override
            public void onDateClicked(View itemView, int year, @Month int month, @DayOfMonth int dayOfMonth) {
                Toast.makeText(MainActivity.this, String.format("%s年%s月%s日", year, month, dayOfMonth), Toast.LENGTH_SHORT).show();
            }
        });
        // 数据适配器
        calendarView.setVagueAdapter(new SampleVagueAdapter());
    }

}
