package ca.frousseau.lieux

import android.os.Bundle
import android.widget.Toast
import androidx.preference.PreferenceFragmentCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.preference.SwitchPreferenceCompat

class fragment_parametres : PreferenceFragmentCompat() {

    lateinit var isAdmin : SwitchPreferenceCompat


    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        isAdmin = findPreference("isAdmin")!!

        // toggle adming mode true/false and display toast
        isAdmin.setOnPreferenceChangeListener { preference, newValue ->
            if (newValue == true) {
                Toast.makeText(context, "Mode admin activé", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Mode admin désactivé", Toast.LENGTH_SHORT).show()
            }
            true
        }


    }






}