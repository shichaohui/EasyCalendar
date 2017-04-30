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

package com.sch.calendar.listener;

import android.view.View;

import com.sch.calendar.annotation.DayOfMonth;
import com.sch.calendar.annotation.Month;

/**
 * Created by StoneHui on 17/2/16.
 * <p>
 * Listener for day of month be clicked.
 */
public interface OnDateClickedListener {

    /**
     * Call back when day of month be clicked.
     *
     * @param itemView   item view for day of month
     * @param year       year of date
     * @param month      month of date，［0 to 11］for［January to DECEMBER］
     * @param dayOfMonth day of month
     */
    void onDateClicked(View itemView, int year, @Month int month, @DayOfMonth int dayOfMonth);

}
