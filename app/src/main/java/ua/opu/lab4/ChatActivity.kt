package ua.opu.lab4

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.github.javafaker.Faker
import ua.opu.lab4.adapter.MessagesAdapter
import ua.opu.lab4.databinding.ActivityChatBinding
import ua.opu.lab4.model.Message
import java.util.*


class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding
    private lateinit var adapter: MessagesAdapter
    private lateinit var messages: ArrayList<Message>
    private var senderUid: String? = null
    private var receiverUid: String? = null
    private val faker = Faker()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        messages = ArrayList<Message>()
        val name = intent.getStringExtra("name")
        val profile = intent.getStringExtra("image")
        binding.name.text = name
        Glide.with(this@ChatActivity).load(profile)
            .placeholder(R.drawable.avatar)
            .into(binding.profile01)
        binding.imageView2.setOnClickListener { finish() }
        receiverUid = intent.getStringExtra("uid")
        senderUid = faker.idNumber().invalid()

        adapter = MessagesAdapter(this)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
        adapter.submitList(null)

        binding.sendBtn.setOnClickListener {
            val messageTxt: String = binding.messageBox.text.toString()
            val date = Date()
            val message = Message.Builder()
                .message(messageTxt)
                .senderId(senderUid!!)
                .timeStamp(date.time)
                .build()
            binding.messageBox.setText("")
            messages.add(message)
            adapter.submitList(messages.toList())
        }
    }

}