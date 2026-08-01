package com.yash.permissionviewer

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager

object PermissionReader {

    // Saari apps (user + system) fetch karta hai, jinme kam se kam 1 requested permission ho
    fun getAllAppsWithPermissions(context: Context): List<AppInfo> {
        val pm = context.packageManager
        val packages: List<PackageInfo> = pm.getInstalledPackages(PackageManager.GET_PERMISSIONS)

        val result = mutableListOf<AppInfo>()

        for (pkg in packages) {
            val requested = pkg.requestedPermissions ?: continue
            if (requested.isEmpty()) continue

            val flags = pkg.requestedPermissionsFlags
            var grantedCount = 0
            if (flags != null) {
                for (f in flags) {
                    if ((f and PackageInfo.REQUESTED_PERMISSION_GRANTED) != 0) grantedCount++
                }
            }

            val appInfo: ApplicationInfo = pkg.applicationInfo ?: continue
            val label = pm.getApplicationLabel(appInfo).toString()
            val icon = try { pm.getApplicationIcon(appInfo) } catch (e: Exception) { null }

            result.add(
                AppInfo(
                    appName = label,
                    packageName = pkg.packageName,
                    icon = icon,
                    grantedCount = grantedCount,
                    totalCount = requested.size
                )
            )
        }

        // Sabse zyada permissions maangne wali app upar
        return result.sortedByDescending { it.totalCount }
    }

    // Ek specific app ki saari permissions (granted/denied ke saath)
    fun getPermissionsForPackage(context: Context, packageName: String): List<PermissionInfo> {
        val pm = context.packageManager
        val pkg: PackageInfo = try {
            pm.getPackageInfo(packageName, PackageManager.GET_PERMISSIONS)
        } catch (e: Exception) {
            return emptyList()
        }

        val requested = pkg.requestedPermissions ?: return emptyList()
        val flags = pkg.requestedPermissionsFlags ?: IntArray(requested.size)

        val list = mutableListOf<PermissionInfo>()
        for (i in requested.indices) {
            val granted = (flags.getOrElse(i) { 0 } and PackageInfo.REQUESTED_PERMISSION_GRANTED) != 0
            // Sirf permission ka short readable naam dikhate hain (android.permission.CAMERA -> CAMERA)
            val shortName = requested[i].substringAfterLast(".")
            list.add(PermissionInfo(name = shortName, granted = granted))
        }
        return list.sortedByDescending { it.granted }
    }
}
