package com.example.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    int quantity = 1;

    private TextView tvPrice;
    private TextView tvQuantityCount;
    private EditText etvName;
    private CheckBox cbWhippedCream;
    private CheckBox cbChocolate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setComponent();
    }

    private void setComponent() {
        tvQuantityCount = findViewById(R.id.tvQuantityCount);
        etvName = findViewById(R.id.etvName);
        cbWhippedCream = findViewById(R.id.cbWhippedCream);
        cbChocolate = findViewById(R.id.cbChocolate);
        tvPrice= findViewById(R.id.tvPrice);

        tvPrice.setText("Precios: \nCafe=5$ \nCrema de Moka=1$ \nChocolate=2$");
        tvQuantityCount.setText(""+quantity);
    }

    public void submitOrder(View view) {

        String name = etvName.getText().toString();

        boolean hasWhippedCream = cbWhippedCream.isChecked();
        boolean hasChocolate = cbChocolate.isChecked();

        String WhippedCream;
        String Chocolate;

        int price = calculatePrice(hasChocolate, hasWhippedCream);

        if (hasWhippedCream=true){
            WhippedCream="Si";
        }else{
            WhippedCream="No";
        }

        if (hasChocolate=true){
            Chocolate="Si";
        }else{
            Chocolate="No";
        }

        String priceMessage = createOrderSummary(price, WhippedCream, Chocolate, name);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "La orden de Cafe es para " + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private String createOrderSummary(int price, String hasWhippedCream, String hasChocolate, String name) {
        String priceMessage = "Nombre: " + name;
        priceMessage = priceMessage + "\nDesea Crema de Moka? " + hasWhippedCream;
        priceMessage = priceMessage + "\nDesea Chocolate? " + hasChocolate;
        priceMessage = priceMessage + "\nCantidad de cafe: " + quantity;
        priceMessage = priceMessage + "\nEl precio es: $" + price;
        priceMessage = priceMessage + "\nMuchas Gracias por su compra.";
        return priceMessage;
    }

    private int calculatePrice(boolean hasChocolate, boolean hasWhippedCream) {
        int basePrice = 5;
        if (hasChocolate) {
            basePrice = basePrice + 2;
        }
        if (hasWhippedCream) {
            basePrice = basePrice + 1;
        }
        return quantity * basePrice;
    }

    /**
     * funciona cualquiera de los metodos increment y decrement
     */
    public void increment(View view) {
        if (quantity == 100) {
            Toast.makeText(this, "No es posible pedir mas de 100 cafes", Toast.LENGTH_SHORT).show();
        } else {
            quantity = quantity + 1;
            tvQuantityCount.setText("" + quantity);
        }
    }

    public void decrement(View view) {
        if (quantity == 1) {
            Toast.makeText(this, "No es posible pedir menos de 1 cafe", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1;
        tvQuantityCount.setText("" + quantity);
    }

}