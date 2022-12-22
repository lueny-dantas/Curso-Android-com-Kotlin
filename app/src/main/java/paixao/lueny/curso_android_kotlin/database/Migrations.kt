package paixao.lueny.curso_android_kotlin.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            """ CREATE TABLE IF NOT EXISTS `User` (
                            `id` TEXT NOT NULL, 
                           `name` TEXT NOT NULL,
                          `password` TEXT NOT NULL, PRIMARY KEY(`id`))""")
    }
}

val MIGRATION_2_3 = object :Migration (2, 3){
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE Product ADD COLUMN 'userId'TEXT")
    }
}