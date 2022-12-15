package paixao.lueny.curso_android_kotlin.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

import paixao.lueny.curso_android_kotlin.model.User

@Dao
interface UserDao {
    @Insert
    suspend fun save(user: User)

    @Query("""SELECT * FROM User WHERE id = :userId AND password = :password""")
    suspend fun authenticate(
        userId: String,
        password: String
    ): List<User>

    @Query("SELECT * FROM User WHERE id = :userId")
    suspend fun searchById(userId: String): List<User>


}