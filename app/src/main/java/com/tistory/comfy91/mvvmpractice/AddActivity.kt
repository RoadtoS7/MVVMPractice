package com.tistory.comfy91.mvvmpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_add.*

/**
 * 7. AddActivity 생성
 * AddActivity 의 역할
 * 1) intent extra로 사용할 상수를 만든다.(companion object)
 * 2) ViewModel 객체를 만든다
 * 3) 만약 intent가 null 이 아니고, extra에 주소록 정보가 모두 들어있따면
 * EditText와 id값을 지정해준다.
 *  MainActivity에서 ADD버튼을 눌렀을 때에는 신규 추가 이므로 intent가 없고, RecyclerView Item을 눌렀을 때에는
 *  편집을 위해 클릭된 정보를 불러오기 위해서 인텐트 값을 불러올 것이다.
 *  4) 하단의 DONE 버튼을 통해 EditText의 null 체크를 한 후, ViewModel을 통해 insert 해주고, MAinActivity로 돌아간다.
 *
 *  코드 설명
 *  아이디값이 null 일 경우 자동으로 room에서 id를 생성해주면서 새로운 contact를 DB에 추가해준다.
 *  id값을main에서 intent로 받아온 경우 , 완료버튼을 누르면 해당 아이템을 수정하게 된다.
 *  DAO 에서 OnConflictStrategy를 REPLACE로 설정해두었기 때문이다.
 *  -> MainActivity로 돌아가서 코드를 수정한다.
 *
 */
class AddActivity : AppCompatActivity() {

    private lateinit var contactViewModel: ContactViewModel
    private var id: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        contactViewModel = ViewModelProviders.of(this).get(ContactViewModel::class.java)

        // intent null check and get extras
        if (intent != null
            && intent.hasExtra(EXTRA_CONTACT_NAME)
            && intent.hasExtra(EXTRA_CONTACT_NAME)
            && intent.hasExtra(EXTRA_CONTACT_ID)){
            add_edittext_name.setText(intent.getStringExtra(EXTRA_CONTACT_NAME))
            add_edittext_number.setText(intent.getStringExtra(EXTRA_CONTACT_NUMBER))
            id = intent.getLongExtra(EXTRA_CONTACT_ID, -1)
        }

        add_button.setOnClickListener {
            val name = add_edittext_name.text.toString().trim()
            val number = add_edittext_number.text.toString()

            if(name.isEmpty() || number.isEmpty()){
                Toast.makeText(this, "Please enter name and number", Toast.LENGTH_LONG).show()
            }
            else{
                val initial = name[0].toUpperCase()
                val contact = Contact(id, name, number, initial)
                contactViewModel.insert(contact)
                finish()
            }
        }

    }

    companion object{
        const val EXTRA_CONTACT_NAME = "EXTRA_CONTACT_NAME"
        const val EXTRA_CONTACT_NUMBER = "EXTRA_CONTACT_NUMBER"
        const val EXTRA_CONTACT_ID= "EXTRA_CONTACT_ID"
    }
}
