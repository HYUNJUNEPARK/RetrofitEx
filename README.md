# RetrofitEx

---
1. <a href = "#content1">레트로핏 라이브러리</a></br>
2. <a href = "#content2">Call vs Response</a></br>
3. <a href = "#content3">JSON TO Kotlin Class 플러그인 설치</a></br>
4. <a href = "#content4">Generic</a></br>
* <a href = "#ref">참고링크</a>
---
><a id = "content1">**1. 레트로핏 라이브러리**</a></br>

`implementation 'com.squareup.retrofit2:retrofit:2.9.0'`</br>
`implementation 'com.squareup.retrofit2:converter-gson:2.9.0'`</br>
`implementation 'com.squareup.okhttp3:logging-interceptor:4.6.0'`</br>

-인터페이스를 해석해 HTTP 로 데이터를 통신하는 라이브러리</br>
-Response Data Class 생성 시 `JSON TO Kotlin Class` 사용하면 편리 (3. JSON TO Kotlin Class 플러그인 설치 참고)</br>
-baseUrl 이 HTTP 인 경우 AndroidManifest-<application> `android:usesCleartextTraffic="true"` 추가</br>
-GsonConverterFactory : GSON 데이터를 코틀린 데이터 클래스로 변환해 주는 컨터버</br>

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
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
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
            //Success
        }
        override fun onFailure(call: Call<Repository>, t: Throwable) {
            //Failed
        }
    })
```

<br></br>
<br></br>

><a id = "content2">**2. Call vs Response**</a></br>

**Call**</br>
-레트로핏을 사용하여 서버로부터 응답을 받을 때 사용하는 일반적인 방법</br>
-명시적으로 성공/실패가 나눠져 그에 따른 동작 처리가 가능</br>
-"Call" is useful when we are willing to use its enqueue callback function-Async</br>

**Response**</br>
-response.code() 로 케이스를 나눠서 처리할 수 있음</br>
-Coroutine/RXjava 등 비동기 실행을 한다면 Response 를 사용</br>
-When we use Coroutines or RxJava in the project(which is the best professional practice)</br>
to provide asynchronous execution , we don't need enqueue callback. We could just use Response.</br>
When we use Coroutines or RxJava in the project(which is the best professional practice)</br>
to provide asynchronous execution, we don't need enqueue callback. We could just use Response.</br>

<br></br>
<br></br>

><a id = "content3">**3. JSON TO Kotlin Class 플러그인 설치**</a></br>

-JSON 형식으로 된 텍스트 데이터를 코틀린 클래스로 간단하게 변환해주는 플러그인</br>
-[File]-[Settings]->[Plugins] 선택 후 JASON To Kotlin Class 플러그인 검색 후 설치</br>
-기본 패키지 우클릭 -> [New]-[Kotlin data class File from JSON] -> 샘플 JSON 형식 데이터를 복사 붙혀넣고 'Repository' 를 생성 -> 데이터 클래스가 생성됨</br>

<br></br>
<br></br>

><a id = "content4">**4. Generic**</a></br>

**무공변성(invariant)**</br>
-상속관계 상관없이 자신의 타입만 허용함</br>
-자바에서의 <T>와 같은 개념</br>
-코틀린에서는 따로 지정해주지 않으면 기본적으로 모든 제네릭은 무공변</br>


**공변성(covariant)**</br>
-자기 자신과 자식 객체를 허용함</br>
-자바에서의 <? extent T>와 같은 개념</br>
-코틀린에서는 `out` 키워드를 사용</br>


**반공변성(contravariant)**</br>
-자기 자신과 부모 객체만 허용함(공변성의 반대)</br>
-자바에서의 <? super T>와 같은 개념</br>
-코를린에서는 `in` 키워드를 사용</br>


<br></br>
<br></br>

---

><a id = "ref">**참고링크**</a></br>

Retrofit 전체 Response 받기</br>
https://th-biglight.tistory.com/11</br>

Retrofit2 기본 사용법2 -'GET/POST/PUT/DELETE'</br>
https://jaejong.tistory.com/38</br>

안드로이드 - 레트로핏 errorBody() 파싱하기</br>
https://salix97.tistory.com/237</br>

Retrofit errorBody() 값 확인하기</br>
https://50billion-dollars.tistory.com/entry/Android-Retrofit-errorBody-%EA%B0%92-%ED%99%95%EC%9D%B8%ED%95%98%EA%B8%B0</br>

Retrofit Call 과 Response 차이</br>
https://jeongupark-study-house.tistory.com/208</br>

Retrofit 응답 상태 관리</br>
https://landroid.tistory.com/2</br>

Call or Response in Retrofit?</br>
https://stackoverflow.com/questions/64124670/call-or-response-in-retrofit</br>

Modeling Retrofit Responses With Sealed Classes and Coroutines</br>
https://proandroiddev.com/modeling-retrofit-responses-with-sealed-classes-and-coroutines-9d6302077dfe</br>

Generics - 공변성(covariant)과 반공변성(contravariant)</br>
https://deep-dive-dev.tistory.com/39</br>