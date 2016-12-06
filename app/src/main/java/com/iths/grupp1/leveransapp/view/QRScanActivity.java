package com.iths.grupp1.leveransapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QRScanActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private static final String TAG = "SCANNER_VIEW";
    public static final String ORDER_ID_KEY = "ORDER_ID_KEY";

    private ZXingScannerView scannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);
    }

    @Override
    public void onResume() {
        super.onResume();
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        scannerView.stopCamera(); // Stop camera on pause
    }


    @Override
    public void handleResult(Result result) {
        // Do something with the result here
        String value = result.getText();
        Log.v(TAG, value); // Prints scan results
        String typeOfBarcode = result.getBarcodeFormat().toString();
        Log.v(TAG, typeOfBarcode); // Prints the scan format (qrcode, pdf417 etc.)

        if(!result.getText().isEmpty()) {
            String message = String.format("Order %s found!", result.getText());
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, OrderActivity.class);
            intent.putExtra(ORDER_ID_KEY, Integer.parseInt(result.getText()));
            startActivity(intent);
        }

        // If you would like to resume scanning, call this method below:
        scannerView.resumeCameraPreview(this);
    }
}
