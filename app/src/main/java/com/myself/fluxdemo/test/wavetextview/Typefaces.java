package com.myself.fluxdemo.test.wavetextview;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;

import java.util.Hashtable;

public class Typefaces {
    private static final String TAG = Typefaces.class.getSimpleName();
    public static final String ASSETPATH = "Satisfy-Regular.ttf";
    private static final Hashtable<String, Typeface> cache = new Hashtable<>();

    public static Typeface get(Context context, String assetPath) {
        synchronized (cache) {
            if (!cache.containsKey(assetPath)) {
                try {
                    Typeface t = Typeface.createFromAsset(context.getAssets(), assetPath);
                    cache.put(assetPath, t);
                } catch (Exception e) {
                    Log.e(TAG, "Could not get typeface '" + assetPath + "' because " + e.getMessage());
                    return null;
                }
            }

            return cache.get(assetPath);
        }
    }

    public static Typeface get(Context context) {
        synchronized (cache) {
            if (!cache.containsKey(ASSETPATH)) {
                try {
                    Typeface t = Typeface.createFromAsset(context.getAssets(), ASSETPATH);
                    cache.put(ASSETPATH, t);
                } catch (Exception e) {
                    Log.e(TAG, "Could not get typeface '" + ASSETPATH + "' because " + e.getMessage());
                    return null;
                }
            }

            return cache.get(ASSETPATH);
        }
    }
}