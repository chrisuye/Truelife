package com.christian.seyoum.truelife

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.food_layout.view.*

class MainAdapter (val control:IFoodControl): RecyclerView.Adapter<CustomViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context).inflate(R.layout.food_layout, parent, false)
        val viewHolder = CustomViewHolder(layoutInflater)

        return viewHolder
    }

    override fun getItemCount(): Int {
        return control.count()
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val foos = control.foods.getFoods()[position]

        holder.BindTodo(foos)
    }

}

class CustomViewHolder(view: View): RecyclerView.ViewHolder(view) {

    fun BindTodo(food: Food?){

        itemView.food_view.text = food?.food
        itemView.catagory_view.text = food?.catagory
        itemView.note_view.text = food?.notes
    }

}