package com.example.calculosalario.model

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import android.icu.util.TimeZone
import java.util.*
import java.util.TimeZone.*

@SuppressLint("SimpleDateFormat")
class Salario {
    var salarioBruto: Double = 0.0
    var dependentes: Int = 0
    var pensao: Double = 0.0
    var planoSaude: Double = 0.0
    var outros: Double = 0.0
    var salarioLiquido: Double = 0.0
    var desconto: Double = 0.0
    var percentual: Double = 0.0
    var inss: Double = 0.0
    var irpf: Double = 0.0
    var dataAtual: String = ""
    var horaAtual: String = ""

    val dateFormat = SimpleDateFormat("dd/MM/yyyy")
    val horaFormat = SimpleDateFormat("HH:mm:ss")

    val format: SimpleDateFormat = SimpleDateFormat("HH:mm:ss")

    constructor(
        salarioBruto: Double,
        dependentes: Int,
        pensao: Double,
        planoSaude: Double,
        outros: Double,
    ) {
        this.salarioBruto = salarioBruto
        this.dependentes = dependentes
        this.pensao = pensao
        this.planoSaude = planoSaude
        this.outros = outros
    }
    constructor()

    fun getSalarioLiquido(): String{
        return this.salarioLiquido.toString()
    }
    fun getDesconto(): String{
        return this.desconto.toString()
    }
    fun getPercentual(): String{
        return this.percentual.toString()
    }

    override fun toString(): String {
        return "Salario(salarioBruto=$salarioBruto,\ndependentes=$dependentes,\npensao=$pensao,\nplanoSaude=$planoSaude,\noutros=$outros,\nsalarioLiquido=$salarioLiquido,\ndesconto=$desconto,\npercentual=$percentual,\ninss=$inss,\nirpf=$irpf,\ndataAtual='$dataAtual',\nhoraAtual='$horaAtual')"
    }

    fun calculaSalario(salario: Salario): Boolean {
        this.inss = calculaInss(this.salarioBruto)
        this.irpf = calculaIrpf(this.salarioBruto)
        this.desconto = this.inss +
                    this.irpf +
                    this.pensao +
                    (this.dependentes * 189.59)
        this.salarioLiquido = this.salarioBruto - this.desconto
        this.percentual = this.desconto / this.salarioBruto
        return true
    }

    fun montaRegistro(): String {
        horaFormat.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"))

        this.dataAtual = dateFormat.format(Date())
        this.horaAtual = horaFormat.format(Date())
        return this.toString() + "\n"
    }

    private fun calculaInss(valor: Double): Double {
        var inss: Double = 0.0
        if (valor <= 1659.38) {
            inss = valor * 0.08
        } else if (valor > 1659.38 && valor <= 2765.66) {
            inss = valor * 0.09
        } else if (valor > 2765.66 && valor <= 5531.31) {
            inss = valor * 0.11
        } else {
            inss = 608.44
        }
        return inss
    }

    private fun calculaIrpf(valor: Double): Double {
        var irpf: Double = 0.0
        if (valor <= 1903.98) {
            irpf = 0.0
        } else if (valor > 1903.98 && valor <= 2826.65) {
            irpf = valor * 0.075
        } else if (valor > 2826.65 && valor <= 3751.05) {
            irpf = valor * 0.15
        } else if (valor > 3751.05 && valor <= 4664.68) {
            irpf = valor * 0.225
        } else {
            irpf = valor * 0.275
        }
        return irpf
    }
}