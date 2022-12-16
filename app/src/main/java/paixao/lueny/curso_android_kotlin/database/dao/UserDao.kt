package paixao.lueny.curso_android_kotlin.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import paixao.lueny.curso_android_kotlin.model.Product

import paixao.lueny.curso_android_kotlin.model.User

@Dao
interface UserDao {
    @Insert
    suspend fun save(user: User)

    @Query("""SELECT * FROM User WHERE id = :userId AND password = :password""")
    suspend fun authenticate(
        userId: String,
        password: String
    ): User?

    @Query("SELECT * FROM User WHERE id = :userId")
    fun searchById(userId: String): Flow<User>
}