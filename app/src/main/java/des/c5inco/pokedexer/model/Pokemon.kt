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
    @ColumnInfo(name = "types") val typeOfPokemon: List<String>,
    val category: String,
    val image: Int,
    val hp: Int,
    val attack: Int,
    val defense: Int,
    val specialAttack: Int,
    val specialDefense: Int,
    val speed: Int,
)

fun Pokemon.color(): Color {
    val type = typeOfPokemon.elementAtOrNull(0)

    return when (type?.lowercase()) {
        "grass", "bug" -> PokemonColors.LightTeal
        "fire" -> PokemonColors.LightRed
        "water", "fighting", "normal" -> PokemonColors.LightBlue
        "electric", "psychic" -> PokemonColors.LightYellow
        "poison", "ghost" -> PokemonColors.LightPurple
        "ground", "rock" -> PokemonColors.LightBrown
        "dark" -> PokemonColors.Black
        else -> return PokemonColors.LightBlue
    }
}