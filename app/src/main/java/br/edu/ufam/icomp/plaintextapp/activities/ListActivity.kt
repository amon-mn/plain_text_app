package br.edu.ufam.icomp.plaintextapp.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast // Importe Toast
import androidx.activity.viewModels // Importe viewModels para obter a ViewModel
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Lifecycle // Importe Lifecycle
import androidx.lifecycle.lifecycleScope // Importe lifecycleScope
import androidx.lifecycle.repeatOnLifecycle // Importe repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton // <-- VERIFIQUE/CORRIJA ESTE IMPORT
import br.edu.ufam.icomp.plaintextapp.R
import br.edu.ufam.icomp.plaintextapp.viewmodel.PasswordListViewModel // Importe PasswordListViewModel
import kotlinx.coroutines.launch // Importe launch


class ListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PasswordsAdapter
    private lateinit var fabAddPassword: FloatingActionButton

    // Obtém a ViewModel usando o delegate viewModels()
    private val viewModel: PasswordListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        // Configurar a Toolbar
        val toolbar = findViewById<Toolbar>(R.id.list_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "PlainText"

        recyclerView = findViewById(R.id.list_recycler)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // O adapter agora receberá a lista da ViewModel
        adapter = PasswordsAdapter(this) { passwordId ->
            // Este é o callback de clique do item, que chama a ViewModel
            viewModel.onPasswordItemClick(passwordId)
        }
        recyclerView.adapter = adapter

        fabAddPassword = findViewById(R.id.fab_add_password)
        fabAddPassword.setOnClickListener {
            // O clique do FAB também chama a ViewModel
            viewModel.onAddPasswordClick()
        }

        // --- Observar o estado da ViewModel ---
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    // Atualiza o adapter com a nova lista de senhas
                    adapter.updateData(uiState.passwords)

                    // Reage a mensagens de erro
                    uiState.errorMessage?.let { message ->
                        Toast.makeText(this@ListActivity, message, Toast.LENGTH_SHORT).show()
                        // Opcional: Limpar a mensagem de erro na ViewModel após exibi-la
                        // viewModel.resetErrorMessage()
                    }

                    // Reage ao estado de navegação
                    uiState.navigateToEdit?.let { passwordId ->
                        val intent = Intent(this@ListActivity, EditActivity::class.java)
                        intent.putExtra("password_id", passwordId)
                        startActivity(intent)
                        viewModel.resetNavigation() // Reseta o estado de navegação na ViewModel
                    }
                }
            }
        }
        // --- Fim da Observação ---
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.configs -> {
                val intentConfig = Intent(this, PreferencesActivity::class.java)
                startActivity(intentConfig)
                true
            }
            R.id.about -> {
                Toast.makeText(this, "PlainText App v1.0", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onRestart() {
        super.onRestart()
        // Recarrega as senhas da ViewModel quando a Activity volta ao foreground
        viewModel.loadPasswords()
    }
}