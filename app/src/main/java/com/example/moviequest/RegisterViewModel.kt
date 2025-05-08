package com.example.moviequest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.regex.Pattern

data class RegisterFormState(
    val isValid: Boolean = false,
    val usernameError: String? = null,
    val nameError: String? = null,
    val surnameError: String? = null,
    val birthdateError: String? = null,
    val phoneError: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null
)

class RegisterViewModel : ViewModel() {

    // LiveData para el estado del formulario
    private val _formState = MutableLiveData<RegisterFormState>()
    val formState: LiveData<RegisterFormState> = _formState

    // LiveData específicos para tests individuales
    private val _userNoBlankSpaces = MutableLiveData<Boolean>()
    val noBlankSpaces: LiveData<Boolean> = _userNoBlankSpaces

    private val _passwordsMatch = MutableLiveData<Boolean>()
    val passwordsMatch: LiveData<Boolean> = _passwordsMatch

    fun onPasswordChanged(password1: String, password2: String) {
        _passwordsMatch.value = password1 == password2
    }


    // Datos del formulario
    private var username: String = ""
    private var name: String = ""
    private var surname: String = ""
    private var birthdate: String = ""
    private var phone: String = ""
    private var email: String = ""
    private var password: String = ""
    private var confirmPassword: String = ""

    // Lista de términos ofensivos
    private val offensiveTerms = listOf("tonto", "idiota", "estupido", "imbecil")

    // Inicialización del estado del formulario
    init {
        _formState.value = RegisterFormState(isValid = false)
    }

    // Validación de username
    fun onUsernameChanged(username: String) {
        this.username = username
        _userNoBlankSpaces.value = username.isNotBlank()

        val currentState = _formState.value ?: RegisterFormState()

        when {
            username.isBlank() -> {
                _formState.value = currentState.copy(
                    usernameError = "El Nom d'usuari no pot estar buida",
                    isValid = false
                )
            }
            offensiveTerms.any { username.lowercase().contains(it) } -> {
                _formState.value = currentState.copy(
                    usernameError = "El nom d'usuari no pot ser ofensiu",
                    isValid = false
                )
            }
            else -> {
                _formState.value = currentState.copy(usernameError = null)
                validateForm()
            }
        }
    }

    // Validación de nombre
    fun onNameChanged(name: String) {
        this.name = name
        val currentState = _formState.value ?: RegisterFormState()

        if (name.isBlank()) {
            _formState.value = currentState.copy(
                nameError = "El nom no put estar buit",
                isValid = false
            )
        } else {
            _formState.value = currentState.copy(nameError = null)
            validateForm()
        }
    }

    // Validación de apellidos
    fun onSurnameChanged(surname: String) {
        this.surname = surname
        val currentState = _formState.value ?: RegisterFormState()

        when {
            surname.isBlank() -> {
                _formState.value = currentState.copy(
                    surnameError = "Els Cognoms no poden estar buits",
                    isValid = false
                )
            }
            !surname.trim().contains(" ") -> {
                _formState.value = currentState.copy(
                    surnameError = "El cognom ha de tenir com a mínim una paraula",
                    isValid = false
                )
            }
            else -> {
                _formState.value = currentState.copy(surnameError = null)
                validateForm()
            }
        }
    }

    // Validación de fecha de nacimiento
    fun onBirthdateChanged(birthdate: String) {
        this.birthdate = birthdate
        val currentState = _formState.value ?: RegisterFormState()

        when {
            birthdate.isBlank() -> {
                _formState.value = currentState.copy(
                    birthdateError = "La Data naixement no pot estar buida",
                    isValid = false
                )
            }
            !isValidDateFormat(birthdate) -> {
                _formState.value = currentState.copy(
                    birthdateError = "La data ha de ser en format (dd/mm/yyyy)",
                    isValid = false
                )
            }
            isFutureDate(birthdate) -> {
                _formState.value = currentState.copy(
                    birthdateError = "La data és incorrecta",
                    isValid = false
                )
            }
            else -> {
                _formState.value = currentState.copy(birthdateError = null)
                validateForm()
            }
        }
    }

    // Validación de teléfono
    fun onPhoneChanged(phone: String) {
        this.phone = phone
        val currentState = _formState.value ?: RegisterFormState()

        when {
            phone.isBlank() -> {
                _formState.value = currentState.copy(
                    phoneError = "El Telèfon no pot estar buit",
                    isValid = false
                )
            }
            !isValidPhoneNumber(phone) -> {
                _formState.value = currentState.copy(
                    phoneError = "El número de telèfon ha de tenir 9 dígits",
                    isValid = false
                )
            }
            else -> {
                _formState.value = currentState.copy(phoneError = null)
                validateForm()
            }
        }
    }

