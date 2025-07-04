package br.edu.ufam.icomp.plaintextapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.WindowCompat // Re-importe WindowCompat

import androidx.preference.PreferenceFragmentCompat

import br.edu.ufam.icomp.plaintextapp.R


class PreferencesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // RE-ADICIONE ESTA LINHA: Habilita o desenho edge-to-edge
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContentView(R.layout.activity_preferences)

        val toolbar = findViewById<Toolbar>(R.id.preferences_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // Adiciona o botão de voltar
        supportActionBar?.title = "PlainText" // Garante o título

        // Anexa o PreferencesFragment ao FragmentContainerView
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.preferences_fragment_container, PreferencesFragment())
                .commit()
        }
    }

    // Sobrescreve onSupportNavigateUp para lidar com o botão de voltar da Toolbar
    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed() // Simula o botão de voltar
        return true
    }

    class PreferencesFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.preferences, rootKey)
        }
    }
}