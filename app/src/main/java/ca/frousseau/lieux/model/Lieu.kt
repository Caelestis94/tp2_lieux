package ca.frousseau.lieux.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lieux")
data class Lieu(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "nom")
    var nom: String,

    @ColumnInfo(name = "description")
    var description: String,

    @ColumnInfo(name = "adresse")
    var adresse: String,

    @ColumnInfo(name = "image")
    var image: String
)