package com.yash.permissionviewer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AppListAdapter(
    private val items: List<AppInfo>,
    private val onClick: (AppInfo) -> Unit
) : RecyclerView.Adapter<AppListAdapter.VH>() {

    class VH(view: View) : RecyclerView.ViewHolder(view) {
        val icon: ImageView = view.findViewById(R.id.imgIcon)
        val name: TextView = view.findViewById(R.id.txtAppName)
        val count: TextView = view.findViewById(R.id.txtPermCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_app, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]
        holder.name.text = item.appName
        holder.count.text = "${item.grantedCount}/${item.totalCount} permissions granted"
        holder.icon.setImageDrawable(item.icon)
        holder.itemView.setOnClickListener { onClick(item) }
    }

    override fun getItemCount(): Int = items.size
}
