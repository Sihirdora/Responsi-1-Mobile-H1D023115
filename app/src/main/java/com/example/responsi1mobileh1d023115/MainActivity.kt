package com.example.responsi1mobileh1d023115

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

import com.example.responsi1mobileh1d023115.fragments.ClubHomeFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        // Muat ClubHomeFragment saat aplikasi dimulai
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ClubHomeFragment())
                .commit()
        }
    }

    /**
     * Fungsi utilitas untuk mengganti Fragment di dalam FrameLayout container.
     * Digunakan oleh semua Fragment untuk berpindah halaman.
     */
    fun navigateTo(fragment: Fragment) { // Menggunakan androidx.fragment.app.Fragment
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null) // Agar tombol 'Back' sistem bisa berfungsi
            .commit()
    }
}