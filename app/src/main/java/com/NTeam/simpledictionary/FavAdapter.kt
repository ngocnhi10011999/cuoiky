package com.NTeam.simpledictionary

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.NTeam.simpledictionary.models.Favorite
import com.mkhrussell.simpledictionary.R
import kotlinx.android.synthetic.main.row_fav.view.*


class MyAdapter(context: FavoriteList, val myFavorte: ArrayList<Favorite>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    val mCtx = context
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val txtWord = itemView.txtWords
        val txtType = itemView.txtTypes
        val txtMeaning = itemView.txtMeanings
        val btn_del = itemView.btn_del

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_fav, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return myFavorte.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val favorite: Favorite = myFavorte[position]
        holder.txtWord.text = favorite.word
        holder.txtType.text = favorite.description
        holder.txtMeaning.text = favorite.pronounce

        holder.btn_del.setOnClickListener{
            val word_detai : String = favorite.word
            var alertDialog = AlertDialog.Builder(mCtx)
                .setTitle("Warning")
                .setMessage("Are you  Sure to Delete : $word_detai ?")
                    .setPositiveButton("Yes", DialogInterface.OnClickListener{ dialog, which ->
                        if (FavoriteList.favDB.deleteFavorite(favorite.id))
                        {
                            myFavorte.removeAt(position)
                            notifyItemRemoved(position)
                            notifyItemRangeChanged(position, myFavorte.size)
                            Toast.makeText(mCtx, "Favorite $word_detai Deleted", Toast.LENGTH_SHORT).show()
                        }else{
                            Toast.makeText(mCtx, "Delete Error!", Toast.LENGTH_SHORT).show()
                        }
                    })
                    .setNegativeButton("No", DialogInterface.OnClickListener{ dialog, which -> })
                    .setIcon(R.drawable.ic_warning)
                    .show()
        }
    }
}