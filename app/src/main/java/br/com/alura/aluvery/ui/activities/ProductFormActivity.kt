package br.com.alura.aluvery.ui.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.R
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.alura.aluvery.dao.ProductDao
import br.com.alura.aluvery.model.Product
import br.com.alura.aluvery.ui.theme.AluveryTheme
import coil.compose.AsyncImage
import java.lang.NumberFormatException
import java.math.BigDecimal
import java.text.DecimalFormat


class ProductFormActivity : ComponentActivity() {

    private val dao = ProductDao()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AluveryTheme {
                Surface {
                    ProductFormScreen({
                        product ->
                        dao.save(product)
                        finish()
                    })

                }
            }
        }
    }
}

@Composable
fun ProductFormScreen(
    onSaveClick:(Product) -> Unit = {  }
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(state = rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = "Criando o produto", Modifier.fillMaxWidth(), fontSize = 28.sp)
        
        var url by remember {
            mutableStateOf("")
        }
        if (url.isNotBlank()){
            AsyncImage(
                model = url,
                contentDescription = null,
                Modifier
                    .fillMaxWidth()
                    .height(200.dp),

                //se ele não conseguir ver o conteúdo ele corta
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = br.com.alura.aluvery.R.drawable.placeholder),
                error = painterResource(id = br.com.alura.aluvery.R.drawable.placeholder)
            )
        }


        TextField(
            value = url, onValueChange = {
            url = it
            }, Modifier.fillMaxWidth(), label = {
                Text(text = "Url da imagem")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Uri,
                imeAction = ImeAction.Next
            )
        )

        var name by remember {
            mutableStateOf("")
        }
        TextField(
                value = name, onValueChange = {
                name = it
            }, Modifier.fillMaxWidth(), label = {
                Text(text = "Digite o nome")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
                capitalization = KeyboardCapitalization.Words
            )
        )

        var price by remember {
            mutableStateOf("")
        }
        //usamos remember para que seja instanciado uma unica vez
        val formatter = remember {
            DecimalFormat("#.##")
        }
        TextField(
            value = price, onValueChange = {
                    try {
                        price = formatter.format(BigDecimal(it))
                    }catch (e: IllegalArgumentException){
                        if(it.isBlank()){
                            price = it
                        }
                    }

                },
            Modifier.fillMaxWidth(), label = {
                Text(text = "Preço")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Next
            )
        )

        var description by remember {
            mutableStateOf("")
        }
        TextField(
            value = description, onValueChange = {
                description = it
            },
            Modifier
                .fillMaxWidth()
                .heightIn(100.dp),
            label = {
                Text(text = "Descrição")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
                capitalization = KeyboardCapitalization.Sentences
            )
        )

        Button(onClick = {
            val convertedPrice = try {
                BigDecimal(price)
            }catch (e: NumberFormatException){
                BigDecimal.ZERO
            }
            val product = Product(
                name = name,
                image = url,
                price = convertedPrice,
                description = description
            )
            Log.i("ProductFormActivity", "ProductFormScreen: $product")
            onSaveClick(product)
        }, Modifier.fillMaxWidth()) {
            Text(text = "Salvar")
        }
        Spacer(modifier = Modifier)
    }

}

@Preview
@Composable
fun ProductFormScreenPreview() {
    AluveryTheme {
        Surface {
            ProductFormScreen()
        }
    }

}



