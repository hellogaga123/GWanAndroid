package com.gaga.lib_core.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.yanzhenjie.permission.AndPermission
import java.util.*


/**
 * @Author Gaga
 * @Date 2020/12/25 13:56
 * @Description
 */

fun FragmentActivity.checkPermissions(
    granted: () -> Unit,
    canceled: (permissions: MutableList<String>) -> Unit,
    denied: (permissions: MutableList<String>) -> Unit,
    vararg permissions: String
) {
    AndPermission.with(this)
        .runtime()
        .permission(permissions)
        .onGranted { granted() }
        .onDenied {
            // 判断用户是不是不再显示权限弹窗了，若不再显示的话进入权限设置页
            if (AndPermission.hasAlwaysDeniedPermission(this, it)) {
                // 用户点击了不在提醒，打开权限设置页
//                AndPermission.permissionSetting(MainActivity.this).execute();
                denied(it)
                return@onDenied
            }
            canceled(it)
        }
        .start()
}

fun Fragment.checkPermissions(
    granted: () -> Unit,
    canceled: (permissions: MutableList<String>) -> Unit,
    denied: (permissions: MutableList<String>) -> Unit,
    vararg permissions: String
) {
    AndPermission.with(this)
        .runtime()
        .permission(permissions)
        .onGranted { granted() }
        .onDenied {
            // 判断用户是不是不再显示权限弹窗了，若不再显示的话进入权限设置页
            if (AndPermission.hasAlwaysDeniedPermission(this, it)) {
                // 用户点击了不在提醒，打开权限设置页
//                AndPermission.permissionSetting(MainActivity.this).execute();
                denied(it)
                return@onDenied
            }
            canceled(it)
        }
        .start()
}