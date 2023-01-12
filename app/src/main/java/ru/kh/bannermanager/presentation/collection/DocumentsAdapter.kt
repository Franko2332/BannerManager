package ru.kh.bannermanager.presentation.collection

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.kh.bannermanager.R
import ru.kh.bannermanager.domain.entity.PromotionEntity

class DocumentsAdapter : RecyclerView.Adapter<DocumentsAdapter.CollectionItemHolder>() {
    class CollectionItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title = itemView.findViewById<TextView>(R.id.document_title)
        val description = itemView.findViewById<TextView>(R.id.document_description)
        val popuMenu = itemView.findViewById<ImageView>(R.id.img_popup_menu)
    }

    interface DeleteDocument{
        fun deleteDocument(id : Int)
    }

    private var data = ArrayList<PromotionEntity>()

    fun setData(data: ArrayList<PromotionEntity>) {
        this.data = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionItemHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.documents_items, parent, false)
        return CollectionItemHolder(itemView)
    }

    override fun onBindViewHolder(holder: CollectionItemHolder, position: Int) {
        holder.title.text = data[position].title
        holder.description.text = data[position].description
        holder.popuMenu.setOnClickListener {
            val popupMenu = PopupMenu(it.context, holder.popuMenu).apply {
                inflate(R.menu.popup_menu)
            }
            popupMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.delete_popup_menu_item -> {
                        val actualPosition = holder.adapterPosition
                        data.removeAt(actualPosition)
                        notifyItemRemoved(actualPosition)
                        notifyItemRangeChanged(actualPosition, data.size)
                    }
                }
                true
            }
            popupMenu.show()
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}