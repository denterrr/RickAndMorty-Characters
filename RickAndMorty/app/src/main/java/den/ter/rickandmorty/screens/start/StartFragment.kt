package den.ter.rickandmorty.screens.start

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import den.ter.rickandmorty.R
import den.ter.rickandmorty.data.utils.ConstObjects.APP
import den.ter.rickandmorty.data.utils.ConstObjects.ID_KEY
import den.ter.rickandmorty.databinding.FragmentStartBinding
import den.ter.rickandmorty.model.characters.CharactersModel
import den.ter.rickandmorty.model.characters.Result
import kotlinx.coroutines.*

class StartFragment : Fragment() {

    lateinit var binding: FragmentStartBinding
    lateinit var rv: RecyclerView
    lateinit var adapter: StartAdapter
    private var pageCount = 1
    private var curItems = 0
    private var itogList: ArrayList<Result> = ArrayList()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentStartBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }


    private fun init() {
        rv = binding.rvCharacters
        adapter = StartAdapter()
        rv.adapter = adapter
        rv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        rv.isNestedScrollingEnabled = false
        val tvLoad = binding.load
        if(isOnline(requireContext())){
            showCharacters(tvLoad)
        }else{
            binding.load.visibility = View.GONE
            binding.noConnection.visibility = View.VISIBLE
        }


    }

    private fun showCharacters(tvLoad: TextView){
        val viewModel = ViewModelProvider(this).get(StartViewModel::class.java)
        for (i in 1..43) {
            viewModel.getCharacters(i.toString())
            viewModel.charactersResp.observe(viewLifecycleOwner) { characters ->
                if (characters.results[0] !in itogList) {
                    itogList.addAll(characters.results)
                }
            }
            CoroutineScope(Dispatchers.Main).launch { delay(300L) }
        }
        CoroutineScope(Dispatchers.Main).launch{
            delay(500L)
            tvLoad.visibility = View.INVISIBLE
            adapter.setList(itogList.sortedBy { it.id })
        }
        CoroutineScope(Dispatchers.Main).launch{
            delay(1000L)
            adapter.setList(itogList.sortedBy { it.id })
        }
        CoroutineScope(Dispatchers.Main).launch{
            delay(1500L)
            adapter.setList(itogList.sortedBy { it.id })
        }
        CoroutineScope(Dispatchers.Main).launch{
            delay(2500L)
            adapter.setList(itogList.sortedBy { it.id })
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

    companion object{
        fun clickCharacter(character: Result){
            val bundle = Bundle()
            bundle.putInt(ID_KEY,character.id)
            APP.navController.navigate(R.id.action_startFragment_to_detailFragment,bundle)
        }
    }

}