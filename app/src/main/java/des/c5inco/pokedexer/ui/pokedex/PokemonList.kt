package des.c5inco.pokedexer.ui.pokedex

import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.toBitmap
import com.skydoves.landscapist.coil.CoilImage
import des.c5inco.pokedexer.data.pokemon.LocalPokemonRepository
import des.c5inco.pokedexer.data.pokemon.SamplePokemonData
import des.c5inco.pokedexer.model.Pokemon
import des.c5inco.pokedexer.model.color
import des.c5inco.pokedexer.ui.common.*
import des.c5inco.pokedexer.ui.common.TypeLabelMetrics.Companion.SMALL
import des.c5inco.pokedexer.ui.theme.Theme.Companion.PokedexerTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PokemonList(
    loading: Boolean = false,
    pokemon: List<Pokemon>,
    onPokemonSelected: (Pokemon) -> Unit = {}
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        contentPadding = PaddingValues(32.dp),
        content = {
            item({ GridItemSpan(2) }) {
                Text(
                    text = "Pokedex",
                    style = MaterialTheme.typography.h4,
                    modifier = Modifier.padding(
                        top = 64.dp, bottom = 24.dp
                    )
                )
            }
            if (loading) {
                item({ GridItemSpan(2) }) {
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
fun PokeDexCard(
    modifier: Modifier = Modifier,
    pokemon: Pokemon,
    onPokemonSelected: (Pokemon) -> Unit = {}
) {
    Surface(
        modifier = modifier,
        color = pokemon.color(),
        shape = RoundedCornerShape(16.dp)
    ) {
        PokeDexCardContent(
            modifier = Modifier.clickable {
                onPokemonSelected(pokemon)
            },
            pokemon = pokemon
        )
    }
}

@Composable
fun PokeDexCardContent(
    modifier: Modifier = Modifier,
    pokemon: Pokemon
) {
    Box(modifier.height(120.dp)) {
        Column(
            Modifier.padding(top = 8.dp, start = 12.dp)
        ) {
            PokemonName(pokemon.name)
            PokemonTypeLabels(pokemon.typeOfPokemon, SMALL)
        }
        Text(
            text = formatId(pokemon.id),
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 10.dp, end = 12.dp)
                .graphicsLayer {
                    alpha = 0.1f
                }
        )
        PokeBall(
            Modifier
                .align(Alignment.BottomEnd)
                .offset(x = 5.dp, y = 10.dp)
                .requiredSize(96.dp),
            Color.White,
            0.25f
        )

        CoilImage(
            imageModel = artworkUrl(pokemon.image),
            contentDescription = pokemon.name,
            previewPlaceholder = placeholderPokemonImage(pokemon.image),
            success = { imageState ->
                val currentState = remember { MutableTransitionState(ImageState.Loading) }
                currentState.targetState = ImageState.Loaded
                val transition = updateTransition(currentState, label = "imageLoad")
                val animateBlur by transition.animateDp(label = "blur") { state ->
                    if (state == ImageState.Loading) 16.dp else 0.dp
                }

                imageState.drawable?.let {
                    Image(
                        bitmap = it.toBitmap().asImageBitmap(),
                        contentDescription = null,
                        modifier = Modifier
                            .blur(animateBlur, BlurredEdgeTreatment.Unbounded)
                    )
                }
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 8.dp, end = 8.dp)
                .size(72.dp)
        )
    }
}

@Composable
fun PokemonName(name: String?) {
    Text(
        text = name ?: "",
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        color = Color.White,
        modifier = Modifier.padding(bottom = 8.dp)
    )
}

@Composable
fun PokemonListScreen(
    viewModel: PokedexViewModel,
    onPokemonSelected: (Pokemon) -> Unit = {}
) {
    Surface(Modifier.fillMaxSize()) {
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
fun PokeDexCardPreview() {
    PokedexerTheme {
        Surface {
            Column(
                Modifier.width(200.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                PokeDexCard(
                    modifier = Modifier.fillMaxWidth(),
                    pokemon = SamplePokemonData[0]
                )
                PokeDexCard(
                    modifier = Modifier.fillMaxWidth(),
                    pokemon = SamplePokemonData[3]
                )
                PokeDexCard(
                    modifier = Modifier.fillMaxWidth(),
                    pokemon = SamplePokemonData[6]
                )
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
