package com.tistory.comfy91.mvvmpractice

import android.app.Application
import androidx.lifecycle.LiveData

// ContactRepository.kt

/**
 * Repository
 *
 * Database, Dao, contacts를 각가 초기화한다.
 *
 * ViewModel에서 DB에 접근하라고 요청할 때 수행할 함수들을 만들어준다.
 * 주의: Room DB를 메인 스레드에서 접근하려고 하면 크래쉬가 발생한다. = ANR 발생
 * 발생이유 = 접근하는 동안에 메인 UI 화면이 오랫동안 멈춰있게 되기 때문이다.
 * 따라서 별도의 스레드에서 Room의 데이터베이스에 접근해야한다.
 */

class ContactRepository (application: Application){
   private val contactDatabase = ContactDatabase.getInstance(application)!!
    private val contactDao: ContactDao = contactDatabase.contactDao()
    private val contacts: LiveData<List<Contact>> = contactDao.getAll()

    fun getAll(): LiveData<List<Contact>>{
        return contacts
    }

    fun insert(contact: Contact){
        try{
            val thread = Thread(Runnable{
                contactDao.insert(contact)})
            thread.start()
        }catch (e: Exception){}
    }

    fun delete(contact: Contact){
        try {
            val thread = Thread(Runnable{
                contactDao.delete(contact)
            })
            thread.start()
        }catch(e: Exception){}
    }

}