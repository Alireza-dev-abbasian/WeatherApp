package com.example.myapp.util.permission

import android.content.Context
import android.view.Gravity

data class PermissionMessageDialog(
    var context: Context,
    var title: String? = null,
    var desc: String? = null,
    var additionalDesc: String? = null,
    var icon: List<Int>? = null,
    var positiveTxt: String? = null,
    var positiveCallBack: (() -> Unit),
    var gravity: Int
) {

    private constructor(builder: Builder) : this(
        builder.context, builder.title, builder.desc, builder.additionalDesc, builder.icon,
        builder.positiveTxt, builder.positiveCallBack, builder.gravity
    )

    companion object {
        inline fun create(context: Context, block: Builder.() -> Unit) =
            Builder(context).apply(block).build()
    }

    class Builder(val context: Context) {

        var title: String = ""
        var desc: String = ""
        var additionalDesc: String = ""
        var icon: List<Int> = listOf()
        var positiveTxt: String = ""
        var positiveCallBack: (() -> Unit) = {}
        var gravity: Int = Gravity.CENTER

        fun build() = PermissionMessageDialog(this)

    }

}