package com.NTeam.simpledictionary

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout.VERTICAL
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mkhrussell.simpledictionary.R
import kotlinx.android.synthetic.main.activity_favorite_list.*
import kotlinx.android.synthetic.main.row_fav.*

class FavoriteList : AppCompatActivity() {

    companion object{
        lateinit var favDB: FavDBHelper
    }
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_list)

        favDB = FavDBHelper(this, null, null, 1)

        //viewFavorite()
//        val favoritelist = favDB.getFavorites(this)
//        viewManager = LinearLayoutManager(this)
//        viewAdapter = MyAdapter(favoritelist)
//        recyclerView = findViewById<RecyclerView>(R.id.list_fav).apply {
//            setHasFixedSize(true)
//            layoutManager = viewManager
//            adapter = viewAdapter
//        }

        val context = this
        add_button.setOnClickListener {
            DictionaryEntryContract.TABLE_NAME = "av"
            val intent = Intent(context, DictionaryActivity::class.java)
            startActivity(intent)
        }
    }

     @SuppressLint("WrongConstant")
     private  fun viewFavorite(){
         val favoritelist = favDB.getFavorites(this)
         val adapter = MyAdapter(this, favoritelist)
         val fav : RecyclerView = findViewById(R.id.list_fav)
         fav.layoutManager = LinearLayoutManager(this, VERTICAL,false)
         fav.adapter = adapter
     }

    override fun onResume() {
        viewFavorite()
        super.onResume()
    }
}
