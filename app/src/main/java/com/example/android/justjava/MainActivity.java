package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static int quantity = 2;
    private static int price = 10;
    private int basePrice = 5;
    private boolean hasWhippedCream = false;
    private boolean hasChocolate = false;

    /**
     * This app displays an order form to order coffee
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is invoked when the ORDER button is pressed
     */
    public void submitOrder(View view) {
        //Getting the name from the name_field
        nameField = (EditText) findViewById(R.id.name_field);
        String name = nameField.getText().toString();

        //Prevents the order summary to be made if the name field is empty
        if (name.equals("")) {
            Toast toastMessage = Toast.makeText(this, "Please enter a valid name in the name field provided!", Toast.LENGTH_LONG);
            toastMessage.show();
            return;
        }

        //Checking the states of the checkboxes
        String toppings = null;

        if (hasWhippedCream && hasChocolate) {
            toppings = "whipped cream & chocolate";
        } else if (hasWhippedCream) {
            toppings = "whipped cream";
        } else if (hasChocolate) {
            toppings = "chocolate";
        } else
            toppings = "none";

        //Creating and dipslaying order summary
//        createOrderSummary(name, toppings, quantity, price);

        //Passing email intent
        Intent intent =  new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, new String[] {"talhaiqbalabc@gmail.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT,"OrderSummary");
        intent.putExtra(Intent.EXTRA_TEXT,createOrderSummary(name, toppings, quantity, price));

        if(intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }
    }

    EditText nameField;
//    private void makeOrder() {
        
//        //Getting the name from the name_field
//        nameField = (EditText) findViewById(R.id.name_field);
//        String name = nameField.getText().toString();
//
//        //Prevents the order summary to be made if the name field is empty
//        if (name.equals("")) {
//            Toast toastMessage = Toast.makeText(this, "Please enter a valid name in the name field provided!", Toast.LENGTH_LONG);
//            toastMessage.show();
//            return;
//        }
//
//        //Checking the states of the checkboxes
//        String toppings = null;
//
//        if (hasWhippedCream && hasChocolate) {
//            toppings = "whipped cream & chocolate";
//        } else if (hasWhippedCream) {
//            toppings = "whipped cream";
//        } else if (hasChocolate) {
//            toppings = "chocolate";
//        } else
//            toppings = "none";
//
//        //Creating and dipslaying order summary
//        createOrderSummary(name, toppings, quantity, price);

//        final ScrollView scrollView = (ScrollView) findViewById(R.id.scroll_view);
//        scrollView.smoothScrollTo(0,scrollView.getBottom());
//        scrollView.post(new Runnable() {
//            @Override
//            public void run() {
//                scrollView.setSmoothScrollingEnabled(true);
//                scrollView.fullScroll(scrollView.FOCUS_DOWN);
//            }
//        });
//
//        //Description here
//        final Button orderButton = (Button) findViewById(R.id.order_button);
//        orderButton.setText("RESET");
//        orderButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if ((orderButton.getText().toString()).equals("RESET")) {
//                    reset();
//                }
//                orderButton.setText("ORDER");
//                orderButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        makeOrder();
//                    }
//                });
//            }
//        });

//    }

    /**
     * This method is invoked when the increment button is pressed
     */
    public void increment(View view) {
        if (quantity == 50) {
            Toast toastMessage = Toast.makeText(this, "you cannot order more than " + quantity + " cups of coffee", Toast.LENGTH_LONG);
            toastMessage.show();
            return;
        }
        quantity += 1;
        displayQuantity(quantity);  //displays the quantity value of coffee cups
        displayPrice(calculatePrice(basePrice));  //displays the price value of the coffee
    }

    /**
     * This method is invoked when the decrement button is pressed
     */
    public void decrement(View view) {
        if (quantity == 1) {
            Toast toastMessage = Toast.makeText(this, "you cannot order less than " + quantity + " cup of coffee", Toast.LENGTH_LONG);
            toastMessage.show();
            return;
        }
        quantity -= 1;
        displayQuantity(quantity);  //displays the quantity value of coffee cups
        displayPrice(calculatePrice(basePrice));  //displays the price value of the coffee
    }

    /**
     * This method calculates and displays the total price of the coffee to be ordered
     */
    private int calculatePrice(int basePrice) {
        int calculatedPrice = basePrice * quantity;
        price = calculatedPrice;
        return calculatedPrice;
    }

    /**
     * This method updates the quantity value displayed on the screen
     */
    private void displayQuantity(int quantity) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText(String.valueOf(quantity));
    }

    /**
     * Thsis method updates the price value displayed on the screen
     */
    private void displayPrice(int price) {
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText("$" + price);
    }

    /**
     * This method creates an order summary that includes all the details of the coffee ordered and then displays it on the screen
     */
    TextView orderSummaryTextView = null;

    public String createOrderSummary(String name, String topping, int quantity, int price) {
        String orderSummary = "Name: " + name + "\nTopping: " + topping + "\nQuantity: " + quantity + "\nPrice: $" + price + "\nThank you!";
//        orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
//        orderSummaryTextView.setText(orderSummary);
        return orderSummary;
    }

    /**
     * This method checks whether the whipped cream checkbox has been checked or not and then it updates the price displayed accordingly
     */
    CheckBox whippedCreamCbx;
    public void hasWhippedCream(View view) {
        whippedCreamCbx = (CheckBox) findViewById(R.id.whipped_cream_id);
        hasWhippedCream = whippedCreamCbx.isChecked();
        if (hasWhippedCream)
            basePrice += 1;
        else
            basePrice -= 1;
        displayPrice(calculatePrice(basePrice));  //displays the price value of the coffee
    }

    /**
     * This method checks whether the chocolate checkbox has been checked or not and then it updates the price displayed accordingly
     */
    CheckBox chocolateCbx;
    public void hasChocolate(View view) {
        chocolateCbx = (CheckBox) findViewById(R.id.chocolate_id);
        hasChocolate = chocolateCbx.isChecked();
        if (hasChocolate)
            basePrice += 2;
        else
            basePrice -= 2;
        displayPrice(calculatePrice(basePrice));  //displays the price value of the coffee
    }

    /**
     * Description here
     */
    public void reset() {
        nameField = (EditText) findViewById(R.id.name_field);
        nameField.setText("");
        quantity = 2;
        price = 10;
        basePrice = 5;
        hasWhippedCream = false;
        hasChocolate = false;

        displayQuantity(quantity);
        displayPrice(calculatePrice(basePrice));

        chocolateCbx = (CheckBox) findViewById(R.id.chocolate_id);
        if (chocolateCbx.isChecked())
            chocolateCbx.setChecked(false);

        whippedCreamCbx = (CheckBox) findViewById(R.id.whipped_cream_id);
        if (whippedCreamCbx.isChecked())
            whippedCreamCbx.setChecked(false);

        orderSummaryTextView.setText("order summary comes here");
    }

}
