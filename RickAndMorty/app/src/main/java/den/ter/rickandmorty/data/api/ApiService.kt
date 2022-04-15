package den.ter.rickandmorty.data.api

import den.ter.rickandmorty.model.characters.CharactersModel
import den.ter.rickandmorty.model.singlecharacter.SingleCharacterModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("api/character?")
    suspend fun getCharacters(@Query("page") page: String):Response<CharactersModel>

    @GET("api/character/{id}")
    suspend fun getSingleCharacter(@Path("id") id : String):Response<SingleCharacterModel>
}