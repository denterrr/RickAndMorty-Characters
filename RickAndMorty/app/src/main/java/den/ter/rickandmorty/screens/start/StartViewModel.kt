package den.ter.rickandmorty.screens.start

import android.util.Log
import androidx.lifecycle.*
import den.ter.rickandmorty.data.repository.MyRepository
import den.ter.rickandmorty.model.characters.CharactersModel
import den.ter.rickandmorty.model.singlecharacter.SingleCharacterModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class StartViewModel: ViewModel() {
    private val _respCharacters = MutableLiveData<CharactersModel>()

    val charactersResp: LiveData<CharactersModel>
        get() = _respCharacters

    private val repository = MyRepository()


    fun getCharacters(page: String)=viewModelScope.launch {
        repository.getCharacters(page).let { response ->
            if(response.isSuccessful){
                _respCharacters.postValue(response.body())
            }else{
                Log.d("mytag", "Error response: {${response.message()}}")
            }
        }
    }


}