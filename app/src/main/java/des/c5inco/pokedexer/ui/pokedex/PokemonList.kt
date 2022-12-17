package des.c5inco.pokedexer.ui.pokedex

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import des.c5inco.pokedexer.data.pokemon.LocalPokemonRepository
import des.c5inco.pokedexer.model.Pokemon
import des.c5inco.pokedexer.ui.common.PokeBallBackground
import des.c5inco.pokedexer.ui.theme.Theme.Companion.PokedexerTheme

@Composable
fun PokemonList(
    loading: Boolean = false,
    pokemon: List<Pokemon>,
    onPokemonSelected: (Pokemon) -> Unit = {}
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(32.dp),
        content = {
            item(span = { GridItemSpan(2) }) {
                Text(
                    text = "Pokedex",
                    style = MaterialTheme.typography.h4,
                    modifier = Modifier.padding(
                        top = 64.dp, bottom = 24.dp
                    )
                )
            }
            if (loading) {
                item(span = { GridItemSpan(2) }) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(
                            color = Color.Black,
                            modifier = Modifier
                                .size(48.dp)
                                .padding(vertical = 24.dp)
                        )
                    }
                }
            } else {
                items(pokemon) { pokemon ->
                    PokeDexCard(
                        pokemon = pokemon,
                        onPokemonSelected = onPokemonSelected
                    )
                }
            }
        }
    )
}

@Composable
fun PokemonListScreen(
    viewModel: PokedexViewModel,
    onPokemonSelected: (Pokemon) -> Unit = {}
) {
    Surface(
        Modifier
            .fillMaxSize()
            .navigationBarsPadding()
    ) {
        Box {
            PokeBallBackground(
                Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = 90.dp, y = (-70).dp)
            )

            PokemonList(
                loading = viewModel.uiState.loading,
                pokemon = viewModel.uiState.pokemon
            ) {
                onPokemonSelected(it)
            }
        }
    }
}

@Preview
@Composable
private fun PokemonListPreview() {
    PokedexerTheme {
        PokemonListScreen(
            PokedexViewModel(LocalPokemonRepository())
        )
    }
}
