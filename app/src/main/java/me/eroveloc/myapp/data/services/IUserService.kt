package me.eroveloc.myapp.data.services

import me.eroveloc.myapp.data.services.requests.UserLoginRequest
import me.eroveloc.myapp.data.services.requests.UserRegisterRequest
import me.eroveloc.myapp.data.services.responses.UserLoginResponse

import retrofit2.http.Body
import retrofit2.http.POST

interface IUserService {
    @POST("User/login")
    suspend fun userLogin(@Body request: UserLoginRequest) : UserLoginResponse

    @POST("User/register")
    suspend fun userRegister(@Body request: UserRegisterRequest) : UserLoginResponse
}