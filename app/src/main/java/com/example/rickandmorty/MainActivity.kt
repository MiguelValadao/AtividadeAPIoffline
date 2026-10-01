package com.example.rickandmorty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.rickandmorty.data.CharacterRepository
import com.example.rickandmorty.data.local.AppDatabase
import com.example.rickandmorty.data.remote.RetrofitInstance
import com.example.rickandmorty.ui.CharacterViewModel
import com.example.rickandmorty.ui.MultiverseRadarScreen
import com.example.rickandmorty.ui.theme.RickAndMortyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val repository = CharacterRepository(
            api = RetrofitInstance.api,
            characterDao = AppDatabase.getInstance(this).characterDao()
        )

        setContent {
            RickAndMortyTheme {
                val viewModel: CharacterViewModel = viewModel(
                    factory = CharacterViewModel.Factory(repository)
                )
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MultiverseRadarScreen(
                        viewModel = viewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
