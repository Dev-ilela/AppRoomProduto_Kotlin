package br.unisanta.approomproduto.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.unisanta.approomproduto.R
import br.unisanta.approomproduto.model.Product

class ProductAdapter(
    private val produtos: List<Product>,
    private val onDeleteClick: (Product) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nomeProduct: TextView = itemView.findViewById(R.id.txv_nome_item)
        val precoProduct: TextView = itemView.findViewById(R.id.txv_preco_item)
        val descricaoProduct: TextView = itemView.findViewById(R.id.txv_descricao)
        val btnDeletar: ImageButton = itemView.findViewById(R.id.btnDeletar)
        val btnEditar: ImageButton = itemView.findViewById(R.id.btnAtualizar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_lista, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = produtos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val produto = produtos[position]
        holder.nomeProduct.text = produto.name
        holder.precoProduct.text = "R$ ${produto.price}"
        holder.descricaoProduct.text = produto.description

        holder.btnDeletar.setOnClickListener {
            onDeleteClick(produto)
        }
    }
}