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

import android.support.annotation.LayoutRes;

import com.sch.calendar.R;

/**
 * Created by StoneHui on 17/2/17.
 * <p>
 * Simple implementation of {@link VagueAdapter}. Only show date, no custom data.
 */
public class SampleVagueAdapter extends VagueAdapter {

    public SampleVagueAdapter() {
        super(R.layout.def_date_layout);
    }

    /**
     * Initialization adapter.
     *
     * @param dayLayout layout for date
     */
    public SampleVagueAdapter(@LayoutRes int dayLayout) {
        super(dayLayout);
    }

}
