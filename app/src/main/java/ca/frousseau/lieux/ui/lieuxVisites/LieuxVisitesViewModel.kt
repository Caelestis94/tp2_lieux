package ca.frousseau.lieux.ui.lieuxVisites

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ca.frousseau.lieux.data.LieuDatabase
import ca.frousseau.lieux.data.LieuxVisitesDao
import ca.frousseau.lieux.model.Lieu
import ca.frousseau.lieux.model.LieuVisite

/**
 * ViewModel de la liste des lieux visités
 */
class LieuxVisitesViewModel(application: Application) : AndroidViewModel(application) {

    private val lieuxVisitesDao: LieuxVisitesDao =
        LieuDatabase.getInstace(application).lieuxVisiteDao() // DAO des lieux visités

    /**
     * Insertion d'un lieu visité
     * @param lieuVisite Le lieu visité à insérer
     */
    fun insertLieuVisite(lieuVisite: LieuVisite) {
        lieuxVisitesDao.insertLieuVisite(lieuVisite)
    }

    /**
     * Insertion d'un lieu visité et retourne son id
     * @param lieuVisite Le lieu visité à insérer
     * @return L'id du lieu visité inséré
     */
    fun insertLieuVisiteReturnId(lieuVisite: LieuVisite): Long {
        return lieuxVisitesDao.insertLieuVisiteReturnId(lieuVisite)
    }

    /**
     * Insertion d'une liste de lieux visités
     * @param lieuxVisites La liste de lieux visités à insérer
     * @return La liste des ids des lieux visités insérés
     */
    fun insertLieuxVisites(lieuxVisites: List<LieuVisite>): List<Long> {
        return lieuxVisitesDao.insertLieuxVisites(lieuxVisites)
    }

    /**
     * Vérifie si un lieu est visité
     * @param lieuId L'id du lieu à vérifier
     * @return True si le lieu est visité, false sinon
     */
    fun isLieuVisite(lieuId: Int): Boolean {
        return lieuxVisitesDao.isLieuVisite(lieuId)
    }

    /**
     * Modifier un lieu visité
     * @param lieuVisite Le lieu visité à modifier
     */
    fun updateLieuVisite(lieuVisite: LieuVisite) {
        lieuxVisitesDao.updateLieuVisite(lieuVisite)
    }

    /**
     * Supprimer un lieu visité par le id du lieu
     * @param lieuId L'id du lieu à supprimer
     */
    fun deleteLieuVisite(lieuId: Int) {
        lieuxVisitesDao.deleteLieuVisiteByLieuId(lieuId)
    }

    /**
     * Retourne tous les lieux visités
     * @return La liste des lieux visités sous forme de LiveData
     */
    fun getAllLieuxVisites(): LiveData<List<Lieu>> {
        return lieuxVisitesDao.getAllLieuxVisites()
    }


}