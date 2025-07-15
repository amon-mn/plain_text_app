package br.edu.ufam.icomp.plaintextapp.data.datasource.local.dao

import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.widget.Toast
import br.edu.ufam.icomp.plaintextapp.database.Database
import br.edu.ufam.icomp.plaintextapp.domain.entities.model.Password
import kotlinx.coroutines.Dispatchers // Importe Dispatchers
import kotlinx.coroutines.withContext // Importe withContext

class MyPasswordDAO(private val context: Context) {

    private val database: SQLiteDatabase

    init {
        this.database = Database(context).writableDatabase
    }

    fun getList(): ArrayList<Password> {
        val result = ArrayList<Password>()
        val sql = "SELECT * FROM passwords ORDER BY name"
        var cursor: Cursor? = null

        try {
            cursor = database.rawQuery(sql, null)
            while (cursor.moveToNext()) {
                val id = cursor.getInt(0)
                val name = cursor.getString(1)
                val login = cursor.getString(2)
                val password = cursor.getString(3)
                val notes = cursor.getString(4)
                result.add(Password(id, name, login, password, notes))
            }
        } catch (e: SQLException) {
            Toast.makeText(context, "Erro ao buscar senhas: ${e.message}", Toast.LENGTH_SHORT).show()
        } finally {
            cursor?.close()
        }
        return result
    }

    // AGORA SUSPEND
    suspend fun add(password: Password): Boolean = withContext(Dispatchers.IO) {
        val sql = "INSERT INTO passwords VALUES (NULL, " +
                "'${password.name}', " +
                "'${password.login}', " +
                "'${password.password}', " +
                "'${password.notes}')"
        try {
            database.execSQL(sql)
            Toast.makeText(context, "Senha salva!", Toast.LENGTH_SHORT).show() // Remova Toast aqui, ViewModel lida com isso
            true
        } catch (e: SQLException) {
            Toast.makeText(context, "Erro ao salvar senha: ${e.message}", Toast.LENGTH_SHORT).show() // Remova Toast
            false
        }
    }

    // AGORA SUSPEND
    suspend fun update(password: Password): Boolean = withContext(Dispatchers.IO) {
        val sql = "UPDATE passwords SET " +
                "name='${password.name}', " +
                "login='${password.login}', " +
                "password='${password.password}', " +
                "notes='${password.notes}' " +
                "WHERE id=${password.id}"
        try {
            database.execSQL(sql)
            Toast.makeText(context, "Senha atualizada!", Toast.LENGTH_SHORT).show() // Remova Toast
            true
        } catch (e: SQLException) {
            Toast.makeText(context, "Erro ao atualizar senha: ${e.message}", Toast.LENGTH_SHORT).show() // Remova Toast
            false
        }
    }

    // AGORA SUSPEND
    suspend fun get(id: Int): Password? = withContext(Dispatchers.IO) {
        val sql = "SELECT * FROM passwords WHERE id=$id"
        var cursor: Cursor? = null

        try {
            cursor = database.rawQuery(sql, null)
            if (cursor.moveToFirst()) {
                val name = cursor.getString(1)
                val login = cursor.getString(2)
                val password = cursor.getString(3)
                val notes = cursor.getString(4)
                return@withContext Password(id, name, login, password, notes)
            }
        } catch (e: SQLException) {
            Toast.makeText(context, "Erro ao buscar senha: ${e.message}", Toast.LENGTH_SHORT).show() // Remova Toast
        } finally {
            cursor?.close()
        }
        return@withContext null
    }

    // NOVO MeTODO: delete
    suspend fun delete(id: Int): Boolean = withContext(Dispatchers.IO) {
        val sql = "DELETE FROM passwords WHERE id=$id"
        try {
            database.execSQL(sql)
            Toast.makeText(context, "Senha excluída!", Toast.LENGTH_SHORT).show() // Remova Toast
            true
        } catch (e: SQLException) {
            Toast.makeText(context, "Erro ao excluir senha: ${e.message}", Toast.LENGTH_SHORT).show() // Remova Toast
            false
        }
    }
}