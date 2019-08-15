package com.aa4cc.obrusvit.queuelentool

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import androidx.preference.PreferenceManager
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread
import kotlinx.android.synthetic.main.activity_main.*




class MainActivity : AppCompatActivity() {

    /* Lifecycle area */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(savedInstanceState!=null){
            val strToLoad: String? = savedInstanceState.getString("EditTextString")
            editTextCars.setText(strToLoad)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_go_to_settings -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val strToSave: String = getStringFromEditTextCars()
        outState.putString("EditTextString", strToSave)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val strToLoad: String? = savedInstanceState.getString("EditTextString")
        editTextCars.setText(strToLoad)
    }


    /* fun area */
    fun sendCars(view: View){
        val num = getStringFromEditTextCars()
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

    /* private fun area */
    private fun performBgTask(messageStr: String){
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        val debug = pref.getBoolean("debug", false)

        val socket = if(!debug) {
            val addr = pref.getString("destination_address", "")
            val port = pref.getString("destination_port", "0")
            if (addr == null || port == null) {
                toast("Not sent, address or port null")
                return
            }
            UdpSocketOptions(addr, port.toInt())
        }else{
            DefaultUdpSocketSoftOptions
        }

        toast("Sending $messageStr")
        doAsync {
            val ret = UdpSender.sendUDP(messageStr, socket)
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
