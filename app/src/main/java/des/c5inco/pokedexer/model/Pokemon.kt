package des.c5inco.pokedexer.model

import androidx.compose.ui.graphics.Color
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import des.c5inco.pokedexer.ui.theme.PokemonColors

@Entity
data class Pokemon(
    @PrimaryKey val id: Int,
    val name: String,
    val description: String,
    @ColumnInfo(name = "types")
    val typeOfPokemon: List<String> = listOf(),
    val category: String,
    val image: Int,
    val hp: Int,
    val attack: Int,
    val defense: Int,
    val specialAttack: Int,
    val specialDefense: Int,
    val speed: Int,
    @ColumnInfo(name = "evolutions", defaultValue = "")
    val evolutionChain: List<Evolution> = listOf(),
)

fun Pokemon.color(): Color {
    val type = typeOfPokemon.elementAtOrNull(0)
    return type?.let { mapTypeToColor(it) } ?: PokemonColors.LightBlue
}