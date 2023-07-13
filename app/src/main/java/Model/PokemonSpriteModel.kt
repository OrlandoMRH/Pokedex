package Model

import com.google.gson.annotations.SerializedName

data class PokemonSpriteModel (
    @SerializedName("front_default") var fmacho : String, /*  Serealiza el campo front_default  del json para vincularlo con la propiedad fmacho  de esta clase */
    @SerializedName("front_female")  var fhembra: String  /*  Serealiza el campo front_female   del json para vincularlo con la propiedad fhembra de esta clase */
)