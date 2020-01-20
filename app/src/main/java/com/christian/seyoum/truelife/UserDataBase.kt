package com.christian.seyoum.truelife

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

object UserContract {
    object UserEntry : BaseColumns {
        const val TABLE_NAME = "users"
        const val COLUMN_NAME_FIRST = "user_first"
        const val COLUMN_NAME_LAST = "user_last"
        const val COLUMN_NAME_DATE = "user_date"
        const val COLUMN_NAME_NAME = "user_name"
        const val COLUMN_NAME_PASSWORD = "user_pass"
        const val COLUMN_NAME_REMEMBER = "user_remember"
        const val COLUMN_NAME_DELETED = "deleted"
    }
}

private const val CREATE_USER_TABEL = "CREATE TABLE ${UserContract.UserEntry.TABLE_NAME} (" +
        "${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
        "${UserContract.UserEntry.COLUMN_NAME_FIRST} TEXT, " +
        "${UserContract.UserEntry.COLUMN_NAME_LAST} TEXT, " +
        "${UserContract.UserEntry.COLUMN_NAME_DATE} TEXT, " +
        "${UserContract.UserEntry.COLUMN_NAME_NAME} TEXT, " +
        "${UserContract.UserEntry.COLUMN_NAME_PASSWORD} TEXT, " +
        "${UserContract.UserEntry.COLUMN_NAME_REMEMBER} BOOL, " +
        "${UserContract.UserEntry.COLUMN_NAME_DELETED} BOOL DEFAULT 0" +
        ")"

interface IUserDataBase{
    fun getUsers(): MutableList<User>
    fun getUser(idx: Int): User
    fun addUser(user: User)
    fun updateUser(user: User)
    fun deleteUser(user: User)
}

private const val DELETE_USER_TABLE = "DROP TABLE IF EXISTS ${UserContract.UserEntry.TABLE_NAME}"

class UserDataBase (ctx: Context) : IUserDataBase{

    class UserDbHelper(ctx: Context): SQLiteOpenHelper(ctx, DATABASE_NAME, null, DATABASE_VERSION){
        override fun onCreate(db: SQLiteDatabase?) {
            db?.execSQL(CREATE_USER_TABEL)
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            db?.execSQL(DELETE_USER_TABLE)
        }

        companion object{
            val DATABASE_NAME = "users.db"
            val DATABASE_VERSION = 1
        }

    }
    private val db: SQLiteDatabase

    init {
        db = UserDbHelper(ctx).writableDatabase
    }



    override fun getUsers(): MutableList<User> {
        val project = arrayOf(BaseColumns._ID, UserContract.UserEntry.COLUMN_NAME_FIRST, UserContract.UserEntry.COLUMN_NAME_LAST,
            UserContract.UserEntry.COLUMN_NAME_DATE, UserContract.UserEntry.COLUMN_NAME_NAME, UserContract.UserEntry.COLUMN_NAME_PASSWORD,
            UserContract.UserEntry.COLUMN_NAME_REMEMBER)
        val sortorder = "${BaseColumns._ID} ASC"
        val selection = "${UserContract.UserEntry.COLUMN_NAME_DELETED} = ?"
        val selectionArg = arrayOf("0")

        val cursor = db.query(
            UserContract.UserEntry.TABLE_NAME,
            project,
            selection,
            selectionArg,
            null,
            null,
            sortorder
        )
        val users = mutableListOf<User>()
        with(cursor){
            while (cursor.moveToNext()){

                val id = getInt(getColumnIndex(BaseColumns._ID))
                val first = getString(getColumnIndex(UserContract.UserEntry.COLUMN_NAME_FIRST))
                val last = getString(getColumnIndex(UserContract.UserEntry.COLUMN_NAME_LAST))
                val date = getString(getColumnIndex(UserContract.UserEntry.COLUMN_NAME_DATE))
                val userName = getString(getColumnIndex(UserContract.UserEntry.COLUMN_NAME_NAME))
                val password = getString(getColumnIndex(UserContract.UserEntry.COLUMN_NAME_PASSWORD))
                val remember = getInt(getColumnIndex(UserContract.UserEntry.COLUMN_NAME_REMEMBER)) > 0

                val user = User(id,first,last,date,userName,password,remember)

                users.add(user)

            }
        }
        return users
    }

    override fun getUser(idx: Int): User {
        val project = arrayOf(BaseColumns._ID, UserContract.UserEntry.COLUMN_NAME_FIRST, UserContract.UserEntry.COLUMN_NAME_LAST,
            UserContract.UserEntry.COLUMN_NAME_DATE, UserContract.UserEntry.COLUMN_NAME_NAME, UserContract.UserEntry.COLUMN_NAME_PASSWORD,
            UserContract.UserEntry.COLUMN_NAME_REMEMBER)
        val sortorder = "${BaseColumns._ID} ASC"
        val selection = "${UserContract.UserEntry.COLUMN_NAME_DELETED} = ?"
        val selectionArg = arrayOf("0", idx.toString())

        val cursor = db.query(
            UserContract.UserEntry.TABLE_NAME,
            project,
            selection,
            selectionArg,
            null,
            null,
            sortorder
        )
        val users = mutableListOf<User>()
        with(cursor){
            while (cursor.moveToNext()){

                val id = getInt(getColumnIndex(BaseColumns._ID))
                val first = getString(getColumnIndex(UserContract.UserEntry.COLUMN_NAME_FIRST))
                val last = getString(getColumnIndex(UserContract.UserEntry.COLUMN_NAME_LAST))
                val date = getString(getColumnIndex(UserContract.UserEntry.COLUMN_NAME_DATE))
                val userName = getString(getColumnIndex(UserContract.UserEntry.COLUMN_NAME_NAME))
                val password = getString(getColumnIndex(UserContract.UserEntry.COLUMN_NAME_PASSWORD))
                val remember = getInt(getColumnIndex(UserContract.UserEntry.COLUMN_NAME_REMEMBER)) > 0

                val user = User(id,first,last,date,userName,password,remember)

                users.add(user)

            }
        }
        return users[0]
    }

    override fun addUser(user: User) {
        val cvs = toContentValues(user)
        db.insert(UserContract.UserEntry.TABLE_NAME, null, cvs)
    }

    override fun updateUser(user: User) {
        val cvs = toContentValues(user)
        val selection = "${BaseColumns._ID} = ?"
        val selectionArgs = arrayOf(user.id.toString())
        db.update(UserContract.UserEntry.TABLE_NAME, cvs, selection, selectionArgs)
    }

    override fun deleteUser(user: User) {
        val cvs = toContentValues(user)
        cvs.put(UserContract.UserEntry.COLUMN_NAME_DELETED, "1")
        val selection = "${BaseColumns._ID} = ?"
        val selectionArgs = arrayOf(user.id.toString())
        db.update(UserContract.UserEntry.TABLE_NAME, cvs, selection, selectionArgs)
    }

    private fun toContentValues(user: User): ContentValues {
        val cv = ContentValues()
        cv.put(UserContract.UserEntry.COLUMN_NAME_FIRST, user.firstName)
        cv.put(UserContract.UserEntry.COLUMN_NAME_LAST, user.lastName)
        cv.put(UserContract.UserEntry.COLUMN_NAME_DATE, user.dateBirth)
        cv.put(UserContract.UserEntry.COLUMN_NAME_NAME, user.userName)
        cv.put(UserContract.UserEntry.COLUMN_NAME_PASSWORD, user.password)
        cv.put(UserContract.UserEntry.COLUMN_NAME_REMEMBER, user.remember)

        return cv
    }
}