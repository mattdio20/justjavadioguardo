package com.example.android.justjavadioguardo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.text.Editable;
import android.content.Intent;
import android.net.Uri;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    public int quantity = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
        // subtract 1 from total, min 0
        public void inc(View v)
        {
            if(quantity == 100)
            {
                return;
            }
            quantity += 1;
            displayQuantity(quantity);
        }

        public void dec(View v)
        {
            if(quantity == 0)
            {
                return;
            }
            quantity -= 1;
            displayQuantity(quantity);
        }

        public void displayQuantity(int n)
        {
            TextView tv = (TextView) findViewById(R.id.num);
            tv.setText("" + n);
        }

        public double calculatePrice(boolean b1, boolean b2)
        {
            double price = quantity * (1.99);

            if(b1)
            {
                price += quantity * (2.00);
            }

            if(b2)
            {
                price += quantity * (1.00);
            }

            return price;
        }

         private String createOrderSummary(String name, double price, boolean b1, boolean b2)
         {
             String priceMessage = "Name: " + name;
             priceMessage += "\n" + "Whipped Cream: " + b1;
             priceMessage += "\n" + "Chocolate: " + b2;
             priceMessage += "\n" + "Quantity: " + quantity;
             priceMessage += "\n" + "Price: " + price;
             NumberFormat.getCurrencyInstance().format(price);
             priceMessage += "\n" + "Thank You!";
             return priceMessage;
         }

        public void displayOrder(String m)
        {
            TextView orderSummary = (TextView) findViewById(R.id.orderSummary);
            orderSummary.setText(m);
        }

        public void submitOrder(View view)
        {
            // get name from order
            EditText nameField = (EditText) findViewById(R.id.nameInput);
            Editable nameEditable = nameField.getText();
            String name = nameEditable.toString();

            // check for whipped cream
            CheckBox c1 = (CheckBox) findViewById(R.id.check1);
            boolean b1 = c1.isChecked();

            // check for chocolate
            CheckBox c2 = (CheckBox) findViewById(R.id.check2);
            boolean b2 = c2.isChecked();

            // calc the price
            double price = calculatePrice(b1, b2);


            String message = createOrderSummary(name, price, b1, b2);
            displayOrder(message);

            // Use an intent to launch an email app.
            // Send the order summary in the email body.
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:")); // only email apps should handle this
            intent.putExtra(Intent.EXTRA_SUBJECT,"Just Java Order for " + name);
            intent.putExtra(Intent.EXTRA_TEXT, message);

            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        }





}