package ua.opu.lab4

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.github.javafaker.Faker
import ua.opu.lab4.adapter.UserAdapter
import ua.opu.lab4.databinding.ActivityMainBinding
import ua.opu.lab4.model.User

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var users: ArrayList<User> = ArrayList()
    private lateinit var usersAdapter: UserAdapter
    private val faker = Faker()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fillListUsers()
        usersAdapter = UserAdapter(this, users)
        val layoutManager = GridLayoutManager(this@MainActivity, 2)

        binding.mRec.layoutManager = layoutManager

        usersAdapter = UserAdapter(this, users)
        binding.mRec.layoutManager = layoutManager
        binding.mRec.adapter = usersAdapter

    }

    private fun fillListUsers() {
        for (i in 1..6) {
            val user = User.Builder()
                .uid(faker.idNumber().invalid())
                .name(faker.name().fullName())
                .phoneNumber(faker.phoneNumber().phoneNumber())
                .description(faker.job().position())
                .build()

            users.add(user)
        }
    }

}