package den.ter.rickandmorty.screens.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import den.ter.rickandmorty.data.repository.MyRepository
import den.ter.rickandmorty.model.singlecharacter.SingleCharacterModel
import kotlinx.coroutines.launch

class DetailViewModel: ViewModel() {

    private val _respSingleCharacter = MutableLiveData<SingleCharacterModel>()

    val singleCharacterResp: LiveData<SingleCharacterModel>
        get() = _respSingleCharacter

    private val repository = MyRepository()

    fun getSingleCharacter(id: String) = viewModelScope.launch {
        repository.getSingleCharacter(id).let { response2 ->
            if(response2.isSuccessful){
                _respSingleCharacter.postValue(response2.body())
            }else{
                Log.d("ErrorResponse", "Error response: {${response2.message()}}")
            }
        }
    }

}