package com.NTeam.simpledictionary

import android.R.attr
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.NTeam.simpledictionary.models.Favorite
import com.mkhrussell.simpledictionary.DatabaseHelper
import com.mkhrussell.simpledictionary.R
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_word_detail.*
import java.util.*


class WordDetailActivity : AppCompatActivity() {

    companion object {
        const val WORD_ID = "WORD_ID"
    }
    private val REQUEST_CODE_SPEECH_INPUT = 100
    lateinit var mTTS: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_word_detail)

        if (DictionaryEntryContract.TABLE_NAME == "va"){
            btn_voice.visibility = View.GONE
            favBtn.visibility = View.GONE
            btn_mic.visibility = View.GONE
        }
        else{
            btn_voice.visibility = View.VISIBLE
            favBtn.visibility = View.VISIBLE
            btn_mic.visibility = View.VISIBLE
        }


        favBtn.setOnClickListener {
            try {
                val word = txtWord.text.toString()
                val dbWord = FavoriteList.favDB
                val result = dbWord.checkWord(word)
                if(result){
                    favBtn.setBackgroundResource(R.drawable.ic_star)
                    Toast.makeText(this, "Word exist", Toast.LENGTH_SHORT).show()
                }else{
                    favBtn.setBackgroundResource(R.drawable.ic_fav)
                    val favorite = Favorite()
                    favorite.word = txtWord.text.toString()
                    favorite.description = txtType.text.toString()
                    favorite.pronounce = txtMeaning.text.toString()
                    FavoriteList.favDB.addFavorite(this, favorite)
                    favBtn.requestFocus()
                }

            }catch (e: Exception){
                val intent = Intent(this, FavoriteList::class.java)
                startActivity(intent)
                Toast.makeText(this, "Click Button add", Toast.LENGTH_SHORT).show()
            }

        }
        mTTS = TextToSpeech(applicationContext, TextToSpeech.OnInitListener { status ->
            if (status != TextToSpeech.ERROR){
                mTTS.language = Locale.UK
            }
        })

        btn_voice.setOnClickListener {
            val toSpeak = txtWord.text.toString()
            if (toSpeak == "") {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, toSpeak, Toast.LENGTH_SHORT).show()
                mTTS.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null)
            }
        }
        btn_mic.setOnClickListener{
            speak()
        }
        val wordId = intent.getStringExtra(WORD_ID) ?: ""
        if(wordId.isBlank()) {
            finish()
        }

        val dbHelper = DatabaseHelper(applicationContext)

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

    private fun speak(){
        val mIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        mIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        mIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        mIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak word")

        try {
            startActivityForResult(mIntent, REQUEST_CODE_SPEECH_INPUT)
        }
        catch (e: Exception){
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_CODE_SPEECH_INPUT -> {
                if (resultCode === Activity.RESULT_OK && null != attr.data) {
                    val result = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    if (txtWord.text == result?.get(0)){
                        var alertDialog = AlertDialog.Builder(this)
                        alertDialog.setTitle("Correct")
                        alertDialog.setMessage("Good job !!")
                        alertDialog.setIcon(R.drawable.ic_correct)
                        alertDialog.show()
                        Toast.makeText(this, "Corecct", Toast.LENGTH_SHORT).show()
                    }else{
                        var alertDialog = AlertDialog.Builder(this)
                        alertDialog.setTitle("Not Correct")
                        alertDialog.setMessage("Try again!!")
                        alertDialog.setIcon(R.drawable.ic_correct_not)
                        alertDialog.show()
                        Toast.makeText(this, "Not Corecct", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
