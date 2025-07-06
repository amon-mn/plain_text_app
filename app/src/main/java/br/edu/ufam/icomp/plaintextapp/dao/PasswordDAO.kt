package br.edu.ufam.icomp.plaintextapp.dao // <-- SEU PACOTE

import android.content.Context
import android.database.Cursor
import android.database.SQLException // Importe SQLException
import android.database.sqlite.SQLiteDatabase // Importe SQLiteDatabase
import android.widget.Toast
import br.edu.ufam.icomp.plaintextapp.database.Database // Importe a classe Database
import br.edu.ufam.icomp.plaintextapp.model.Password

class PasswordDAO(private val context: Context) {

    // Inicializa o banco de dados no construtor
    private val database: SQLiteDatabase =
        Database(context).writableDatabase // Propriedade para o banco de dados

    // Remove o companion object com passwordsList, pois não usaremos mais o ArrayList em memória
    // companion object {
    //     private val passwordsList = ArrayList<Password>()
    // }

    // Metodo getList - Agora busca do banco de dados
    fun getList(): ArrayList<Password> {
        val result = ArrayList<Password>()
        val sql = "SELECT * FROM passwords ORDER BY name" // Ordena por nome
        var cursor: Cursor? = null // Use um Cursor anulável

        try {
            cursor = database.rawQuery(sql, null) // Executa a query
            while (cursor.moveToNext()) { // Itera sobre os resultados
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
            cursor?.close() // Garante que o cursor seja fechado
        }

        return result
    }

    // Metodo add - Agora insere no banco de dados
    fun add(password: Password): Boolean {
        // SQL Injection é um risco com concatenação direta.
        // Para um app real, use ContentValues ou Prepared Statements.
        // Para este lab, seguiremos o formato do slide.
        val sql = "INSERT INTO passwords VALUES (NULL, " +
                "'${password.name}', " +
                "'${password.login}', " +
                "'${password.password}', " +
                "'${password.notes}')"
        try {
            database.execSQL(sql) // Executa o comando SQL
            Toast.makeText(context, "Senha salva!", Toast.LENGTH_SHORT).show()
            return true
        } catch (e: SQLException) {
            Toast.makeText(context, "Erro ao salvar senha: ${e.message}", Toast.LENGTH_SHORT).show()
            return false
        }
    }

    // Metodo update - Agora atualiza no banco de dados
    fun update(password: Password): Boolean {
        val sql = "UPDATE passwords SET " +
                "name='${password.name}', " +
                "login='${password.login}', " +
                "password='${password.password}', " +
                "notes='${password.notes}' " +
                "WHERE id=${password.id}"
        try {
            database.execSQL(sql) // Executa o comando SQL
            Toast.makeText(context, "Senha atualizada!", Toast.LENGTH_SHORT).show()
            return true
        } catch (e: SQLException) {
            Toast.makeText(context, "Erro ao atualizar senha: ${e.message}", Toast.LENGTH_SHORT).show()
            return false
        }
    }

    // Metodo get - Agora busca uma senha específica do banco de dados
    fun get(id: Int): Password? { // Retorna Password? porque pode não encontrar
        val sql = "SELECT * FROM passwords WHERE id=$id"
        var cursor: Cursor? = null

        try {
            cursor = database.rawQuery(sql, null)
            if (cursor.moveToFirst()) { // moveToFirst para ir para o primeiro resultado
                val name = cursor.getString(1)
                val login = cursor.getString(2)
                val password = cursor.getString(3)
                val notes = cursor.getString(4)
                return Password(id, name, login, password, notes)
            }
        } catch (e: SQLException) {
            Toast.makeText(context, "Erro ao buscar senha: ${e.message}", Toast.LENGTH_SHORT).show()
        } finally {
            cursor?.close()
        }
        return null // Retorna null se não encontrar ou houver erro
    }
}