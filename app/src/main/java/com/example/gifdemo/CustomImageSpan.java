package com.example.gifdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.style.ImageSpan;

import androidx.annotation.NonNull;

public class CustomImageSpan extends ImageSpan {
    public CustomImageSpan(@NonNull Bitmap b) {
        super(b);
    }

    public CustomImageSpan(@NonNull Bitmap b, int verticalAlignment) {
        super(b, verticalAlignment);
    }

    public CustomImageSpan(@NonNull Context context, @NonNull Bitmap bitmap) {
        super(context, bitmap);
    }

    public CustomImageSpan(@NonNull Context context, @NonNull Bitmap bitmap, int verticalAlignment) {
        super(context, bitmap, verticalAlignment);
    }

    public CustomImageSpan(@NonNull Drawable drawable) {
        super(drawable);
    }

    public CustomImageSpan(@NonNull Drawable drawable, int verticalAlignment) {
        super(drawable, verticalAlignment);
    }

    public CustomImageSpan(@NonNull Drawable drawable, @NonNull String source) {
        super(drawable, source);
    }

    public CustomImageSpan(@NonNull Drawable drawable, @NonNull String source, int verticalAlignment) {
        super(drawable, source, verticalAlignment);
    }

    public CustomImageSpan(@NonNull Context context, @NonNull Uri uri) {
        super(context, uri);
    }

    public CustomImageSpan(@NonNull Context context, @NonNull Uri uri, int verticalAlignment) {
        super(context, uri, verticalAlignment);
    }

    public CustomImageSpan(@NonNull Context context, int resourceId) {
        super(context, resourceId);
    }

    public CustomImageSpan(@NonNull Context context, int resourceId, int verticalAlignment) {
        super(context, resourceId, verticalAlignment);
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        Drawable b = getDrawable();
        canvas.save();
//        int transY = 0;
//        //获得将要显示的文本高度 - 图片高度除2 = 居中位置+top(换行情况)
//        transY = ((bottom - top) - b.getBounds().bottom) / 2 + top;
        //偏移画布后开始绘制
        canvas.translate(x, top);
        b.draw(canvas);
        canvas.restore();
    }

    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end,
                       Paint.FontMetricsInt fm) {
        Drawable d = getDrawable();
        Rect rect = d.getBounds();
        if (fm != null) {
            Paint.FontMetricsInt fmPaint = paint.getFontMetricsInt();
            //获得文字、图片高度
            int fontHeight = fmPaint.bottom - fmPaint.top;
            int drHeight = rect.bottom - rect.top;

            int top = drHeight / 2 - fontHeight / 4;
            int bottom = drHeight / 2 + fontHeight / 4;

            fm.ascent = -bottom;
            fm.top = -bottom;
            fm.bottom = top;
            fm.descent = top;
        }
        return rect.right;
    }
}
