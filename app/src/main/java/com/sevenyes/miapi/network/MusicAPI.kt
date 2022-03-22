package com.sevenyes.miapi.network

import com.sevenyes.miapi.model.Classic
import com.sevenyes.miapi.model.ClassicList
import com.sevenyes.miapi.model.PopList
import com.sevenyes.miapi.model.RockList
import io.reactivex.Single
import retrofit2.http.GET

interface MusicAPI {

    @GET(ROCK_PATH)
    fun getRock () : Single<RockList>

    @GET(POP_PATH)
    fun getPop () : Single<PopList>

    @GET(CLASSIC_PATH)
    fun getClassic() : Single<ClassicList>


    companion object {
        const val BASE_PATH = "https://itunes.apple.com/search/"
       private const val POP_PATH = "?term=pop&amp;media=music&amp;entity=song&amp;limit=50"
       private const val CLASSIC_PATH = "?term=classick&amp;media=music&amp;entity=song&amp;limit=50"
        private const val ROCK_PATH = "?term=rock&amp;media=music&amp;entity=song&amp;limit=50"

    }
}