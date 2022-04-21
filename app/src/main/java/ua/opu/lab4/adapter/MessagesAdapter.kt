package ua.opu.lab4.adapter

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ua.opu.lab4.R
import ua.opu.lab4.databinding.DeleteLayoutBinding
import ua.opu.lab4.databinding.SendMsgBinding
import ua.opu.lab4.model.Message


class MessagesAdapter(
    private val context: Context
) :
    ListAdapter<Message, RecyclerView.ViewHolder?>(MessageDiffCallback) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.send_msg, parent, false)
        return SentViewHolder(view)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message: Message = getItem(position)
        val viewHolder = holder as SentViewHolder
        if (message.message.equals("photo")) {
            viewHolder.binding.image.visibility = View.VISIBLE
            viewHolder.binding.message.visibility = View.GONE
            viewHolder.binding.mLinear.visibility = View.GONE
            Glide.with(context)
                .load(message.imageUrl)
                .placeholder(R.drawable.placeholder)
                .into(viewHolder.binding.image)
        }
        viewHolder.binding.message.text = message.message
        viewHolder.itemView.setOnLongClickListener {
            val view: View =
                LayoutInflater.from(context).inflate(R.layout.delete_layout, null)
            val binding: DeleteLayoutBinding = DeleteLayoutBinding.bind(view)
            val dialog: AlertDialog = AlertDialog.Builder(context)
                .setTitle("Delete Message")
                .setView(binding.root)
                .create()
            binding.cancel.setOnClickListener { dialog.dismiss() }
            dialog.show()
            false
        }
    }

    inner class SentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: SendMsgBinding = SendMsgBinding.bind(itemView)

    }

    object MessageDiffCallback : DiffUtil.ItemCallback<Message>() {
        override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean =
            newItem.messageId == oldItem.messageId

        override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean =
            newItem == oldItem

    }
}