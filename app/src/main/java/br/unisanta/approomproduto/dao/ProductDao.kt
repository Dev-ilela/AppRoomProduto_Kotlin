package br.unisanta.approomproduto.dao

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Update
import br.unisanta.approomproduto.model.Product
@Dao
interface ProductDao {
    companion object{
        private val produtos = mutableListOf<Product>()

        fun salvar(id: Int,nome: String,preco: Double,descricao: String?):String{
            val produto = Product(id, nome, preco, descricao)
            produtos.add(produto)
            return "Produto Salvo"
        }

        fun obter():List<Product>{
            return produtos
        }
    }
    @Query("SELECT * FROM product")
    suspend fun getAll(): List<Product>
    @Insert
    suspend fun insertAll(vararg products: Product)
    @Query("SELECT * FROM product WHERE name LIKE :nome")
    suspend fun buscarPorNome(nome: String): List<Product>
    @Query("DELETE FROM product")
    suspend fun deleteAll()
    @Delete
    suspend fun delete(product: Product)
    @Update
    suspend fun update(product: Product)
}