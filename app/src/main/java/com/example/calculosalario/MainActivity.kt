package com.example.calculosalario

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.calculosalario.model.Salario
import java.io.File

class MainActivity : AppCompatActivity() {
    var salario = Salario()

    val EXTERNAL_STORAGE_PERMISSION_CODE = 100

    val filename = "resultado.txt"

    @SuppressLint("SdCardPath")
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

        btnApagar.setOnClickListener() {
            val f = File("/data/user/0/com.example.calculosalario/files/" + filename)
            if (f.exists()) {
                if (f.delete()) {
                    Toast.makeText(this, "Arquivo excluído com sucesso", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "Arquivo NÃO pode ser excluído", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, "Arquivo NÃO existe, portanto não foi excluído", Toast.LENGTH_LONG).show()
            }
        }

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
        if (this.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            this.requestPermissions(
                arrayOf(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ),
                EXTERNAL_STORAGE_PERMISSION_CODE
            )
        } else {
            if (isExternalStorageWritable()) {
                val fileOutputStream = openFileOutput(filename, MODE_APPEND)
                fileOutputStream.write(salario.montaRegistro().toByteArray())
                fileOutputStream.close()
            }
        }
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

    private fun isExternalStorageWritable(): Boolean {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == EXTERNAL_STORAGE_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(
                    this,
                    "Acesso de gravação na memório externa concedida",
                    Toast.LENGTH_LONG
                ).show()
                if (isExternalStorageWritable()) {
                    if (isExternalStorageWritable()) {
                        val fileOutputStream = openFileOutput(filename, MODE_APPEND)
                        fileOutputStream.write(salario.montaRegistro().toByteArray())
                        fileOutputStream.close()
                    }
                } else {
                    Toast.makeText(this, "Acesso de gravação na memório externa", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }

    }
}