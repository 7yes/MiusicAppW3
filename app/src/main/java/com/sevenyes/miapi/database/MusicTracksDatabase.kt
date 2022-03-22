package com.sevenyes.miapi.database

import android.content.Context
import androidx.room.*
import com.sevenyes.miapi.model.Rock
import io.reactivex.Completable
import io.reactivex.Single

@Database(
    entities = [Rock::class],
    version = 1
)
abstract class MusicTracksDatabase: RoomDatabase() {

    abstract fun rockDao(): RockDao

    companion object {

        private var instance: MusicTracksDatabase? = null

        fun getInstance(context: Context): MusicTracksDatabase? {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context,
                    MusicTracksDatabase::class.java,
                    "rock_tracks_db")
                    .build()
            }

            return instance
        }
    }
}

@Dao
interface RockDao {

    @Query("SELECT * FROM rock")
    fun getAllRock(): Single<List<Rock>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun  writeAll(rockTracks: List<Rock>): Completable
}