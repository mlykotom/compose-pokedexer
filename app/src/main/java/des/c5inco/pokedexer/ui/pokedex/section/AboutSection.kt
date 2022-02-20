package des.c5inco.pokedexer.ui.pokedex.section

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import des.c5inco.pokedexer.ui.entity.Pokemon

@Composable
fun AboutSection(
    pokemon: Pokemon
) {
    pokemon.description?.let {
        Text(it)
    }
}
