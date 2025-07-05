package br.edu.ufam.icomp.plaintextapp.activities

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import br.edu.ufam.icomp.plaintextapp.R
import br.edu.ufam.icomp.plaintextapp.dao.PasswordDAO
import br.edu.ufam.icomp.plaintextapp.model.Password
import kotlin.collections.ArrayList

class PasswordsAdapter(private val context: Context) :
    RecyclerView.Adapter<PasswordsViewHolder>() {

    // CORREÇÃO AQUI: Inicialize a propriedade 'passwords'
    private var passwords: ArrayList<Password> = ArrayList() // <-- ADICIONE = ArrayList()
    // Ou, se você quiser garantir que ela seja inicializada pelo DAO logo de cara,
    // pode fazer: private var passwords: ArrayList<Password> = passwordDAO.getList()
    // Mas a forma mais segura é inicializar vazia e depois chamar update().

    private val passwordDAO: PasswordDAO = PasswordDAO(context)

    init {
        update() // Carrega os dados iniciais do DAO
    }

    fun update() {
        passwords = passwordDAO.getList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PasswordsViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false) as ConstraintLayout
        return PasswordsViewHolder(v, context)
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