package com.example.calculosalario

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.calculosalario.model.Salario
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    var salario = Salario()

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.calculosalario", appContext.packageName)
    }
    @Test
    fun calculaSalarioTest(){
        salario = Salario(
            2850.0,
            2,
            0.0,
            125.0,
            0.0)

        val result = salario.calculaSalario(salario)

        if (result) {
            Log.i( "Registro a ser gravado: ", salario.montaRegistro())
        }else{
            Log.i( "Erro calculo salário: ", result.toString())
        }

    }
}