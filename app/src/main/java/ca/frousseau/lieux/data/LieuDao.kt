package ca.frousseau.lieux.data

import androidx.lifecycle.LiveData
import androidx.room.*
import ca.frousseau.lieux.model.Lieu

/**
 * Interface de communication avec la base de données pour les lieux
 */
@Dao
interface LieuDao {

    /**
     * Insert un lieu dans la base de données
     * @param lieu, le lieu à insérer
     */
    @Insert
    fun insertLieu(lieu: Lieu)

    /**
     * Insert un lieu dans la base de données et retourne son id
     * @param lieu, le lieu à insérer
     * @return l'id du lieu inséré
     */
    @Insert
    fun insertLieuReturnId(lieu: Lieu): Long

    /**
     * Insert une liste de lieux dans la base de données
     * @param lieux, la liste de lieux à insérer
     * @return la liste des ids des lieux insérés
     */
    @Insert
    fun insertLieux(lieux: List<Lieu>): List<Long>

    /**
     * Met à jour un lieu dans la base de données
     * @param lieu, le lieu à mettre à jour
     */
    @Update
    fun updateLieu(lieu: Lieu)

    /**
     * Supprime un lieu de la base de données
     * @param lieu, le lieu à supprimer
     */
    @Delete
    fun deleteLieu(lieu: Lieu)

    /**
     * Retourne tous les lieux de la base de données
     */
    @Query("SELECT * FROM lieux")
    fun getAllLieux(): LiveData<List<Lieu>>

    /**
     * Retourne un lieu de la base de données
     * @param id, l'id du lieu à retourner
     * @return le lieu
     */
    @Query("SELECT * FROM lieux WHERE id = :id")
    fun getLieuById(id: Int): LiveData<Lieu>

    /**
     * Supprime tous les lieux de la base de données
     */
    @Query("DELETE FROM lieux")
    fun deleteAllLieux()


}
