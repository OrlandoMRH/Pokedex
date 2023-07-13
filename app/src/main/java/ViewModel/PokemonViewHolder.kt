package ViewModel

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.databinding.ItemPokemonBinding
import com.squareup.picasso.Picasso

class PokemonViewHolder (view: View): RecyclerView.ViewHolder(view) {

    private val binding = ItemPokemonBinding.bind(view)

    fun bind(image:String){
        Picasso.get().load(image).into(binding.ivPokemon)
    }
}