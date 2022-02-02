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
    int quantity = 2;

    private TextView tvQuantityCount;
    private EditText etvName;
    private CheckBox cbWhippedCream;
    private CheckBox cbChocolate;
    private Button btDown;
    private Button btUp;
    private Button btOrder;

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
    }

    public void submitOrder(View view) {

        String name = etvName.getText().toString();

        boolean hasWhippedCream = cbWhippedCream.isChecked();
        boolean hasChocolate = cbChocolate.isChecked();

        int price = calculatePrice(hasChocolate, hasWhippedCream);
        String priceMessage = createOrderSummary(price, hasWhippedCream, hasChocolate, name);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just java order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private String createOrderSummary(int price, boolean hasWhippedCream, boolean hasChocolate, String name) {
        String priceMessage = "Nombre: " + name;
        priceMessage = priceMessage + "\nDesea Crema de Moka? " + hasWhippedCream;
        priceMessage = priceMessage + "\nDesea Chocolate? " + hasChocolate;
        priceMessage = priceMessage + "\nCantidad de cafe: " + quantity + ".";
        priceMessage = priceMessage + "\nEl precio es: $" + price + ".";
        priceMessage = priceMessage + "\nGracias.";
        return priceMessage;
    }

    private int calculatePrice(boolean hasChocolate, boolean hasWhippedCream) {
        int basePrice = 0;
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
            Toast.makeText(this, "you cannot have more than 100 coffee", Toast.LENGTH_SHORT).show();
        } else {
            quantity = quantity + 1;
            tvQuantityCount.setText("" + quantity);
        }
    }

    public void decrement(View view) {
        if (quantity == 1) {
            Toast.makeText(this, "you cannot have more than 1 coffee", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1;
        tvQuantityCount.setText("" + quantity);
    }

}