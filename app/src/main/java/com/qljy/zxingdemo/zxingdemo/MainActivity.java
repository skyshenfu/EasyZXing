package com.qljy.zxingdemo.zxingdemo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.client.android.CaptureActivity;
import com.google.zxing.client.android.callback.QRCodeReadyCallback;
import com.google.zxing.client.android.encode.QRCodeEncoder;
import com.google.zxing.client.android.util.QRUtil;

public class MainActivity extends AppCompatActivity {
    private EditText qrEditText;
    private Button  genButton;
    private Button  scanButton;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        qrEditText=findViewById(R.id.code_edit_text);
        imageView=findViewById(R.id.code_image_view);
        genButton=findViewById(R.id.gen_qr_code_button);
        scanButton=findViewById(R.id.scan_qr_code_button);
        initListener();
    }

    private void initListener() {
        genButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text=qrEditText.getText().toString().trim();
                genQRCode(text);

            }
        });
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,CaptureActivity.class));
            }
        });
    }
    private void genQRCode(String text) {
        QRUtil.getInstance().gainQRBitmap(this, text, new QRCodeReadyCallback() {
            @Override
            public void bitmapReady(Bitmap bitmap) {
                imageView.setImageBitmap(bitmap);
            }

            @Override
            public void bitmapError() {

            }
        });
    }
}
