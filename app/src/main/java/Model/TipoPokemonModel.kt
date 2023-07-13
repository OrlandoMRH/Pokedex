package Model

import com.google.gson.annotations.SerializedName

data class TipoPokemonModel (
    @SerializedName("type") var tipoPokemon   : TipoModel, /*  Serealiza el campo type del json para vincularlo con la propiedad fmacho  de esta tipoPokemon */
)