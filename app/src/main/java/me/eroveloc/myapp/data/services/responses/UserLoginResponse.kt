package me.eroveloc.myapp.data.services.responses

data class UserLoginResponse(
    val id: Int?,
    var email: String?,
    var name: String?
) : GenericResponse()