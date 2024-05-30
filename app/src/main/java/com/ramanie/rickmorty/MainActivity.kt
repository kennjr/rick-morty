package com.ramanie.rickmorty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ramanie.rickmorty.network.KtorClient
import com.ramanie.rickmorty.presentation.character_episodes.CharacterEpisodesScreen
import com.ramanie.rickmorty.presentation.charcter_details.CharacterDetailsScreen
import com.ramanie.rickmorty.presentation.home.HomeScreen
import com.ramanie.rickmorty.ui.theme.RickMortyTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    // private val ktorClient = KtorClient()
    @Inject
    lateinit var ktorClient: KtorClient

    companion object{
        const val CHARACTER_ID_PARAM = "characterId"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val navController = rememberNavController()
            RickMortyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(navController = navController, startDestination = "home_screen"){
                        composable(route = "home_screen"){
                            HomeScreen(onCharacterSelected = {
                                navController.navigate("character_details/$it")
                            })
                        }
                        composable("character_details/{$CHARACTER_ID_PARAM}", arguments = listOf(
                            navArgument(CHARACTER_ID_PARAM){ type = NavType.IntType }
                        )){
                            val charId = it.arguments?.getInt(CHARACTER_ID_PARAM) ?: 13
                            CharacterDetailsScreen(characterId = charId){
                                navController.navigate("character_episodes/$charId")
                            }
                        }
                        composable("character_episodes/{$CHARACTER_ID_PARAM}", arguments = listOf(
                            navArgument(CHARACTER_ID_PARAM){ type = NavType.IntType }
                        )){
                            CharacterEpisodesScreen(ktorClient = ktorClient,
                                characterId = it.arguments?.getInt(CHARACTER_ID_PARAM) ?: 13)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RickMortyTheme {
        Greeting("Android")
    }
}