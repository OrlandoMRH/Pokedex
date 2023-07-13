package com.example.pokedex

import ViewModel.PokemonAdapter
import ViewModel.PokemonService
import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokedex.databinding.ActivityMainBinding
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Random

class MainActivity : AppCompatActivity() {


    private lateinit var ramdomButton: Button               /*  Representa al botón POKEMON_ALEATORIO que muestra los pokemon de forma random   */
    private lateinit var nombPokeText: TextView             /*  Etiqueta que muestra el nombre del Pokemon                                      */
    private lateinit var noPokedex   : TextView             /*  Etiqueta que muestra el número de la Pokedex del Pokemon                        */
    private lateinit var tipoPoke    : TextView             /*  Etiqueta que muestra el(los) tipos del Pokemon                                  */
    private lateinit var binding     : ActivityMainBinding  /*  Contiene todas las vinculaciones, desde las propiedades de diseño               */
    private lateinit var pokeAdapter : PokemonAdapter       /*  El adaptador para convertir las cadenas en imagenes                             */
    private val pokeImage = mutableListOf<String>()         /*  Una lista que va a contener las imagenes en cadenas                             */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        /*  CONSULTA DE POKEMON RANDOM  */
        initRecyclerView()
        ramdomButton = findViewById(R.id.ramdomButton)  /*  Busca dentro del activity_main un elemento con id ramdomButton */
        ramdomButton.setOnClickListener{         /*  Detecta cuando de da click al botón POKEMON_ALEATORIO          */

            /*  CONSULTA DE POKEMON RANDOM  */
            randomPokemonfromAPI()                      /* Llama el método que retorna un pokemon de forma aleatoria */
        }
    }

    /* REALIZA LA CONEXION Y LA PETICION A LA API POR MEDIO DE LA URL ESPECIFICADA  RETORNANDO UN JASON QUE CONVIERTE UN DATA CLASS*/
    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/pokemon/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /*  RETORNA  Y AGREGA LAS FOTOS DE UN POKEMON DE LA API POR MEDIO DE UN ID RANDOM QUE SE GENERA AL CONSULTAR   */
    private fun randomPokemonfromAPI(){
        CoroutineScope(Dispatchers.IO).launch {

            /*  Inicializa los TextView y el Button */
            nombPokeText = findViewById(R.id.nombrePoke) /* Busca en el activity_main.xml un elemento con id nombrePoke para asignarlo al TextView nombrePoke   */
            noPokedex    = findViewById(R.id.noPokedex)  /* Busca en el activity_main.xml un elemento con id noPokedex  para asignarlo al TextView noPokedex    */
            tipoPoke     = findViewById(R.id.tipoPoke)   /* Busca en el activity_main.xml un elemento con id tipoPoke   para asignarlo al TextView tipoPoke     */

            val randomId = Random().nextInt(1010) + 1 /*    Genera un numero aleatorio que representa el numero de registro que qse consultará a la API     */

            val call = getRetrofit().create(PokemonService::class.java).getPokemonFromAPI("$randomId") /*   Llama al PokemonService para retornar por medio del metodo getPokemonFromAPI un pokemon y retornarlo en forma del PokemonModel*/
            val puppies = call.body()

            runOnUiThread(){
                if(call.isSuccessful){                                          /* Verifica si se pudo realizar la petición a la API    */
                    if(puppies!=null)                                           /*  Revisa si el resultado de la consulta no es null    */
                    {
                        if(puppies.fpokemon!=null && puppies.nombrePok!=null)   /*  Revisa  si la lista de imagenes de sprites y el nombre del pokemon de la consulta no sean null  */
                        {
                            limpiarCamposAndLista()                                             /*  Llama un método que limpia todos los TextView y la lista de sprites     */
                            nombPokeText.text = ("NOMBRE:\n" + puppies.nombrePok).uppercase()   /*  Agrega el nombre del pokemon al TextView nombPokeText                   */

                            if(puppies.noPodedex!=null)         {  noPokedex.text    = ("POKEDEX:\n" + puppies.noPodedex).uppercase() }
                            /*  Valida si el campo correspondiente al Sprite del Pokemon no está vacío  */
                            if(puppies.fpokemon.fmacho!=null)   {  pokeImage.add(puppies.fpokemon.fmacho)  } /* Agrega a la lista de Sprites el Sprite de pokemon machio */
                            /*  Valida si el campo correspondiente al Sprite del Pokemon no está vacío  */
                            if(puppies.fpokemon.fhembra!=null)  {  pokeImage.add(puppies.fpokemon.fhembra) } /* Agrega a la lista de Sprites el Sprite de pokemon machio */

                            if(!puppies.tiposPoke.isEmpty())
                            {
                                if(puppies.tiposPoke[0].tipoPokemon!=null && puppies.tiposPoke[0].tipoPokemon.nombreTipo!=null )
                                {
                                    if( puppies.tiposPoke.size>1 && puppies.tiposPoke[1].tipoPokemon.nombreTipo!=null )
                                    {
                                        tipoPoke.text = ("TIPO: \n" + puppies.tiposPoke[0].tipoPokemon.nombreTipo + "\n" + puppies.tiposPoke[1].tipoPokemon.nombreTipo).uppercase()
                                    }
                                    else
                                    {
                                        tipoPoke.text = ("TIPO: \n" + puppies.tiposPoke[0].tipoPokemon.nombreTipo).uppercase()
                                    }
                                }
                            }

                            pokeAdapter.notifyDataSetChanged()
                        }
                        else{
                            limpiarCamposAndLista()     /*  Llama un método que limpia todos los TextView y la lista de sprites     */
                        }
                    }
                }else{
                    limpiarCamposAndLista()                             /*  Llama un método que limpia todos los TextView y la lista de sprites     */
                    showMessage("Error, Intentalo más tarde")   /*  En caso de que el registro esté vació retorna un mensaje de error  */
                }
            }
        }
    }

    /*  LIMPIA LOS TEXTVIEW Y LA LISTA DE SPRITES   */
    private fun limpiarCamposAndLista()
    {
        pokeImage.clear()                   /*  Limpia la lista con imagenes del pokemon                */
        nombPokeText.text = "NOMBRE: "      /*  Limpia y cambia la etiqueta del TextView  nombPokeText  */
        noPokedex.text = "POKEDEX: "        /*  Limpia y cambia la etiqueta del TextView  noPokedex     */
        tipoPoke.text = "TIPO:"             /*  Limpia y cambia la etiqueta del TextView  tipoPoke      */
    }

    /*  ESTE METODO PERMITE ENVIAR LA LISTA DE SPRITES AL ADAPTADOR QUE SE ENCARGARÁ DE CONVERTIR LAS CADENAS EN IMAGENES
        Y LAS VA A IR AGREGANDO EN LA LISTA DINAMICA DEL RECYCLERVIEW                                                       */
    private fun initRecyclerView() {
        pokeAdapter = PokemonAdapter(pokeImage)
        binding.rvPokes.layoutManager = LinearLayoutManager(this)
        binding.rvPokes.adapter = pokeAdapter
    }

    /*  RETORNA UN MENSAJE PERSONALIZADO EN UN TOAST    */
    private fun showMessage( mensaje:String){
        Toast.makeText(this,mensaje,Toast.LENGTH_SHORT).show()
    }
}