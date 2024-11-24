package org.propapel.prospeccion.root.presentation.createReminder.components.utils
/**
 *  Data class que contiene los tipos de citas
 *  
 *  @param name Nombre del tipo de citas
 *  @param icon Icono del tipo de cita
 */
data class TypeOfAppointment(
    val name: String,
    val icon: String
){
    override fun toString(): String {
        return "$icon $name"
    }
}


/**
 * Funcion que provee los de tipos de citas
 *
 * @return retorna los tipos de citas
 */
fun provideTypeOfAppointment(): List<TypeOfAppointment>{
    return listOf(
        TypeOfAppointment(
            "Presencial",
            "🧍‍♂️🏢"
        ),
        TypeOfAppointment(
            "Reunion Remota",
            "📲"
        )
    )
}