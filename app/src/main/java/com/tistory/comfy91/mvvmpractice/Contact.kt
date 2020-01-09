package com.tistory.comfy91.mvvmpractice

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Room 생성
 *  연락처를 저장할 클래스를 Entity 로 사용함
 *
 *
 *  기본키가 되는 id = @PrimaryKey로 지정
 *  null 일 경우에는 자동으로 autoGenerate되도록 autogGenerate = true를 주었다.
 *  나머지 칼럼에는 @ColumneInfo를 통해서 칼럼명을 지정했다. 하지만 칼럼명을 변수명으로 사용하려면 생략도 가능하다
 *
 *  Entity에서 테이블이름을 작성하는 부분인 @Entity(tableName = "contact")같은 경우도 클래스 이름을 엔티티 이름으로 사용할 경우 생략이 가능하다
 */



@Entity(tableName = "contact")
data class Contact (
    @PrimaryKey(autoGenerate = true)
    var id: Long?,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "number")
    var number: String,

    @ColumnInfo(name = "initial")
    var initial: Char
){
    constructor(): this(null, "", "", '\u0000')
}