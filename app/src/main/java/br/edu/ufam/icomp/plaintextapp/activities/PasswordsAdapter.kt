package br.edu.ufam.icomp.plaintextapp.activities

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import br.edu.ufam.icomp.plaintextapp.R
import br.edu.ufam.icomp.plaintextapp.model.Password // Importe Password
import kotlin.collections.ArrayList

// O Adapter agora recebe um callback de clique
class PasswordsAdapter(
    private val context: Context,
    private val onItemClick: (Int) -> Unit // Callback para o clique do item
) : RecyclerView.Adapter<PasswordsViewHolder>() {

    private var passwords: List<Password> = ArrayList() // Agora é uma List (imutável)

    // Novo metodo para atualizar os dados do adapter
    fun updateData(newPasswords: List<Password>) {
        this.passwords = newPasswords
        notifyDataSetChanged() // Notifica o RecyclerView sobre as mudanças
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PasswordsViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false) as ConstraintLayout
        // Passa o callback para o ViewHolder
        return PasswordsViewHolder(v, context, onItemClick)
    }

    override fun onBindViewHolder(holder: PasswordsViewHolder, position: Int) {
        holder.name.text = passwords[position].name
        holder.login.text = passwords[position].login
        holder.id = passwords[position].id
    }

    override fun getItemCount(): Int {
        return passwords.size
    }
}