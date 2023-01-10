package com.example.mylearnapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList
import kotlin.random.Random

class GameActivity : AppCompatActivity() {

    private var counter = 0
    private lateinit var myList: ArrayList<Int>
    private var calculator = 0
    private var score = 0
    private lateinit var myadapter: OperationAdapter
    private var timer: CountDownTimer ? = null
    private var level = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        level = intent.getIntExtra(LEVEL_TAG, 0)
        timer = object : CountDownTimer(10000, 1000){
            override fun onFinish() {
                val intent = Intent()
                intent.putExtra(RESULTAT_TAG, myadapter.itemCount)
                setResult(RESULT_OK, intent)
                finish()
            }

            override fun onTick(p0: Long) {
                val minutes = TimeUnit.MILLISECONDS.toMinutes(p0)
                val seconde = TimeUnit.MILLISECONDS.toSeconds(p0) - minutes * 60

                findViewById<TextView>(R.id.timerTv).text = "$minutes : $seconde"
            }
        }.start()
        generateAnswer()
        myadapter = OperationAdapter()
        findViewById<RecyclerView>(R.id.recycleView).adapter = myadapter

        var btn = findViewById<Button>(R.id.IncrementButton)
        var input = findViewById<EditText>(R.id.answer)
        btn.setOnClickListener{
           if(input.text.toString() == calculator.toString()){
               score++
               myadapter.add(findViewById<TextView>(R.id.mainTitle).text.toString() + "="
                       + input.text.toString())
               generateAnswer()
           }else{
               Toast.makeText(this, "Wrong Answer", Toast.LENGTH_SHORT).show()
           }
        }

    }



   private fun generateAnswer(){
        val firstNumber = kotlin.random.Random.nextInt(0, 100)
        val secondNumber = kotlin.random.Random.nextInt(0, 100)
        var title = "$firstNumber + $secondNumber"
        calculator = firstNumber + secondNumber
        findViewById<TextView>(R.id.mainTitle).text = title
    }

    /*recycle view */
   class OperationAdapter : RecyclerView.Adapter<OperationAdapter.OperationViewHolder>(){

            private val operations = ArrayList<String>()

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OperationViewHolder {
                return OperationViewHolder(LayoutInflater.from(parent.context), parent)
            }

            override fun onBindViewHolder(holder: OperationViewHolder, position: Int) {
                holder.bind(operations[position])
            }

            override fun getItemCount(): Int {
                return operations.size
            }

            class OperationViewHolder(inflater: LayoutInflater, viewGroup: ViewGroup): RecyclerView.ViewHolder(
                inflater.inflate(R.layout.items_operations, viewGroup, false)){
                fun bind(oparation: String){
                    itemView.findViewById<TextView>(R.id.OperationTv).text = oparation
                }
            }

            fun add(operation : String){
                operations.add(0, operation)
                notifyItemInserted(0)
            }
        }

    interface Operation{
        fun result():Int
        fun getTextColor():OperationTextColor

        fun getOperationTemplate():String

        companion object{
            fun generateOperation(level:Int) : Operation{
                val num1 = Random.nextInt(0, 100)
                val num2 = Random.nextInt(0, 100)
                return if(level == 0 || num1 % 2 == 0){
                    Addition(num1, num2)
                }else{
                    Substraction(num1, num2)
                }
            }
        }
    }

    data class Substraction(val num1:Int, val num2:Int): Operation {
        override fun result() :Int{
            return num1 - num2
        }

        override fun getTextColor(): OperationTextColor {
            TODO("Not yet implemented")
        }

        override fun getOperationTemplate(): String {
            return "$num1 -  $num2"
        }

        override fun toString(): String {
            return "$num1 - $num2 = ${result()}"
        }
    }

    data class Addition(val num1: Int, val num2: Int) : Operation {
        override fun result(): Int {
            return num1 + num2
        }

        override fun getTextColor(): OperationTextColor {
            TODO("Not yet implemented")
        }

        override fun getOperationTemplate(): String {
            return "$num1 + $num2"
        }

    }

    enum class OperationTextColor{
        Bleu, RED
    }

    companion object{
        const val RESULTAT_TAG = "RESULT"
        const val LEVEL_TAG = "LEVEL"
    }
}