    // Validación de email
    fun onEmailChanged(email: String) {
        this.email = email
        val currentState = _formState.value ?: RegisterFormState()

        when {
            email.isBlank() -> {
                _formState.value = currentState.copy(
                    emailError = "El Gmail no pot estar buit",
                    isValid = false
                )
            }
            !isValidEmail(email) -> {
                _formState.value = currentState.copy(
                    emailError = "El format del correu és incorrecte",
                    isValid = false
                )
            }
            else -> {
                _formState.value = currentState.copy(emailError = null)
                validateForm()
            }
        }
    }

    // Validación de contraseña
    fun onPasswordChanged(password: String) {
        this.password = password
        val currentState = _formState.value ?: RegisterFormState()

        when {
            password.isBlank() -> {
                _formState.value = currentState.copy(
                    passwordError = "La Contrasenya no pot estar buida",
                    isValid = false
                )
            }
            !isValidPassword(password) -> {
                _formState.value = currentState.copy(
                    passwordError = "El format de la contrasenya ha de complir amb els següents requisits.",
                    isValid = false
                )
            }
            this.confirmPassword.isNotEmpty() && password != this.confirmPassword -> {
                _formState.value = currentState.copy(
                    confirmPasswordError = "La contrasenya i la confirmació han de ser iguals",
                    isValid = false
                )
            }
            else -> {
                _formState.value = currentState.copy(passwordError = null)
                // Si la confirmación ya estaba llena, validamos también
                if (this.confirmPassword.isNotEmpty()) {
                    onConfirmPasswordChanged(this.confirmPassword)
                } else {
                    validateForm()
                }
            }
        }
    }

    // Validación de confirmación de contraseña
    fun onConfirmPasswordChanged(confirmPassword: String) {
        this.confirmPassword = confirmPassword
        val currentState = _formState.value ?: RegisterFormState()

        when {
            confirmPassword.isBlank() -> {
                _formState.value = currentState.copy(
                    confirmPasswordError = "La Confirmar contrasenya no pot estar buida",
                    isValid = false
                )
            }
            confirmPassword != this.password -> {
                _formState.value = currentState.copy(
                    confirmPasswordError = "La contrasenya i la confirmació han de ser iguals",
                    isValid = false
                )
            }
            else -> {
                _formState.value = currentState.copy(confirmPasswordError = null)
                validateForm()
            }
        }
    }

    // Validación del formulario completo
    private fun validateForm() {
        val currentState = _formState.value ?: RegisterFormState()
        val isFormValid = currentState.usernameError == null &&
                currentState.nameError == null &&
                currentState.surnameError == null &&
                currentState.birthdateError == null &&
                currentState.phoneError == null &&
                currentState.emailError == null &&
                currentState.passwordError == null &&
                currentState.confirmPasswordError == null &&
                username.isNotBlank() &&
                name.isNotBlank() &&
                surname.isNotBlank() &&
                birthdate.isNotBlank() &&
                phone.isNotBlank() &&
                email.isNotBlank() &&
                password.isNotBlank() &&
                confirmPassword.isNotBlank()

        _formState.value = currentState.copy(isValid = isFormValid)
    }

    // Funciones auxiliares para validación

    private fun isValidDateFormat(date: String): Boolean {
        return try {
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            sdf.isLenient = false
            sdf.parse(date)
            true
        } catch (e: Exception) {
            false
        }
    }

    private fun isFutureDate(date: String): Boolean {
        return try {
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val parsedDate = sdf.parse(date)
            val currentDate = Date()
            parsedDate.after(currentDate)
        } catch (e: Exception) {
            false
        }
    }

    private fun isValidPhoneNumber(phone: String): Boolean {
        return phone.length == 9 && phone.all { it.isDigit() }
    }

    private fun isValidEmail(email: String): Boolean {
        val pattern = Pattern.compile(
            "[a-zA-Z0-9+._%\\-]{1,256}" +
                    "@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
        )
        return pattern.matcher(email).matches()
    }

    private fun isValidPassword(password: String): Boolean {
        val hasLowerCase = password.any { it.isLowerCase() }
        val hasUpperCase = password.any { it.isUpperCase() }
        val hasDigit = password.any { it.isDigit() }
        val hasSpecialChar = password.any { !it.isLetterOrDigit() }
        val hasMinLength = password.length >= 10

        return hasLowerCase && hasUpperCase && hasDigit && hasSpecialChar && hasMinLength
    }
}