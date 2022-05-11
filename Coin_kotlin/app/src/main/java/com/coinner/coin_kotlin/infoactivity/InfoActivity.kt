package com.coinner.coin_kotlin.infoactivity

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.coinner.coin_kotlin.R
import com.coinner.coin_kotlin.activity.LoginActivity
import com.coinner.coin_kotlin.databinding.ActivityInfoBinding
import com.coinner.coin_kotlin.info.User
import com.coinner.coin_kotlin.model.Repository
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InfoActivity : AppCompatActivity() {

    private val auth = FirebaseAuth.getInstance().currentUser
    lateinit var binding: ActivityInfoBinding
    lateinit var user: User
    private val RC_SIGN_IN = 9001
    private val TAG = "InfoActivity"

    override fun onResume() {
        super.onResume()
        getUser()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setView()
    }

    private fun setView() {
        getUser()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_info)

        binding.changeNickT.setOnClickListener {
            startActivity(Intent(this, ChangeNicknameActivity::class.java).run {
                putExtra("user", user)
            })
        }

        binding.myPostT.setOnClickListener {
            startActivity(Intent(this, MypostActivity::class.java).run {
                putExtra("user", user)
            })
        }

        binding.withdraw.setOnClickListener {
            withdrawDialog()
        }

        binding.appNotificationT.setOnClickListener {
            startActivity(Intent(this, AlarmSettingActivity::class.java))
        }
    }

    private fun withdrawDialog() {
        val dialog = AlertDialog.Builder(this, android.R.style.Theme_DeviceDefault_Dialog)

        dialog.setMessage("회원탈퇴를 하시겠습니까?\n\n\'사용자의 정보\'와 \'결제내역\'이 모두 삭제됩니다. \n또한,\'작성글\'과 \'댓글\'의 내용은 남아있게 됩니다.")
            .setPositiveButton("탈퇴") { _, _ ->
                requestTocken()
            }
            .setNeutralButton("아니오") { _, _ ->
            }
            .show()
    }

    fun requestTocken() {
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
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.e("InfoActivity", "firebaseAuthWithGoogle success:" + account.id)
                withdraw(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.e("InfoActivity", "Google sign in failed: " + e.message)
                Toast("로그인에 실패했습니다.")
            }
        }
    }

    fun withdraw(idToken: String) {
        val user = FirebaseAuth.getInstance().currentUser!!
        val id = user.uid

        val credential = GoogleAuthProvider.getCredential(idToken, null)
        user.reauthenticate(credential)
            .addOnCompleteListener(this) { task ->
                signout(user,id)
            }
    }

    fun signout(user: FirebaseUser, id: String) {
        val opt = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()
        val client = GoogleSignIn.getClient(this, opt)

        client.signOut().addOnCompleteListener {
            if (it.isSuccessful) {
                user.delete().addOnCompleteListener {
                    if (it.isSuccessful) {
                        Repository.delUser(id).enqueue(object : Callback<User> {
                            override fun onResponse(call: Call<User>, response: Response<User>) {
                                if (response.isSuccessful) {
                                    response.body()?.msg?.let { it1 ->
                                        Toast(it1)
                                        loginActivity()
                                    }
                                }
                            }

                            override fun onFailure(call: Call<User>, t: Throwable) {
                                Log.e("InfoActi", "fail withdraw:" + t.message)
                            }
                        })
                    } else {
                        Toast("실패")
                        Log.e("asdd", "asdasd ${it.exception.toString()}")
                    }
                }
            }
        }
    }

    private fun getUser() {
        auth?.run {
            CoroutineScope(Dispatchers.Main).launch {
                try {
                    val res_user = Repository.getUser(uid)
                    if(res_user.result){
                        user = res_user
                        binding.user = user
                    }
                }catch (e:Exception){
                    Log.e(TAG, "getUser in failed: " + e.message)
                    Toast("회원정보 확인에 실패했습니다.")
                }
            }
//
//
//            Repository.getUser(uid).enqueue(object : Callback<User> {
//                override fun onResponse(call: Call<User>, response: Response<User>) {
//                    if (response.isSuccessful && response.body() != null) {
//                        user = response.body()!!
//                        binding.user = user
//                    }
//                }
//
//                override fun onFailure(call: Call<User>, t: Throwable) {
//                    Log.e("infoActivity", "onFailure user")
//                }
//            })
        }
    }

    fun loginActivity() {
        startActivity(Intent(this, LoginActivity::class.java).run {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        })
    }

    fun Toast(str: String) {
        android.widget.Toast.makeText(this, str, android.widget.Toast.LENGTH_SHORT).show();
    }
}