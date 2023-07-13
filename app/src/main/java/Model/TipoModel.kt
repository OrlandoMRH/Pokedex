package Model

import com.google.gson.annotations.SerializedName

data class TipoModel (
    @SerializedName("name") var nombreTipo : String, /*  Serealiza el campo name del json para vincularlo con la propiedad fmacho  de esta nombreTipo */
)