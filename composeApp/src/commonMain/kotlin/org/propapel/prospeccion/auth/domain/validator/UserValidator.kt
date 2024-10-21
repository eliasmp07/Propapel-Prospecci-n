package org.propapel.prospeccion.auth.domain.validator


 class UserValidator {
     companion object {
         fun isEmailValid(email: String): Boolean {
             val emailRegex = Regex("^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})")
             return email.matches(emailRegex)
         }
         fun isPasswordStrong(password: String): Boolean {
             val passwordRegex = Regex("^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])(?=.*[@#$%^&+=])(?=\\S+\$).{8,}")
             return  password.matches(passwordRegex)
         }
         const val MIN_PASSWORD_LENGTH = 8
     }
}