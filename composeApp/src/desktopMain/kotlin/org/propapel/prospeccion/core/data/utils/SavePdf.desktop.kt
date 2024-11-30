package org.propapel.prospeccion.core.data.utils

import java.io.File
import javax.swing.JFileChooser
import javax.swing.filechooser.FileNameExtensionFilter

actual fun savePDF(
    pdfData: ByteArray,
    fileName: String
): Boolean {
    val fileChooser = JFileChooser().apply {
        dialogTitle = "Guardar PDF"
        fileSelectionMode = JFileChooser.FILES_ONLY
        fileFilter = FileNameExtensionFilter("Archivos PDF", "pdf")
        selectedFile = File(if (fileName.endsWith(".pdf")) fileName else "$fileName.pdf")
    }

    val userSelection = fileChooser.showSaveDialog(null)
    if (userSelection != JFileChooser.APPROVE_OPTION) {
        return false // Cancelado por el usuario
    }

    val fileToSave = fileChooser.selectedFile
    val safeFile = if (fileToSave.extension.lowercase() == "pdf") {
        fileToSave
    } else {
        File("${fileToSave.absolutePath}.pdf")
    }

    return try {
        safeFile.writeBytes(pdfData) // Guardar el archivo PDF
        true // PDF guardado correctamente
    } catch (e: Exception) {
        e.printStackTrace() // Log para depuración
        false // Fallo al guardar el archivo
    }
}
