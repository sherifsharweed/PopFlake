package com.shekoo.popflake.ui.settings

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.shekoo.popflake.databinding.FragmentSettingBinding
import com.shekoo.popflake.utilities.Constants
import com.shekoo.popflake.utilities.Constants.DARK_MODE
import com.shekoo.popflake.utilities.Constants.FILE_NAME
import com.shekoo.popflake.utilities.Network

class SettingFragment : Fragment() {

    private lateinit var binding: FragmentSettingBinding
    private val sharedPreferences: SharedPreferences by lazy {
        requireContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSettingBinding.inflate(inflater, container, false)
        checkDarkMode()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.darkMode.setOnCheckedChangeListener { _, boolean ->
            val editor = sharedPreferences.edit()
            editor.putBoolean(DARK_MODE, boolean)
            editor.apply()
            checkDarkMode()
        }
        binding.apply {
            submitButton.setOnClickListener {
                if (Network.hasInternet(requireContext())) {
                    when {
                        nameTextView.text.isNullOrEmpty() -> {
                            Toast.makeText(requireContext(), "Insert Name", Toast.LENGTH_SHORT)
                                .show()
                        }
                        phoneTextView.text.isNullOrEmpty() -> {
                            Toast.makeText(requireContext(), "Insert Phone", Toast.LENGTH_SHORT)
                                .show()
                        }
                        complainTextView.text.isNullOrEmpty() -> {
                            Toast.makeText(
                                requireContext(),
                                "Insert Complaint Body",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        else -> {
                            Toast.makeText(
                                requireContext(),
                                "Complaint Sent Successfully",
                                Toast.LENGTH_SHORT
                            ).show()
                            nameTextView.text.clear()
                            phoneTextView.text.clear()
                            complainTextView.text.clear()
                        }
                    }
                } else Toast.makeText(
                    requireContext(),
                    "No Internet Connection",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }

    private fun checkDarkMode() {
        val darkMode: Boolean = sharedPreferences.getBoolean(DARK_MODE, false)
        if (darkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            binding.darkMode.isChecked = true
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            binding.darkMode.isChecked = false
        }
    }


}