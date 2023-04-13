package paixao.lueny.curso_android_kotlin.activity.compose

import android.content.Context
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.R
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.viewinterop.AndroidViewBinding
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.appcompattheme.AppCompatTheme
import paixao.lueny.curso_android_kotlin.R.*
import paixao.lueny.curso_android_kotlin.activity.ID_PRODUCT_KEY
import paixao.lueny.curso_android_kotlin.activity.ui.theme.CursoandroidkotlinTheme
import paixao.lueny.curso_android_kotlin.database.AppDatabase
import paixao.lueny.curso_android_kotlin.databinding.ActivityProductDetailsBinding
import paixao.lueny.curso_android_kotlin.databinding.ActivityProductFormBinding
import paixao.lueny.curso_android_kotlin.extensions.currencyFormatting
import paixao.lueny.curso_android_kotlin.model.Product

class ActivityComposeProductDetails : ComponentActivity() {

    private var productId: Long = 0L
    private val productDao by lazy { AppDatabase.instance(context = this).productDao() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tryLoadProduct()
        setContent {
            AppCompatTheme() {
                var productState: Product? by remember {
                    mutableStateOf(productDao.searchById(productId))
                }
                productState?.let { } ?: finish()
                SearchProduct(onResume = { })

            }
        }
    }

    private fun tryLoadProduct() {
        productId = intent.getLongExtra(ID_PRODUCT_KEY, 0L)

    }
}

@Composable
private fun SearchProduct(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    onResume: () -> Unit,
) {
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
    TopAppBar {
        title = {
            Text(
                text = stringResource(id = R.string.title.)
            )
        }
    }

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
                placeholder = rememberDrawablePaiter(
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
                fontWeight = FontWeight.Bold,

            )

        }

    }
}


@Composable
fun CustomView(text: String) {
    val counter = remember { mutableStateOf(0) }
    AndroidView(factory = { context ->
        TextView(context).apply {
            setText(text)
            setTextSize(42f)
            setOnClickListener {
                counter.value++
                Toast.makeText(context, "Hello $text!", Toast.LENGTH_SHORT).show()
            }
        }
    },
        update = {
            counter.value
            it.setText("$text ${counter.value}")
        })

}


@Preview(showBackground = true)
@Composable
fun DetailsAppBarPreview() {
    AppCompatTheme() {
        DetailsAppBar()
    }
}

@Preview(showBackground = true)
@Composable
fun DetailsContentPreview() {
    AppCompatTheme() {
        DetailsContent(
            Modifier,
            Product()
        )
    }
}