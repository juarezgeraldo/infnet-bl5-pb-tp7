package com.example.calculosalario

import android.content.Intent
import android.icu.text.DecimalFormat
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class ResultadoSalario : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resultado_salario)

        val txtSalarioLiquido = this.findViewById<TextView>(R.id.txtSalarioLiquido)
        val txtDesconto = this.findViewById<TextView>(R.id.txtDesconto)
        val txtPercentual = this.findViewById<TextView>(R.id.txtPercentual)
        val btnVoltar = this.findViewById<Button>(R.id.btnVoltar)

        val moeda = DecimalFormat.getCurrencyInstance(Locale("pt", "BR"))

        val salarioLiquido = intent.getStringExtra("salarioLiquido")
        val desconto = intent.getStringExtra("desconto")
        val percentual = intent.getStringExtra("percentual")

        val salarioLiquidoString = salarioLiquido?.let { moeda.format(it.toDouble()).replace(moeda.currency.currencyCode, moeda.currency.currencyCode + " ") }

        val descontoString = desconto?.let { moeda.format(it.toDouble()).replace(moeda.currency.currencyCode, moeda.currency.currencyCode + " ") }
        val percentualString = percentual?.let { DecimalFormat("0.## %").format(it.toDouble()) }

        txtSalarioLiquido.text = salarioLiquidoString
        txtDesconto.text = descontoString
        txtPercentual.text = percentualString

        btnVoltar.setOnClickListener(){
            val profileIntent = Intent(this, MainActivity::class.java)
            startActivity(profileIntent)
        }
    }
}