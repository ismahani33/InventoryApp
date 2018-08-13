package com.ratajczykdev.inventoryapp.catalog

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.ratajczykdev.inventoryapp.R
import com.ratajczykdev.inventoryapp.database.Product
import com.ratajczykdev.inventoryapp.detailandedit.ProductDetailActivity
import com.squareup.picasso.Picasso
import java.util.*

/**
 * Provides data from list of [Product] to [RecyclerView]
 *
 * @author Mikolaj Ratajczyk <mikolaj.ratajczyk@gmail.com>
 */
class ProductListRecyclerAdapter(val context: Context) : RecyclerView.Adapter<ProductListRecyclerAdapter.ProductViewHolder>() {

    companion object {
        const val DATA_SELECTED_PRODUCT_ID = "DATA_SELECTED_PRODUCT_ID"
    }

    private val layoutInflater = LayoutInflater.from(context)
    var productList: List<Product> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val itemView = layoutInflater.inflate(R.layout.product_list_item, parent, false)
        return ProductViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val currentProduct = productList[position]
        holder.textName.text = currentProduct.name
        holder.textPrice.text = String.format(Locale.US, "%.2f", currentProduct.price)
        holder.textQuantity.text = currentProduct.quantity.toString()
        setProductPhotoFromUri(holder, currentProduct)

        holder.listItemRootView.setOnClickListener {
            val intent = Intent(context, ProductDetailActivity::class.java)
            intent.putExtra(DATA_SELECTED_PRODUCT_ID, currentProduct.id.toString())
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    /**
     * Helper method, it loads product photo to ImageView
     */
    private fun setProductPhotoFromUri(holder: ProductViewHolder, currentProduct: Product) {
        Picasso.get()
                .load(currentProduct.photoUri)
                .placeholder(R.drawable.product_list_item_placeholder)
                .error(R.drawable.ic_error)
                .fit()
                .into(holder.imagePhoto)
    }


    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal val textName: TextView = itemView.findViewById(R.id.text_name)
        internal val imagePhoto: ImageView = itemView.findViewById(R.id.image_photo)
        internal val textPrice: TextView = itemView.findViewById(R.id.text_price)
        internal val textQuantity: TextView = itemView.findViewById(R.id.text_quantity)
        internal val listItemRootView: View = itemView.findViewById(R.id.product_list_item_root_linearlayout)
    }

}