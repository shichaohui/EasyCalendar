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

import com.sch.calendar.annotation.Month;

/**
 * Created by StoneHui on 17/2/17.
 * <p>
 * Listener for custom data changed.
 */
public class OnVagueDataSetChangeListener {

    /**
     * Call back for custom data changed.
     */
    public void onVagueDataSetChange() {
    }

    /**
     * Call back for custom data changed.
     *
     * @param year  year of date that data change
     * @param month month of date that data change
     */
    public void onVagueDataSetChange(int year, @Month int month) {
    }

}
