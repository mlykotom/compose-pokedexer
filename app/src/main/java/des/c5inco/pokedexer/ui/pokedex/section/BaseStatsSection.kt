package des.c5inco.pokedexer.ui.pokedex.section

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import des.c5inco.pokedexer.data.pokemon.SamplePokemonData
import des.c5inco.pokedexer.model.Pokemon
import des.c5inco.pokedexer.ui.common.Label
import des.c5inco.pokedexer.ui.theme.M3Theme
import des.c5inco.pokedexer.ui.theme.TypesMaterialTheme
import kotlinx.coroutines.delay

data class Stat(
    val label: String,
    val value: Int?,
    val max: Int = 100
) {
    val progress: Float =
        1f * (value ?: 0) / max
}

@Composable
fun BaseStatsSection(
    pokemon: Pokemon
) {
    val stats = listOf(
        Stat("HP", pokemon.hp),
        Stat("Attack", pokemon.attack),
        Stat("Defense", pokemon.defense),
        Stat("Sp. Atk", pokemon.specialAttack),
        Stat("Sp. Def", pokemon.specialDefense),
        Stat("Speed", pokemon.speed),
    )
    val trackColor = MaterialTheme.colorScheme.surfaceVariant

    Column(Modifier.fillMaxWidth()) {
        stats.forEachIndexed { idx, stat ->
            val statValue = remember { Animatable(0f) }

            LaunchedEffect(stat) {
                delay(70L * idx)
                statValue.animateTo(
                    targetValue = stat.progress,
                    animationSpec = spring(
                        0.6f,
                        1000f
                    )
                )
            }

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Label(
                    text = stat.label,
                    modifier = Modifier
                        .weight(1f)
                        .graphicsLayer { alpha = 0.7f }
                )
                Text(
                    "${stat.value}",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .weight(0.6f)
                )
                TypesMaterialTheme(
                    types = pokemon.typeOfPokemon
                ) {
                    LinearProgressIndicator(
                        progress = statValue.value,
                        trackColor = trackColor,
                        modifier = Modifier
                            .clip(RoundedCornerShape(100))
                            .weight(2.5f)
                    )
                }
            }
        }
    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun BaseStatsSectionPreview() {
    M3Theme {
        Surface(Modifier.fillMaxWidth()) {
            BaseStatsSection(pokemon = SamplePokemonData[0])
        }
    }
}