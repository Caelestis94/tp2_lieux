package ca.frousseau.lieux.model

import android.icu.lang.UCharacter.GraphemeClusterBreak.L
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import ca.frousseau.lieux.model.Lieu

/**
 * Data class représentant un lieu visité dans la base de données
 */
@Entity(
    tableName = "lieux_visites",
    foreignKeys = [ForeignKey(
        entity = Lieu::class,
        parentColumns = ["id"],
        childColumns = ["lieu_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class LieuVisite(
    /**
     * Id du lieu visité
     */
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    /**
     * Id du lieu
     */
    @ColumnInfo(name = "lieu_id")
    var lieuId: Int,

    )