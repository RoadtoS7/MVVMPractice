package com.tistory.comfy91.mvvmpractice

import androidx.lifecycle.LiveData
import androidx.room.*

// ContactDao.kt
/**
 * Entity 를 만들었으므로 SQL을 작성하기 위한 DAO 인터페이스를 만들어준다.
 *
 * room 에서는 @Query, @Insert @Update @Delete와 같은 어노테이션을 제공한다.
 * 그리고 Inser와 Update에서는 onConflict 속성을 지정할 수 있다. onConflict 속성을 통해서 중복된 데이터의 경우 어떻게 처리할지를 설정할 수 있다.
 * OnConflictStrategy 인터페이스를 호출해 REPLACE, IGNORE, ABORT, FAIL, ROLLBACK 등의 처리 방식을 지정할 수 있다.
 *
 * 주목할점 : 연락처 전체 리스트를 반환하는 getAll()함수를 만들 때 LiveData를 반환해준다는 점이다.
 * 기존에 익숙한 데이터 형을 LiveData<> 로 감싸주면된다.
 */

@Dao
interface ContactDao {

    @Query("SELECT * FROM contact ORDER BY name ASC")
    fun getAll(): LiveData<List<Contact>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(contact: Contact)

    @Delete
    fun delete(contact: Contact)
}
