package com.example.aplicacion01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GRecyclerView : AppCompatActivity() {
    var totalLikes = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grecycler_view)
        val lista_entrenador = arrayListOf<BEntrenador>()
        lista_entrenador
            .add(
                BEntrenador(
                    "Carlos",
                    "1723476170"
                )
            )
        lista_entrenador
            .add(
                BEntrenador(
                    "Andres",
                    "1723476170"
                )
            )
        val recyclerViewEntrenador = findViewById<RecyclerView>(
            R.id.rv_entrenadores
        )
        inicializarRecyclerView(
            lista_entrenador,
            this,
            recyclerViewEntrenador
        )
    }
    fun inicializarRecyclerView(
        lista : List<BEntrenador>,
        actividad : GRecyclerView,
        recyclerView: RecyclerView
    ){
        val adaptador = FRecyclerViewAdaptadorNombreCedula(
            actividad,
            lista,
            recyclerView
        )

        recyclerView.adapter = adaptador
        recyclerView.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(actividad)
        adaptador.notifyDataSetChanged()
    }
    fun aumentarTotalLikes(){
        totalLikes = totalLikes + 1
        val textView = findViewById<TextView>(R.id.tv_total_likes)
        textView.text = totalLikes.toString()
    }

}