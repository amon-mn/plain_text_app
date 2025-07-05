package br.edu.ufam.icomp.plaintextapp.activities

import android.content.Context
import android.content.Intent // Importe Intent
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import br.edu.ufam.icomp.plaintextapp.R

class PasswordsViewHolder(v: ConstraintLayout, val context: Context) :
    RecyclerView.ViewHolder(v), View.OnClickListener {

    var login: TextView = v.findViewById(R.id.itemLogin)
    var name: TextView = v.findViewById(R.id.itemName)
    var id: Int = 0 // O ID da senha que este ViewHolder representa

    init {
        v.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        // Lógica de clique do item da lista: Inicia EditActivity
        val intent = Intent(context, EditActivity::class.java)
        intent.putExtra("password_id", id) // Passa o ID da senha clicada
        context.startActivity(intent)

        Toast.makeText(context, "Abrindo senha com ID: $id", Toast.LENGTH_SHORT).show()
    }
}