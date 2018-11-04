package com.example.ahad.bookinventory;


import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ahad.bookinventory.data.InventoryContract;

/**
 * {@link BookCursorAdapter} is an adapter for a list or grid view
 * that uses a {@link Cursor} of book data as its data source. This adapter knows
 * how to create list items for each row of pet data in the {@link Cursor}.
 */
public class BookCursorAdapter extends CursorAdapter {

    /**
     * Constructs a new {@link BookCursorAdapter}.
     *
     * @param context The context
     * @param c       The cursor from which to get the data.
     */
    public BookCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    /**
     * Makes a new blank list item view. No data is set (or bound) to the views yet.
     *
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already
     *                moved to the correct position.
     * @param parent  The parent to which the new view is attached to
     * @return the newly created list item view.
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Inflate a list item view using the layout specified in list_item.xml
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    /**
     * This method binds the book data (in the current row pointed to by cursor) to the given
     * list item layout. For example, the name for the current book can be set on the name TextView
     * in the list item layout.
     *
     * @param view    Existing view, returned earlier by newView() method
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already moved to the
     *                correct row.
     */
    @Override
    public void bindView(View view, final Context context, Cursor cursor) {

        // Find individual views that we want to modify in the list item layout
        TextView nameTextView = view.findViewById(R.id.name);
        TextView priceTextView = view.findViewById(R.id.price);
        final TextView quantityTextView = view.findViewById(R.id.quantity);
        TextView supplierNameTextView = view.findViewById(R.id.supplierName);
        TextView supplierNumberTextView = view.findViewById(R.id.supplierNumber);
        ImageView salesImage = view.findViewById(R.id.sale);

        // Find the columns of book attributes that we're interested in
        int bookIdColumnIndex = cursor.getColumnIndex(InventoryContract.BookEntry._ID);
        int nameColumnIndex = cursor.getColumnIndex(InventoryContract.BookEntry.COLUMN_PRODUCT_NAME);
        int priceColumnIndex = cursor.getColumnIndex(InventoryContract.BookEntry.COLUMN_PRODUCT_PRICE);
        int quantityColumnIndex = cursor.getColumnIndex(InventoryContract.BookEntry.COLUMN_PRODUCT_QUANTITY);
        int supplierNameColumnIndex = cursor.getColumnIndex(InventoryContract.BookEntry.COLUMN_SUPPLER_NAME);
        int supplierNumberColumnIndex = cursor.getColumnIndex(InventoryContract.BookEntry.COLUMN_SUPPLIER_PHONE_NUMBER);

        // Read the book attributes from the Cursor for the current book
        final int bookId = cursor.getInt(bookIdColumnIndex);
        String bookName = cursor.getString(nameColumnIndex);
        int price = cursor.getInt(priceColumnIndex);
        String priceString = String.valueOf(price);
        final int quantity = cursor.getInt(quantityColumnIndex);
        String quantityString = String.valueOf(quantity);
        String supplierName = cursor.getString(supplierNameColumnIndex);
        String supplierNumber = cursor.getString(supplierNumberColumnIndex);

// Changing the color of the received book name and using the SpannableString
// Update the TextViews with the attributes for the current Book
        String bookNameSpannable = context.getString(R.string.book_spannable) + bookName;
        Spannable spanable = new SpannableString(bookNameSpannable);
        spanable.setSpan(new ForegroundColorSpan(Color.BLUE), context.getString(R.string.book_spannable).length(), (bookName + context.getString(R.string.book_spannable)).length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        nameTextView.setText(spanable, TextView.BufferType.SPANNABLE);

        // Update the TextViews with the attributes for the current Book
        priceTextView.setText("Price : ₹ " + priceString);
        quantityTextView.setText("Quantity : " + quantityString);
        supplierNameTextView.setText("Supplier Name : " + supplierName);
        supplierNumberTextView.setText("Ph : " + supplierNumber);


        salesImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri currentBookUri = ContentUris.withAppendedId(InventoryContract.BookEntry.CONTENT_URI, bookId);
                makeSale(context, quantity, currentBookUri);
            }
        });
    }

    private void makeSale(Context context, int bookQuantity, Uri uriBook) {
        if (bookQuantity == 0) {
            Toast.makeText(context, R.string.no_book_to_sale, Toast.LENGTH_SHORT).show();
        } else {
            int newQuantity = bookQuantity - 1;
            ContentValues values = new ContentValues();
            values.put(InventoryContract.BookEntry.COLUMN_PRODUCT_QUANTITY, newQuantity);
            context.getContentResolver().update(uriBook, values, null, null);
        }
    }
}
