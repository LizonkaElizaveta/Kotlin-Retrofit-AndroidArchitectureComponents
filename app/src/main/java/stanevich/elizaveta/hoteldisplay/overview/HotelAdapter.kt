package stanevich.elizaveta.hoteldisplay.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import stanevich.elizaveta.hoteldisplay.databinding.ListViewItemBinding
import stanevich.elizaveta.hoteldisplay.network.HotelsProperty

class HotelAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<HotelsProperty, HotelAdapter.HotelsPropertyViewHolder>(DiffCallback) {

    class HotelsPropertyViewHolder(private var binding: ListViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(hotelsProperty: HotelsProperty) {
            binding.property = hotelsProperty

            binding.executePendingBindings()
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HotelsPropertyViewHolder {
        return HotelsPropertyViewHolder(ListViewItemBinding.inflate(LayoutInflater.from(parent.context)))
    }


    override fun onBindViewHolder(holder: HotelsPropertyViewHolder, position: Int) {
        val hotelsProperty = getItem(position)


        holder.itemView.setOnClickListener {
            onClickListener.onClick(hotelsProperty)
        }
        holder.bind(hotelsProperty)
    }


    class OnClickListener(val clickListener: (hotelsProperty: HotelsProperty) -> Unit) {
        fun onClick(hotelsProperty: HotelsProperty) = clickListener(hotelsProperty)
    }


    companion object DiffCallback : DiffUtil.ItemCallback<HotelsProperty>() {
        override fun areItemsTheSame(oldItem: HotelsProperty, newItem: HotelsProperty): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: HotelsProperty, newItem: HotelsProperty): Boolean {
            return oldItem.id == newItem.id
        }
    }

}