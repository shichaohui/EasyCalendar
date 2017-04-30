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

package com.sch.calendar.annotation;

import android.support.annotation.IntRange;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by StoneHui on 17/2/16.
 * <p>
 * Limited week to prevent the occurrence of larger or smaller numbers.
 */
@Retention(RetentionPolicy.SOURCE)
@IntRange(from = Week.SUNDAY, to = Week.SATURDAY)
public @interface Week {

    /**
     * 星期日 | Sunday
     */
    int SUNDAY = 0;

    /**
     * 星期一 | Monday
     */
    int MONDAY = 1;

    /**
     * 星期二 | Tuesday
     */
    int TUESDAY = 2;

    /**
     * 星期三 | Wednesday
     */
    int WEDNESDAY = 3;

    /**
     * 星期四 | Thursday
     */
    int THURSDAY = 4;

    /**
     * 星期五 | Friday
     */
    int FRIDAY = 5;

    /**
     * 星期六 | Saturday
     */
    int SATURDAY = 6;

}
