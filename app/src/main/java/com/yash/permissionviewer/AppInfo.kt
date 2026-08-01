package com.yash.permissionviewer

import android.graphics.drawable.Drawable

data class AppInfo(
    val appName: String,
    val packageName: String,
    val icon: Drawable?,
    val grantedCount: Int,
    val totalCount: Int
)

data class PermissionInfo(
    val name: String,
    val granted: Boolean
)
