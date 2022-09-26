# Retrofit

<img src="https://github.com/HYUNJUNEPARK/ImageRepository/blob/master/androidProgramming/retrofit.png" height="400"/>

---
1. <a href = "#content1">레트로핏 라이브러리</a></br>
2. <a href = "#content2">GSON 라이브러리</a></br>
3. <a href = "#content3">JSON TO Kotlin Class 플러그인</a></br>
* <a href = "#ref">참고링크</a>
---
><a id = "content1">**1. 레트로핏 라이브러리**</a></br>


`implementation 'com.squareup.retrofit2:retrofit:2.9.0'`</br>

-HttpURLConnection 클래스보다 더 간단하게 HTTP 로 데이터를 통신하는 라이브러리</br>
-레트로핏 통신 라이브러리는 레트로핏 인터페이스를 해석해 HTTP 통신 처리</br>
-모델 클래스에서 키와 프로퍼티 이름이 다를 때 `@SerializedName` 이라는 애너테이션으로 명시</br>

**Retrofit 동작 방식**</br>
(1) 통신용 함수를 선언한 서비스 인터페이스를 작성</br>
```
interface RetrofitInterface {
    @GET(DETAIL_URL)
    fun getUsers(): Call<Repository>
}
```

(2) Retrofit 에 인터페이스를 전달</br>
`retrofitService = retrofit.create(RetrofitInterface::class.java)`</br>
(3) Retrofit 이 통신용 서비스 객체를 반환</br>
(4) 서비스의 통신용 함수를 호출한 후에 Call 객체를 반환</br>
(5) Call 객체의 enqueue() 함수를 호출하여 네트워크 통신을 수행</br>
```
retrofitService.getUsers().enqueue(object : Callback<Repository> {
    override fun onResponse(call: Call<Repository>, response: Response<Repository>) {
    }
    override fun onFailure(call: Call<Repository>, t: Throwable) {
    }
}
```

*서비스 인터페이스</br>
-네트워크 통신이 필요한 순간에 호출할 함수를 포함하는 인터페이스</br>
-레트로핏 통신 라이브러리가 RetrofitInterface 를 해석해 HTTP 통신 처리</br>
-서비스 인터페이스를 통해 Retrofit 이 통신하는 클래스를 자동으로 만들어 주며 이때 함수에 선언한 애너테이션을 보고 그 정보대로 네트워크 통신을 할 수 있는 코드를 자동으로 생성</br>
-호출 방식, 세부 주소, 함수 등 데이터가 담겨 있음</br>

<br></br>
<br></br>

><a id = "content2">**2. GSON 라이브러리**</a></br>

`implementation 'com.squareup.retrofit2:converter-gson:2.9.0'`</br>

-레트로핏 통신으로 가져온 JSON 데이터를 코틀린 데이터 클래스로 변환해 주는 컨터버</br>
-레트로핏 객체 생성 시 `addConverterFactory()` 에 GsonConverterFactory 전달</br>
```
retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()
```

<br></br>
<br></br>

><a id = "content3">**3. JSON TO Kotlin Class 플러그인**</a></br>

-JSON 형식으로 된 텍스트 데이터를 코틀린 클래스로 간단하게 변환해주는 플러그인</br>
-[File]-[Settings]->[Plugins] 선택 후 JASON To Kotlin Class 플러그인 검색 후 설치</br>
-기본 패키지 우클릭 -> [New]-[Kotlin data class File from JSON] -> 샘플 JSON 형식 데이터를 복사 붙혀넣고 'Repository' 를 생성 -> 데이터 클래스가 생성됨</br>

<br></br>
<br></br>
---

><a id = "ref">**참고링크**</a></br>
