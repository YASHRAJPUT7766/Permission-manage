package com.yash.permissionviewer

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Background thread pe load karo, kyunki 100+ apps scan karne me time lagta hai
        CoroutineScope(Dispatchers.Main).launch {
            val apps = withContext(Dispatchers.Default) {
                PermissionReader.getAllAppsWithPermissions(this@MainActivity)
            }
            recyclerView.adapter = AppListAdapter(apps) { app ->
                val intent = Intent(this@MainActivity, AppDetailActivity::class.java)
                intent.putExtra("package_name", app.packageName)
                intent.putExtra("app_name", app.appName)
                startActivity(intent)
            }
        }
    }
}
