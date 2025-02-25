package com.aa4cc.obrusvit.queuelentool

import android.os.Bundle
import android.text.InputType
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.EditTextPreference
import androidx.preference.PreferenceFragmentCompat

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings, SettingsFragment())
            .commit()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }


    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)

            // set port EditTextPreference to number type
            val portPreference: EditTextPreference? = findPreference("destination_port")
            portPreference?.setOnBindEditTextListener {
                editText -> editText.inputType = InputType.TYPE_CLASS_NUMBER
            }
            // set approach EditTextPreference to number type
            val approachPreference: EditTextPreference? = findPreference("approach_num")
            approachPreference?.setOnBindEditTextListener {
                editText -> editText.inputType = InputType.TYPE_CLASS_NUMBER
            }
        }


    }
}