package com.example.foodly.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.example.foodly.LoginActivity
import com.example.foodly.MainActivity
import com.example.foodly.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButtonToggleGroup
import com.parse.ParseUser


class ProfileFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    private fun logoutUser() {
        ParseUser.logOut()
        val intent = Intent(requireContext(), LoginActivity::class.java)
        startActivity(intent)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.btn_logout).setOnClickListener {
            logoutUser()
        }

        var bottomNavigationView: BottomNavigationView = requireActivity().findViewById(R.id.bottom_navigation)

        val username = view.findViewById<TextView>(R.id.profile_username)
        username.setText(ParseUser.getCurrentUser()?.username)

//        Theme Switch feature
        val btnTheme = view.findViewById<MaterialButtonToggleGroup>(R.id.btn_theme)

        btnTheme.addOnButtonCheckedListener(object : MaterialButtonToggleGroup.OnButtonCheckedListener {
            override fun onButtonChecked(group: MaterialButtonToggleGroup?, checkedId: Int, isChecked: Boolean) {
                if (isChecked) {
                    val theme = when (checkedId) {
                        R.id.btn_light_mode -> AppCompatDelegate.MODE_NIGHT_NO
                        R.id.btn_dark_mode -> AppCompatDelegate.MODE_NIGHT_YES
                        else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                    }
                    AppCompatDelegate.setDefaultNightMode(theme)
                    bottomNavigationView.setSelectedItemId(R.id.action_home)

                }
            }
        })
        }
}