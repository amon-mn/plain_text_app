package br.edu.ufam.icomp.plaintextapp.activities // <-- SEU PACOTE PRINCIPAL

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat // Importe PreferenceFragmentCompat
import br.edu.ufam.icomp.plaintextapp.R

class PreferencesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Substitui o conteúdo da Activity pelo nosso PreferencesFragment
        // android.R.id.content é o ID do contêiner raiz da Activity
        supportFragmentManager
            .beginTransaction()
            .replace(android.R.id.content, PreferencesFragment())
            .commit()
    }

    // Classe interna que hospeda as preferências do XML
    class PreferencesFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            // Carrega as preferências do arquivo XML que criamos
            setPreferencesFromResource(R.xml.preferences, rootKey)
        }
    }
}