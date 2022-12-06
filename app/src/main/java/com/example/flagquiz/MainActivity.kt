package com.example.flagquiz

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import android.widget.Toolbar.LayoutParams
import androidx.core.widget.addTextChangedListener
import com.example.flagquiz.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var binding: ActivityMainBinding
    lateinit var list: ArrayList<Flag>
    var index = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initFlag()

        initUI()
    }

    private fun initUI() {
        binding.iv.setImageResource(list[index].img)
        val str = "ABSDEFGHIJKLMN"
        val lengthStr = str.length
        val text = list[index].name
        val length = text.length

        val charList = ArrayList<Char>()

        for (c in text) {
            charList.add(c)
        }
        for (i in 0 until lengthStr - length) {
            charList.add(str[i])
        }
        charList.shuffle()

        for (i in 0 until 7) {
            val button = Button(this)
            button.text = charList[i].toString()
            val layoutParams = LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1f)
            button.layoutParams = layoutParams
            button.setOnClickListener(this)

            binding.question1.addView(button)
        }
        for (i in 7 until 14) {
            val button = Button(this)
            button.text = charList[i].toString()
            button.setBackgroundColor(Color.parseColor("#FF1234"))
            button.setOnClickListener(this)
            val layoutParams = LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1f)
            button.layoutParams = layoutParams

            binding.question2.addView(button)
        }
    }

    private fun initFlag() {
        list = ArrayList()

        list.add(Flag("USA", R.drawable.ic_launcher_foreground))
        list.add(Flag("UZB", R.drawable.ic_launcher_background))
        list.add(Flag("Korea", R.drawable.ic_launcher_foreground))
        list.add(Flag("China", R.drawable.ic_launcher_background))
        list.add(Flag("Turkish", R.drawable.ic_launcher_foreground))
    }

    override fun onClick(v: View?) {
        val button = v as Button
        button.visibility = View.INVISIBLE

        val btn = Button(this)
        btn.text = button.text
        btn.setOnClickListener {
            binding.answer.removeView(btn)
            button.visibility = View.VISIBLE
        }
        val layoutParams = LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1f)
        btn.layoutParams = layoutParams

        binding.answer.addView(btn)

        val childCount = binding.answer.childCount

        if (childCount == list[index].name.length) {
            var stringBuilder = ""
            for (i in 0 until childCount) {
                val newBtn = binding.answer.getChildAt(i) as Button
                stringBuilder += newBtn.text.toString()
            }
            if (stringBuilder == list[index].name) {
                Toast.makeText(this, "Sucsessfuly", Toast.LENGTH_SHORT).show()
                ++index
                binding.question1.removeAllViews()
                binding.question2.removeAllViews()
                binding.answer.removeAllViews()

                initUI()
            } else {
                Toast.makeText(this, "UnSucsessfuly", Toast.LENGTH_SHORT).show()
            }
        }
    }
}