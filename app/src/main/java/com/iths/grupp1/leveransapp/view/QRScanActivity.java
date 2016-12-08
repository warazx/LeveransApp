package com.iths.grupp1.leveransapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.zxing.Result;
import com.iths.grupp1.leveransapp.R;
import com.iths.grupp1.leveransapp.database.OrderSQLiteOpenHelper;
import com.iths.grupp1.leveransapp.model.Customer;
import com.iths.grupp1.leveransapp.model.Order;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static com.iths.grupp1.leveransapp.adapter.OrderAdapter.EXTRA_CUSTOMER;
import static com.iths.grupp1.leveransapp.adapter.OrderAdapter.EXTRA_ORDER;

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


    /**
     * Takes the result from the scan and check if it gets a match from the database.
     * Upon match sends the user to that orders page.
     * @param result value of the scan.
     */
    @Override
    public void handleResult(Result result) {
        // Do something with the result here
        String value = result.getText();
        int intValue;
        try {
            intValue = Integer.parseInt(value);
            Log.v(TAG, value); // Prints scan results
            String typeOfBarcode = result.getBarcodeFormat().toString();
            Log.v(TAG, typeOfBarcode); // Prints the scan format (qrcode, pdf417 etc.)

            OrderSQLiteOpenHelper db = new OrderSQLiteOpenHelper(this);
            Order order = db.getOrder(intValue);

            if (order != null) {
                String message = String.format(getString(R.string.activity_qr_scan_order_found_toast), result.getText());
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                Customer customer = db.getCustomer(order.getCustomer());

                Intent intent = new Intent(this, OrderActivity.class);
                intent.putExtra(EXTRA_ORDER, order);
                intent.putExtra(EXTRA_CUSTOMER, customer);
                startActivity(intent);
                finish();
            }
        } catch (Exception e) {
        Toast.makeText(this, R.string.activity_qr_scan_invalid_order_toast, Toast.LENGTH_SHORT).show();
    }

        // If you would like to resume scanning, call this method below:
        scannerView.resumeCameraPreview(this);
    }
}
