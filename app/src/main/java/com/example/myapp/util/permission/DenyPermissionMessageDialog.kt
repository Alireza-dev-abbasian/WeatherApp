package com.example.myapp.util.permission

import android.content.Context

data class DenyPermissionMessageDialog(
    var context: Context,
    var title: String? = null,
    var message: String? = null,
    var buttonText: String? = null,
    var icon: List<Int> = listOf()
) {

    private constructor(builder: Builder) : this(
        builder.context, builder.title, builder.message, builder.buttonText, builder.icon
    )

    companion object {
        inline fun create(context: Context, block: Builder.() -> Unit) =
            Builder(context).apply(block).build()
    }

    class Builder(val context: Context) {

        var title: String = ""
        var message: String = ""
        var buttonText: String = ""
        var icon: List<Int> = listOf()

        fun build() = DenyPermissionMessageDialog(this)

    }

}