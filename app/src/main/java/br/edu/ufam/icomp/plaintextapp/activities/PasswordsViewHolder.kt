package br.edu.ufam.icomp.plaintextapp.activities

import android.content.Context
import android.view.View
import android.widget.TextView
import android.widget.Toast // Importe Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import br.edu.ufam.icomp.plaintextapp.R

// O ViewHolder agora recebe um callback de clique
class PasswordsViewHolder(
    v: ConstraintLayout,
    val context: Context,
    private val onItemClick: (Int) -> Unit // Callback de clique
) : RecyclerView.ViewHolder(v), View.OnClickListener {

    var login: TextView = v.findViewById(R.id.itemLogin)
    var name: TextView = v.findViewById(R.id.itemName)
    var id: Int = 0 // O ID da senha que este ViewHolder representa

    init {
        v.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        // Invoca o callback de clique, passando o ID da senha
        onItemClick(id)
        // Removido o Toast direto daqui, a ListActivity/ViewModel decidirá a reação
        // Toast.makeText(context, "Olá " + this.login.text.toString(), Toast.LENGTH_LONG).show()
    }
}