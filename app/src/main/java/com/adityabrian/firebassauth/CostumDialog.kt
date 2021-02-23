package com.adityabrian.firebassauth

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable

/** kita akan membuat proses loading */
object CostumDialog{
    private var dialog:Dialog? = null

    // kita akan membuat fun nya
    fun showLoading(activity: Activity){
            /** kita membbuat objeknya untuk nantinya kita taro di dialog
             *  layout_progress = yaitu file untuk menampilkan loading */
        val dilogView = activity.layoutInflater.inflate(R.layout.layout_progress,null,false)

        dialog = Dialog(activity)

            // jadi ketika muncul dialog dia tidak bisa di cancel
        dialog?.setCancelable(false)

            // jadi agar dialognya transparant jadi mengikutin layout di progress
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.setContentView(dilogView)

        dialog?.show()
    }

    fun hideLoading(){
        dialog?.dismiss()
    }
    /** SETELAH ITU DITAMBAHKAN KE REGISTER*/
}