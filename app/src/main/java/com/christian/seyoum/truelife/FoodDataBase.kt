package com.christian.seyoum.truelife

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

object FoodContract {
    object FoodEntry : BaseColumns {
        const val TABLE_NAME = "foods"
        const val COLUMN_NAME_FOOD = "user_food"
        const val COLUMN_NAME_CATAGORY = "user_catagory"
        const val COLUMN_NAME_NOTE = "user_note"
        const val COLUMN_NAME_DELETED = "deleted"
    }
}

private const val CREATE_FOOD_TABEL = "CREATE TABLE ${FoodContract.FoodEntry.TABLE_NAME} (" +
        "${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
        "${FoodContract.FoodEntry.COLUMN_NAME_FOOD} TEXT, " +
        "${FoodContract.FoodEntry.COLUMN_NAME_CATAGORY} TEXT, " +
        "${FoodContract.FoodEntry.COLUMN_NAME_NOTE} TEXT, " +
        "${FoodContract.FoodEntry.COLUMN_NAME_DELETED} BOOL DEFAULT 0" +
        ")"

interface IFoodDataBase{
    fun getFoods(): MutableList<Food>
    fun getFood(idx: Int): Food
    fun addFood(food: Food)
    fun updateFood(food: Food)
    fun deleteFood(food: Food)
}

private const val DELETE_FOOD_TABLE = "DROP TABLE IF EXISTS ${FoodContract.FoodEntry.TABLE_NAME}"

class FoodDataBase (ctx: Context) : IFoodDataBase{

    class FoodDbHelper(ctx: Context): SQLiteOpenHelper(ctx, DATABASE_NAME, null, DATABASE_VERSION){
        override fun onCreate(db: SQLiteDatabase?) {
            db?.execSQL(CREATE_FOOD_TABEL)
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            db?.execSQL(DELETE_FOOD_TABLE)
        }

        companion object{
            val DATABASE_NAME = "foods.db"
            val DATABASE_VERSION = 1
        }

    }
    private val db: SQLiteDatabase

    init {
        db = FoodDbHelper(ctx).writableDatabase
    }

    override fun getFoods(): MutableList<Food> {
        val project = arrayOf(BaseColumns._ID, FoodContract.FoodEntry.COLUMN_NAME_FOOD, FoodContract.FoodEntry.COLUMN_NAME_CATAGORY,
            FoodContract.FoodEntry.COLUMN_NAME_NOTE)
        val sortorder = "${BaseColumns._ID} ASC"
        val selection = "${FoodContract.FoodEntry.COLUMN_NAME_DELETED} = ?"
        val selectionArg = arrayOf("0")

        val cursor = db.query(
            FoodContract.FoodEntry.TABLE_NAME,
            project,
            selection,
            selectionArg,
            null,
            null,
            sortorder
        )
        val foods = mutableListOf<Food>()
        with(cursor){
            while (cursor.moveToNext()){

                val id = getInt(getColumnIndex(BaseColumns._ID))
                val food = getString(getColumnIndex(FoodContract.FoodEntry.COLUMN_NAME_FOOD))
                val category = getString(getColumnIndex(FoodContract.FoodEntry.COLUMN_NAME_CATAGORY))
                val note = getString(getColumnIndex(FoodContract.FoodEntry.COLUMN_NAME_NOTE))

                val foo = Food(id,food,category,note)

                foods.add(foo)

            }
        }
        return foods
    }

    override fun getFood(idx: Int): Food{
        val project = arrayOf(BaseColumns._ID, FoodContract.FoodEntry.COLUMN_NAME_FOOD, FoodContract.FoodEntry.COLUMN_NAME_CATAGORY,
            FoodContract.FoodEntry.COLUMN_NAME_NOTE)
        val sortorder = "${BaseColumns._ID} ASC"
        val selection = "${FoodContract.FoodEntry.COLUMN_NAME_DELETED} = ?"
        val selectionArg = arrayOf("0", idx.toString())

        val cursor = db.query(
            FoodContract.FoodEntry.TABLE_NAME,
            project,
            selection,
            selectionArg,
            null,
            null,
            sortorder
        )
        val foods = mutableListOf<Food>()
        with(cursor){
            while (cursor.moveToNext()){

                val id = getInt(getColumnIndex(BaseColumns._ID))
                val food = getString(getColumnIndex(FoodContract.FoodEntry.COLUMN_NAME_FOOD))
                val category = getString(getColumnIndex(FoodContract.FoodEntry.COLUMN_NAME_CATAGORY))
                val note = getString(getColumnIndex(FoodContract.FoodEntry.COLUMN_NAME_NOTE))

                val foo = Food(id,food,category,note)

                foods.add(foo)

            }
        }
        return foods[0]
    }

    override fun addFood(food: Food) {
        val cvs = toContentValues(food)
        db.insert(FoodContract.FoodEntry.TABLE_NAME, null, cvs)
    }

    override fun updateFood(food: Food) {
        val cvs = toContentValues(food)
        val selection = "${BaseColumns._ID} = ?"
        val selectionArgs = arrayOf(food.id.toString())
        db.update(FoodContract.FoodEntry.TABLE_NAME, cvs, selection, selectionArgs)
    }

    override fun deleteFood(food: Food) {
        val cvs = toContentValues(food)
        cvs.put(FoodContract.FoodEntry.COLUMN_NAME_DELETED, "1")
        val selection = "${BaseColumns._ID} = ?"
        val selectionArgs = arrayOf(food.id.toString())
        db.update(FoodContract.FoodEntry.TABLE_NAME, cvs, selection, selectionArgs)
    }


    private fun toContentValues(food: Food): ContentValues {
        val cv = ContentValues()
        cv.put(FoodContract.FoodEntry.COLUMN_NAME_FOOD, food.food)
        cv.put(FoodContract.FoodEntry.COLUMN_NAME_CATAGORY, food.catagory)
        cv.put(FoodContract.FoodEntry.COLUMN_NAME_NOTE, food.notes)

        return cv
    }

}