package ua.opu.lab4.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ua.opu.lab4.ChatActivity
import ua.opu.lab4.R
import ua.opu.lab4.databinding.ItemProfileBinding
import ua.opu.lab4.model.User

class UserAdapter(var context: Context, var userList: ArrayList<User>) :
    ListAdapter<User, UserAdapter.UserViewHolder>(UserDiffCallback) {
    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: ItemProfileBinding = ItemProfileBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        var v = LayoutInflater.from(context).inflate(
            R.layout.item_profile,
            parent, false
        )
        return UserViewHolder(v)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {

        val user = userList[position]
        holder.binding.username.text = user.name
        holder.binding.description.text = user.description
        Glide.with(context).load(user.profileImage)
            .placeholder(R.drawable.avatar)
            .into(holder.binding.profile)
        holder.itemView.setOnClickListener {
            val intent = Intent(context, ChatActivity::class.java)
            intent.putExtra("name", user.name)
            intent.putExtra("image", user.profileImage)
            intent.putExtra("uid", user.uid)
            context.startActivity(intent)

        }
    }

    override fun getItemCount(): Int = userList.size


    object UserDiffCallback: DiffUtil.ItemCallback<User>(){
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean = newItem.uid == oldItem.uid

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean = newItem == oldItem

    }
}