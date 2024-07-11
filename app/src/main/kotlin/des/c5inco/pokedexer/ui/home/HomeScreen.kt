package des.c5inco.pokedexer.ui.home

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.hilt.navigation.compose.hiltViewModel
import des.c5inco.pokedexer.model.Type
import des.c5inco.pokedexer.ui.home.appbar.MainAppBar
import des.c5inco.pokedexer.ui.home.appbar.SearchResult
import des.c5inco.pokedexer.ui.home.appbar.search.ItemResultExpandedCard
import des.c5inco.pokedexer.ui.home.appbar.search.MoveResultCard
import des.c5inco.pokedexer.ui.home.appbar.search.MoveResultExpandedCard
import des.c5inco.pokedexer.ui.theme.AppTheme

sealed class MenuItem(
    val label: String,
    val typeColor: Type
) {
    object Pokedex : MenuItem("Pokedex", Type.Grass)
    object Moves : MenuItem("Moves", Type.Fire)
    object Abilities : MenuItem("Abilities", Type.Water)
    object Items : MenuItem("Items", Type.Electric)
    object Locations : MenuItem("Locations", Type.Dragon)
    object TypeCharts : MenuItem("Type charts", Type.Psychic)
}

@Composable
fun HomeScreenRoute(
    viewModel: HomeViewModel,
    onMenuItemSelected: (MenuItem) -> Unit = { _ -> },
    onSearchResultSelected: (SearchResult) -> Unit = { _ -> }
) {
    val searchResponse by viewModel.searchResponses.collectAsState()

    HomeScreen(
        searchText = viewModel.searchText,
        searchResponse = searchResponse,
        onMenuItemSelected = onMenuItemSelected,
        onSearchResultSelected = onSearchResultSelected
    )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun HomeScreen(
    searchText: TextFieldState,
    searchResponse: SearchResponse,
    onMenuItemSelected: (MenuItem) -> Unit = { _ -> },
    onSearchResultSelected: (SearchResult) -> Unit = { _ -> }
) {
    var openAlertDialog by remember { mutableStateOf(false) }
    var expandSearchResult by remember { mutableStateOf(false) }
    var searchResult by remember { mutableStateOf<SearchResult?>(null) }

    SharedTransitionLayout {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Box {
                Column {
                    MainAppBar(
                        searchText = searchText,
                        searchResponse = searchResponse,
                        selectedSearchResult = searchResult,
                        sharedTransitionScope = this@SharedTransitionLayout,
                        onMenuItemSelected = {
                            when (it) {
                                MenuItem.Moves,
                                MenuItem.Pokedex,
                                MenuItem.Items ->
                                    onMenuItemSelected(it)

                                else ->
                                    openAlertDialog = true
                            }
                        },
                        onSearchResultSelected = {
                            // TODO: Build Pokemon expanded result card later, for now navigate to details
                            if (it is SearchResult.PokemonEvent) {
                                onSearchResultSelected(it)
                            } else {
                                expandSearchResult = true
                                searchResult = it
                            }
                        }
                    )
                }
            }
        }
        AnimatedContent(
            targetState = searchResult,
            transitionSpec = {
                fadeIn() togetherWith fadeOut()
            },
            label = "searchResultSharedElementTransition"
        ) { targetResult ->
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                targetResult?.let {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable {
                                expandSearchResult = false
                                searchResult = null
                            }
                            .background(Color.Black.copy(alpha = 0.5f))
                    )
                    when (it) {
                        is SearchResult.PokemonEvent -> TODO()
                        is SearchResult.ItemEvent -> {
                            ItemResultExpandedCard(
                                item = it.item,
                                animatedVisibilityScope = this@AnimatedContent
                            )
                        }
                        is SearchResult.MoveEvent -> {
                            MoveResultExpandedCard(
                                move = it.move,
                                animatedVisibilityScope = this@AnimatedContent
                            )
                        }
                    }
                }
            }
        }
    }


    if (openAlertDialog) {
        AlertDialog(
            onDismissRequest = { openAlertDialog = false },
            title = {
                Text(text = "\uD83D\uDEA7 Feature WIP")
            },
            text = {
                Text(
                    "This area is under construction!"
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        openAlertDialog = false
                    }
                ) {
                    Text("Dismiss")
                }
            },
        )
    }
}

@PreviewLightDark
@Composable
fun HomeScreenPreview() {
    AppTheme {
        HomeScreen(
            searchText = TextFieldState(),
            searchResponse = SearchResponse(
                currentText = "pikachu"
            )
        )
    }
}