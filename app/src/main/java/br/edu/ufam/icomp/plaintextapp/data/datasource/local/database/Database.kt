package br.edu.ufam.icomp.plaintextapp.database // <-- SEU PACOTE

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class Database(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    // Companion object para constantes estáticas
    companion object {
        const val DATABASE_VERSION = 4 // Versão do banco de dados. Incremente para forçar onUpgrade.
        const val DATABASE_NAME = "PlainText.db" // Nome do arquivo do banco de dados

        // Comandos SQL para a tabela de senhas
        private const val SQL_CREATE_PASS = "CREATE TABLE passwords (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "login TEXT, " +
                "password TEXT, " +
                "notes TEXT)"

        private const val SQL_POPULATE_PASS = "INSERT INTO passwords VALUES " +
                "(NULL, 'GMail', 'dovahkiin', 'Teste123', 'Nota de Teste')"

        private const val SQL_DELETE_PASS = "DROP TABLE IF EXISTS passwords"
    }

    // Método executado quando o banco de dados é criado pela primeira vez
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_PASS) // Cria a tabela de senhas
        db.execSQL(SQL_POPULATE_PASS) // Popula a tabela com um dado inicial
    }

    // Método executado quando a versão do banco de dados é atualizada
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // No exemplo, apagamos a tabela e a criamos novamente
        db.execSQL(SQL_DELETE_PASS)
        onCreate(db)
    }
}