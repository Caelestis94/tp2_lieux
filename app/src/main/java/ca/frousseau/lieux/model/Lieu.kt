package ca.frousseau.lieux.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Data class représentant un lieu dans la base de données
 */
@Entity(tableName = "lieux")
data class Lieu(
    /**
     * Id du lieu
     */
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    /**
     * Nom du lieu
     */
    @ColumnInfo(name = "nom")
    var nom: String,
    /**
     * Description du lieu
     */
    @ColumnInfo(name = "description")
    var description: String,

    /**
     * Adresse du lieu
     */
    @ColumnInfo(name = "adresse")
    var adresse: String,
    /**
     * Le URI de l'image du lieu (String)
     */
    @ColumnInfo(name = "image")
    var image: String
)