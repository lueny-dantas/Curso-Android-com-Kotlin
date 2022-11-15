package paixao.lueny.curso_android_kotlin.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import paixao.lueny.curso_android_kotlin.Produto.Product
import paixao.lueny.curso_android_kotlin.database.converter.Converters
import paixao.lueny.curso_android_kotlin.database.dao.ProductDao

@Database(entities = [Product::class], version = 1, exportSchema = false )
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun productDao(): ProductDao

    companion object {
        fun instance(context: Context): AppDatabase{
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "app.db"
            ).allowMainThreadQueries()
                .build()
        }
    }

}