# RetrofitEx

---
1. <a href = "#content1">레트로핏 라이브러리</a></br>
2. <a href = "#content2">Call vs Response</a></br>
3. <a href = "#content3">errorBody() 처리</a></br>
4. <a href = "#content4">JSON TO Kotlin Class 플러그인 설치</a></br>
* <a href = "#ref">참고링크</a>
---
><a id = "content1">**1. 레트로핏 라이브러리**</a></br>

`implementation 'com.squareup.retrofit2:retrofit:2.9.0'`</br>
`implementation 'com.squareup.retrofit2:converter-gson:2.9.0'`</br>
`implementation 'com.squareup.okhttp3:logging-interceptor:4.6.0'`</br>

-인터페이스를 해석해 HTTP 로 데이터를 통신하는 라이브러리</br>
-모델 클래스에서 키와 프로퍼티 이름이 다를 때 `@SerializedName` 이라는 애너테이션으로 명시</br>
-baseUrl 이 HTTP 인 경우 AndroidManifest-<application> 에 `android:usesCleartextTraffic="true"` 추가</br>


**Retrofit 동작 방식**</br>
(1) 통신용 함수를 선언한 서비스 인터페이스를 작성</br>
```kotlin
interface GitApiService {
    @GET(DETAIL_URL)
    fun getUsers(): Call<Repository>
}
```
(2) retrofit 객체 생성</br>
```kotlin
Retrofit.Builder()
    .baseUrl(GIT_BASE_URL)
    .addConverterFactory(GsonConverterFactory.create()) //GsonConverter : JSON 데이터를 코틀린 데이터 클래스로 변환해 주는 컨터버
    .build()
    .create(GitApiService::class.java)
```
(3) retrofit 객체에서 서비스 인터페이스 함수를 호출하고 `enqueue()` 로 콜백처리</br>
```kotlin
GitRetrofitClient
    .retrofit    
    .getUsers()
    .enqueue(object : Callback<Repository> {
        override fun onResponse(call: Call<Repository>, response: Response<Repository>) {
        }
        override fun onFailure(call: Call<Repository>, t: Throwable) {
        }
    })
```

<br></br>
<br></br>



><a id = "content2">**2. Call vs Response**</a></br>

<br></br>
<br></br>


><a id = "content3">**3. errorBody() 처리**</a></br>

<br></br>
<br></br>



><a id = "content4">**4. JSON TO Kotlin Class 플러그인 설치**</a></br>

-JSON 형식으로 된 텍스트 데이터를 코틀린 클래스로 간단하게 변환해주는 플러그인</br>
-[File]-[Settings]->[Plugins] 선택 후 JASON To Kotlin Class 플러그인 검색 후 설치</br>
-기본 패키지 우클릭 -> [New]-[Kotlin data class File from JSON] -> 샘플 JSON 형식 데이터를 복사 붙혀넣고 'Repository' 를 생성 -> 데이터 클래스가 생성됨</br>

<br></br>
<br></br>
---

><a id = "ref">**참고링크**</a></br>

안드로이드 - 레트로핏 errorbody() 파싱하기</br>
https://salix97.tistory.com/237</br>

Retrofit errorBody() 값 확인하기
https://50billion-dollars.tistory.com/entry/Android-Retrofit-errorBody-%EA%B0%92-%ED%99%95%EC%9D%B8%ED%95%98%EA%B8%B0