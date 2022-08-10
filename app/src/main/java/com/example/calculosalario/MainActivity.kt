package com.example.calculosalario

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.calculosalario.model.Salario
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {
    var salario = Salario()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val txtSalarioBruto = this.findViewById<EditText>(R.id.txtSalarioBruto)
        val txtDependentes = this.findViewById<EditText>(R.id.txtDependentes)
        val txtPensao = this.findViewById<EditText>(R.id.txtPensao)
        val txtPlanoSaude = this.findViewById<EditText>(R.id.txtPlanoSaude)
        val txtOutros = this.findViewById<EditText>(R.id.txtOutros)

        val btnCalcular = this.findViewById<Button>(R.id.btnCaluclar)
        val btnApagar = this.findViewById<Button>(R.id.btnApagar)

        btnCalcular.setOnClickListener() {
            if (!verificaValorOk(txtSalarioBruto.text.toString())) {
                Toast.makeText(
                    this,
                    "É necessário informar o valor do salário bruto.",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                salario = Salario(
                    txtSalarioBruto.text.toString().toDouble(),
                    valorInt(txtDependentes.text.toString()),
                    valorDouble(txtPensao.text.toString()),
                    valorDouble(txtPlanoSaude.text.toString()),
                    valorDouble(txtOutros.text.toString())
                )

                salario.calculaSalario(salario)

                gravaRegistro()

                val resultadoIntent = Intent(this, ResultadoSalario::class.java)
                resultadoIntent.putExtra("salarioLiquido", salario.getSalarioLiquido())
                resultadoIntent.putExtra("desconto", salario.getDesconto())
                resultadoIntent.putExtra("percentual", salario.getPercentual())
                startActivity(resultadoIntent)

            }
        }

    }

    private fun gravaRegistro() {
        val fos: FileOutputStream = this.openFileOutput("resultados.txt", MODE_PRIVATE)
        fos.write(salario.montaRegistro().toByteArray())
        fos.close()
    }

    private fun verificaValorOk(valor: String): Boolean {
        if (valor.length <= 0 || valor.toDouble().equals(0.0)) {
            return false
        }
        return true
    }

    private fun valorDouble(valor: String): Double {
        if (valor.length <= 0) {
            return 0.0
        }
        return valor.toDouble()
    }

    private fun valorInt(valor: String): Int {
        if (valor.length <= 0) {
            return 0
        }
        return valor.toInt()
    }

//    private fun isExternalStorageWritable(): Boolean {
//        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
//    }
//
//    private fun isExternalStorageReadable(): Boolean {
//        return Environment.getExternalStorageState() in
//                setOf(Environment.MEDIA_MOUNTED, Environment.MEDIA_MOUNTED_READ_ONLY)
//    }
}