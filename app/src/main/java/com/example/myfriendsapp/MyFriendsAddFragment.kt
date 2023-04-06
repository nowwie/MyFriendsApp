package com.example.myfriendsapp

import android.os.Bundle
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MyFriendsAddFragment : Fragment() {
    private var db:AppDataBase? = null
    private var myFriendDao: MyFriendDao? = null

    private var namaInput: String=""
    private var emailInput: String=""
    private var telpInput: String=""
    private var alamatInput: String=""
    private var genderInput: String=""

    private var edtName: EditText? = null
    private var edtEmail: EditText? = null
    private var edtTelp: EditText? =null
    private var edtAddress: EditText? =null
    private var btnSave: Button?= null
    private var spinnerGender: Spinner? =null

    companion object {
        fun newInstance(): MyFriendsAddFragment {
            return MyFriendsAddFragment()
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container:
    ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.my_friends_add_fragment,
            container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState:
    Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLocalDB()
        initView()
    }
    private fun initView() {
        spinnerGender = activity?.findViewById(R.id.spinnerGender)

        edtName = activity?.findViewById(R.id.edtName)
        edtEmail = activity?.findViewById(R.id.edtEmail)
        edtAddress = activity?.findViewById(R.id.edtAddress)
        edtTelp = activity?.findViewById(R.id.edtTelp)

        btnSave = activity?.findViewById(R.id.btnSave)

        btnSave?.setOnClickListener {
//            (activity as MainActivity).tampilMyFriendsFragment()
            validasiInput()
        }

    }

    private fun initLocalDB(){
        db = AppDataBase.getAppDataBase(requireActivity())
        myFriendDao = db?.myFriendDao()
        setDataSpinnerGener()
    }

    private fun tambahDataTeman(teman: MyFriends) : Job {
        return GlobalScope.launch {
            myFriendDao?.tambahTeman(teman)
            (activity as MainActivity).tampilMyFriendsFragment()
        }
    }

    private fun setDataSpinnerGener(){
        val adapter = ArrayAdapter.createFromResource(requireActivity(),R.array.gender_list,
        android.R.layout.simple_spinner_item)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinnerGender?.adapter = adapter
    }

    private fun validasiInput(){
        namaInput = edtName?.text.toString()
        emailInput = edtEmail?.text.toString()
        telpInput = edtTelp?.text.toString()
        alamatInput= edtAddress?.text.toString()
        genderInput= spinnerGender?.selectedItem.toString()

        when{
            namaInput.isEmpty() -> edtName?.error = "Nama tidak boleh kosong say"
            genderInput.equals("Pilih Kelamin") -> tampilToast("Kelamin harus dipilih")
            emailInput.isEmpty() -> edtEmail?.error = "Email gaboleh ksonk dikkk"
            telpInput.isEmpty() -> edtTelp?.error = "Telpon jgn ksonk"
            alamatInput.isEmpty() -> edtAddress?.error = "Alamat gbleh ksonk"

            else -> {
                val teman= MyFriends(nama = namaInput, kelamin = genderInput, email = emailInput, telp = telpInput, alamat = alamatInput)
                tambahDataTeman(teman)
            }
        }
    }
    private fun tampilToast(message: String){
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }
    override fun onDestroy() {
        super.onDestroy()
        db?.close()
        edtName = null
        edtEmail = null
        edtTelp = null
        edtAddress =null
        btnSave = null
        spinnerGender = null

    }
}