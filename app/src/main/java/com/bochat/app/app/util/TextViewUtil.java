package com.bochat.app.app.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ImageSpan;
import android.widget.TextView;

/**
 * Author      : FJ
 * CreateDate  : 2019/06/24 13:46
 * Description :
 */

public class TextViewUtil {
    public static void appendImage(TextView textView, String text, int imageSourceId){
        Bitmap bitmap = BitmapFactory.decodeResource(textView.getResources(), imageSourceId);
        ImageSpan imgSpan = new ImageSpan(textView.getContext(), bitmap, DynamicDrawableSpan.ALIGN_BASELINE);
        String spanText = text + "  ";
        SpannableString spanString = new SpannableString(spanText);
        spanString.setSpan(imgSpan, spanText.length() - 1, spanText.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        textView.setText(spanString);
    }
}
