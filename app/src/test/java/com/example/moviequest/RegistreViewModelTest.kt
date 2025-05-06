package com.example.moviequest


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RegisterViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: RegisterViewModel

    @Before
    fun setup() {
        viewModel = RegisterViewModel()
    }

    @Test
    fun `username no puede ser en blanco`() {
        // Observamos cambios
        val results = mutableListOf<Boolean>()
        viewModel.noBlankSpaces.observeForever { results.add(it) }

        // Caso 1: cadena vacía
        viewModel.onUsernameChanged("")
        assertEquals(1, results.size)
        assertFalse(results[0])

        // Caso 2: cadena con espacios solo
        viewModel.onUsernameChanged("   ")
        assertEquals(2, results.size)
        assertFalse(results[1])

        // Caso 3: cadena válida
        viewModel.onUsernameChanged("hola")
        assertEquals(3, results.size)
        assertTrue(results[2])
    }
    /*
    @Test
    fun `la data no pot estar vuida`() {
        val results = mutableListOf<Boolean>()
        viewModel.noBlankSpaces.observeForever { results.add(it) }

        // Caso 1: cadena vacía
        viewModel.onUsernameChanged("")
        assertEquals(1, results.size)
        assertFalse(results[0])

        // Caso 2: cadena con espacios solo
        viewModel.onUsernameChanged("   ")
        assertEquals(2, results.size)
        assertFalse(results[1])

        // Caso 3: cadena válida
        viewModel.onUsernameChanged("hola")
        assertEquals(3, results.size)
        assertTrue(results[2])
    }
*/
}