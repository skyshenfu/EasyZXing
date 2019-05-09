package com.google.zxing.client.android.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.android.Contents;
import com.google.zxing.client.android.Intents;
import com.google.zxing.client.android.callback.QRCodeReadyCallback;
import com.google.zxing.client.android.encode.QRCodeEncoder;

import static android.content.Context.WINDOW_SERVICE;

public class QRUtil {
    private QRUtil(){

    }

    public static QRUtil getInstance(){
        return QRUtilHolder.instance;
    }
    private static class QRUtilHolder {
        private static QRUtil instance = new QRUtil();
    }

    public synchronized void gainQRBitmap(Context context, String text,QRCodeReadyCallback callback){
        WindowManager manager = (WindowManager) context.getSystemService(WINDOW_SERVICE);
        QRCodeEncoder qrCodeEncoder;
        Display display = manager.getDefaultDisplay();
        Point displaySize = new Point();
        display.getSize(displaySize);
        int width = displaySize.x;
        int height = displaySize.y;
        int smallerDimension = width < height ? width : height;
        smallerDimension = smallerDimension * 7 / 8;
        Intent intent = new Intent(Intents.Encode.ACTION);
        intent.addFlags(Intents.FLAG_NEW_DOC);
        intent.putExtra(Intents.Encode.TYPE, Contents.Type.TEXT);
        intent.putExtra(Intents.Encode.DATA, text);
        intent.putExtra(Intents.Encode.FORMAT, BarcodeFormat.QR_CODE.toString());

        Bitmap bitmap = null;

        try {
            qrCodeEncoder = new QRCodeEncoder(context, intent, smallerDimension, false);
            bitmap = qrCodeEncoder.encodeAsBitmap();
            callback.bitmapReady(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        if (bitmap == null) {
            qrCodeEncoder = null;
            callback.bitmapError();
        }
    }

}
