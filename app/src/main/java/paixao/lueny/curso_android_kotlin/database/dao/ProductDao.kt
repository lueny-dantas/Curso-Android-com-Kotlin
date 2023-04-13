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
    fun remove(product: Product)

    @Query("SELECT * FROM Product WHERE userId = :userId")
    fun searchAllProductByUserId(userId: String): Flow<List<Product>>

    @Query("SELECT * FROM Product WHERE id = :productId")
    fun searchById(productId: Long): Flow<Product?>

    @Query("SELECT * FROM Product WHERE userId = :userId ORDER BY name ASC")
    fun searchAllSortByNameAsc(userId: String):Flow<List<Product>>

    @Query("SELECT * FROM Product WHERE userId = :userId ORDER BY name DESC")
    fun searchAllSortByNameDesc(userId: String): Flow<List<Product>>

    @Query("SELECT * FROM Product WHERE userId = :userId ORDER BY description ASC")
    fun searchAllSortByDescriptionAsc(userId: String):Flow<List<Product>>

    @Query("SELECT * FROM Product WHERE userId = :userId ORDER BY description DESC")
    fun searchAllSortByDescriptionDesc(userId: String): Flow<List<Product>>

    @Query("SELECT * FROM Product WHERE userId = :userId ORDER BY value ASC")
    fun searchAllSortByValueAsc(userId: String):Flow<List<Product>>

    @Query("SELECT * FROM Product WHERE userId = :userId ORDER BY value DESC")
    fun searchAllSortByValueDesc(userId: String):Flow<List<Product>>


}