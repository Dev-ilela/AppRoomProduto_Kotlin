package br.unisanta.approomproduto.view

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import br.unisanta.approomproduto.adapter.ProductAdapter
import br.unisanta.approomproduto.dao.ProductDao
import br.unisanta.approomproduto.databinding.ActivityMainBinding
import br.unisanta.approomproduto.db.AppDatabase
import br.unisanta.approomproduto.model.Product
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var db: AppDatabase
    private lateinit var productDao: ProductDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configuração inicial do RecyclerView
        binding.listaItens.layoutManager = LinearLayoutManager(this)

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-name"
        )
            .fallbackToDestructiveMigration()
            .build()

        productDao = db.productDao()

        // Inserção de produto
        binding.btnInserirProduto.setOnClickListener {
            val name = binding.editProductName.text.toString()
            val priceText = binding.editProductPrice.text.toString()
            val description = binding.editProductDescription.text.toString()

            if (name.isNotEmpty() && priceText.isNotEmpty()) {
                val price = priceText.toDoubleOrNull()
                if (price == null || price <= 0.0) {
                    binding.editProductPrice.error = "Informe um preço válido"
                } else {
                    val product = Product(
                        name = name,
                        price = price,
                        description = description
                    )

                    lifecycleScope.launch {
                        productDao.insertAll(product)
                        Log.i("TAG", "Produto inserido: $name R$$price")
                        Toast.makeText(
                            this@MainActivity, "Produto inserido!",
                            Toast.LENGTH_SHORT
                        ).show()

                        carregarProdutos()
                    }

                    binding.editProductName.text.clear()
                    binding.editProductPrice.text.clear()
                    binding.editProductDescription.text.clear()
                    binding.editProductName.requestFocus()
                }
            } else {
                Toast.makeText(
                    this, "Preencha os campos obrigatórios!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        binding.btnListarProdutos.setOnClickListener {
            carregarProdutos()
        }

        binding.btnBusca.setOnClickListener {
            buscarProdutos()
        }

        binding.btnDeletarProdutos.setOnClickListener {
            lifecycleScope.launch {
                productDao.deleteAll()
                Toast.makeText(this@MainActivity, "Todos os produtos foram deletados", Toast.LENGTH_SHORT).show()
                carregarProdutos()
            }
        }
    }

    private fun carregarProdutos() {
        lifecycleScope.launch {
            val products = productDao.getAll()
            configurarAdapter(products)
        }
    }

    private fun buscarProdutos() {
        lifecycleScope.launch {
            val termoBusca = binding.edtBusca.text.toString()
            val products = productDao.buscarPorNome("%$termoBusca%")

            if (products.isNotEmpty()) {
                configurarAdapter(products)
                val valorTotal = products.sumOf { it.price }
                binding.txvResultado.text = "Total de produtos: ${products.size} | Valor total: R$ %.2f".format(valorTotal)
            } else {
                configurarAdapter(emptyList())
                binding.txvResultado.text = "Total de produtos: 0 | Valor total: R$ 0.00"
                Toast.makeText(this@MainActivity, "Nenhum produto encontrado.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Função auxiliar para evitar repetição de código ao configurar o Adapter
    private fun configurarAdapter(products: List<Product>) {
        if (products.isNotEmpty()) {
            binding.listaItens.adapter = ProductAdapter(products) { produtoParaDeletar ->
                lifecycleScope.launch {
                    productDao.delete(produtoParaDeletar)
                    Toast.makeText(this@MainActivity, "Produto deletado!", Toast.LENGTH_SHORT).show()
                    carregarProdutos()
                }
            }
        } else {
            binding.listaItens.adapter = ProductAdapter(emptyList()) {}
        }
    }
}