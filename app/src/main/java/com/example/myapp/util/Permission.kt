package com.example.myapp.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import com.example.myapp.R
import com.example.myapp.util.permission.DenyPermissionMessageDialog
import com.example.myapp.util.permission.PermissionMessageDialog
import com.example.myapp.util.ui.dip2px
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.orhanobut.dialogplus.DialogPlus
import com.orhanobut.dialogplus.ViewHolder

fun showDexterRequestMultiplePermission(
    context: Context,
    permission: MutableList<String>,
    messageDialog: PermissionMessageDialog? = null,
    denyDialog: DenyPermissionMessageDialog? = null,
    permissionGranted: (() -> Unit)? = null,
    permissionDenied: (() -> Unit)? = null

) {
    val permissionNeeded = checkDexterPermission(context, permission)
    if (permissionNeeded.isNotEmpty()) {
        if (messageDialog != null) {
            messageDialog.positiveCallBack = {
                showMultiPermission(
                    context, permissionNeeded, denyDialog,
                    permissionGranted, permissionDenied
                )
            }
            showDexterPermissionMessage(messageDialog)
        } else {
            showMultiPermission(
                context, permissionNeeded, denyDialog,
                permissionGranted, permissionDenied
            )
        }
    } else {
        permissionGranted?.invoke()
    }
}

private fun showMultiPermission(
    context: Context, permissionNeeded: MutableList<String>,
    denyDialog: DenyPermissionMessageDialog? = null, permissionGranted: (() -> Unit)? = null,
    permissionDenied: (() -> Unit)? = null
) {

    Dexter.withContext(context).withPermissions(permissionNeeded)
        .withListener(
            object : MultiplePermissionsListener {
                override fun onPermissionsChecked(multiplePermissionsReport: MultiplePermissionsReport?) {
                    if (multiplePermissionsReport != null && multiplePermissionsReport.areAllPermissionsGranted()) {
                        permissionGranted?.invoke()
                    } else {
                        permissionDenied?.invoke()
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: MutableList<PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    token?.continuePermissionRequest()
                }
            }
        )
        .check()
}

fun checkDexterPermission(context: Context, permission: MutableList<String>): MutableList<String> {
    //if list is Empty, it means all permission granted
    val listPermissionNeeded: MutableList<String> = mutableListOf()
    permission.forEach {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, it)
                != PackageManager.PERMISSION_GRANTED
            ) {
                listPermissionNeeded.add(it)
            }
        } else {
            if (PermissionChecker.checkSelfPermission(context, it)
                != PermissionChecker.PERMISSION_GRANTED
            ) {
                listPermissionNeeded.add(it)
            }
        }
    }
    return listPermissionNeeded
}

private fun showDexterPermissionMessage(model: PermissionMessageDialog) {
    val viewHolder = ViewHolder(R.layout.dialog_permission)
    val dialogMessage = DialogPlus.newDialog(model.context)
        .setContentHolder(viewHolder)
        .setContentBackgroundResource(R.drawable.circle_radius_card_view)
        .setOverlayBackgroundResource(R.color.color_transparent)
        .setGravity(model.gravity)
        .setCancelable(false)
        .create()
    dialogMessage.show()

    val downloadCustomView = viewHolder.inflatedView
    val tvTitle = downloadCustomView.findViewById(R.id.tv_title_permission) as TextView
    val tvDesc = downloadCustomView.findViewById(R.id.tv_desc_permission) as TextView
    val tvAdditionalDesc = downloadCustomView.findViewById(R.id.tv_additional_desc) as TextView
    val btnAllow = downloadCustomView.findViewById(R.id.btn_allow_permission) as Button
    val iconsCnt = downloadCustomView.findViewById(R.id.ll_icons_cnt) as LinearLayout

    tvTitle.text = model.title
    tvDesc.text = model.desc
    tvAdditionalDesc.text = model.additionalDesc

    model.icon?.forEach {
        val imgIcon = ImageView(model.context)

        val layParam = LinearLayout.LayoutParams(
            dip2px(model.context, 45.0f),
            dip2px(model.context, 45.0f)
        )
        layParam.setMargins(dip2px(model.context, 5.0f), 0, dip2px(model.context, 5.0f), 0)
        imgIcon.layoutParams = layParam
        imgIcon.setColorFilter(ContextCompat.getColor(model.context, R.color.primary))
        imgIcon.setImageDrawable(ContextCompat.getDrawable(model.context, it))
        iconsCnt.addView(imgIcon)
    }

    btnAllow.text = model.positiveTxt
    btnAllow.setOnClickListener {
        model.positiveCallBack!!.invoke()
        dialogMessage.dismiss()
    }
}

//permissions
fun checkLocationPermission(
    context: Context, permissionGranted: (() -> Unit)? = null,
    permissionDenied: (() -> Unit)? = null
) {
    showDexterRequestMultiplePermission(context = context,
        permission = mutableListOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ),
        permissionGranted = {
            permissionGranted?.invoke()
        },
        permissionDenied = {
            permissionDenied?.invoke()
        })
}



