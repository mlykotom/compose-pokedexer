package des.c5inco.pokedexer.data.pokemon

import des.c5inco.pokedexer.R
import des.c5inco.pokedexer.model.Pokemon

val SamplePokemonData = listOf(
    Pokemon(
        id = 1,
        name = "Bulbasaur",
        description = "Bulbasaur can be seen napping in bright sunlight. There is a seed on its back. By soaking up the sun's rays, the seed grows progressively larger.",
        typeOfPokemon = listOf("Grass", "Poison"),
        category = "Seed",
        image = R.drawable.poke001,
        hp = 45,
        attack = 49,
        defense = 49,
        specialAttack = 65,
        specialDefense = 65,
        speed = 45,
    ),
    Pokemon(
        id = 2,
        name = "Ivysaur",
        description = "There is a bud on this Pokémon's back. To support its weight, Ivysaur's legs and trunk grow thick and strong. If it starts spending more time lying in the sunlight, it's a sign that the bud will bloom into a large flower soon.",
        typeOfPokemon = listOf("Grass", "Poison"),
        category = "Seed",
        image = R.drawable.poke002,
        hp = 60,
        attack = 62,
        defense = 63,
        specialAttack = 80,
        specialDefense = 80,
        speed = 60,
    ),
    Pokemon(
        id = 3,
        name = "Venusaur",
        description = "There is a large flower on Venusaur's back. The flower is said to take on vivid colors if it gets plenty of nutrition and sunlight. The flower's aroma soothes the emotions of people.",
        typeOfPokemon = listOf("Grass", "Poison"),
        category = "Seed",
        image = R.drawable.poke003,
        hp = 80,
        attack = 100,
        defense = 123,
        specialAttack = 122,
        specialDefense = 120,
        speed = 80,
    ),
    Pokemon(
        id = 4,
        name = "Charmander",
        description = "The flame that burns at the tip of its tail is an indication of its emotions. The flame wavers when Charmander is enjoying itself. If the Pokémon becomes enraged, the flame burns fiercely.",
        typeOfPokemon = listOf("Fire"),
        category = "Lizard",
        image = R.drawable.poke004,
        hp = 20,
        attack = 30,
        defense = 20,
        specialAttack = 30,
        specialDefense = 20,
        speed = 40,
    ),
    Pokemon(
        id = 5,
        name = "Charmeleon",
        description = "Charmeleon mercilessly destroys its foes using its sharp claws. If it encounters a strong foe, it turns aggressive. In this excited state, the flame at the tip of its tail flares with a bluish white color.",
        typeOfPokemon = listOf("Fire"),
        category = "Flame",
        image = R.drawable.poke005,
        hp = 30,
        attack = 30,
        defense = 30,
        specialAttack = 40,
        specialDefense = 30,
        speed = 40,
    ),
    Pokemon(
        id = 6,
        name = "Charizard",
        description = "Charizard flies around the sky in search of powerful opponents. It breathes fire of such great heat that it melts anything. However, it never turns its fiery breath on any opponent weaker than itself.",
        typeOfPokemon = listOf("Fire"),
        category = "Flame",
        image = R.drawable.poke006,
        hp = 30,
        attack = 40,
        defense = 30,
        specialAttack = 50,
        specialDefense = 40,
        speed = 50,
    ),
    Pokemon(
        id = 7,
        name = "Squirtle",
        description = "Squirtle's shell is not merely used for protection. The shell's rounded shape and the grooves on its surface help minimize resistance in water, enabling this Pokémon to swim at high speeds.",
        typeOfPokemon = listOf("Water"),
        category = "Tiny Turtle",
        image = R.drawable.poke007,
        hp = 20,
        attack = 30,
        defense = 30,
        specialAttack = 20,
        specialDefense = 30,
        speed = 20,
    ),
    Pokemon(
        id = 8,
        name = "Wartortle",
        description = "Its tail is large and covered with a rich, thick fur. The tail becomes increasingly deeper in color as Wartortle ages. The scratches on its shell are evidence of this Pokémon's toughness as a battler.",
        typeOfPokemon = listOf("Water"),
        category = "Turtle",
        image = R.drawable.poke008,
        hp = 30,
        attack = 30,
        defense = 40,
        specialAttack = 30,
        specialDefense = 30,
        speed = 30,
    ),
    Pokemon(
        id = 9,
        name = "Blastoise",
        description = "Blastoise has water spouts that protrude from its shell. The water spouts are very accurate. They can shoot bullets of water with enough accuracy to strike empty cans from a distance of over 160 feet.",
        typeOfPokemon = listOf("Water"),
        category = "Shellfish",
        image = R.drawable.poke009,
        hp = 30,
        attack = 40,
        defense = 40,
        specialAttack = 40,
        specialDefense = 40,
        speed = 40,
    ),
)