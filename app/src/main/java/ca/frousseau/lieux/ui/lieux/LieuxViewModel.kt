package ca.frousseau.lieux.ui.lieux

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ca.frousseau.lieux.data.LieuDao
import ca.frousseau.lieux.data.LieuDatabase
import ca.frousseau.lieux.model.Lieu

class LieuxViewModel(application: Application) : AndroidViewModel(application) {

    private val lieuDao : LieuDao = LieuDatabase.getInstace(application).lieuDao()
    private val _lieux = MutableLiveData<List<Lieu>>()
    val lieux: LiveData<List<Lieu>> = _lieux

    init {
        _lieux.value = lieuDao.getAllLieux().value
    }





    fun insertLieu(lieu: Lieu){
        lieuDao.insertLieu(lieu)
    }

    fun insertLieuReturnId(lieu: Lieu): Long{
        return lieuDao.insertLieuReturnId(lieu)
    }

    fun getAllLieux(): LiveData<List<Lieu>>{
        return lieuDao.getAllLieux()
    }

    fun getLieuById(id: Int): LiveData<Lieu>{
        return lieuDao.getLieuById(id)
    }

    fun deleteAllLieux(){
        lieuDao.deleteAllLieux()
    }

    fun deleteLieu(lieu: Lieu){
        lieuDao.deleteLieu(lieu)
    }

    fun updateLieu(lieu: Lieu){
        lieuDao.updateLieu(lieu)
    }

    fun insertLieux(lieux: List<Lieu>): List<Long>{
        return lieuDao.insertLieux(lieux)
    }


}