package ca.frousseau.lieux.data

import androidx.lifecycle.LiveData
import androidx.room.*
import ca.frousseau.lieux.model.Lieu
import ca.frousseau.lieux.model.LieuVisite

@Dao
interface LieuxVisitesDao {

    @Insert
    fun insertLieuVisite(lieuVisite: LieuVisite)

    @Insert
    fun insertLieuVisiteReturnId(lieuVisite: LieuVisite): Long

    @Insert
    fun insertLieuxVisites(lieuxVisites: List<LieuVisite>): List<Long>

    @Update
    fun updateLieuVisite(lieuVisite: LieuVisite)

    @Delete
    fun deleteLieuVisite(lieuVisite: LieuVisite)

    // query use the foreign key to get the lieu from the lieu table
    @Query("SELECT * FROM lieux_visites INNER JOIN lieux ON lieux_visites.lieu_id = lieux.id")
    fun getAllLieuxVisites(): LiveData<List<Lieu>>

    @Query("SELECT * FROM lieux_visites INNER JOIN lieux ON lieux_visites.lieu_id = lieux.id WHERE lieux_visites.id = :id")
    fun getLieuVisiteById(id: Int): Lieu

    @Query("SELECT * FROM lieux_visites WHERE lieu_id = :id")
    fun getLieuVisiteByLieuId(id: Int): LieuVisite?

    @Query("DELETE FROM lieux_visites")
    fun deleteAllLieuxVisites()


}