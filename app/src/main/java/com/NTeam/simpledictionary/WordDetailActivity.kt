package com.NTeam.simpledictionary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.NTeam.simpledictionary.models.Favorite
import com.mkhrussell.simpledictionary.DatabaseHelper
import com.mkhrussell.simpledictionary.R
import kotlinx.android.synthetic.main.activity_word_detail.*
import java.lang.Exception

class WordDetailActivity : AppCompatActivity() {

    companion object {
        const val WORD_ID = "WORD_ID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_word_detail)

        favBtn.setOnClickListener {
            try {
//                if(txtWord.text.isEmpty()){
//                    Toast.makeText(this, "Enter Word", Toast.LENGTH_SHORT).show()
//                    txtWord.requestFocus()
//                }else{
                val favorite = Favorite()
                favorite.word = txtWord.text.toString()
                favorite.description = txtType.text.toString()
                favorite.pronounce = txtMeaning.text.toString()
                FavoriteList.favDB.addFavorite(this, favorite)
                favBtn.requestFocus()
                //}

            }catch (e: Exception){
                val intent = Intent(this, DictionaryActivity::class.java)
                startActivity(intent)
            }

        }
        val wordId = intent.getStringExtra(WORD_ID) ?: ""
        if(wordId.isBlank()) {
            finish()
        }

        val dbHelper = DatabaseHelper(applicationContext)
        //if (dbHelper.getWord(wordId) != null){
        val cursor = dbHelper.getWord(wordId)
        if(cursor.moveToFirst()) {
            val txtWord = findViewById<TextView>(R.id.txtWord)
            val txtType = findViewById<TextView>(R.id.txtType)
            val txtMeaning = findViewById<TextView>(R.id.txtMeaning)

            txtWord?.text = cursor.getString(cursor.getColumnIndexOrThrow(DictionaryEntryContract.COLUMN_WORD))
            txtType?.text = cursor.getString(cursor.getColumnIndexOrThrow(DictionaryEntryContract.COLUMN_TYPE))
            txtMeaning?.text = cursor.getString(cursor.getColumnIndexOrThrow(DictionaryEntryContract.COLUMN_MEANING))
        }
    }
}
