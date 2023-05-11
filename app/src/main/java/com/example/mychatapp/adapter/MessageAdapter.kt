package com.example.mychatapp.adapter

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.mychatapp.R
import com.example.mychatapp.databinding.DeleteLayoutBinding
import com.example.mychatapp.databinding.SendMessageBinding
import com.example.mychatapp.model.Message
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class MessageAdapter (
    var context: Context,
    messages:ArrayList<Message>?,
    senderRoom:String,
    receiverRoom:String
):RecyclerView.Adapter<RecyclerView.ViewHolder?>()
{
    lateinit var messages:ArrayList<Message>
    val ITEM_SENT=1
    val ITEM_RECEIVE=2
    val senderRoom:String
    val receiverRoom: String

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return if (viewType == ITEM_SENT){
            val view = LayoutInflater.from(context).inflate(R.layout.send_message,parent,false)
            SentMsgHolder(view)
        }
        else{
            val view = LayoutInflater.from(context).inflate(R.layout.receive_message,parent,false)
            ReceiveMsgHolder(view)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val messages = messages[position]
        return if (FirebaseAuth.getInstance().uid == messages.SenderId){
            ITEM_SENT
        }else{
            ITEM_RECEIVE
        }
    }

    override fun getItemCount(): Int = messages.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val message = messages[position]
        if (holder.javaClass == SentMsgHolder::class.java) {
            val viewHolder = holder as SentMsgHolder
            if (message.message.equals("photo")) {
                viewHolder.binding.image.visibility = View.VISIBLE
                viewHolder.binding.message.visibility = View.GONE
                viewHolder.binding.mLinear.visibility = View.GONE
                Glide.with(context)
                    .load(message.imageUrl)
                    .placeholder(R.drawable.placeholder)
                    .into(viewHolder.binding.image)
            }
            viewHolder.binding.message.text = message
            viewHolder.itemView.setOnLongClickListener {

                val view = LayoutInflater.from(context)
                    .inflate(R.layout.delete_layout, null)
                val binding: DeleteLayoutBinding = DeleteLayoutBinding.bind(view)

                val dialog = AlertDialog.Builder(context)
                    .setTitle("Delete Message")
                    .setView(binding.root)
                    .create()
                binding.everyone.setOnClickListener {
                    message.message = "This message is removed"
                    message.messageId.let { it1 ->
                        FirebaseDatabase.getInstance().reference.child("chats")
                            .child(senderRoom)
                            .child("message")
                            .child(it1!!).setValue(message)

                    }
                    message.messageId.let { it1 ->
                        FirebaseDatabase.getInstance().reference.child("chats")
                            .child(receiverRoom)
                            .child("message")
                            .child(it1!!).setValue(message)
                    }
                    dialog.dismiss()
                }
                binding.delete.setOnClickListener {
                    message.messageId.let { it1 ->
                        FirebaseDatabase.getInstance().reference.child("chats")
                            .child(senderRoom)
                            .child("message")
                            .child(it1!!).setValue(null)
                    }
                    dialog.dismiss()
                }
                binding.cancel.setOnClickListener { dialog.dismiss() }
                dialog.show()
                false
            }

        } else {

            val ViewHolder = holder as ReceiveMsgHolder
            if (message.message.equals("photo")) {
                ViewHolder.binding.image.visibility = View.VISIBLE
                ViewHolder.binding.message.visibility = View.GONE
                ViewHolder.binding.M
                Glide.with(context)
                    .load(message.imageUrl)
                    .placeholder(R.drawable.placeholder)
                    .into(ViewHolder.binding.image)
            }

        }
        ViewHolder.binding.message.text = message
        ViewHolder.itemView.setOnLongClickListener {

            val view = LayoutInflater.from(context)
                .inflate(R.layout.delete_layout, null)
            val binding: DeleteLayoutBinding = DeleteLayoutBinding.bind(view)

            val dialog = AlertDialog.Builder(context)
                .setTitle("Delete Message")
                .setView(binding.root)
                .create()
            binding.everyone.setOnClickListener {
                message.message = "This message is removed"
                message.messageId.let { it1 ->
                    FirebaseDatabase.getInstance().reference.child("chats")
                        .child(senderRoom)
                        .child("message")
                        .child(it1!!).setValue(message)

                }
                message.messageId.let { it1 ->
                    FirebaseDatabase.getInstance().reference.child("chats")
                        .child(receiverRoom)
                        .child("message")
                        .child(it1!!).setValue(message)
                }
                dialog.dismiss()
            }
            binding.delete.setOnClickListener {
                message.messageId.let { it1 ->
                    FirebaseDatabase.getInstance().reference.child("chats")
                        .child(senderRoom)
                        .child("message")
                        .child(it1!!).setValue(null)
                }
                dialog.dismiss()
            }
            binding.cancel.setOnClickListener { dialog.dismiss() }
            dialog.show()
            false

        }



    }

    inner class SentMsgHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        var binding:SendMessageBinding = SendMessageBinding.bind(itemView)
    }
    inner class ReceiveMsgHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        var binding:SendMessageBinding = SendMessageBinding.bind(itemView)
    }
    init {
        if (messages !=null){
            this.messages = messages
        }
        this.senderRoom = senderRoom
        this.receiverRoom = receiverRoom
    }


}