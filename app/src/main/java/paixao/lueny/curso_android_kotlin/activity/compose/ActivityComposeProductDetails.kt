package paixao.lueny.curso_android_kotlin.activity.compose

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import paixao.lueny.curso_android_kotlin.R.*
import paixao.lueny.curso_android_kotlin.R.font.*
import paixao.lueny.curso_android_kotlin.activity.ActivityProductForm
import paixao.lueny.curso_android_kotlin.activity.ID_PRODUCT_KEY
import paixao.lueny.curso_android_kotlin.database.AppDatabase
import paixao.lueny.curso_android_kotlin.extensions.currencyFormatting
import paixao.lueny.curso_android_kotlin.model.Product

class ActivityComposeProductDetails : ComponentActivity() {

    private var productId: Long = 0L
    private val productDao by lazy { AppDatabase.instance(context = this).productDao() }
    var productState: Product? by remember { mutableStateOf(productDao.searchById(productId)) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tryLoadProduct()
        setContent {
            MaterialTheme() {

            }
            productState?.let { product ->
                ProductScreenDetails(product = product,
                    onEditProductClick = {
                        Intent(this, ActivityProductForm::class.java).apply {
                            putExtra(ID_PRODUCT_KEY, productId)
                            startActivity(this)

                        }
                    },
                    onDeleteProductClick = {
                        productState.let {
                            productDao.remove(product)
                        }
                        finish()
                    }
                )
            } ?: finish()

            SearchProduct(onResume = {
                productState = productDao.searchById(productId)
            })
        }
    }

    private fun tryLoadProduct() {
        productId = intent.getLongExtra(ID_PRODUCT_KEY, 0L)

    }

    @Composable
    private fun SearchProduct(
        lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
        onResume: () -> Unit,
    ) {
        val currentOnResume by rememberUpdatedState(newValue = onResume)

        DisposableEffect(lifecycleOwner) {
            val observer = LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_RESUME) {
                    currentOnResume()
                }
            }
            lifecycleOwner.lifecycle.addObserver(observer)

            onDispose {
                lifecycleOwner.lifecycle.removeObserver(observer)
            }
        }
    }

    @Composable
    fun ProductScreenDetails(
        product: Product,
        onEditProductClick: () -> Unit = {},
        onDeleteProductClick: () -> Unit = {}
    ) {
        Scaffold(
            topBar = {
                DetailsAppBar(
                    onEditProductClick,
                    onDeleteProductClick
                )
            },
            content = { paddingValues ->
                DetailsContent(
                    Modifier.padding(paddingValues), product
                )
            })
    }

    @Composable
    fun DetailsAppBar(onEditProductClick: () -> Unit = {}, onDeleteProductClick: () -> Unit = {}) {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(id = string.title_activity_product_details_compose),
                    color = colorResource(id = color.white)
                )
            },
            actions = {
                IconButton(onClick = { onEditProductClick() }) {
                    Icon(
                        painterResource(id = drawable.ic_action_edit),
                        contentDescription = "Editar Produto",
                        tint = Color.White
                    )
                }
                IconButton(onClick = { onDeleteProductClick() }) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "Deletar Produto",
                        tint = Color.White
                    )

                }
            })
    }


    @Composable
    fun DetailsContent(modifier: Modifier, product: Product) {
        Column(modifier = modifier) {
            val imageHeight = 200.dp
            val boxHeight = 230.dp

            Box(modifier = Modifier.height(boxHeight)) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(product.image)
                        .build(),
                    error = painterResource(id = drawable.erro),
                    placeholder = rememberDrawablePainter(
                        ContextCompat.getDrawable(
                            LocalContext.current,
                            drawable.placeholder
                        )
                    ),
                    contentDescription = "Imagem do Produto",
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(imageHeight),
                    alignment = Alignment.Center,
                    contentScale = ContentScale.Crop
                )
                Surface(
                    shape = CircleShape,
                    color = Color.White,
                    elevation = 10.dp,
                    modifier = Modifier
                        .offset(y = 180.dp, x = 16.dp)
                ) {
                    Text(
                        text = product.value.currencyFormatting(),
                        fontSize = 24.sp,
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily(Font(montserrat_bold)),
                        fontWeight = FontWeight.Bold,
                        color = colorResource(color.green),
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 12.dp)

                    )

                }

            }

            Column {
                Text(
                    text = product.name,
                    fontSize = 28.sp,
                    fontFamily = FontFamily(Font(montserrat_bold)),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                )

                Text(
                    text = product.description,
                    fontSize = 28.sp,
                    fontFamily = FontFamily(Font(montserrat_bold)),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                )
            }

        }
    }


    @Preview(showBackground = true)
    @Composable
    fun DetailsAppBarPreview() {
        MaterialTheme() {
            DetailsAppBar()
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun DetailsContentPreview() {
        MaterialTheme() {
            DetailsContent(
                Modifier,
                Product()
            )
        }
    }
}