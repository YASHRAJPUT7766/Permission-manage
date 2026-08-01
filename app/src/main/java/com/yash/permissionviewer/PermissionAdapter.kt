package com.yash.permissionviewer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PermissionAdapter(
    private val items: List<PermissionInfo>
) : RecyclerView.Adapter<PermissionAdapter.VH>() {

    class VH(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.txtPermName)
        val status: TextView = view.findViewById(R.id.txtPermStatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_permission, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]
        holder.name.text = item.name
        if (item.granted) {
            holder.status.text = "GRANTED"
            holder.status.setTextColor(0xFF2E7D32.toInt())
        } else {
            holder.status.text = "DENIED"
            holder.status.setTextColor(0xFFC62828.toInt())
        }
    }

    override fun getItemCount(): Int = items.size
}
