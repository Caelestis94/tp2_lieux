package ca.frousseau.lieux.ui.lieuxVisites

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ca.frousseau.lieux.data.LieuDatabase
import ca.frousseau.lieux.data.LieuxVisitesDao
import ca.frousseau.lieux.model.Lieu
import ca.frousseau.lieux.model.LieuVisite

class LieuxVisitesViewModel(application: Application) : AndroidViewModel(application) {

    private val lieuxVisitesDao : LieuxVisitesDao = LieuDatabase.getInstace(application).lieuxVisiteDao()
    private val _lieuxVisites = MutableLiveData<List<Lieu>>()
    val lieuxVisites: LiveData<List<Lieu>> = _lieuxVisites

    init {
        _lieuxVisites.value = lieuxVisitesDao.getAllLieuxVisites().value
    }

    fun insertLieuVisite(lieuVisite: LieuVisite){
        lieuxVisitesDao.insertLieuVisite(lieuVisite)
    }

    fun insertLieuVisiteReturnId(lieuVisite: LieuVisite): Long{
        return lieuxVisitesDao.insertLieuVisiteReturnId(lieuVisite)
    }

    fun insertLieuxVisites(lieuxVisites: List<LieuVisite>): List<Long>{
        return lieuxVisitesDao.insertLieuxVisites(lieuxVisites)
    }

    fun updateLieuVisite(lieuVisite: LieuVisite){
        lieuxVisitesDao.updateLieuVisite(lieuVisite)
    }

    fun deleteLieuVisite(lieuVisite: LieuVisite){
        lieuxVisitesDao.deleteLieuVisite(lieuVisite)
    }

    fun getAllLieuxVisites(): LiveData<List<Lieu>>{
        return lieuxVisitesDao.getAllLieuxVisites()
    }




}