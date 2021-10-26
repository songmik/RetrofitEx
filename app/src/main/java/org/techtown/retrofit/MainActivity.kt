package org.techtown.retrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var retrofit = Retrofit.Builder()
            // url 서버 주소를 맞게 써주기
            .baseUrl("http://3.34.49.43:8000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var loginService = retrofit.create(LoginService::class.java)

        button.setOnClickListener{
            var textId = editText.text.toString()
            var textPw = editText2.text.toString()

            loginService.requestLogin(textId,textPw).enqueue(object:Callback<Login>{
                // 웹 통신에 실패했을때 실행되는 코드
                override fun onFailure(call: Call<Login>, t:Throwable) {

                    var dialog = AlertDialog.Builder(this@MainActivity)
                    dialog.setTitle("실패!")
                    dialog.setMessage("통신에 실패했습니다.")
                    dialog.show()
                }

                // 웹 통신에 성공했을때 실행되는 코드, 응답값을 받아옴
                override fun onResponse(call: Call<Login>, response: Response<Login>) {

                    var login = response.body() // code, msg
                    var dialog = AlertDialog.Builder(this@MainActivity)
                    dialog.setTitle("알람!")
                    dialog.setMessage("code = " + login?.code + "msg = " + login?.msg)
                    dialog.show()

                }
            })

            var dialog = AlertDialog.Builder(this)
            dialog.setTitle("알람!")
            dialog.setMessage(textId + "님 로그인 성공")
            dialog.show()

        }
    }
}




