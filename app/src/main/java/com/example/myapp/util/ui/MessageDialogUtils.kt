package com.example.myapp.util.ui

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import com.example.myapp.R
import com.orhanobut.dialogplus.DialogPlus
import com.orhanobut.dialogplus.ViewHolder

fun showPermissionMessage(
    mContext: Context,
    title: String?,
    desc: String,
    additionalDesc: String,
    iconResId: List<Int>,
    positiveTxt: String,
    positiveCallBack: (() -> Unit)? = null,
    gravity: Int = Gravity.CENTER
) {
    val viewHolder = ViewHolder(R.layout.dialog_permission)
    val dialogMessage = DialogPlus.newDialog(mContext)
        .setContentHolder(viewHolder)
        .setContentBackgroundResource(R.drawable.circle_radius_card_view)
        .setOverlayBackgroundResource(R.color.color_transparent)
        .setGravity(gravity)
        .setCancelable(false)
        .create()
    dialogMessage.show()

    val downloadCustomView = viewHolder.inflatedView
    val tvTitle = downloadCustomView.findViewById(R.id.tv_title_permission) as TextView
    val tvDesc = downloadCustomView.findViewById(R.id.tv_desc_permission) as TextView
    val tvAdditionalDesc = downloadCustomView.findViewById(R.id.tv_additional_desc) as TextView
    val btnAllow = downloadCustomView.findViewById(R.id.btn_allow_permission) as Button
    val iconsCnt = downloadCustomView.findViewById(R.id.ll_icons_cnt) as LinearLayout

    tvTitle.text = title
    tvDesc.text = desc
    tvAdditionalDesc.text = additionalDesc

    iconResId.forEach {
        val imgIcon = ImageView(mContext)

        val layParam = LinearLayout.LayoutParams(
            dip2px(mContext, 45.0f),
            dip2px(mContext, 45.0f)
        )
        layParam.setMargins(dip2px(mContext, 5.0f), 0, dip2px(mContext, 5.0f), 0)
        imgIcon.layoutParams = layParam
        imgIcon.setColorFilter(ContextCompat.getColor(mContext, R.color.primary))
        imgIcon.setImageDrawable(ContextCompat.getDrawable(mContext, it))
        iconsCnt.addView(imgIcon)
    }

    btnAllow.text = positiveTxt
    btnAllow.setOnClickListener {
        if (positiveCallBack != null) {
            positiveCallBack()
            dialogMessage.dismiss()
        } else {
            dialogMessage.dismiss()
        }
    }
}

