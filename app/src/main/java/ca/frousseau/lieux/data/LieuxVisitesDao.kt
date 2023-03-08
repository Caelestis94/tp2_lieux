package ca.frousseau.lieux.data

import androidx.lifecycle.LiveData
import androidx.room.*
import ca.frousseau.lieux.model.Lieu
import ca.frousseau.lieux.model.LieuVisite

/**
 * Interface de DAO pour les lieux visités
 */
@Dao
interface LieuxVisitesDao {

    /**
     * Insert un lieu visité dans la base de données
     * @param lieuVisite, le lieu visité à insérer
     */
    @Insert
    fun insertLieuVisite(lieuVisite: LieuVisite)

    /**
     * Insert un lieu visité dans la base de données et retourne son id
     * @param lieuVisite, le lieu visité à insérer
     * @return l'id du lieu visité inséré
     */
    @Insert
    fun insertLieuVisiteReturnId(lieuVisite: LieuVisite): Long

    /**
     * Insert une liste de lieux visités dans la base de données
     * @param lieuxVisites, la liste de lieux visités à insérer
     * @return la liste des ids des lieux visités insérés
     */
    @Insert
    fun insertLieuxVisites(lieuxVisites: List<LieuVisite>): List<Long>

    /**
     * Met à jour un lieu visité dans la base de données
     * @param lieuVisite, le lieu visité à mettre à jour
     */
    @Update
    fun updateLieuVisite(lieuVisite: LieuVisite)

    /**
     * Supprime un lieu visité de la base de données
     * @param lieuVisite, le lieu visité à supprimer
     */
    @Delete
    fun deleteLieuVisite(lieuVisite: LieuVisite)

    /**
     * Supprime un lieu visité de la base de données par le id du lieu
     * @param lieuId, l'id du lieu visité à supprimer
     */
    @Query("DELETE FROM lieux_visites WHERE lieu_id = :lieuId")
    fun deleteLieuVisiteByLieuId(lieuId: Int)

    /**
     * Retourne tous les lieux visités de la base de données
     */
    @Query("SELECT * FROM lieux_visites INNER JOIN lieux ON lieux_visites.lieu_id = lieux.id")
    fun getAllLieuxVisites(): LiveData<List<Lieu>>

    /**
     * Retourne un booléen indiquant si le lieu a été visité
     * @param id, l'id du lieu visité à retourner
     * @return vrai si le lieu a été visité, faux sinon
     */
    @Query("SELECT * FROM lieux_visites WHERE lieu_id = :id")
    fun isLieuVisite(id: Int): Boolean

    /**
     * Retourne un lieu visité de la base de données par le id du lieu
     * @param id, l'id du lieu visité à retourner
     * @return le lieu visité
     */
    @Query("SELECT * FROM lieux_visites WHERE lieu_id = :id")
    fun getLieuVisiteByLieuId(id: Int): LieuVisite?

    /**
     * Supprime tous les lieux visités de la base de données
     */
    @Query("DELETE FROM lieux_visites")
    fun deleteAllLieuxVisites()


}