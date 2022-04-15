package den.ter.rickandmorty.screens.detail

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import den.ter.rickandmorty.MainActivity
import den.ter.rickandmorty.R
import den.ter.rickandmorty.data.utils.ConstObjects
import den.ter.rickandmorty.data.utils.ConstObjects.APP
import den.ter.rickandmorty.data.utils.ConstObjects.ID_KEY
import den.ter.rickandmorty.databinding.FragmentDetailBinding
import kotlinx.android.synthetic.main.character_model.view.*

class DetailFragment : Fragment() {

    lateinit var binding: FragmentDetailBinding
    private var curId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentDetailBinding.inflate(layoutInflater,container,false)
        curId = arguments?.getInt(ID_KEY)!!
        binding.btnBack.setOnClickListener {
            APP.navController.navigate(R.id.action_detailFragment_to_startFragment)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        val viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        if(isOnline(requireContext())) {
            viewModel.getSingleCharacter(curId.toString())
            viewModel.singleCharacterResp.observe(viewLifecycleOwner) { character ->
                binding.apply {
                    name.text = character.name
                    species.text = character.species
                    gender.text = character.gender
                    status.text = character.status
                    lastLocation.text = character.location.name
                    episodes.text = character.episode.size.toString()
                    Glide.with(ConstObjects.APP)
                        .load(character.image)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .circleCrop()
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .fallback(R.drawable.ic_launcher_foreground)
                        .into(iv)
                }
            }
        }else{
            binding.noConnection.visibility = View.VISIBLE
        }
    }

    private fun isOnline(ctx: Context): Boolean{
        val cm: ConnectivityManager = ctx.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = cm.activeNetwork ?: return false
        val activeNetwork = cm.getNetworkCapabilities(network) ?: return false
        return when{
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    }

}