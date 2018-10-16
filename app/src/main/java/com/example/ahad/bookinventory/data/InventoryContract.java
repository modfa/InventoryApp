package com.example.ahad.bookinventory.data;

import android.provider.BaseColumns;

public final class InventoryContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private InventoryContract() {
    }

    /**
     * Inner class that defines constant values for the inventory database table.
     * Each entry in the table represents a single book item.
     */
    public static final class BookEntry implements BaseColumns {

        /**
         * Name of database table for the product
         */
        public final static String TABLE_NAME = "books";

        /**
         * Unique ID number for the book (only for use in the database table).
         * <p>
         * Type: INTEGER
         */
        public final static String _ID = BaseColumns._ID;

        /**
         * Name of the product.
         * <p>
         * Type: TEXT
         */
        public final static String COLUMN_PRODUCT_NAME = "productName";

        /**
         * Price of the product.
         * <p>
         * Type: INTEGER
         */
        public final static String COLUMN_PRODUCT_PRICE = "productPrice";

        /**
         * Quantity of the product.
         * Type: INTEGER
         */
        public final static String COLUMN_PRODUCT_QUANTITY = "productQuantity";

        /**
         * Supplier Name of the product.
         * <p>
         * Type: STRING
         */
        public final static String COLUMN_SUPPLER_NAME = "supplierName";

        /**
         * Supplier PHONE NUMBER of the product.
         * <p>
         * Type: STRING
         */
        public final static String COLUMN_SUPPLIER_PHONE_NUMBER = "supplierNumber";

    }
}
