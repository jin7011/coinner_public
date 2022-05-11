package com.coinner.coin_kotlin.model

import com.coinner.coin_kotlin.data.Candle_List
import com.coinner.coin_kotlin.data.Ticker_Response
import com.coinner.coin_kotlin.info.*
import io.reactivex.rxjava3.core.Single
import okhttp3.ResponseBody
import retrofit2.Call

object Repository {

    val ec2_URL = "http://ec2-15-165-159-175.ap-northeast-2.compute.amazonaws.com:3000"
//    val ec2_URL = "http://localhost:3000"

    fun get_CandleList_Single(path:String) : Single<Candle_List>{
        return RetrofitFactory
            .createRetrofit("https://api.bithumb.com/")
            .create(Service::class.java)
            .CANDLE_LIST_SINGLE(path)
    }

    fun get_Ticker(path: String): Single<Ticker_Response>{
        return RetrofitFactory
            .createRetrofit("https://api.bithumb.com/")
            .create(Service::class.java)
            .TICKER_SINGLE(path)
    }

    fun get_CoinImage(coin_name:String) : Call<ResponseBody>{
        return RetrofitFactory
                .createRetrofit("https://cryptoicon-api.vercel.app/")
                .create(Service::class.java)
                .downloadImage(coin_name)
    }

    suspend fun setUser(id: String,
                nickname: String,
                mail: String,
                token: String
    ): User {
        return RetrofitFactory
                .createRetrofit(ec2_URL)
                .create(Service::class.java)
                .setUser(id,nickname,mail, token)
    }

    suspend fun getUser(id: String): User {
        return RetrofitFactory
            .createRetrofit(ec2_URL)
            .create(Service::class.java)
            .getUser(id)
    }
    fun delUser(id: String): Call<User> {
        return RetrofitFactory
            .createRetrofit(ec2_URL)
            .create(Service::class.java)
            .delUser(id)
    }

    fun updateNick(
        id:String,
        nickname:String
    ):Call<User>{
        return RetrofitFactory
            .createRetrofit(ec2_URL)
            .create(Service::class.java)
            .updateNick(id,nickname)
    }

    suspend fun updateToken(
        id:String,
        token:String
    ):User{
        return RetrofitFactory
            .createRetrofit(ec2_URL)
            .create(Service::class.java)
            .updateUserToken(id, token)
    }

    suspend fun writePost(postid: String,
                  title: String,
                  content: String,
                  nickname: String,
                  id: String,
                  coin: String
    ): Post {
        return RetrofitFactory
                .createRetrofit(ec2_URL)
                .create(Service::class.java)
                .writePost(postid,title, content, nickname, id, coin)
    }

    fun getPostList(coin: String
    ): Call<PostList> {
        return RetrofitFactory
                .createRetrofit(ec2_URL)
                .create(Service::class.java)
                .getPostList(coin)
    }

    suspend fun submission(postid: String,id: String):Boolean{
        return RetrofitFactory
            .createRetrofit(ec2_URL)
            .create(Service::class.java)
            .submission(postid,id)
    }

    suspend fun getPost(postid: String
    ): Post {
        return RetrofitFactory
            .createRetrofit(ec2_URL)
            .create(Service::class.java)
            .getPost(postid)
    }

    fun searchPostList(
            coin: String,
            keyword: String,
    ): Call<PostList> {
        return RetrofitFactory
                .createRetrofit(ec2_URL)
                .create(Service::class.java)
                .searchPostList(coin, keyword)
    }

    fun myPostList(id: String,
    ): Call<PostList> {
        return RetrofitFactory
                .createRetrofit(ec2_URL)
                .create(Service::class.java)
                .myPostList(id)
    }

    fun deletePost(
        postid:String
    ): Call<Post> {
        return RetrofitFactory
            .createRetrofit(ec2_URL)
            .create(Service::class.java)
            .deletePost(postid)
    }

    fun deleteComment(
        commentid: String,
        postid: String
    ): Call<Comment> {
        return RetrofitFactory
            .createRetrofit(ec2_URL)
            .create(Service::class.java)
            .deleteComment(commentid,postid)
    }

    suspend fun getCommentList(
        postid:String
    ): CommentList {
        return RetrofitFactory
            .createRetrofit(ec2_URL)
            .create(Service::class.java)
            .getCommentListSingle(postid)
    }

    suspend fun writeComment(
        postid: String,
        commentid: String,
        content: String,
        nickname: String,
        id:String
    ): Comment {
        return RetrofitFactory
            .createRetrofit(ec2_URL)
            .create(Service::class.java)
            .writeComment(postid, commentid, content, nickname,id)
    }

    suspend fun plove(
        postid:String,
        id:String
    ): Post {
        return RetrofitFactory
            .createRetrofit(ec2_URL)
            .create(Service::class.java)
            .plove(postid, id)
    }

    suspend fun clove(
        commentid: String,
        postid:String,
        id:String
    ): Post {
        return RetrofitFactory
            .createRetrofit(ec2_URL)
            .create(Service::class.java)
            .clove(commentid, postid, id)
    }

    suspend fun addAlarm(
        price: String,
        id:String,
        coin:String
    ): Alarm {
        return RetrofitFactory
            .createRetrofit(ec2_URL)
            .create(Service::class.java)
            .addAlarm(price, id, coin)
    }

    suspend fun delAlarm(
        price: String,
        id:String,
        coin:String,
    ): Alarm {
        return RetrofitFactory
            .createRetrofit(ec2_URL)
            .create(Service::class.java)
            .delAlarm(price, id, coin)
    }

    suspend fun getAlarms(
        id:String,
        coin:String,
    ): AlarmList {
        return RetrofitFactory
            .createRetrofit(ec2_URL)
            .create(Service::class.java)
            .getAlarms(id, coin)
    }
}