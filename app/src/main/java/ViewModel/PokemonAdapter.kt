package ViewModel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.R

class PokemonAdapter (private val images: List<String>) : RecyclerView.Adapter<PokemonViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PokemonViewHolder(layoutInflater.inflate(R.layout.item_pokemon, parent, false))
    }
    override fun getItemCount(): Int = images.size

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val item = images[position]
        holder.bind(item)
    }
}