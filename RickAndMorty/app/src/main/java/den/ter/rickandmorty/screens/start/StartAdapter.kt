package den.ter.rickandmorty.screens.start

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import den.ter.rickandmorty.R
import den.ter.rickandmorty.data.utils.ConstObjects.APP
import den.ter.rickandmorty.model.characters.CharactersModel
import den.ter.rickandmorty.model.characters.Result
import kotlinx.android.synthetic.main.character_model.view.*

class StartAdapter: RecyclerView.Adapter<StartAdapter.StartViewHolder>() {
    var listStart = emptyList<Result>()
    lateinit var ctx: Context
    var isSucces = false
    var isDoed = false

    class StartViewHolder(view:View):RecyclerView.ViewHolder(view) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.character_model,parent,false)
        return StartViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: StartViewHolder, position: Int) {
        holder.itemView.name.text = listStart[position].name
        holder.itemView.gender.text = listStart[position].gender
        holder.itemView.species.text = listStart[position].species
        Glide.with(APP)
            .load(listStart[position].image)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .circleCrop()
            .placeholder(R.drawable.ic_launcher_foreground)
            .fallback(R.drawable.ic_launcher_foreground)
            .into(holder.itemView.iv_character)

    }

    override fun getItemCount(): Int = listStart.size


    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<Result>){
        listStart = list
        notifyDataSetChanged()
    }

    override fun onViewAttachedToWindow(holder: StartViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.itemView.setOnClickListener {
            StartFragment.clickCharacter(listStart[holder.adapterPosition])
        }
    }

    override fun onViewDetachedFromWindow(holder: StartViewHolder) {
       holder.itemView.setOnClickListener(null)

    }
}