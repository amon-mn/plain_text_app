package br.edu.ufam.icomp.plaintextapp.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu // Importe Menu
import android.view.MenuItem // Importe MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar // Importe Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import br.edu.ufam.icomp.plaintextapp.R
import br.edu.ufam.icomp.plaintextapp.activities.PreferencesActivity // Importe PreferencesActivity


class ListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PasswordsAdapter
    private lateinit var fabAddPassword: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        // Configurar a Toolbar
        val toolbar = findViewById<Toolbar>(R.id.list_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "PlainText" // Define o título, embora já esteja no XML

        recyclerView = findViewById(R.id.list_recycler)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = PasswordsAdapter(this)
        recyclerView.adapter = adapter

        fabAddPassword = findViewById(R.id.fab_add_password)
        fabAddPassword.setOnClickListener {
            val intent = Intent(this, EditActivity::class.java)
            intent.putExtra("password_id", -1)
            startActivity(intent)
        }
    }

    // --- Adicionar Menu à AppBar ---
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu) // Infla o menu que já usamos na MainActivity
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
                // Lógica para "Sobre" (você pode adicionar um AlertDialog simples aqui)
                AlertDialog.Builder(this).setMessage("PlainText App v1.0").setNeutralButton("Ok", null).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    // --- Fim da Lógica de Menu ---

    override fun onRestart() {
        super.onRestart()
        adapter.update()
        adapter.notifyDataSetChanged()
    }
}