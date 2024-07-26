package mg.geit.anjarafeno

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHandler(context: Context?) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + ID_PRODUCT + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_PRODUCT + " TEXT, "
                + QUANTITY_PRODUCT + " INTEGER, "
                + PRICE_PRODUCT + " DOUBLE, "
                + IMAGE_PRODUCT + " TEXT)")
        db.execSQL(query)
    }

    fun addNewProduct(
        nameProduct: String?,
        quantityProduct: Int?,
        priceProduct: Double?,
        imageProduct: String?
    ) {
        val db = this.writableDatabase
        val values = ContentValues()

        values.put(NAME_PRODUCT, nameProduct)
        values.put(QUANTITY_PRODUCT, quantityProduct)
        values.put(PRICE_PRODUCT, priceProduct)
        values.put(IMAGE_PRODUCT, imageProduct)

        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun readProducts(): ArrayList<Product> {
        val db = this.readableDatabase
        val cursorProducts: Cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)

        val arrayListProduct: ArrayList<Product> = ArrayList()
        if (cursorProducts.moveToFirst()) {
            do {
                arrayListProduct.add(
                    Product(
                        cursorProducts.getInt(cursorProducts.getColumnIndexOrThrow(ID_PRODUCT)),
                        cursorProducts.getString(cursorProducts.getColumnIndexOrThrow(NAME_PRODUCT)),
                        cursorProducts.getInt(cursorProducts.getColumnIndexOrThrow(QUANTITY_PRODUCT)),
                        cursorProducts.getDouble(cursorProducts.getColumnIndexOrThrow(PRICE_PRODUCT)),
                        cursorProducts.getString(cursorProducts.getColumnIndexOrThrow(IMAGE_PRODUCT))
                    )
                )
            } while (cursorProducts.moveToNext())
        }
        cursorProducts.close()
        return arrayListProduct
    }

    fun updateProducts(
        idProduct: Int,
        nameProduct: String,
        quantityProduct: Int?,
        priceProduct: Double
    ) {
        val db = this.writableDatabase
        val values = ContentValues()

        values.put(NAME_PRODUCT, nameProduct)
        values.put(QUANTITY_PRODUCT, quantityProduct)
        values.put(PRICE_PRODUCT, priceProduct)
        db.update(TABLE_NAME, values, "$ID_PRODUCT=?", arrayOf(idProduct.toString()))
        db.close()
    }

    fun deleteProduct(idProduct: Int) {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, "$ID_PRODUCT=?", arrayOf(idProduct.toString()))
        db.close()
    }

    companion object {
        private const val DB_NAME = "PRODUCTS_DB"
        private const val DB_VERSION = 1
        private const val TABLE_NAME = "products"
        private const val ID_PRODUCT = "idProduct"
        private const val NAME_PRODUCT = "nameProduct"
        private const val QUANTITY_PRODUCT = "quantityProduct"
        private const val PRICE_PRODUCT = "priceProduct"
        private const val IMAGE_PRODUCT = "imageProduct"
    }

    fun getProductById(productId: Int): Product? {
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_NAME,
            arrayOf(ID_PRODUCT, NAME_PRODUCT, QUANTITY_PRODUCT, PRICE_PRODUCT, IMAGE_PRODUCT),
            "$ID_PRODUCT = ?",
            arrayOf(productId.toString()),
            null,
            null,
            null
        )

        var product: Product? = null
        if (cursor.moveToFirst()) {
            val idProduct = cursor.getInt(cursor.getColumnIndexOrThrow(ID_PRODUCT))
            val nameProduct = cursor.getString(cursor.getColumnIndexOrThrow(NAME_PRODUCT))
            val quantityProduct = cursor.getInt(cursor.getColumnIndexOrThrow(QUANTITY_PRODUCT))
            val priceProduct = cursor.getDouble(cursor.getColumnIndexOrThrow(PRICE_PRODUCT))
            val imageProduct = cursor.getString(cursor.getColumnIndexOrThrow(IMAGE_PRODUCT))

            product = Product(idProduct, nameProduct, quantityProduct, priceProduct, imageProduct)
        }
        cursor.close()
        db.close()
        return product
    }
}