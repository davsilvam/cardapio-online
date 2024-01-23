package com.cardapioonline.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cardapioonline.databinding.OptionItemBinding
import com.cardapioonline.model.Option
import java.text.NumberFormat
import java.util.Currency

class OptionsAdapter(private val context: Context, private val optionsList: MutableList<Option>) :
    RecyclerView.Adapter<OptionsAdapter.OptionViewHolder>() {
    var onItemClickListener: ((Option) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OptionViewHolder {
        val binding = OptionItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return OptionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OptionViewHolder, position: Int) {
        val option = optionsList[position]

        holder.image.setImageResource(option.image)
        holder.image.contentDescription = "Imagem de ${option.name}"
        holder.name.text = option.name

        val format = NumberFormat.getCurrencyInstance()
        format.maximumFractionDigits = 2
        format.currency = Currency.getInstance("BRL")

        holder.price.text = "${format.format(option.price).replace(".", ",").replace("R$", "R$ ")}"
        holder.time.text = option.time.toString() + " minutos"

        holder.checkbox.isChecked = option.isChecked

        holder.checkbox.setOnCheckedChangeListener { _, isChecked ->
            option.isChecked = isChecked
            onItemClickListener?.invoke(option)
        }
    }

    override fun getItemCount() = optionsList.size

    inner class OptionViewHolder(binding: OptionItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val image = binding.imageOption
        val name = binding.textOptionName
        val checkbox = binding.checkboxOption
        val price = binding.textOptionPrice
        val time = binding.textOptionTime
    }
}