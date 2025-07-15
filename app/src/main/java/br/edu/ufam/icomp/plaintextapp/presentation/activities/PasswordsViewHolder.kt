package br.edu.ufam.icomp.plaintextapp.presentation.activities

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import br.edu.ufam.icomp.plaintextapp.R

class PasswordsViewHolder(
    v: ConstraintLayout,
    val context: Context,
    private val onItemClick: (Int) -> Unit, // Callback para clique normal
    private val onItemLongClick: (Int) -> Unit // NOVO: Callback para clique longo
) : RecyclerView.ViewHolder(v), View.OnClickListener, View.OnLongClickListener { // Implementa OnLongClickListener

    var login: TextView = v.findViewById(R.id.itemLogin)
    var name: TextView = v.findViewById(R.id.itemName)
    var id: Int = 0

    init {
        v.setOnClickListener(this)
        v.setOnLongClickListener(this) // Define o listener de clique longo
    }

    override fun onClick(v: View?) {
        onItemClick(id) // Chama o callback de clique normal
    }

    override fun onLongClick(v: View?): Boolean {
        onItemLongClick(id) // Chama o callback de clique longo
        return true // Retorna true para consumir o evento e não disparar o onClick
    }
}