package den.ter.rickandmorty.data.repository

import den.ter.rickandmorty.data.api.RetrofitInstance
import den.ter.rickandmorty.model.characters.CharactersModel
import den.ter.rickandmorty.model.singlecharacter.SingleCharacterModel
import retrofit2.Response

class MyRepository {

    suspend fun getCharacters(page: String): Response<CharactersModel>{
        return RetrofitInstance.apiService.getCharacters(page)
    }

    suspend fun getSingleCharacter(id: String): Response<SingleCharacterModel>{
        return RetrofitInstance.apiService.getSingleCharacter(id)
    }
}