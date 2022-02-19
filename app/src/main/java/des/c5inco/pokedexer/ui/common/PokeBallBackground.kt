package des.c5inco.pokedexer.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import des.c5inco.pokedexer.R
import des.c5inco.pokedexer.ui.theme.Theme.Companion.PokedexerTheme

@Composable
fun PokeBallBackground(
    modifier: Modifier = Modifier
) {
    Box(
        modifier.size(240.dp),
    ) {
        PokeBallLarge(tint = colorResource(id = R.color.grey_100))
    }
}

@Preview
@Composable
fun PokeBallBackgroundPreview() {
    PokedexerTheme {
        Surface(Modifier.fillMaxSize()) {
            PokeBallBackground()
        }
    }
}