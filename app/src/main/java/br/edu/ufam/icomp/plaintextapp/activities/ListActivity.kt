package br.edu.ufam.icomp.plaintextapp.activities

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import br.edu.ufam.icomp.plaintextapp.R
import br.edu.ufam.icomp.plaintextapp.activities.PreferencesActivity
import br.edu.ufam.icomp.plaintextapp.viewmodel.PasswordListViewModel
import kotlinx.coroutines.launch


class ListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PasswordsAdapter
    private lateinit var fabAddPassword: FloatingActionButton

    private val viewModel: PasswordListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        val toolbar = findViewById<Toolbar>(R.id.list_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "PlainText"

        recyclerView = findViewById(R.id.list_recycler)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // CORREÇÃO AQUI: Passar o segundo lambda para onItemLongClick
        adapter = PasswordsAdapter(
            this,
            onItemClick = { passwordId ->
                viewModel.onPasswordItemClick(passwordId) // Clique para editar
            },
            onItemLongClick = { passwordId ->
                viewModel.onDeletePasswordClick(passwordId) // Clique longo para excluir
            }
        )
        recyclerView.adapter = adapter

        fabAddPassword = findViewById(R.id.fab_add_password)
        fabAddPassword.setOnClickListener {
            viewModel.onAddPasswordClick()
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    adapter.updateData(uiState.passwords)

                    uiState.errorMessage?.let { message ->
                        Toast.makeText(this@ListActivity, message, Toast.LENGTH_SHORT).show()
                        viewModel.resetDeleteStatus()
                    }

                    uiState.navigateToEdit?.let { passwordId ->
                        val intent = Intent(this@ListActivity, EditActivity::class.java)
                        intent.putExtra("password_id", passwordId)
                        startActivity(intent)
                        viewModel.resetNavigation()
                    }

                    uiState.showDeleteConfirmation?.let { passwordId ->
                        AlertDialog.Builder(this@ListActivity)
                            .setTitle("Excluir Senha")
                            .setMessage("Tem certeza que deseja excluir esta senha?")
                            .setPositiveButton("Sim") { dialog: DialogInterface, _: Int ->
                                viewModel.confirmDeletePassword()
                                dialog.dismiss()
                            }
                            .setNegativeButton("Não") { dialog: DialogInterface, _: Int ->
                                viewModel.cancelDeletePassword()
                                dialog.dismiss()
                            }
                            .show()
                    }

                    if (uiState.deleteSuccess) {
                        Toast.makeText(this@ListActivity, "Senha excluída com sucesso!", Toast.LENGTH_SHORT).show()
                        viewModel.resetDeleteStatus()
                    }
                }
            }
        }
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
        viewModel.refreshPasswords()
    }
}