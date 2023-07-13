package Model

import com.google.gson.annotations.SerializedName

data class PokemonModel(
    @SerializedName("id")   var noPodedex   : String,                   /*  Serealiza el campo Id       del json para vincularlo con la propiedad noPokedex de esta clase */
    @SerializedName("name")     var nombrePok   : String,               /*  Serealiza el campo name     del json para vincularlo con la propiedad nombrePok de esta clase */
    @SerializedName("sprites")  var fpokemon    : PokemonSpriteModel,   /*  Serealiza el campo sprites  del json para vincularlo con la propiedad fpokemon  de esta clase */
    @SerializedName("types")    var tiposPoke   : List<TipoPokemonModel>/*  Serealiza el campo types    del json para vincularlo con la propiedad tiposPoke de esta clase */
)