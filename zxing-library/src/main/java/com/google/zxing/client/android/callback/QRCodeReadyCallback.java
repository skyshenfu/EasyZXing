package com.google.zxing.client.android.callback;

import android.graphics.Bitmap;

public interface QRCodeReadyCallback {
    void bitmapReady(Bitmap bitmap);
    void bitmapError();
}
