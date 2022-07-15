package paixao.lueny.curso_android_kotlin.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import paixao.lueny.curso_android_kotlin.Produto.Product
import paixao.lueny.curso_android_kotlin.R
import paixao.lueny.curso_android_kotlin.dao.DaoProducts
import java.math.BigDecimal

class ActivityProductForm :
    AppCompatActivity(R.layout.activity_activity_product_form) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        configureButtonSave()
    }

    private fun configureButtonSave(){
        val salveButton = findViewById<Button>(R.id.activity_products_list_save_button)
        val dao = DaoProducts()

        salveButton.setOnClickListener {
            val createdProduct = createProduct()
            dao.add(createdProduct)
            finish()
        }
    }

    private fun createProduct(): Product {
        val nameField = findViewById<EditText>(R.id.activity_products_list_name)
        val name = nameField.text.toString()
        val descriptionField = findViewById<EditText>(R.id.activity_products_list_description)
        val description = descriptionField.text.toString()
        val valueField = findViewById<EditText>(R.id.activity_products_list_value)
        val textValue = valueField.text.toString()
        val value = if (textValue.isBlank()) {
            BigDecimal.ZERO
        } else {
            BigDecimal(textValue)
        }
        return Product(
            name = name,
            description = description,
            value = value
        )
    }
}
