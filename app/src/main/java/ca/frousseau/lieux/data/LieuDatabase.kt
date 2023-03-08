package ca.frousseau.lieux.data

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import ca.frousseau.lieux.model.Lieu
import ca.frousseau.lieux.model.LieuVisite
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Lieu::class,LieuVisite::class], version = 1)
abstract class LieuDatabase : RoomDatabase() {
    abstract fun lieuDao(): LieuDao
    abstract fun lieuxVisiteDao(): LieuxVisitesDao


    companion object{
        @Volatile
        private var INSTANCE: LieuDatabase? = null

        fun getInstace(context: Context): LieuDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context,
                    LieuDatabase::class.java,
                    "lieu_database"
                ).addCallback(object : Callback(){
                    override fun onCreate(db: SupportSQLiteDatabase){
                        super.onCreate(db)

                        Log.d("LieuDatabase", "onCreate: BD crée")
                    }
                }).build()

                INSTANCE = instance
                instance
            }
        }
    }

}
