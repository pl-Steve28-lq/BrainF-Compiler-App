package com.example.kotlinpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private fun BrainFuck(code:String, input:String): String {
        fun indexFind(ind: Int, c:String): Int {
            var leftdatas:MutableList<Int> = ArrayList()
            var rightdatas:MutableList<Int> = ArrayList()
            var index = 0
            var leftbrind = -1
            var rightbrind = -1
            var res = 0
            loop@ for (i in c) {
                when (i) {
                    '[' -> { leftdatas.add(index); if ( index == ind ) { res = 1; break@loop }; leftbrind++ }
                    ']' -> { rightdatas.add(index); if ( index == ind ) { res = 2; break@loop }; rightbrind++ }
                }
                index++
            }
            if (res == 1) return rightdatas[rightbrind]
            if (res == 2) return leftdatas[leftbrind]
            return 0
        }
        val regex = """[^+\-><.,\[\]]+""".toRegex()
        val coder = regex.replace(code,"").replace(" ","");
        val value:MutableList<Int> = MutableList(256,{0})
        var result = ""
        var pointer = 0
        var inputindex = 0
        var x = 0
        val y = coder.length
        var cnt = 0
        while (x < y) {
            pointer %= 256
            var q = coder[x]
            when (q) {
                '+' -> value[pointer]++
                '-' -> value[pointer]--
                '>' -> pointer++
                '<' -> pointer--
                '[' -> { if (value[pointer] == 0) x = indexFind(x, coder)-1 }
                ']' -> { if (value[pointer] != 0) x = indexFind(x, coder)-1 }
                '.' -> result += value[pointer].toChar()
                ',' -> {
                    try {
                        value[pointer] = input[inputindex].toInt()
                        inputindex++
                    }
                    catch (e: StringIndexOutOfBoundsException) {
                    }
                }
            }
            x++
            cnt++
            if (cnt == 5000000) { return "Loop Error at (${indexFind(x, coder)}, $x)!" }
        }
        return result
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("Test","Start!!!")

        var result = findViewById<TextView>(R.id.result)
        val compile = findViewById<Button>(R.id.compile)
        val input = findViewById<EditText>(R.id.stdin)
        val Code = findViewById<EditText>(R.id.Code)

        compile.setOnClickListener {
            var codes = Code.text.toString()
            var inputs = input.text.toString()
            result.text = BrainFuck(codes,inputs)
        }
    }
}