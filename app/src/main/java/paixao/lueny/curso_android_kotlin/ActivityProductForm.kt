package paixao.lueny.curso_android_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import paixao.lueny.curso_android_kotlin.Produto.Product
import java.math.BigDecimal

class ActivityProductForm :
    AppCompatActivity(R.layout.activity_activity_product_form) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val nameField = findViewById<EditText>(R.id.name)
        val descriptionField = findViewById<EditText>(R.id.description)
        val valueField = findViewById<EditText>(R.id.value)
        val salveButton = findViewById<Button>(R.id.save_button)

        salveButton.setOnClickListener {
            val name = nameField.text.toString()
            val description = descriptionField.text.toString()
            val textValue = valueField.text.toString()

            val value = if (textValue.isBlank()) {
                BigDecimal.ZERO
            } else {
                BigDecimal(textValue)
            }

            val createdProduct = Product(
                name = name,
                description = description,
                value = value
            )
            Log.i("ActivityProductForm)" , "onCreate: $createdProduct")
        }

    }
}
