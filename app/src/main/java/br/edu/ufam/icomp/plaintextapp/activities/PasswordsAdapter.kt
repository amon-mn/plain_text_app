package br.edu.ufam.icomp.plaintextapp.activities

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import br.edu.ufam.icomp.plaintextapp.R
import br.edu.ufam.icomp.plaintextapp.model.Password
import kotlin.collections.ArrayList

class PasswordsAdapter(
    private val context: Context,
    private val onItemClick: (Int) -> Unit,
    private val onItemLongClick: (Int) -> Unit // NOVO: Callback para clique longo
) : RecyclerView.Adapter<PasswordsViewHolder>() {

    private var passwords: List<Password> = ArrayList()

    fun updateData(newPasswords: List<Password>) {
        this.passwords = newPasswords
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PasswordsViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false) as ConstraintLayout
        // Passa ambos os callbacks para o ViewHolder
        return PasswordsViewHolder(v, context, onItemClick, onItemLongClick)
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