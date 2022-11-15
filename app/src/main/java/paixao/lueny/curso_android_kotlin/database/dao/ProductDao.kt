package paixao.lueny.curso_android_kotlin.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import paixao.lueny.curso_android_kotlin.Produto.Product


@Dao
interface ProductDao {

    @Query("SELECT * FROM Product")
    fun searchAll(): List<Product>

    @Insert
    fun save(vararg product: Product)
}