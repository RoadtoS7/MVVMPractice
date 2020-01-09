package com.tistory.comfy91.mvvmpractice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

// MainActivity.kt

/**
 * 5. MainActivity 설정
 * 1) ContactViewModel인스턴스를 만들고 이를 관찰하도록 만들어야 한다.
 * 2) 뷰모델 인스턴스는 직접 만드는 것이 아니라, 안드로이드 시스템을 통해서 생성한다.
 * 시스템에서는 이미 생성도니 뷰모델 인스턴스가 있다면 이를 반환한다. 따라서 메모리 낭비를 줄여준다.
 * 뷰 모델 프로바이더라는 시스템 프로그램을 이용해서 get해준다.
 *
 * Observer를 만들어서 뷰모델이 어느 액티비티/프래그먼트의 생명주기를 관찰할 것인지 정한다.
 * 설정해준 액티비티가 파괴되는 시점에 뷰모델도 자동적으로 파괴될 것이다.
 * Kotlin 에서는 람다를 이용해서 간편하게 설정할 수 있다.
 * Observer는 onChanged는 메소드를 가지고 있어서, 관찰하고 이는 LiveData가 변화되면 무엇을 할 것인지 액션을 정할 수 있다.
 * 액티비티, 프레그먼트가 활성화되면 Viwe(액티비티)에서 LiveData를 관차하여 자동으로 변경사항을 파악하고 이를 수행한다.
 * onChange에서 UI가 업데이트 되도록 만들 것이다.
 *
 * 6. RecyclerView 설정
 * Adpater에 onClick시 해야할 일 과 onLongClick시 해야할일 두개의 (contact) -> Unit 타입의 파라미터를 넙겨주어야한다.
 * 클릭했을 때에는 현재 contact에서 name, number, id를 뽑아 intenct 에 포함시켜 AddActivity로 넘겨주면서 액티비티를 시작하도록 만들 것이다.
 * 롱클릭 했을 때에는 다이얼로그를 통해 아이템을 삭제하도록 만들었다.
 *
 * DAO의 getAll() 함수의 Query에 이름으로 ASC 정렬하도록 해두었기 때문에 새로운 데이터를 추가하면
 * Model -> ViewModel 에 라이브데이터 리스트를 넘겨줄 때 자동으로 이름순으로 정렬된다.
 * 뷰에서는 observer.onChange를 통해서 관찰하고 있으므로 자동으로 UI를 갱신한다.
 */

/**
 * 최종적인 main코드
 * add 버튼을 눌럿을 때 새로운 주소록 추가를 위해서 AddActivit를 시작했다.
 * adapter의 contactItemClick에는 해당 아티메을 수정하기 위해 intent를 통해 contact정보를 extra로 추가하고 AddActivity를 싲가했다.
 */

class MainActivity : AppCompatActivity() {

    private lateinit var contactViewModel: ContactViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set contactItemClick & contactItemLongClick Lambda
        val adapter = ContactAdapter({contact ->
            val intent = Intent(this, AddActivity::class.java)
            intent.putExtra(AddActivity.EXTRA_CONTACT_NAME, contact.name)
            intent.putExtra(AddActivity.EXTRA_CONTACT_NUMBER, contact.number)
            intent.putExtra(AddActivity.EXTRA_CONTACT_ID, contact.id)
            startActivity(intent)
        },
            {contact -> deleteDialog(contact)})


        main_recycleview.adapter = adapter
        main_recycleview.layoutManager = LinearLayoutManager(this)
        main_recycleview.setHasFixedSize(true)

        contactViewModel = ViewModelProviders.of(this).get(ContactViewModel::class.java)
        contactViewModel.getAll().observe(this, Observer<List<Contact>>{contacts ->
          adapter.setContacts(contacts!!)
        })

        main_button.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
        }
    }

    private fun deleteDialog(contact: Contact){
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Delete selected contact?")
            .setNegativeButton("NO"){_,_ ->}
            .setPositiveButton("YES"){_,_ ->
                contactViewModel.delete(contact)
            }
        builder.show()
    }
}
