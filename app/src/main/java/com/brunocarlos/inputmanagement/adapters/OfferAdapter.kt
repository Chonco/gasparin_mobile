package com.brunocarlos.inputmanagement.adapters

import android.app.Activity
import android.content.Intent
import android.view.*
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.brunocarlos.inputmanagement.R
import com.brunocarlos.inputmanagement.models.Offer
import com.brunocarlos.inputmanagement.shared.OfferDetailView
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

class OfferAdapter(offerList:List<Offer>,
                   layout: Int,
                   activity: Activity
): RecyclerView.Adapter<OfferAdapter.OfferViewHolder>(){

    private val offerList : List<Offer>
    private val itemCardLayout : Int
    private val activity: Activity

    init {
        this.offerList = offerList
        this.itemCardLayout = layout
        this.activity = activity
    }

    interface onItemClickListener{
        fun onItemClick(position: Int, offerList: List<Offer>)

    }


    inner class OfferViewHolder (view : View): RecyclerView.ViewHolder(view),
        View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener, onItemClickListener{

        private val offerName : TextView
        private val producerName : TextView
        private val offerPrice : TextView
        private val offerImg : ImageView
        private val offerTypeContainer : LinearLayout

        init {
            offerName = view.findViewById(R.id.offerName)
            producerName = view.findViewById(R.id.producerName)
            offerPrice = view.findViewById(R.id.offerPrice)
            offerImg = view.findViewById(R.id.imgOffer)
            offerTypeContainer = view.findViewById(R.id.offer_food_types_container)
            view.setOnCreateContextMenuListener(this)

        }

        fun render(offerModel : Offer){
            offerImg.setImageBitmap(offerModel.getLogoAsBitmap())
            offerName.text = offerModel.name
            producerName.text = offerModel.sellerName
            try {
                val symbols = DecimalFormatSymbols()
                symbols.decimalSeparator = '.'
                symbols.groupingSeparator = '\\'
                val decimalFormat = DecimalFormat("$ #,###.00", symbols)
                offerPrice.text = decimalFormat.format(offerModel.price)
            }catch (e:Exception){}

            val layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            );
            layoutParams.marginEnd = 5

            for (i in 0 until offerModel.foodType.size){
                val textView = TextView(activity)
                textView.text = offerModel.foodType[i]
                textView.layoutParams = layoutParams
                textView.setBackgroundResource(R.drawable.pill_offer_bg)
                offerTypeContainer.addView(textView)
            }
            itemView.setOnClickListener{
                onItemClick(adapterPosition, offerList)
            }

        }

        override fun onCreateContextMenu(
            contextMenu: ContextMenu,
            view: View,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            val offerSelected = offerList[this.adapterPosition]

            contextMenu.setHeaderTitle(offerSelected.name)
            val inflater = activity.menuInflater
            inflater.inflate(R.menu.item_offer_context_menu, contextMenu)

            for (i in 0 until contextMenu.size()){
                contextMenu.getItem(i).setOnMenuItemClickListener(this)
            }
        }

        override fun onMenuItemClick(menuItem: MenuItem): Boolean {
            return when (menuItem.itemId){
                R.id.accept_Offer -> {
                    true
                }
                else -> false


            }
        }

        override fun onItemClick(position: Int, offerList: List<Offer>) {
            val intent = Intent(activity, OfferDetailView::class.java)
            intent.putExtra("offer", offerList[this.adapterPosition])
            activity.startActivity(intent)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferViewHolder {
        val layoutInflater = LayoutInflater.from(activity).inflate(itemCardLayout,parent,false)
        return OfferViewHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder: OfferViewHolder, position: Int) {
        holder.render(offerList[position])
    }

    override fun getItemCount(): Int = offerList.size

}