package com.beeline.forecast.adapters

import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.beeline.forecast.R
import com.beeline.forecast.adapters.DeleteAdapter.MainViewHolder
import com.beeline.forecast.data.models.DeleteModel
import com.beeline.forecast.data.room.entity.LocalWeather

abstract class DeleteAdapter(private val deleteModels: List<DeleteModel>) : RecyclerView.Adapter<MainViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MainViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_list_native, viewGroup, false)
        return MainViewHolder(view)
    }

    override fun onBindViewHolder(mainViewHolder: MainViewHolder, i: Int) {
        if (deleteModels[i].selected) {
            mainViewHolder.llContainer.setBackgroundResource(R.color.md_grey_200)
            mainViewHolder.imgUnchecked.setBackgroundResource(R.drawable.layer_list_checked)
            mainViewHolder.imgUnchecked.setImageResource(0)
            mainViewHolder.imgChecked.visibility = View.VISIBLE
        } else {
            mainViewHolder.llContainer.setBackgroundResource(R.color.md_white_1000)
            mainViewHolder.imgUnchecked.setBackgroundResource(R.drawable.layer_list_unchecked)
            mainViewHolder.imgUnchecked.setImageResource(R.drawable.ic_home_white_48dp)
            mainViewHolder.imgChecked.visibility = View.INVISIBLE
        }
        mainViewHolder.lblTitle.text = deleteModels[i].localWeather.getName()
    }

    override fun getItemCount(): Int = deleteModels.size

    inner class MainViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val llContainer: LinearLayout = itemView.findViewById(R.id.llContainer)
        val lblTitle: TextView = itemView.findViewById(R.id.lblTitle)
        val imgChecked: ImageView = itemView.findViewById(R.id.imgChecked)
        val imgUnchecked: ImageView = itemView.findViewById(R.id.imgUnchecked)
        override fun onClick(view: View) {
            val leftIn: AnimatorSet = AnimatorInflater.loadAnimator(
                lblTitle.context,
                R.animator.card_flip_left_in
            ) as AnimatorSet

            val rightOut: AnimatorSet = AnimatorInflater.loadAnimator(
                lblTitle.context,
                R.animator.card_flip_right_out
            ) as AnimatorSet
            val showFrontAnim = AnimatorSet()
            val showBackAnim = AnimatorSet()
            leftIn.setTarget(imgUnchecked)
            showFrontAnim.play(leftIn)
            showFrontAnim.start()
            showFrontAnim.addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animator: Animator) {}
                override fun onAnimationEnd(animator: Animator) {
                    if (deleteModels[adapterPosition].selected) {
                        llContainer.setBackgroundResource(R.color.md_white_1000)
                        deleteModels[adapterPosition].selected = false
                        imgUnchecked.setBackgroundResource(R.drawable.layer_list_unchecked)
                        imgUnchecked.setImageResource(R.drawable.ic_home_white_48dp)
                        imgChecked.visibility = View.INVISIBLE
                    } else {
                        llContainer.setBackgroundResource(R.color.md_grey_200)
                        deleteModels[adapterPosition].selected = true
                        imgUnchecked.setBackgroundResource(R.drawable.layer_list_checked)
                        imgUnchecked.setImageResource(0)
                        imgChecked.visibility = View.VISIBLE
                    }
                    rightOut.setTarget(imgUnchecked)
                    showBackAnim.play(rightOut)
                    showBackAnim.start()
                    onItemClick(adapterPosition)
                }

                override fun onAnimationCancel(animator: Animator) {}
                override fun onAnimationRepeat(animator: Animator) {}
            })
        }

        init {
            itemView.setOnClickListener(this)
        }
    }

    fun clearAllSelections() {
        deleteModels.filter { it.selected }.forEach { it.selected=false }
        notifyDataSetChanged()
    }

    fun  selectedItemCount(): Int = getSelectedItems().size

    fun getSelectedItems(): List<LocalWeather> =  deleteModels.filter{it.selected}.map { it.localWeather }

    abstract fun onItemClick(pos: Int?)
}