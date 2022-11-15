package paixao.lueny.curso_android_kotlin.database.converter

import androidx.room.TypeConverter
import java.math.BigDecimal

class Converters {

    @TypeConverter
    fun fromDouble(value: Double?): BigDecimal{
         return value.let { BigDecimal(value.toString()) } ?: BigDecimal.ZERO
    }

    @TypeConverter
    fun bigDecimaltoDouble( value:BigDecimal?) : Double?{
        return value?.let { value.toDouble() }
    }
}
