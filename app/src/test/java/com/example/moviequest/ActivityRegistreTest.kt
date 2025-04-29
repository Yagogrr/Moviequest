import com.example.moviequest.ActivityRegistre
import org.junit.Assert.*
import org.junit.Test

class ActivityRegistreTest {

    private val viewModel = ActivityRegistre()

    //tests nom
    @Test
    fun `retorna error quan el nom està buit`() {

        // Dades d'entrada amb nom d'usuari buit
        viewModel.actualitzanomUsuari("")

        assertEquals("El nom d'usuari és obligatori", viewModel.validaciodades.value?.errorNomUsuari)
    }

    //tests cognoms



    //tests data



    //tests numero telefon



    //tests nom d'usuari



    //tests contrasenya i contrasenya 2



}
