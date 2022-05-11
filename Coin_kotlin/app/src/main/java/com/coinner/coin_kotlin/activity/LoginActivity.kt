package com.coinner.coin_kotlin.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.coinner.coin_kotlin.R
import com.coinner.coin_kotlin.databinding.ActivityLoginBinding
import com.coinner.coin_kotlin.info.Post
import com.coinner.coin_kotlin.model.PreferenceManager
import com.coinner.coin_kotlin.model.Repository
import com.coinner.coin_kotlin.utility.NetworkStatus
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.*


class LoginActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "LoginActivity"
        private const val RC_SIGN_IN = 9001
        private val REQ_ONE_TAP = 2
    }

    val auth = FirebaseAuth.getInstance()
    lateinit var binding: ActivityLoginBinding

    fun animateLogo(){
        val animation: Animation =
            AnimationUtils.loadAnimation(baseContext, R.anim.adbtn)
        binding.logoView.startAnimation(animation)
    }

    override fun onStart() {
        super.onStart()
        animateLogo()
        Handler(Looper.getMainLooper()).postDelayed({
            auth.currentUser?.let {
                getSetUser(
                    it.uid,
                    it.displayName ?: "익명",
                    it.email ?: "이메일없음",
                    MainActivity::class.java,
                    null
                )
            }
        }, 1000)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setView()
    }

    @SuppressLint("SetTextI18n")
    private fun setView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
//        (binding.loginBtn.getChildAt(0) as TextView).text = "Google 로그인"
        binding.loginBtn.setOnClickListener {
            Log.e("onclick login", "")
            signIn()
        }

        binding.outBtn.setOnClickListener {
//            logout()
            startActivity(MainActivity::class.java,null)
        }
    }

    private fun signIn() {
        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("900857055162-5110niio0f1b612kc0bgrlt34tdpg7c4.apps.googleusercontent.com")
            .requestEmail()
            .build()

        val signInIntent = GoogleSignIn.getClient(this, gso).signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        when(requestCode){
            RC_SIGN_IN -> {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    val account = task.getResult(ApiException::class.java)!!
                    Log.e(TAG, "firebaseAuthWithGoogle success:" + account.id)
                    firebaseAuthWithGoogle(account.idToken!!)
                } catch (e: ApiException) {
                    // Google Sign In failed, update UI appropriately
                    Log.e(TAG, "Google sign in failed: " + e.message)
                    Toast("로그인에 실패했습니다.")
                }
            }

//            REQ_ONE_TAP -> {
//                try {
//                    val credential = oneTapClient.getSignInCredentialFromIntent(data)
//                    val idToken = credential.googleIdToken
//                    when {
//                        idToken != null -> {
//                            // Got an ID token from Google. Use it to authenticate
//                            // with Firebase.
//                            Log.d(TAG, "Got ID token.")
//                        }
//                        else -> {
//                            // Shouldn't happen.
//                            Log.d(TAG, "No ID token!")
//                        }
//                    }
//                } catch (e: ApiException) {
//                    // ...
//                }
//            }

        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.e(TAG, "signInWithCredential: success")
                    val user = auth.currentUser
                    val uid = user?.uid ?: "non_uid"
                    val nickname = user?.displayName ?: "non_name"
                    val mail = user?.email ?: "non_email"

                    var tag = ""
                    for (i in 0..1)
                        tag += uid[i]
                    PreferenceManager.setString(this,"id",uid)
                    getSetUser(uid, "$nickname (#$tag)", mail,MainActivity::class.java,null)
                } else {
                    logout()
                    Log.e(TAG, "signInWithCredential: failure: " + task.exception?.message)
                    Toast("로그인에 실패했습니다. 다시 시도해주세요.")
                }
            }
    }

    private fun logout() {
        val opt = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()
        val client = GoogleSignIn.getClient(this, opt)
        client.signOut()
        client.revokeAccess()
    }

    private fun startActivity(activity: Class<*>,post:Post?) {
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        when(activity){
            MainActivity::class.java -> {
                startActivity(Intent(this, MainActivity::class.java))
            }

            PostActivity::class.java -> {
                startActivity(Intent(this, PostActivity::class.java).apply {
                    if(post != null)
                     this.putExtra("post",post)
                })
            }
        }
        finish()
    }

    fun getSetUser(
        id: String,
        nickname: String,
        mail: String,
        activity: Class<*>,
        post:Post?
    ) {
        checkNetWork()

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val user = Repository.getUser(id)
                val token = getToken()
                val dbToken = user.token
                val result = user.result
                Log.e(TAG,"userToken: $dbToken , preferToken: $token")

                if(result){//이미 가입된 정보가 있는 경우
                    if(dbToken != token) { //다른 기기로 접속해서 아이디는 있으나 토큰이 새로 들어온 경우 토큰이 다를 때
                        Log.e(TAG,"token different")
                        val apply = Repository.updateToken(id,token)
                        Toast(apply.msg)
                        startActivity(activity,post)
                    }else{
                        Log.e(TAG,"token same")
                        startActivity(activity,post)
                    }
                }else{
                    Log.e(TAG,"first login")
                    val apply = Repository.setUser(id, nickname, mail, token)
                    Toast(apply.msg)
                    startActivity(activity,post)
                }
            }catch (e:Exception){
                Log.e(TAG, "onStart in failed: " + e.message)
                Toast("로그인에 실패했습니다.")
            }
        }
    }

    fun getToken(): String{
        val token = PreferenceManager.getString(this,"fcmToken")
        return if(token.isNullOrEmpty())
            ""
        else{
            token
        }
    }

    fun Toast(str: String) {
        android.widget.Toast.makeText(this, str, android.widget.Toast.LENGTH_SHORT).show();
    }

    fun checkNetWork() {
        if (!NetworkStatus.isConnected(this)) {
            Log.e("main_network", "network is disconnected")
            this.run {
                Handler(Looper.getMainLooper()).postDelayed({ Toast("인터넷 연결이 되어있지 않습니다.") }, 0)
            }
            return
        }
    }

}