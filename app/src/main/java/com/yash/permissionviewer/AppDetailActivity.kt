package com.yash.permissionviewer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView

class AppDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_detail)

        val packageName = intent.getStringExtra("package_name") ?: return
        val appName = intent.getStringExtra("app_name") ?: packageName

        findViewById<TextView>(R.id.txtHeader).text = appName

        val recycler = findViewById<RecyclerView>(R.id.recyclerPerms)
        recycler.layoutManager = LinearLayoutManager(this)

        val perms = PermissionReader.getPermissionsForPackage(this, packageName)
        recycler.adapter = PermissionAdapter(perms)
    }
}
