package com.tistory.comfy91.mvvmpractice

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
// ContactDatabase.kt

/**
 * 실질적인 데이터베이스 인스턴스를 생성할 Database클래스를 만든다.
 *
 * RoomDatabase클래스를 상속받는 추상클래스로 만든다.
 *
 * 클래스 이름 위에 @Database 어노테이션을이용해서 entity를 정의하고 SQLite 버전을 지정한다.
 * 또한 데이터베이스를 싱글톤으로 정의하기 위해서 companion object를 만들어준다.
 *
 * getInstance는 여러 스레드가 접근하지 못하도록 하기 위해서 synchronized로 설정한다.
 *
 * 여기서 Room.databaseBuilder로 인스턴스를 생성하고 fallbackToDestructiveMigration()을 통해 데이터베이스가 갱신ㄴ될 때 기존 테이블을 버리고 새로 사용하도록 설정했다.
 *
 * 이렇게 만들어진 DB 인스턴스는 Repository에서 호출하여 사용할 것이다.
 * (이제까지 DB를 생성하고 접근하기 위한 것들을 만들었다.)
 */

@Database(entities = [Contact::class], version = 1)
abstract class ContactDatabase : RoomDatabase(){

    abstract fun contactDao(): ContactDao

    companion object{
        private var Instance: ContactDatabase? = null

        fun getInstance(context: Context): ContactDatabase?{
            if(Instance == null){
                synchronized(ContactDatabase::class){
                    Instance = Room.databaseBuilder(context.applicationContext,
                        ContactDatabase::class.java, "contact")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return Instance
        }
    }

}