package com.example.aplicacion01

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FRecyclerViewAdaptadorNombreCedula(
    private val contexto : GRecyclerView,
    private val listaEntrenador : List<BEntrenador>,
    private val recyclerView : RecyclerView
) : RecyclerView.Adapter<FRecyclerViewAdaptadorNombreCedula.MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val nombreTextView: TextView
        val cedulaTextView: TextView
        val likesTextView: TextView
        val accionButton: Button
        var numeroLikes = 0

        init{
            nombreTextView = view.findViewById(R.id.tv_nombre)
            cedulaTextView = view.findViewById(R.id.tv_cedula)
            accionButton = view.findViewById(R.id.btn_dar_like)
            likesTextView = view.findViewById(R.id.tv_likes)
            accionButton.setOnClickListener{
                this.anadirLike()
            }
        }

        fun anadirLike(){

        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder { //Seteamos el layout o viewholder
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.recycler_view_vista, //Definimos la vista de nuestro recyclerview
                parent,
                false
            )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val entrenador = listaEntrenador[position]
        holder.nombreTextView.text = entrenador.nombre
        holder.cedulaTextView.text = entrenador.descripcion
        holder.accionButton.text ="Like ${entrenador.nombre}"
        holder.nombreTextView.text = "0"
    }

    override fun getItemCount(): Int { //Devolvemos el tamaño del arreglo
        return listaEntrenador.size
    }

}