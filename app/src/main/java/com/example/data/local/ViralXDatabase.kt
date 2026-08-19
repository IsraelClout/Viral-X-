package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.local.dao.ViralXDao
import com.example.data.local.entities.AdminAuditLogEntity
import com.example.data.local.entities.CommentEntity
import com.example.data.local.entities.FollowEntity
import com.example.data.local.entities.LikeEntity
import com.example.data.local.entities.NotificationEntity
import com.example.data.local.entities.PostEntity
import com.example.data.local.entities.RatingEntity
import com.example.data.local.entities.ReportEntity
import com.example.data.local.entities.SavedPostEntity
import com.example.data.local.entities.SystemSettingsEntity
import com.example.data.local.entities.TransactionEntity
import com.example.data.local.entities.UserEntity
import com.example.data.local.entities.ViewRecordEntity
import com.example.data.local.entities.WalletEntity
import com.example.data.local.entities.WithdrawalEntity

@Database(
    entities = [
        UserEntity::class,
        PostEntity::class,
        LikeEntity::class,
        RatingEntity::class,
        ViewRecordEntity::class,
        CommentEntity::class,
        FollowEntity::class,
        SavedPostEntity::class,
        WalletEntity::class,
        TransactionEntity::class,
        WithdrawalEntity::class,
        NotificationEntity::class,
        ReportEntity::class,
        SystemSettingsEntity::class,
        AdminAuditLogEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class ViralXDatabase : RoomDatabase() {
    abstract fun viralXDao(): ViralXDao

    companion object {
        @Volatile
        private var INSTANCE: ViralXDatabase? = null

        fun getInstance(context: Context): ViralXDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ViralXDatabase::class.java,
                    "viralx_db"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
