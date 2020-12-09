package com.example.retrofitdemo

import retrofit2.Response
import retrofit2.http.*

interface AlbumService {

    //    calling an endpoint with GET method
    @GET("/albums")
    suspend fun getAlbums(): Response<Albums>

    //    calling an endpoint with query parameter
    @GET("/albums")
    suspend fun getSortedAlbums(@Query("userId") userId: Int): Response<Albums>

    //  calling an endpoint with path parameter
    @GET("/albums/{id}")
    suspend fun getAlbum(@Path("id") albumId: Int): Response<AlbumsItem>

    //    calling an endpoint with POST method
    @POST("/albums")
    suspend fun uploadAlbum(@Body album: AlbumsItem): Response<AlbumsItem>

}