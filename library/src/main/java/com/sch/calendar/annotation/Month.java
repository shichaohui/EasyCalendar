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
 * Limited month to prevent the occurrence of larger or smaller numbers.
 */
@Retention(RetentionPolicy.SOURCE)
@IntRange(from = Month.JANUARY, to = Month.DECEMBER)
public @interface Month {

    /**
     * 一月 | January
     */
    int JANUARY = 0;

    /**
     * 二月 | February
     */
    int FEBRUARY = 1;

    /**
     * 三月 | March
     */
    int MARCH = 2;

    /**
     * 四月 | April
     */
    int APRIL = 3;

    /**
     * 五月 | May
     */
    int MAY = 4;

    /**
     * 六月 | June
     */
    int JUNE = 5;

    /**
     * 七月 | July
     */
    int JULY = 6;

    /**
     * 八月 | August
     */
    int AUGUST = 7;

    /**
     * 九月 | September
     */
    int SEPTEMBER = 8;

    /**
     * 十月 | October
     */
    int OCTOBER = 9;

    /**
     * 十一月 | November
     */
    int NOVEMBER = 10;

    /**
     * 十二月 | December
     */
    int DECEMBER = 11;
}