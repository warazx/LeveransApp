package com.iths.grupp1.leveransapp.view;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.iths.grupp1.leveransapp.R;
import com.iths.grupp1.leveransapp.adapter.OrderAdapter;
import com.iths.grupp1.leveransapp.database.OrderSQLiteOpenHelper;
import com.iths.grupp1.leveransapp.model.Customer;
import com.iths.grupp1.leveransapp.model.Order;
import com.iths.grupp1.leveransapp.util.GpsTracker;
import com.iths.grupp1.leveransapp.model.Session;

/**
 * Show info about one specific order. Has different views depending on if the order
 * has been delivered or not.
 */
public class OrderActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, OnMapReadyCallback {

    private static final String PERM_CHECK = "PERMISSION_CHECK";
    private final String TAG = "TAG";
    private static final int SEND_SMS_PERMISSION = 1;
    private static final int ACCESS_FINE_LOCATION = 2;

    private TextView orderIdText;
    private TextView placedDateText;
    private TextView customerNameText;
    private TextView deliveryAddressText;
    private TextView phoneNumberText;
    private TextView deliveredDateText;
    private TextView orderSumText;
    private Button deliveryBtn;

    private SmsManager smsManager;
    private GoogleApiClient googleApiClient;
    private SharedPreferences sharedPref;
    private MapFragment mapFragment;
    private GoogleMap googleMap;

