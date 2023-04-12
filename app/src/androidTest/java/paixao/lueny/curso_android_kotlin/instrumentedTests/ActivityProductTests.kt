package paixao.lueny.curso_android_kotlin.instrumentedTests

import androidx.test.core.app.ActivityScenario.launch
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import paixao.lueny.curso_android_kotlin.R
import paixao.lueny.curso_android_kotlin.activity.ActivityLogin
import paixao.lueny.curso_android_kotlin.activity.ActivityProductForm
import paixao.lueny.curso_android_kotlin.activity.ActivityProductsList
import paixao.lueny.curso_android_kotlin.activity.ActivityUserRegistration
import paixao.lueny.curso_android_kotlin.database.AppDatabase

class ProductScreenTests {

//    @get:Rule
//    val rule = ActivityScenarioRule(ActivityProductsList::class.java)

    @Before
    fun preparaAmbiente() {
        AppDatabase.instance(
            InstrumentationRegistry.getInstrumentation().targetContext
        )
            .clearAllTables()
    }

    @Test
    fun shouldmostraronomedoaplicativo() {
        launch(ActivityProductsList::class.java)
        onView(withText("Lista de Produtos")).check(matches(isDisplayed()))
    }

    @Test
    fun shouldMostrarNomeDoAplicativoWhenEstaNaTelaDeLogin() {

        launch(ActivityLogin::class.java)
        onView(withText("Produtos Naturais")).check(matches(isDisplayed()))
    }

    @Test
    fun shouldTerTodosOsCamposNecessariosFazerLogin() {

        launch(ActivityLogin::class.java)
        onView(withId(R.id.activity_login_user)).check(matches(isDisplayed()))
        onView(withId(R.id.activity_login_password)).check(matches(isDisplayed()))
        onView(withId(R.id.activity_login_button)).check(matches(isDisplayed()))
    }

    @Test
    fun shouldTerTodosOsCamposNecessariosFazerCadastro() {

        launch(ActivityUserRegistration::class.java)
        onView(withId(R.id.activity_form_user_registration)).check(matches(isDisplayed()))
        onView(withId(R.id.activity_form_password_registration)).check(matches(isDisplayed()))
        onView(withId(R.id.activity_form_registration_button_register)).check(matches(isDisplayed()))
    }

    @Test
    fun shouldmostrarCamposNecessariosParaCriarUmProduto() {

        launch(ActivityProductForm::class.java)
        onView(withId(R.id.activity_products_list_name)).check(matches(isDisplayed()))
        onView(withId(R.id.activity_products_list_description)).check(matches(isDisplayed()))
        onView(withId(R.id.activity_products_list_value)).check(matches(isDisplayed()))
        onView(withId(R.id.activity_products_list_save_button)).check(matches(isDisplayed()))
    }

    @Test
    fun shouldSerCapazDePreencherOsCamposESalvarOProduto() {

        launch(ActivityProductsList::class.java)
        clicaNoFAB()
        preencheCamposDoFormulario(
            nome = "Melancia",
            descricao = "Fruta",
            valor = "11.99"
        )
        clicaEmSalvar()
        onView(withText("Melancia")).check(matches(isDisplayed()))
    }

    @Test
    fun shouldSerCapazDeEditarUmProduto() {
        launch(ActivityProductsList::class.java)

        clicaNoFAB()
        preencheCamposDoFormulario(
            nome = "Melancia",
            descricao = "Fruta",
            valor = "11.99"
        )
        clicaEmSalvar()
        onView(withText("Melancia")).perform(click())
        onView(withId(R.id.menu_product_details_edit)).perform(click())

        preencheCamposDoFormulario(
            nome = "Banana nanica",
            descricao = "da feira",
            valor = "5.99"
        )

        clicaEmSalvar()
        onView(withText("Banana nanica")).check(matches(isDisplayed()))
    }


    private fun clicaNoFAB() {
        onView(withId(R.id.activity_products_list_fab))
            .perform(click())
    }

    private fun clicaEmSalvar() {
        onView(withId(R.id.activity_products_list_save_button))
            .perform(click())
    }

    private fun preencheCamposDoFormulario(
        nome: String,
        descricao: String,
        valor: String
    ) {
        onView(withId(R.id.activity_products_list_name))
            .perform(
                replaceText(nome),
                closeSoftKeyboard()
            )
        onView(withId(R.id.activity_products_list_description))
            .perform(
                replaceText(descricao),
                closeSoftKeyboard()
            )
        onView(withId(R.id.activity_products_list_value))
            .perform(
                replaceText(valor),
                closeSoftKeyboard()
            )

    }
}