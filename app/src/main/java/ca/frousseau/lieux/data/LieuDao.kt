package ca.frousseau.lieux.data

import androidx.lifecycle.LiveData
import androidx.room.*
import ca.frousseau.lieux.model.Lieu

@Dao
interface LieuDao {

    @Insert
    fun insertLieu(lieu: Lieu)

    @Insert
    fun insertLieuReturnId(lieu: Lieu): Long

    @Insert
    fun insertLieux(lieux: List<Lieu>): List<Long>

    @Update
    fun updateLieu(lieu: Lieu)

    @Delete
    fun deleteLieu(lieu: Lieu)

    @Query("SELECT * FROM lieux")
    fun getAllLieux(): LiveData<List<Lieu>>

    @Query("SELECT * FROM lieux WHERE id = :id")
    fun getLieuById(id: Int): LiveData<Lieu>

    @Query("DELETE FROM lieux")
    fun deleteAllLieux()


}
