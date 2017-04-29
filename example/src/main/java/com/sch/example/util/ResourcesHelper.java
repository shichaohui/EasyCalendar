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

package com.sch.example.util;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;


/**
 * Created by Stone辉 on 17/2/16.
 * <p>
 * 资源辅助类
 */
public class ResourcesHelper {

    public static Resources getResources(Context context) {
        return context.getApplicationContext().getResources();
    }

    /**
     * 获取字符串
     *
     * @param context 上下文
     * @param resId   资源ID
     */
    public static String getString(Context context, @StringRes int resId) {
        return getResources(context).getString(resId);
    }

    /**
     * 获取颜色
     *
     * @param context 上下文
     * @param resId   资源ID
     */
    public static int getColor(Context context, @ColorRes int resId) {
        if (Build.VERSION.SDK_INT >= 23) {
            return getResources(context).getColor(resId, context.getTheme());
        }

        return getResources(context).getColor(resId);
    }

    /**
     * 获取颜色
     *
     * @param context 上下文
     * @param resId   资源ID
     */
    public static ColorStateList getColorStateList(Context context, @ColorRes int resId) {
        if (Build.VERSION.SDK_INT >= 23) {
            return getResources(context).getColorStateList(resId, context.getTheme());
        }

        return getResources(context).getColorStateList(resId);
    }

    /**
     * 获取Drawable
     *
     * @param context 上下文
     * @param resId   资源ID
     */
    public static Drawable getDrawable(Context context, @DrawableRes int resId) {
        if (Build.VERSION.SDK_INT >= 23) {
            return getResources(context).getDrawable(resId, context.getTheme());
        }
        return getResources(context).getDrawable(resId);
    }

    /**
     * 获取尺寸资源
     *
     * @param context 上下文
     * @param resId   资源ID
     */
    public static float getDimen(Context context, @DimenRes int resId) {
        return getResources(context).getDimension(resId);
    }

}