    private Order order;
    private Customer customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        getIntentFromList();
        initVarsWithRes();
        toggleLayout();
        initVarValues();
    }

    // Gets the intent from the RecyclerView and gets the information about the objects.
    private void getIntentFromList() {
        Intent intent = getIntent();
        if(intent.hasExtra(OrderAdapter.EXTRA_ORDER)) {
            order = intent.getExtras().getParcelable(OrderAdapter.EXTRA_ORDER);
            customer = intent.getExtras().getParcelable(OrderAdapter.EXTRA_CUSTOMER);
        } else {
            int orderID = intent.getIntExtra(QRScanActivity.ORDER_ID_KEY, -1);
            OrderSQLiteOpenHelper db = new OrderSQLiteOpenHelper(this);
            order = db.getOrder(orderID);
            int customerID = order.getCustomer();
            customer = db.getCustomer(customerID);
        }
    }

    // Binds the variables with its resource counterpart.
    private void initVarsWithRes() {
        orderIdText = (TextView) findViewById(R.id.order_activity_orderid_value);
        placedDateText = (TextView) findViewById(R.id.order_activity_placed_value);
        customerNameText = (TextView) findViewById(R.id.order_activity_name_value);
        deliveryAddressText = (TextView) findViewById(R.id.order_activity_delivery_value);
        phoneNumberText = (TextView) findViewById(R.id.order_activity_phone_value);
        deliveredDateText = (TextView) findViewById(R.id.activity_order_delivered_date_value);
        orderSumText = (TextView) findViewById(R.id.activity_order_order_sum_value);
        deliveryBtn = (Button) findViewById(R.id.order_activity_delivery_btn);
        smsManager = SmsManager.getDefault();
        sharedPref = getSharedPreferences(SettingsActivity.STATUS_USER_SETTINGS, Context.MODE_PRIVATE);
        initGoogleApiClient();
        mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void initGoogleApiClient() {
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    // Sets the initial values for the variables;
    private void initVarValues() {
        orderIdText.setText(order.getOrderNumber() + "");
        placedDateText.setText(order.getFormattedPlacementDate());
        customerNameText.setText(customer.getName());
        deliveryAddressText.setText(customer.getAddress());
        phoneNumberText.setText(customer.getPhoneNumber());
        deliveredDateText.setText(order.getFormattedDeliveryDate());
        orderSumText.setText(order.getOrderSum() + " kr");
    }

    /**
     * Inflates meny for this activity
     * @param menu
     * @return
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_order_menu, menu);
        return true;
    }

    /**
     * Show Settings when user click on item in actionbar
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.actionbar_settings_item:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.actionbar_logout_item:
                activityLogOut();
                break;
            default:
                Log.d(TAG, getString(R.string.log_message));
        }

        return super.onOptionsItemSelected(item);
    }

    /*
    Toggles between the views depending on if the order has been delivered or not.
     */
    private void toggleLayout() {
        if (order.isDelivered()) {
            mapFragment.getMapAsync(this);
            findViewById(R.id.ll_delivered).setVisibility(View.VISIBLE);
            findViewById(R.id.ll_not_delivered).setVisibility(View.GONE);
        } else {
            findViewById(R.id.ll_delivered).setVisibility(View.GONE);
            findViewById(R.id.ll_not_delivered).setVisibility(View.VISIBLE);
        }
    }

    /**
     * Changes the order to delivered. Updates the database with the location and changes
     * the display to match an delivered order.
     */
    public void deliverOrder(View view) {
        order.setDelivered(true);
        long time = System.currentTimeMillis();
        order.setDeliveryDate(time);
        try {
            order.setDeliveryLocation();
        } catch (Exception e) {
            e.getStackTrace();
            Toast.makeText(this, "No GPS info", Toast.LENGTH_SHORT).show();
        }
        deliveredDateText.setText(order.getFormattedDeliveryDate());
        toggleLayout();

        OrderSQLiteOpenHelper db = new OrderSQLiteOpenHelper(this);
        db.updateOrder(order);

        sendSMS();
    }

    /*
     * Sends sms to user when an order is delivered.
     */
    private void sendSMS() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, SEND_SMS_PERMISSION);
                Log.d(PERM_CHECK, String.format("Asking for permission: %s", Manifest.permission.SEND_SMS.toString()));
            }
        } else {
            sendSmsConfirmation();
        }
    }

    private void saveCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Location currentLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            GpsTracker.setLastLocation(currentLocation);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case SEND_SMS_PERMISSION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    sendSmsConfirmation();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "Need permission to use service", Toast.LENGTH_SHORT).show();
                }
                return;
            }
            case ACCESS_FINE_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    saveCurrentLocation();
                } else {
                    Toast.makeText(this, "Need permission to use service", Toast.LENGTH_SHORT).show();
                }
            }


            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private void sendSmsConfirmation() {
        String phoneNumber = sharedPref.getString(SettingsActivity.PHONE_NUMBER_TO_ADD, "0");
        String message = String.format(getString(R.string.activity_order_sms_message), order.getOrderNumber());

        try {
            smsManager.sendTextMessage(phoneNumber, "", message, null, null);
        } catch (IllegalArgumentException iae) {
            iae.printStackTrace();
            Toast.makeText(this, "Invalid phone number, please change in settings.", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "SMS could not be sent", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Starts Google Maps Navigation from the user's current location to the current order customer address."
     */
    public void startNavigation(View view) {
        String address = customer.getAddress();
        Uri uri = Uri.parse("google.navigation:q=" + address + "&mode=d");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setPackage(getString(R.string.activity_order_google_maps_package));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                Log.d(PERM_CHECK, String.format("Asking for permission: %s", Manifest.permission.ACCESS_FINE_LOCATION.toString()));
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_FINE_LOCATION);
            }
        } else {
            saveCurrentLocation();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onStart() {
        if (! Session.isSessionValid(this)) {
            activityLogOut();
        } else {
            googleApiClient.connect();
        }
        super.onStart();
    }

    @Override
    protected void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        LatLng deliveredPos = new LatLng(order.getDeliveryLatitude(), order.getDeliveryLongitude());
        googleMap.addMarker(new MarkerOptions().position(deliveredPos).title(order.getFormattedDeliveryDate()));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(deliveredPos, 16));
    }

    private void activityLogOut() {
        Session.closeSession(this);
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
