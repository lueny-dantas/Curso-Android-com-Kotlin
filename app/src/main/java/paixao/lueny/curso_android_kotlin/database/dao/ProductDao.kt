package paixao.lueny.curso_android_kotlin.database.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import paixao.lueny.curso_android_kotlin.model.Product


@Dao
interface ProductDao {

    @Query("SELECT * FROM Product")
    fun searchAll(): Flow<List<Product>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(vararg product: Product)

    @Delete
    suspend fun remove(product: Product)

    @Query("SELECT * FROM Product WHERE id = :productId")
    suspend fun searchById(productId: Long): Product?

    @Query("SELECT * FROM Product ORDER BY name ASC")
    fun searchAllSortbyNameAsc():Flow<List<Product>>

    @Query("SELECT * FROM Product ORDER BY name DESC")
    fun searchAllSortByNameDesc(): Flow<List<Product>>

    @Query("SELECT * FROM Product ORDER BY description ASC")
    fun searchAllSortByDescriptionAsc():Flow<List<Product>>

    @Query("SELECT * FROM Product ORDER BY description DESC")
    fun searchAllSortByDescriptionDesc(): Flow<List<Product>>

    @Query("SELECT * FROM Product ORDER BY value ASC")
    fun searchAllSortByValueAsc():Flow<List<Product>>

    @Query("SELECT * FROM Product ORDER BY value DESC")
    fun searchAllSortByValueDesc():Flow<List<Product>>
}