package paixao.lueny.curso_android_kotlin.database.dao

import androidx.room.*
import paixao.lueny.curso_android_kotlin.Produto.Product


@Dao
interface ProductDao {

    @Query("SELECT * FROM Product")
    fun searchAll(): List<Product>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(vararg product: Product)

    @Delete
    fun remove(product: Product)

    @Query("SELECT * FROM Product WHERE id = :id")
    fun searchById(id: Long): Product?

    @Query("SELECT * FROM Product ORDER BY name ASC")
    fun searchAllSortbyNameAsc():List<Product>

    @Query("SELECT * FROM Product ORDER BY name DESC")
    fun searchAllSortByNameDesc():List<Product>

    @Query("SELECT * FROM Product ORDER BY description ASC")
    fun searchAllSortByDescriptionAsc(): List<Product>

    @Query("SELECT * FROM Product ORDER BY description DESC")
    fun searchAllSortByDescriptionDesc(): List<Product>

    @Query("SELECT * FROM Product ORDER BY value ASC")
    fun searchAllSortByValueAsc(): List<Product>

    @Query("SELECT * FROM Product ORDER BY value DESC")
    fun searchAllSortByValueDesc(): List<Product>
}