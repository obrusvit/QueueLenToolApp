package com.aa4cc.obrusvit.queuelentool

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun sendCars(view: View){
        val editTextCars = findViewById<EditText>(R.id.editTextCars)
        val num = editTextCars.text.toString()
        performBgTask(num)
    }

    fun carsPlusOne(view: View){
        val inum = getStringFromEditTextCars().toInt()
        val newInum = inum+1
        setNewValToEditTextCars(newInum)
    }

    fun carsMinusOne(view: View){
        val inum = getStringFromEditTextCars().toInt()
        val newInum = inum-1
        setNewValToEditTextCars(newInum)
    }

    /* private area */
    private fun performBgTask(messageStr: String){
        toast("Sending $messageStr")
        doAsync {
            val ret = UdpSender.sendUDP(messageStr)
            uiThread {
                when(ret) {
                    true -> toast("Successfully sent")
                    false -> toast("Error in sending")
                }
            }
        }
    }

    private fun isInBounds(n: Int): Boolean = (n in 0..30)

    private fun getStringFromEditTextCars(): String{
        val editTextCars = findViewById<EditText>(R.id.editTextCars)
        val ret = editTextCars.text.toString()
        return when(ret.isEmpty()){
            true -> "0"
            false -> ret
        }
    }

    private fun setNewValToEditTextCars(newInum: Int) {
        if (isInBounds(newInum)) {
            val editTextCars = findViewById<EditText>(R.id.editTextCars)
            editTextCars.setText(newInum.toString())
        }
    }
}
