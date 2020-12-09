package com.example.retrofitdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var retService: AlbumService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        retService = RetrofitInstance
            .getRetrofitInstance()
            .create(AlbumService::class.java)

//        getRequestWithQueryParameter()
        uploadAlbum()
//        getRequestWithPathParameter()
//        getAlbums()

    }

    private  fun getRequestWithQueryParameter() {
        val responseLiveData: LiveData<Response<Albums>> = liveData {
            val response = retService.getSortedAlbums(4)
            emit(response)
        }

        responseLiveData.observe(this, Observer {
            val albumList = it.body()?.listIterator()
            if (albumList != null) {
                while (albumList.hasNext()) {
                    val albumItem = albumList.next()
                    val result: String = " " + "Album Title: ${albumItem.title}" + "\n" +
                            " " + "Album id: ${albumItem.id}" + "\n" +
                            " " + "User id: ${albumItem.userId}" + "\n\n\n"
                    text_view.append(result)
                }
            }
        })
    }

    private  fun getRequestWithPathParameter() {
        val pathResponse: LiveData<Response<AlbumsItem>> = liveData {
            val response: Response<AlbumsItem> = retService.getAlbum(3)
            emit(response)
        }

        pathResponse.observe(this, Observer {
            val title = it.body()?.title
            Toast.makeText(this, title, Toast.LENGTH_LONG).show()
        })
    }

    private  fun getAlbums() {
        val responseLiveData: LiveData<Response<Albums>> = liveData {
            val response = retService.getAlbums()
            emit(response)
        }

        responseLiveData.observe(this, Observer {
            val albumList = it.body()?.listIterator()
            if (albumList != null) {
                while (albumList.hasNext()) {
                    val albumItem = albumList.next()
                    val result: String = " " + "Album Title: ${albumItem.title}" + "\n" +
                            " " + "Album id: ${albumItem.id}" + "\n" +
                            " " + "User id: ${albumItem.userId}" + "\n\n\n"
                    text_view.append(result)
                }
            }
        })
    }

    private  fun uploadAlbum() {
        val data = AlbumsItem(34, "New Album", 6)

        val responseLiveData: LiveData<Response<AlbumsItem>> = liveData {
            val response = retService.uploadAlbum(data)
            emit(response)
        }

        responseLiveData.observe(this, Observer {
            val newAlbum = it.body()
            val result: String = " " + "Album Title: ${newAlbum?.title}" + "\n" +
                    " " + "Album id: ${newAlbum?.id}" + "\n" +
                    " " + "User id: ${newAlbum?.userId}" + "\n\n\n"
            text_view.text = result
        })
    }

}