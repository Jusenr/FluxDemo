package com.example.library.image.processor;

import android.content.Context;
import android.graphics.Bitmap;

/**
 * 图片处理接口
 * Created by guchenkai on 2015/11/17.
 */
public abstract class ProcessorInterface {

    public abstract void process(Context context, Bitmap bitmap);

    public void process(Context context, Bitmap bitmap, int position) {

    }
}
