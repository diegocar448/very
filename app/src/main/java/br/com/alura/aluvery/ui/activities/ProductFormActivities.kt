package br.com.alura.aluvery.ui.activities

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.core.content.pm.ShortcutInfoCompat
import br.com.alura.aluvery.ui.theme.AluveryTheme

class ProductFormActivities: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContent{
            AluveryTheme {
                Surface{
                    Text(text = "Formulário de produto")
                }
            }
        }
    }

}