package des.c5inco.pokedexer.ui.pokedex

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.toBitmap
import com.skydoves.landscapist.coil.CoilImage
import des.c5inco.pokedexer.R
import des.c5inco.pokedexer.data.pokemon.SamplePokemonData
import des.c5inco.pokedexer.model.Pokemon
import des.c5inco.pokedexer.model.color
import des.c5inco.pokedexer.ui.common.*
import des.c5inco.pokedexer.ui.common.TypeLabelMetrics.Companion.MEDIUM
import des.c5inco.pokedexer.ui.pokedex.section.AboutSection
import des.c5inco.pokedexer.ui.pokedex.section.BaseStatsSection
import des.c5inco.pokedexer.ui.pokedex.section.EvolutionSection
import des.c5inco.pokedexer.ui.pokedex.section.MovesSection
import des.c5inco.pokedexer.ui.theme.Theme.Companion.PokedexerTheme

@Composable
fun PokemonDetails(
    pokemon: Pokemon
) {
    Surface(
        color = pokemon.color()
    ) {
        Box(Modifier.fillMaxSize()) {
            RoundedRectangleDecoration(
                Modifier
                    .offset(x = (-60).dp, y = (-50).dp)
                    .rotate(-20f)
            )
            DottedDecoration(
                Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 4.dp, end = 100.dp)
            )
            RotatingPokeBall(Modifier.align(Alignment.TopCenter))
            HeaderLeft(pokemon = pokemon)
            HeaderRight(
                modifier = Modifier.align(Alignment.TopEnd),
                pokemon = pokemon
            )
            CardContent(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 300.dp)
                ,
                pokemon = pokemon
            )

            PokemonImage(
                modifier = Modifier.align(Alignment.TopCenter),
                image = pokemon.image,
                description = pokemon.name
            )
        }
    }
}

private enum class Sections(val title: String) {
    About("About"),
    BaseStats("Base stats"),
    Evolution("Evolution"),
    Moves("Moves")
}

@Composable
private fun CardContent(
    modifier: Modifier,
    pokemon: Pokemon
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
    ) {
        Column(
            Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            val sectionTitles = Sections.values().map { it.title }
            var section by remember { mutableStateOf(Sections.BaseStats) }
            TabRow(
                selectedTabIndex = section.ordinal,
                divider = {}
            ) {
                sectionTitles.forEachIndexed { index, text ->
                    Tab(
                        selected = index == section.ordinal,
                        onClick = { section = Sections.values()[index] },
                    ) {
                        Text(text, modifier = Modifier.padding(vertical = 12.dp))
                    }
                }
            }
            Box(Modifier.padding(24.dp)) {
                when (section) {
                    Sections.About -> AboutSection(pokemon)
                    Sections.BaseStats -> BaseStatsSection(pokemon)
                    Sections.Evolution -> EvolutionSection(pokemon)
                    else -> MovesSection(pokemon)
                }
            }
        }
    }
}

@Composable
private fun RoundedRectangleDecoration(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(150.dp)
            .background(color = Color(0x22FFFFFF), shape = RoundedCornerShape(32.dp))
    )
}

@Composable
private fun DottedDecoration(
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(id = R.drawable.dotted),
        contentDescription = null,
        modifier = modifier.size(width = 63.dp, height = 34.dp),
        alpha = 0.3f
    )
}

@Composable
private fun HeaderLeft(
    modifier: Modifier = Modifier,
    pokemon: Pokemon
) {
    Column(
        modifier.padding(top = 40.dp, bottom = 32.dp, start = 32.dp, end = 32.dp)
    ) {
        Text(
            text = pokemon.name,
            style = MaterialTheme.typography.h4,
            color = Color.White
        )

        pokemon.typeOfPokemon.let {
            Row {
                PokemonTypeLabels(it, MEDIUM)
            }
        }
    }
}

@Composable
private fun HeaderRight(
    modifier: Modifier = Modifier,
    pokemon: Pokemon
) {
    Column(
        modifier.padding(top = 52.dp, bottom = 32.dp, start = 32.dp, end = 32.dp),
        horizontalAlignment = Alignment.End
    ) {
        Text(
            text = formatId(pokemon.id),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = pokemon.category,
            fontSize = 12.sp,
            color = Color.White
        )
    }
}

@Composable
private fun RotatingPokeBall(
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition()
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 4000, easing = LinearEasing)
        )
    )

    PokeBall(
        modifier = modifier
            .padding(top = 140.dp)
            .rotate(angle)
            .size(200.dp),
        tint = Color(0xffF5F5F5),
        alpha = 0.25f
    )
}

@Composable
private fun PokemonImage(
    modifier: Modifier = Modifier,
    image: Int,
    description: String?
) {
    CoilImage(
        imageModel = artworkUrl(image),
        contentDescription = description,
        previewPlaceholder = placeholderPokemonImage(image),
        success = { imageState ->
            val currentState = remember { MutableTransitionState(ImageState.Loading) }
            currentState.targetState = ImageState.Loaded
            val transition = updateTransition(currentState, label = "imageLoad")
            val animateScale by transition.animateFloat(
                label = "scale",
                transitionSpec = { spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = 100f
                ) }
            ) { state ->
                if (state == ImageState.Loading) 0.8f else 1f
            }
            val animateOffsetY by transition.animateDp(
                label = "offsetY",
                transitionSpec = { spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = 100f
                ) }
            ) { state ->
                if (state == ImageState.Loading) (16).dp else 0.dp
            }

            imageState.drawable?.let {
                Image(
                    bitmap = it.toBitmap().asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier
                        .matchParentSize()
                        .scale(animateScale)
                        .offset(y = animateOffsetY)
                )
            }
        },
        modifier = modifier
            .padding(top = 140.dp)
            .size(200.dp)
    )
}

@Preview
@Composable
fun PokemonDetailsPreview() {
    PokedexerTheme {
        Surface(Modifier.fillMaxSize()) {
            PokemonDetails(SamplePokemonData.first())
        }
    }
}

@Preview
@Composable
fun PokemonDetailsPreviewLast() {
    PokedexerTheme {
        Surface(Modifier.fillMaxSize()) {
            PokemonDetails(SamplePokemonData.last())
        }
    }
}