package com.example.ahad.bookinventory;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ahad.bookinventory.data.InventoryContract.BookEntry;

import com.example.ahad.bookinventory.data.BookDbHelper;


public class BookEditor extends AppCompatActivity {
    /**
     * EditText field to enter the product name
     */
    private EditText mProductNameEditText;

    /**
     * EditText field to enter the product price
     */
    private EditText mProductPriceEditText;

    /**
     * EditText field to enter the product quantity
     */
    private EditText mProductQuantityEditText;

    /**
     * EditText field to enter the product supplier name
     */
    private EditText mProductSupplierNameEditText;

    /**
     * EditText field to enter the product Supplier phone number
     */
    private EditText mProductSupplierPhoneEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_editor);

        // Find all relevant views that we will need to read user input from
        mProductNameEditText = findViewById(R.id.product_name);
        mProductPriceEditText = findViewById(R.id.price);
        mProductQuantityEditText = findViewById(R.id.quantity);
        mProductSupplierNameEditText = findViewById(R.id.supplier_name);
        mProductSupplierPhoneEditText = findViewById(R.id.supplier_ph_number);
    }

    /**
     * Get user input from editor and save new product into database.
     */

    private void insertProduct() {

        // Read from input fields
        // Use trim to eliminate leading or trailing white space
        String nameString = mProductNameEditText.getText().toString().trim();
        String priceString = mProductPriceEditText.getText().toString().trim();
        int price = Integer.parseInt(priceString);
        String quantityString = mProductQuantityEditText.getText().toString().trim();
        int quantity = Integer.parseInt(quantityString);
        String supplierNameString = mProductSupplierNameEditText.getText().toString().trim();
        String supplierPhoneNumberString = mProductSupplierPhoneEditText.getText().toString().trim();

        // Create database helper
        BookDbHelper bookDbhelper = new BookDbHelper(this);

        // Gets the database in write mode
        SQLiteDatabase db = bookDbhelper.getWritableDatabase();

        // Create a ContentValues object where column names are the keys,
        // and product attributes from the editor are the values.
        ContentValues values = new ContentValues();
        values.put(BookEntry.COLUMN_PRODUCT_NAME, nameString);
        values.put(BookEntry.COLUMN_PRODUCT_PRICE, price);
        values.put(BookEntry.COLUMN_PRODUCT_QUANTITY, quantity);
        values.put(BookEntry.COLUMN_SUPPLER_NAME, supplierNameString);
        values.put(BookEntry.COLUMN_SUPPLIER_PHONE_NUMBER, supplierPhoneNumberString);

// Insert a new row for the product in the database, returning the ID of that new row.
        long newRowId = db.insert(BookEntry.TABLE_NAME, null, values);

        // Show a toast message depending on whether or not the insertion was successful
        if (newRowId == -1) {
            // If the row ID is -1, then there was an error with insertion.
            Toast.makeText(this, "Error with saving product", Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the insertion was successful and we can display a toast with the row ID.
            Toast.makeText(this, "Product saved with row id: " + newRowId, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.editor_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                insertProduct();
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
