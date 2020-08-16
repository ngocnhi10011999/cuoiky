package com.NTeam.simpledictionary

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.NTeam.simpledictionary.models.Favorite
import com.mkhrussell.simpledictionary.R
import kotlinx.android.synthetic.main.activity_word_detail.*

class AddFavorite : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_word_detail)

//        favBtn.setOnClickListener {
//            if(txtWords.text.isEmpty()){
//                Toast.makeText(this, "Enter Word", Toast.LENGTH_SHORT).show()
//                txtWords.requestFocus()
//            }else{
//            val favorite = Favorite()
//            favorite.word = txtWords.text.toString()
//            favorite.description = txtTypes.text.toString()
//            favorite.pronounce = txtMeanings.text.toString()
//            FavoriteList.favDB.addFavorite(this, favorite)
//            favBtn.requestFocus()
//            }
//        }
    }

//    private fun clearEdits(){
//        txtWord.text.clear()
//        txtType.text.clear()
//        txtMeaning.text.clear()
//    }
}
