package com.ix.ibrahim7.ps.text.jerusalemapp.repository.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.ix.ibrahim7.ps.text.jerusalemapp.model.Post
import com.ix.ibrahim7.ps.text.jerusalemapp.model.User
import com.ix.ibrahim7.ps.text.jerusalemapp.other.POSTS
import com.ix.ibrahim7.ps.text.jerusalemapp.other.USERS
import com.ix.ibrahim7.ps.text.jerusalemapp.util.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeRepository{

    private val userMutableLiveData = MutableLiveData<Resource<User>>()
    private val postsMutableLiveData = MutableLiveData<Resource<List<Post>>>()

    fun getUserInfo(userID:String) {
        CoroutineScope(Dispatchers.IO).launch {
            userMutableLiveData.postValue(Resource.Loading())
            FirebaseFirestore.getInstance().document("${USERS}/${userID}").get().addOnSuccessListener {
                userMutableLiveData.postValue(Resource.Success(it.toObject(User::class.java)!!))
            }.addOnFailureListener {
                userMutableLiveData.postValue(Resource.Error(it.message!!,null))
            }
        }
    }

    fun getAllPosts() {
        CoroutineScope(Dispatchers.IO).launch {
            postsMutableLiveData.postValue(Resource.Loading())
            val arrayList = ArrayList<Post>()
                FirebaseFirestore.getInstance().collection(POSTS)
                    .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                        arrayList.clear()
                        querySnapshot!!.documents.forEach {
                            val item = it.toObject(Post::class.java)
                            arrayList.add(item!!)
                        }
                        postsMutableLiveData.postValue(Resource.Success(arrayList))
                    }
        }
    }


    fun userLiveData(): LiveData<Resource<User>> = userMutableLiveData
    fun postsLiveData(): LiveData<Resource<List<Post>>> = postsMutableLiveData

}