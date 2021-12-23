package com.ix.ibrahim7.ps.text.jerusalemapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ix.ibrahim7.ps.text.jerusalemapp.network.RetrofitInstance
import com.ix.ibrahim7.ps.text.jerusalemapp.util.ResultRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.RequestBody
import retrofit2.HttpException
import retrofit2.http.Query

class ApiRepository {

    private val newsMutableLiveData = MutableLiveData<ResultRequest<Any>>()

    fun getNews(q: String, apiKey: String) {
        CoroutineScope(Dispatchers.IO).launch {
            newsMutableLiveData.postValue(ResultRequest.loading("loading"))
            val response = RetrofitInstance.api!!.getNews(q, apiKey)
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            newsMutableLiveData.postValue(ResultRequest.success(it))
                            Log.e("getNewsSuccess", it.toString())
                        }

                    } else {
                        Log.e("getNewsErrorRequest", response.errorBody().toString())
                        newsMutableLiveData.postValue(
                            ResultRequest.error(
                                "Ooops: ${response.errorBody()}",
                                response
                            )
                        )
                    }
                } catch (e: HttpException) {
                    Log.e("getNewsErrorHttp", e.message().toString())
                    newsMutableLiveData.postValue(
                        ResultRequest.error(
                            "Ooops: ${e.message()}",
                            e
                        )
                    )

                } catch (t: Throwable) {
                    Log.e("getNewsErrorThrowable", t.message.toString())
                    newsMutableLiveData.postValue(
                        ResultRequest.error(
                            "Ooops: ${t.message}",
                            t
                        )
                    )
                }
            }
        }
    }


    val newsLiveData: LiveData<ResultRequest<Any>> = newsMutableLiveData

}