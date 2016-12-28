package com.example.library.utils;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.example.library.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

/**
 * 图片处理工具
 * Created by guchenkai on 2015/12/8.
 */
public final class ImageUtils {

    /**
     * 将小图片x轴循环填充进imageView中
     *
     * @param imageView imageView
     * @param bitmap    目标图片
     */
    public static void fillXInImageView(Context context, ImageView imageView, Bitmap bitmap) {
        int screenWidth = DensityUtil.getScreenW(context);//屏幕宽度
        imageView.setImageBitmap(createRepeater(screenWidth, bitmap));
    }


    /**
     * 将图片在imageView中x轴循环填充需要的bitmap
     *
     * @param width 填充宽度
     * @param src   图片源
     * @return 新图片源
     */
    private static Bitmap createRepeater(int width, Bitmap src) {
        int count = (width + src.getWidth() - 1) / src.getWidth(); //计算出平铺填满所给width（宽度）最少需要的重复次数
        Bitmap bitmap = Bitmap.createBitmap(src.getWidth() * count, src.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        for (int idx = 0; idx < count; ++idx) {
            canvas.drawBitmap(src, idx * src.getWidth(), 0, null);
        }
        return bitmap;
    }

    /**
     * 根据路径获得图片并压缩，返回bitmap用于显示
     *
     * @param filePath 图片路径
     * @param width    压缩后的宽
     * @param height   压缩后的高
     * @return
     */
    public static Bitmap getSmallBitmap(String filePath, int width, int height) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, width, height);
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    /**
     * 计算图片的缩放值
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    /**
     * 将bitmap输出到sdCard中
     *
     * @param outPath 输出路径
     */
    public static void bitmapOutSdCard(Bitmap bitmap, String outPath) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(outPath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 截取滚动屏画面保存到图片
     *
     * @param view     view源
     * @param savePath 保存路径
     */
    public static void cutOutScrollViewToImage(ScrollView view, String savePath, OnImageSaveCallback callback) {
        int mSrollViewHeight = 0;
        for (int i = 0; i < view.getChildCount(); i++) {
            View child = view.getChildAt(i);
            if (child instanceof LinearLayout || child instanceof RelativeLayout) {
                child.setBackgroundResource(R.color.white);
                mSrollViewHeight += child.getHeight();
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), mSrollViewHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        File file = new File(savePath);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            boolean isSuccess = bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            Logger.d(isSuccess ? "保存图片成功" : "保存图片成功");
            if (callback != null) callback.onImageSave(isSuccess);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 截取滚动屏画面保存到图片
     *
     * @param view     view源
     * @param savePath 保存路径
     */
    public static void cutOutScrollViewToImage(ScrollView view, String savePath) {
        cutOutScrollViewToImage(view, savePath, null);
    }

    /**
     * 截取画面保存到图片
     *
     * @param view     view源
     * @param savePath 保存路径
     */
    public static void cutOutViewToImage(View view, String savePath, OnImageSaveCallback callback) {
//        int mSrollViewHeight = 0;
//        for (int i = 0; i < view.getChildCount(); i++) {
//            View child = view.getChildAt(i);
//            if (child instanceof LinearLayout || child instanceof RelativeLayout) {
//                child.setBackgroundResource(R.color.white);
//                mSrollViewHeight += child.getHeight();
//            }
//        }
        view.setBackgroundResource(R.color.white);
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        File file = new File(savePath);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            boolean isSuccess = bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            Logger.d(isSuccess ? "保存图片成功" : "保存图片成功");
            if (callback != null) callback.onImageSave(isSuccess);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 截取画面保存到图片
     *
     * @param view     view源
     * @param savePath 保存路径
     */
    public static void cutOutViewToImage(View view, String savePath) {
        cutOutViewToImage(view, savePath, null);
    }

    /**
     * 截取view画面保存至bitmap
     *
     * @param view view源
     */
    public static Bitmap cutOutViewToSmallBitmap(Context context, View view) {
//        return Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);

        Bitmap bitmap = Bitmap.createBitmap(DensityUtil.dp2px(context, 30), DensityUtil.dp2px(context, 30), Bitmap.Config.ARGB_8888);
        if (null != view) {
            view.setBackgroundResource(R.color.white);
            Canvas canvas = new Canvas(bitmap);
            view.draw(canvas);
        }
        return bitmap;
        /*Matrix matrix = new Matrix();
        matrix.postScale(0.1f, 0.1f); //长和宽放大缩小的比例
        Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizeBmp;*/
    }

    /**
     * @param context
     * @param filepath
     * @return
     */
    public static File createScaledBitmapFile(Context context, String filepath, int width, int height) {
        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filepath, bmOptions);
        int scaleFactor = calculateInSampleSize(bmOptions, width, height);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPreferredConfig = Bitmap.Config.RGB_565;
        bmOptions.inPurgeable = true;
        bmOptions.inInputShareable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(filepath, bmOptions);

        File file = new File(FileUtils.getPicDirectory(context), FileUtils.getFileName(filepath));
        FileOutputStream out = null;
        try {
            if (bitmap.getWidth() == 0 || bitmap.getHeight() == 0)
                return null;
            out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    /**
     * create a new image file
     *
     * @return
     * @throws IOException
     */
    public static File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM), "Camera");
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        return image;
    }

    /**
     *
     */
    public interface OnImageSaveCallback {

        void onImageSave(boolean isSuccess);
    }


    /**
     * 根据Uri获取图片绝对路径，解决Android4.4以上版本Uri转换
     *
     * @param context
     * @param imageUri
     * @date 2014-10-12
     */
    public static String getImageAbsolutePath(Activity context, Uri imageUri) {
        if (context == null || imageUri == null)
            return null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT && DocumentsContract.isDocumentUri(context, imageUri)) {
            if (isExternalStorageDocument(imageUri)) {
                String docId = DocumentsContract.getDocumentId(imageUri);
                String[] split = docId.split(":");
                String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            } else if (isDownloadsDocument(imageUri)) {
                String id = DocumentsContract.getDocumentId(imageUri);
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            } else if (isMediaDocument(imageUri)) {
                String docId = DocumentsContract.getDocumentId(imageUri);
                String[] split = docId.split(":");
                String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                String selection = MediaStore.Images.Media._ID + "=?";
                String[] selectionArgs = new String[]{split[1]};
                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        } // MediaStore (and general)
        else if ("content".equalsIgnoreCase(imageUri.getScheme())) {
            // Return the remote address
            if (isGooglePhotosUri(imageUri))
                return imageUri.getLastPathSegment();
            return getDataColumn(context, imageUri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(imageUri.getScheme())) {
            return imageUri.getPath();
        }
        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        String column = MediaStore.Images.Media.DATA;
        String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    public static boolean isImage(String url) {
        if (StringUtils.isEmpty(url)) return false;
//        String upperUrl = new String();
        url = url.toUpperCase();
        if (url.contains(".JPG") || url.contains(".PNG") || url.contains(".GIF") || url.contains(".JPEG") || url.contains(".BMP"))
            return true;
        else return false;
    }

    public static String getImageSizeUrl(String url, ImageSizeURL type) {
        if (!isImage(url)) return "";
        else {
            int i = url.lastIndexOf(".");
            String font = url.substring(0, i);
            switch (type) {
                case SIZE__1200X400:
                    font += "_1200x400";
                    break;
                case SIZE_1200x750:
                    font += "_1200x750";
                    break;
                case SIZE_1200x600:
                    font += "_1200x600";
                    break;
                case SIZE_240x240:
                    font += "_240x240";
                    break;
                case SIZE_360x360:
                    font += "_360x360";
                    break;
                case SIZE_180x180:
                    font += "_180x180";
                    break;
                case SIZE_1200x1200:
                    font += "_1200x1200";
                    break;
                case SIZE_120x120:
                    font += "_120x120";
                    break;
                case SIZE_150x150:
                    font += "_150x150";
                    break;
                case SIZE_192x192:
                    font += "_192x192";
                    break;
                case SIZE_96x96:
                    font += "_96x96";
                    break;
                case SIZE_250x0:
                    font += "_250x0";
                    break;
            }
            return font + url.substring(i, url.length());
        }
    }

    public enum ImageSizeURL {
        SIZE__1200X400, SIZE_1200x750, SIZE_1200x600, SIZE_240x240, SIZE_360x360, SIZE_180x180, SIZE_1200x1200, SIZE_120x120, SIZE_150x150, SIZE_192x192, SIZE_96x96, SIZE_250x0
    }

    /**
     * 生成一个二维码图像
     *
     * @param url            传入的字符串，通常是一个URL
     * @param widthAndHeight 图像的宽高
     */
    public static Bitmap createQRCode(String url, int widthAndHeight)
            throws WriterException {
        try {
            // 处理汉字，如果不用更改源码，将字符串转换成ISO-8859-1编码
            url = new String(url.getBytes("UTF-8"), "ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        BitMatrix matrix = new MultiFormatWriter().encode(url,
                BarcodeFormat.QR_CODE, widthAndHeight, widthAndHeight);
        matrix = updateBit(matrix, 8);
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        int[] pixels = new int[width * height];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (matrix.get(x, y)) {
                    pixels[y * width + x] = BLACK;
                } else {
                    pixels[y * width + x] = WHITE;
                }
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    public static BitMatrix updateBit(BitMatrix matrix, int margin) {
        int tempM = margin * 2;
        int[] rec = matrix.getEnclosingRectangle();   //获取二维码图案的属性
        int resWidth = rec[2] + tempM;
        int resHeight = rec[3] + tempM;
        BitMatrix resMatrix = new BitMatrix(resWidth, resHeight); // 按照自定义边框生成新的BitMatrix
        resMatrix.clear();
        for (int i = margin; i < resWidth - margin; i++) {   //循环，将二维码图案绘制到新的bitMatrix中
            for (int j = margin; j < resHeight - margin; j++) {
                if (matrix.get(i - margin + rec[0], j - margin + rec[1])) {
                    resMatrix.set(i, j);
                }
            }
        }
        return resMatrix;
    }

    /**
     * 处理图片
     *
     * @param bitmap    所要转换的bitmap
     * @param newWidth  新的宽
     * @param newHeight 新的高
     * @return 指定宽高的bitmap
     */
    public static Bitmap zoomImg(Bitmap bitmap, int newWidth, int newHeight) {
        // 获得图片的宽高
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
    }

    private static String generateQRUrl(String sn) {
        return "http://m.putao.com/weidu?SN=" + sn + "&type=ptdevice";
    }

    /**
     * generate qr bitmap
     *
     * @param qrCodeUrl
     * @return
     */
    public static Bitmap generateBase64QRBitmap(String qrCodeUrl) {
//        String qrCodeUrl = generateQRUrl(sn);
        QRCodeWriter write = new QRCodeWriter();
        try {
            BitMatrix matrix = new MultiFormatWriter().encode(new String(qrCodeUrl.getBytes("UTF-8"), "iso-8859-1"), BarcodeFormat.QR_CODE, 200, 200);
            Bitmap bitmap = generateQRBitmap(matrix);
            return bitmap;
        } catch (Exception e) {
            String err = e.getMessage();
        }
        return null;
    }

    /**
     * generate qr bitmap according to the assign size
     *
     * @param qrCodeUrl
     * @param width
     * @param height
     * @return
     */
    public static Bitmap generateBase64QRBitmap(String qrCodeUrl, int width, int height) {
//        String qrCodeUrl = generateQRUrl(sn);
        QRCodeWriter write = new QRCodeWriter();
        try {
            Map<EncodeHintType, Object> hints = new HashMap<>();//配置参数
//            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
//            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);  //容错级别

            BitMatrix matrix = new MultiFormatWriter().encode(new String(qrCodeUrl.getBytes("UTF-8"), "iso-8859-1"), BarcodeFormat.QR_CODE, width, height, hints);
            Bitmap bitmap = generateQRBitmap(matrix);
            return bitmap;
        } catch (Exception e) {
            String err = e.getMessage();
        }
        return null;
    }

    public static Bitmap generateQRBitmap(BitMatrix matrix) {
        int w = matrix.getWidth();
        int h = matrix.getHeight();
        int[] rawData = new int[w * h];
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                int color = Color.WHITE;
                if (matrix.get(i, j)) {
                    color = Color.BLACK;
                }
                rawData[i + (j * w)] = color;
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.RGB_565);
        bitmap.setPixels(rawData, 0, w, 0, 0, w, h);
        return bitmap;
    }

    public static String bitmap2Base64(Bitmap bmp) {
        String result = null;
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        try {
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, bao);
            byte[] bitmapBytes = bao.toByteArray();
            bao.flush();
            bao.close();
            result = Base64.encodeToString(bitmapBytes, Base64.NO_WRAP);
        } catch (Exception ex) {

        } finally {
            return result;
        }
    }

    /**
     * 在二维码中间添加Logo图案
     *
     * @param src
     * @param logo
     * @return
     */
    public static Bitmap addLogoBitmap(Bitmap src, Bitmap logo) {
        if (src == null) {
            return null;
        }
        if (logo == null) {
            return src;
        }
        // 获取图片的宽高
        int srcWidth = src.getWidth();
        int srcHeight = src.getHeight();
        int logoWidth = logo.getWidth();
        int logoHeight = logo.getHeight();
        if (srcWidth == 0 || srcHeight == 0) {
            return null;
        }
        if (logoWidth == 0 || logoHeight == 0) {
            return src;
        }
        // logo大小为二维码整体大小的1/5
        float scaleFactor = srcWidth * 1.0f / 5 / logoWidth;
        Bitmap bitmap = Bitmap.createBitmap(srcWidth, srcHeight, Bitmap.Config.ARGB_8888);
        try {
            Canvas canvas = new Canvas(bitmap);
            canvas.drawBitmap(src, 0, 0, null);
            canvas.scale(scaleFactor, scaleFactor, srcWidth / 2, srcHeight / 2);
            canvas.drawBitmap(logo, (srcWidth - logoWidth) / 2, (srcHeight - logoHeight) / 2, null);

            canvas.save(Canvas.ALL_SAVE_FLAG);
            canvas.restore();
        } catch (Exception e) {
            bitmap = null;
            e.getStackTrace();
        }

        return bitmap;
    }

    /**
     * Write text to Bitmap
     *
     * @param bitmap
     * @param text
     * @return
     */
    public static Bitmap drawTextToBitmap(Bitmap bitmap, String text, int textColor, int textSize) {
        int mTextColor = textColor == 0 ? Color.BLACK : textColor;
        int mTextSize = textSize == 0 ? 12 : textSize;
        try {
            int scale = bitmap.getDensity();
            Log.i("drawTextToBitmap: ", "scale=" + scale);
            Bitmap.Config bitmapConfig = bitmap.getConfig();
            if (bitmapConfig == null) {
                bitmapConfig = Bitmap.Config.ARGB_8888;
            }
            bitmap = bitmap.copy(bitmapConfig, true);
            Canvas canvas = new Canvas(bitmap);
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setColor(mTextColor);
            paint.setTextSize(mTextSize);
//            paint.setShadowLayer(1f, 0f, 1f, Color.DKGRAY);
            Rect bounds = new Rect();
            paint.getTextBounds(text, 0, text.length(), bounds);
            int x = bitmap.getWidth() / 4;
            int y = bitmap.getHeight() - 10;
//            canvas.drawText(text, x * scale, y * scale, paint);
            canvas.drawText(text, x, y, paint);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Write text to Bitmap
     *
     * @param mContext
     * @param resourceId
     * @param text
     * @return
     */
    public static Bitmap drawTextToBitmap(Context mContext, int resourceId, String text, int textColor, int textSize) {
        int mTextColor = textColor == 0 ? Color.BLACK : textColor;
        int mTextSize = textSize == 0 ? 12 : textSize;
        try {
            Resources resources = mContext.getResources();
            float scale = resources.getDisplayMetrics().density;
            Log.i("drawTextToBitmap: ", "scale=" + scale);
            Bitmap bitmap = BitmapFactory.decodeResource(resources, resourceId);

            Bitmap.Config bitmapConfig = bitmap.getConfig();
            if (bitmapConfig == null) {
                bitmapConfig = Bitmap.Config.ARGB_8888;
            }
            bitmap = bitmap.copy(bitmapConfig, true);
            Canvas canvas = new Canvas(bitmap);
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setColor(mTextColor);
            paint.setTextSize(mTextSize);
//            paint.setShadowLayer(1f, 0f, 1f, Color.DKGRAY);
            Rect bounds = new Rect();
            paint.getTextBounds(text, 0, text.length(), bounds);
            int x = 2;
            int y = bitmap.getHeight() - 10;
//            canvas.drawText(text, x * scale, y * scale, paint);
            canvas.drawText(text, x, y, paint);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
