package des.c5inco.pokedexer.ui.common

import androidx.compose.foundation.Image
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import com.skydoves.landscapist.coil.CoilImage
import des.c5inco.pokedexer.data.pokemon.placeholderPokemonImage

@Composable
fun PokemonImage(
    modifier: Modifier = Modifier,
    image: Int,
    description: String? = null,
    tint: Color? = null
) {
    CoilImage(
        imageModel = { artworkUrl(image) },
        previewPlaceholder = placeholderPokemonImage(image),
        loading = {
            CircularProgressIndicator()
        },
        success = { _, painter ->
            Image(
                painter = painter,
                contentDescription = description,
                modifier = Modifier.matchParentSize(),
                colorFilter = tint?.let { ColorFilter.tint(it) }
            )
        },
        modifier = modifier
    )
}