package com.ix.ibrahim7.ps.text.jerusalemapp.repository.home

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.ix.ibrahim7.ps.text.jerusalemapp.model.Comment
import com.ix.ibrahim7.ps.text.jerusalemapp.model.Post
import com.ix.ibrahim7.ps.text.jerusalemapp.model.User
import com.ix.ibrahim7.ps.text.jerusalemapp.other.*
import com.ix.ibrahim7.ps.text.jerusalemapp.util.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class AddPostRepository{


    private val addPostLiveData = MutableLiveData<Resource<Boolean>>()
    fun getAddPostLiveData(): LiveData<Resource<Boolean>> = addPostLiveData

    val postContentLiveData = MutableLiveData<Resource<Boolean>>()


    fun addPost(postDescription:String,user: User,postType:String,postContent:String) {
        CoroutineScope(Dispatchers.IO).launch {
            val post_id = FirebaseFirestore.getInstance().collection(POSTS).document()
            addPostLiveData.postValue(Resource.Loading())
            FirebaseFirestore.getInstance().collection(POSTS)
                .document(post_id.id)
                .set(Post(post_id = post_id.id,comment_id = post_id.id,post_description = postDescription,user = user,post_type = postType,postContent = postContent))
                .addOnSuccessListener {
                    addPostLiveData.postValue(Resource.Success(true))
                    addComment(post_id.id,user)
                }.addOnFailureListener {
                    addPostLiveData.postValue(Resource.Error(it.message!!, false))
                }
        }
    }

    fun addComment(postID:String,user: User){
        CoroutineScope(Dispatchers.IO).launch {
            val comment_id = FirebaseFirestore.getInstance().collection(POSTS).document(postID).collection(COMMENTS).document()
            comment_id.set(Comment(comment_id = comment_id.id,user = user))
            //.set(Comment(comment_id = comment_id.id))
        }
    }



    fun ZipImage(uri: Uri, context: Context, onsuccess: (image: ByteArray) -> Unit) {
        val selectedImagePath = uri
        val selectedImageBmp =
            MediaStore.Images.Media.getBitmap(context.contentResolver, selectedImagePath)
        val outputStream = ByteArrayOutputStream()
        selectedImageBmp.compress(Bitmap.CompressFormat.JPEG, 75, outputStream)
        val selectedImageBytes = outputStream.toByteArray()
        onsuccess(selectedImageBytes)
    }

    fun uploadImage(
        selectedImage: ByteArray,
        onsuccess: (imagePath: String) -> Unit
    ) {
        postContentLiveData.postValue(Resource.Loading())
        val time = SimpleDateFormat("HH:mm-yyyy-MM-dd").format(Date())
        val title = "IMAGE_$time"
        val ref =
            FirebaseStorage.getInstance().reference.child("Image").child(title)
        ref.putBytes(selectedImage).addOnCompleteListener {
            if (it.isSuccessful) {
                postContentLiveData.postValue(Resource.Success(true))
                onsuccess(ref.path)
            } else {
                postContentLiveData.postValue(Resource.Error(it.exception!!.message!!, false))
                Log.v("rrr", "${it.exception!!.message}")
            }
        }
    }

    fun uploadVideo(uri: Uri, onsuccess: (videoPath: String) -> Unit) {
        val time = System.currentTimeMillis().toString()
        val title = "VIDEO_$time"
        val ref =
            FirebaseStorage.getInstance().reference.child("Video").child(title)
        ref.putFile(Uri.fromFile(File(uri.toString()))).addOnSuccessListener { success: UploadTask.TaskSnapshot ->
                val audioUrl =
                    success.storage.downloadUrl
                audioUrl.addOnCompleteListener { path: Task<Uri> ->
                    if (path.isSuccessful) {
                        onsuccess(path.result.toString())
                        postContentLiveData.postValue(Resource.Success(true))
                    }
                }
            }.addOnFailureListener {
            postContentLiveData.postValue(Resource.Error(it.message!!, false))
                onsuccess(it.message.toString())
            }.addOnProgressListener {
                val progress = (100.0 * it.bytesTransferred) / it.totalByteCount
                Log.v("eee process", "Upload is $progress% done")
            }
    }

}