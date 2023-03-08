package ca.frousseau.lieux.ui.lieux

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ca.frousseau.lieux.data.LieuDao
import ca.frousseau.lieux.data.LieuDatabase
import ca.frousseau.lieux.model.Lieu

/**
 * ViewModel pour la liste des lieux
 */
class LieuxViewModel(application: Application) : AndroidViewModel(application) {

    private val lieuDao: LieuDao = LieuDatabase.getInstace(application).lieuDao()

    /**
     * Insertion d'un lieu
     * @param lieu Le lieu à insérer
     */
    fun insertLieu(lieu: Lieu) {
        lieuDao.insertLieu(lieu)
    }

    /**
     * Insertion d'un lieu et retourne son id
     * @param lieu Le lieu à insérer
     * @return L'id du lieu inséré
     */
    fun insertLieuReturnId(lieu: Lieu): Long {
        return lieuDao.insertLieuReturnId(lieu)
    }

    /**
     * Retourne tous les lieux de la base de données
     * @return La liste des lieux
     */
    fun getAllLieux(): LiveData<List<Lieu>> {
        return lieuDao.getAllLieux()
    }

    /**
     * Retourne un lieu de la base de données
     * @param id L'id du lieu à retourner
     * @return Le lieu
     */
    fun getLieuById(id: Int): LiveData<Lieu> {
        return lieuDao.getLieuById(id)
    }

    /**
     * Supprime tous les lieux de la base de données
     */
    fun deleteAllLieux() {
        lieuDao.deleteAllLieux()
    }

    /**
     * Supprime un lieu de la base de données
     * @param lieu Le lieu à supprimer
     */
    fun deleteLieu(lieu: Lieu) {
        lieuDao.deleteLieu(lieu)
    }

    /**
     * Met à jour un lieu dans la base de données
     * @param lieu Le lieu à mettre à jour
     */
    fun updateLieu(lieu: Lieu) {
        lieuDao.updateLieu(lieu)
    }

    /**
     * Insertion d'une liste de lieux
     * @param lieux La liste de lieux à insérer
     * @return La liste des ids des lieux insérés
     */
    fun insertLieux(lieux: List<Lieu>): List<Long> {
        return lieuDao.insertLieux(lieux)
    }


}