package com.example.moviequest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class RegisterViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: RegisterViewModel

    @Before
    fun setup() {
        viewModel = RegisterViewModel()
    }

    // Espacios en blanco

    @Test
    fun `test espacios en blanco Nom`() {
        // Observamos cambios en el estado del formulario
        val states = mutableListOf<RegisterFormState>()
        viewModel.formState.observeForever { states.add(it) }

        // Caso 1: cadena vacía
        viewModel.onNameChanged("")
        assertFalse(states.last().isValid)
        assertEquals("El nom no put estar buit", states.last().nameError)

        // Caso 2: cadena con espacios solo
        viewModel.onNameChanged("   ")
        assertFalse(states.last().isValid)
        assertEquals("El nom no put estar buit", states.last().nameError)

        // Caso 3: cadena válida
        viewModel.onNameChanged("Juan")
        assertNull(states.last().nameError)
    }

    @Test
    fun `test espacios en blanco Cognoms`() {
        val states = mutableListOf<RegisterFormState>()
        viewModel.formState.observeForever { states.add(it) }

        // Caso 1: cadena vacía
        viewModel.onSurnameChanged("")
        assertFalse(states.last().isValid)
        assertEquals("Els Cognoms no poden estar buits", states.last().surnameError)

        // Caso 2: cadena con espacios solo
        viewModel.onSurnameChanged("   ")
        assertFalse(states.last().isValid)
        assertEquals("Els Cognoms no poden estar buits", states.last().surnameError)
    }

    @Test
    fun `test espacios en blanco Data naixement`() {
        val states = mutableListOf<RegisterFormState>()
        viewModel.formState.observeForever { states.add(it) }

        // Caso 1: cadena vacía
        viewModel.onBirthdateChanged("")
        assertFalse(states.last().isValid)
        assertEquals("La Data naixement no pot estar buida", states.last().birthdateError)

        // Caso 2: cadena con espacios solo
        viewModel.onBirthdateChanged("   ")
        assertFalse(states.last().isValid)
        assertEquals("La Data naixement no pot estar buida", states.last().birthdateError)
    }

    @Test
    fun `test espacios en blanco Telèfon`() {
        val states = mutableListOf<RegisterFormState>()
        viewModel.formState.observeForever { states.add(it) }

        // Caso 1: cadena vacía
        viewModel.onPhoneChanged("")
        assertFalse(states.last().isValid)
        assertEquals("El Telèfon no pot estar buit", states.last().phoneError)

        // Caso 2: cadena con espacios solo
        viewModel.onPhoneChanged("   ")
        assertFalse(states.last().isValid)
        assertEquals("El Telèfon no pot estar buit", states.last().phoneError)
    }

    @Test
    fun `test espacios en blanco Nom d'usuari`() {
        // Test específico para userNoBlankSpaces
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

        // Test para el estado del formulario
        val states = mutableListOf<RegisterFormState>()
        viewModel.formState.observeForever { states.add(it) }

        // Verificamos el último estado
        assertFalse(states.last().isValid)
        assertNull(states.last().usernameError)
    }

    @Test
    fun `test espacios en blanco Gmail`() {
        val states = mutableListOf<RegisterFormState>()
        viewModel.formState.observeForever { states.add(it) }

        // Caso 1: cadena vacía
        viewModel.onEmailChanged("")
        assertFalse(states.last().isValid)
        assertEquals("El Gmail no pot estar buit", states.last().emailError)

        // Caso 2: cadena con espacios solo
        viewModel.onEmailChanged("   ")
        assertFalse(states.last().isValid)
        assertEquals("El Gmail no pot estar buit", states.last().emailError)
    }

    @Test
    fun `test espacios en blanco Contrasenya`() {
        val states = mutableListOf<RegisterFormState>()
        viewModel.formState.observeForever { states.add(it) }

        // Caso 1: cadena vacía
        viewModel.onPasswordChanged("")
        assertFalse(states.last().isValid)
        assertEquals("La Contrasenya no pot estar buida", states.last().passwordError)

        // Caso 2: cadena con espacios solo
        viewModel.onPasswordChanged("   ")
        assertFalse(states.last().isValid)
        assertEquals("La Contrasenya no pot estar buida", states.last().passwordError)
    }

    @Test
    fun `test espacios en blanco Confirmar contrasenya`() {
        val states = mutableListOf<RegisterFormState>()
        viewModel.formState.observeForever { states.add(it) }

        // Caso 1: cadena vacía
        viewModel.onConfirmPasswordChanged("")
        assertFalse(states.last().isValid)
        assertEquals("La Confirmar contrasenya no pot estar buida", states.last().confirmPasswordError)

        // Caso 2: cadena con espacios solo
        viewModel.onConfirmPasswordChanged("   ")
        assertFalse(states.last().isValid)
        assertEquals("La Confirmar contrasenya no pot estar buida", states.last().confirmPasswordError)
    }

    // Nombres ofensivos

    @Test
    fun `test validació nom ofensius`() {
        val states = mutableListOf<RegisterFormState>()
        viewModel.formState.observeForever { states.add(it) }

        // Caso válido
        viewModel.onUsernameChanged("usuario123")
        assertNull(states.last().usernameError)

        // Caso inválido - nombre ofensivo
        viewModel.onUsernameChanged("usuario_tonto")
        assertEquals("El nom d'usuari no pot ser ofensiu", states.last().usernameError)
        assertFalse(states.last().isValid)

        // Caso inválido - otro nombre ofensivo
        viewModel.onUsernameChanged("IDIOTA_user")
        assertEquals("El nom d'usuari no pot ser ofensiu", states.last().usernameError)
        assertFalse(states.last().isValid)
    }

    //Minimo 1 palabra

    @Test
    fun `test validació mínim una paraula`() {
        val states = mutableListOf<RegisterFormState>()
        viewModel.formState.observeForever { states.add(it) }

        // Caso inválido - sin espacio entre palabras
        viewModel.onSurnameChanged("Garcia")
        assertEquals("El cognom ha de tenir com a mínim una paraula", states.last().surnameError)
        assertFalse(states.last().isValid)

        // Caso válido - con espacio entre palabras
        viewModel.onSurnameChanged("Garcia Martinez")
        assertNull(states.last().surnameError)
    }

    // Formato fecha

    @Test
    fun `test validació format data`() {
        val states = mutableListOf<RegisterFormState>()
        viewModel.formState.observeForever { states.add(it) }

        // Caso inválido - formato incorrecto
        viewModel.onBirthdateChanged("2000-01-01")
        assertEquals("La data ha de ser en format (dd/mm/yyyy)", states.last().birthdateError)
        assertFalse(states.last().isValid)

        // Caso inválido - formato incorrecto
        viewModel.onBirthdateChanged("01-01-2000")
        assertEquals("La data ha de ser en format (dd/mm/yyyy)", states.last().birthdateError)
        assertFalse(states.last().isValid)

        // Caso válido
        viewModel.onBirthdateChanged("01/01/2000")
        assertNull(states.last().birthdateError)
    }

    @Test
    fun `test validació data futura`() {
        val states = mutableListOf<RegisterFormState>()
        viewModel.formState.observeForever { states.add(it) }

        // Obtenemos fecha futura
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.YEAR, 1)
        val futureDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(calendar.time)

        // Caso inválido - fecha futura
        viewModel.onBirthdateChanged(futureDate)
        assertEquals("La data és incorrecta", states.last().birthdateError)
        assertFalse(states.last().isValid)

        // Caso válido - fecha pasada
        calendar.add(Calendar.YEAR, -2) // Restamos 2 años para asegurar fecha pasada
        val pastDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(calendar.time)
        viewModel.onBirthdateChanged(pastDate)
        assertNull(states.last().birthdateError)
    }

    //Validación correo

    @Test
    fun `test validació format Gmail`() {
        val states = mutableListOf<RegisterFormState>()
        viewModel.formState.observeForever { states.add(it) }

        // Caso inválido - sin @
        viewModel.onEmailChanged("correogmail.com")
        assertEquals("El format del correu és incorrecte", states.last().emailError)
        assertFalse(states.last().isValid)

        // Caso inválido - sin dominio
        viewModel.onEmailChanged("correo@")
        assertEquals("El format del correu és incorrecte", states.last().emailError)
        assertFalse(states.last().isValid)

        // Caso inválido - sin punto
        viewModel.onEmailChanged("correo@gmailcom")
        assertEquals("El format del correu és incorrecte", states.last().emailError)
        assertFalse(states.last().isValid)

        // Caso válido
        viewModel.onEmailChanged("correo@gmail.com")
        assertNull(states.last().emailError)
    }

    // Validación telefono

    @Test
    fun `test validació longitud núm telèfon`() {
        val states = mutableListOf<RegisterFormState>()
        viewModel.formState.observeForever { states.add(it) }

        // Caso inválido - menos de 9 dígitos
        viewModel.onPhoneChanged("12345678")
        assertEquals("El número de telèfon ha de tenir 9 dígits", states.last().phoneError)
        assertFalse(states.last().isValid)

        // Caso inválido - más de 9 dígitos
        viewModel.onPhoneChanged("1234567890")
        assertEquals("El número de telèfon ha de tenir 9 dígits", states.last().phoneError)
        assertFalse(states.last().isValid)

        // Caso inválido - con caracteres no numéricos
        viewModel.onPhoneChanged("12345678a")
        assertEquals("El número de telèfon ha de tenir 9 dígits", states.last().phoneError)
        assertFalse(states.last().isValid)

        // Caso válido
        viewModel.onPhoneChanged("123456789")
        assertNull(states.last().phoneError)
    }

    // Validación contraseña

    @Test
    fun `test validació format contrasenya`() {
        val states = mutableListOf<RegisterFormState>()
        viewModel.formState.observeForever { states.add(it) }

        // Caso inválido - sin mayúscula
        viewModel.onPasswordChanged("contraseña123!")
        assertEquals("El format de la contrasenya ha de complir amb els següents requisits.", states.last().passwordError)
        assertFalse(states.last().isValid)

        // Caso inválido - sin minúscula
        viewModel.onPasswordChanged("CONTRASEÑA123!")
        assertEquals("El format de la contrasenya ha de complir amb els següents requisits.", states.last().passwordError)
        assertFalse(states.last().isValid)

        // Caso inválido - sin número
        viewModel.onPasswordChanged("Contraseña!")
        assertEquals("El format de la contrasenya ha de complir amb els següents requisits.", states.last().passwordError)
        assertFalse(states.last().isValid)

        // Caso inválido - sin carácter especial
        viewModel.onPasswordChanged("Contrasenya123")
        assertEquals("El format de la contrasenya ha de complir amb els següents requisits.", states.last().passwordError)
        assertFalse(states.last().isValid)

        // Caso inválido - menos de 10 caracteres
        viewModel.onPasswordChanged("Contra1!")
        assertEquals("El format de la contrasenya ha de complir amb els següents requisits.", states.last().passwordError)
        assertFalse(states.last().isValid)

        // Caso válido
        viewModel.onPasswordChanged("Contrasenya123!")
        assertNull(states.last().passwordError)
    }

    @Test
    fun `test validació confirmació contrasenya`() {
        val states = mutableListOf<RegisterFormState>()
        viewModel.formState.observeForever { states.add(it) }

        // Establecemos una contraseña válida
        viewModel.onPasswordChanged("Contrasenya123!")

        // Caso inválido - confirmación no coincide
        viewModel.onConfirmPasswordChanged("Contrasenya456!")
        assertEquals("La contrasenya i la confirmació han de ser iguals", states.last().confirmPasswordError)
        assertFalse(states.last().isValid)

        // Caso válido - confirmación coincide
        viewModel.onConfirmPasswordChanged("Contrasenya123!")
        assertNull(states.last().confirmPasswordError)
    }

    //Valicación formulario entero

    @Test
    fun `test validació formulari complet`() {
        val states = mutableListOf<RegisterFormState>()
        viewModel.formState.observeForever { states.add(it) }

        // Introducimos todos los campos válidos
        viewModel.onNameChanged("Pere")
        viewModel.onSurnameChanged("Garcia Martinez")
        viewModel.onUsernameChanged("pere_garcia")
        viewModel.onBirthdateChanged("01/01/2000")
        viewModel.onPhoneChanged("123456789")
        viewModel.onEmailChanged("pere@gmail.com")
        viewModel.onPasswordChanged("Contrasenya123!")
        viewModel.onConfirmPasswordChanged("Contrasenya123!")

        // Verificamos que el formulario es válido
        assertTrue(states.last().isValid)
        assertNull(states.last().nameError)
        assertNull(states.last().surnameError)
        assertNull(states.last().usernameError)
        assertNull(states.last().birthdateError)
        assertNull(states.last().phoneError)
        assertNull(states.last().emailError)
        assertNull(states.last().passwordError)
        assertNull(states.last().confirmPasswordError)
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