package br.com.alura.aluvery.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.alura.aluvery.model.Product
import br.com.alura.aluvery.sampledata.sampleProducts
import br.com.alura.aluvery.sampledata.sampleSections
import br.com.alura.aluvery.ui.components.CardProductItem
import br.com.alura.aluvery.ui.components.ProductsSection
import br.com.alura.aluvery.ui.components.SearchTextField
import br.com.alura.aluvery.ui.theme.AluveryTheme

class HomeScreenUiState(searchText: String) {
    var text by mutableStateOf("")

    val searchedProducts get() =
        if (text.isNotBlank()) {
            sampleProducts.filter { product ->
                product.name.contains(
                    text,
                    ignoreCase = true,
                ) ||
                        product.description?.contains(
                            text,
                            ignoreCase = true,
                        ) ?: false
            }
        } else emptyList()

    fun isShowSection(): Boolean{
        return text.isBlank()
    }

}

@Composable
fun HomeScreen(
    sections: Map<String, List<Product>>,
    searchText: String = ""
) {
    Column {

        val state = remember {
            HomeScreenUiState(searchText)
        }
        val text = state.text
        val searchedProducts = state.searchedProducts


        SearchTextField(
            searchText = text,
            onSearchChange = {
                state.text = it
            },

            Modifier
                .padding(16.dp)
                .fillMaxWidth(),
        )

        LazyColumn(
            Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            if (state.isShowSection()) {
                for (section in sections) {
                    val title = section.key
                    val products = section.value
                    item {
                        ProductsSection(
                            title = title,
                            products = products
                        )
                    }
                }
            } else {
                items(searchedProducts) { p ->
                    CardProductItem(
                        product = p,
                        Modifier.padding(horizontal = 16.dp),
                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun HomeScreenPreview() {
    AluveryTheme {
        Surface {
            HomeScreen(sampleSections)
        }
    }
}

@Preview
@Composable
fun HomeScreenWithSearchTextPreview() {
    AluveryTheme {
        Surface {
            HomeScreen(
                sampleSections,
                searchText = "a",
            )
        }
    }
}