package com.NTeam.simpledictionary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mkhrussell.simpledictionary.R
import kotlinx.android.synthetic.main.activity_home.*

class Home_activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val context = this

        btn_en.setOnClickListener {
            DictionaryEntryContract.TABLE_NAME = "av"
            val intent = Intent(context, DictionaryActivity::class.java)
            startActivity(intent)
        }

        btn_vi.setOnClickListener {
            DictionaryEntryContract.TABLE_NAME = "va"
            val intent = Intent(context, DictionaryActivity::class.java)
            startActivity(intent)
        }
        btn_fav.setOnClickListener {
            val intent = Intent(context, FavoriteList::class.java)
            startActivity(intent)
        }
    }
}
