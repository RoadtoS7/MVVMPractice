package com.tistory.comfy91.mvvmpractice

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

// ContactViewModel.kt

/**
 * 4. ViewModel만들기
 * 안드로이드 뷰모델인 AndroidViewModel을 extend받는 ContactViewModel을 만들어준다.
 *
 * 안드로이드 뷰모델은 Application 을 파라미터로 받는다.
 * Room 데이터베이스의 인스턴스를 만들 때 Context가 필요하지만 ViewModel이 액티비티의 context를 사용하게 되면, 액티비티가 destroy가 된 경우에 메모리 릭이 발생할 수 있다.
 * 따라서 Room 데이터베이스의 인스턴스를 만들 때 Application Context를 사용하기 위해서 Application 을 인자로 받는다.
 *
 * ViewModel이 레포지터리에게 디비 제어를 요청하기 위한 함수는 Repository에 있는 함수를 이용해서 설정해준다.
 */
class ContactViewModel(application: Application) : AndroidViewModel(application){

    private val repository = ContactRepository(application)
    private val contacts = repository.getAll()

    fun getAll(): LiveData<List<Contact>>{
        return this.contacts
    }

    fun insert(contact: Contact){
        repository.insert(contact)
    }

    fun delete(contact: Contact){
        repository.delete(contact)
    }
}