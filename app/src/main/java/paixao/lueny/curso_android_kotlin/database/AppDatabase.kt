package paixao.lueny.curso_android_kotlin.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import paixao.lueny.curso_android_kotlin.model.Product
import paixao.lueny.curso_android_kotlin.database.converter.Converters
import paixao.lueny.curso_android_kotlin.database.dao.ProductDao
import paixao.lueny.curso_android_kotlin.database.dao.UserDao
import paixao.lueny.curso_android_kotlin.model.User

@Database(
    entities = [Product::class, User::class],
    version = 2,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var db: AppDatabase? = null
        fun instance(context: Context): AppDatabase {
            return db ?: Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "app.db"
            ).addMigrations(MIGRATION_1_2)
                .build().also { db = it }
        }
    }

}