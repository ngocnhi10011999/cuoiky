package com.NTeam.simpledictionary

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import com.NTeam.simpledictionary.models.Favorite
import java.lang.Exception

class FavDBHelper(context: Context, name: String?, factory: SQLiteDatabase.CursorFactory?, version: Int) :
        SQLiteOpenHelper(context, FavDBHelper.DB_NAME, null, FavDBHelper.DB_VERSION) {

    companion object {

        private val DB_VERSION = 1
        private val DB_NAME = "MyFavorite"
        var TABLE_NAME = "favorite"
        val COLUMN_ID = "id"
        val COLUMN_WORD = "word"
        val COLUMN_TYPE = "description"
        val COLUMN_MEANING = "pronounce"
    }
    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_TABLE = "CREATE TABLE $TABLE_NAME (" +
                COLUMN_ID + " INTEGER PRIMARY KEY," +
                COLUMN_WORD + " TEXT," + COLUMN_TYPE + " TEXT," +
                COLUMN_MEANING + " TEXT);"
        db.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME
        db.execSQL(DROP_TABLE)
        onCreate(db)
    }

    fun getFavorites(mcontext: Context) : ArrayList<Favorite>{
        val qry = "Select * from $TABLE_NAME"
        val db = this.readableDatabase
        val cursor = db.rawQuery(qry, null)
        val favorites = ArrayList<Favorite>()
        if (cursor.count == 0)
            Toast.makeText(mcontext, "No Records Found", Toast.LENGTH_SHORT).show()
        else {
            while (cursor.moveToNext()){
                val favorite = Favorite()
                favorite.id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))
                favorite.word = cursor.getString(cursor.getColumnIndex(COLUMN_WORD))
                favorite.description = cursor.getString(cursor.getColumnIndex(COLUMN_TYPE))
                favorite.pronounce = cursor.getString(cursor.getColumnIndex(COLUMN_MEANING))
                favorites.add(favorite)
            }
            Toast.makeText(mcontext, "${cursor.count.toString()} Records Founds", Toast.LENGTH_SHORT).show()
        }
        cursor.close()
        db.close()
        return favorites
    }

    fun addFavorite(mcontext: Context, favorite: Favorite){
        val values = ContentValues()
        values.put(COLUMN_WORD, favorite.word)
        values.put(COLUMN_TYPE, favorite.description)
        values.put(COLUMN_MEANING, favorite.pronounce)
        val db = this.writableDatabase
        try {
            db.insert(TABLE_NAME, null, values)
            //db.rawQuery("INSERT INTO $TABLE_NAME ($COLUMN_WORD, $COLUMN_TYPE, $COLUMN_TYPE) Values(?, ?, ?)")
            Toast.makeText(mcontext, "Favorite added", Toast.LENGTH_SHORT).show()
        }catch (e: Exception){
            Toast.makeText(mcontext, e.message, Toast.LENGTH_SHORT).show()
        }
        db.close()
    }

    fun deleteFavorite(favoriteID: Int): Boolean{
        val db = this.writableDatabase
        var result: Boolean = false
        try {
            val cursor: Int = db.delete(TABLE_NAME, "$COLUMN_ID = ?", arrayOf(favoriteID.toString()))
            result = true
        }catch (e: Exception){
            Log.e(ContentValues.TAG, "Error")
        }
        db.close()
        return result
    }
}