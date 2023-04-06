package com.example.myfriendsapp

import android.app.Activity
import android.os.Bundle
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MyFriendsFragment : Fragment() {
    private var fabAddFriend: FloatingActionButton? = null
    private var listMyFriends: RecyclerView? = null
//    lateinit var listTeman : MutableList<MyFriends>
    private var listTeman: List<MyFriends>? = null
    private var db: AppDataBase? = null
    private var myFriendDao: MyFriendDao? = null


    companion object {
        fun newInstance(): MyFriendsFragment {
            return MyFriendsFragment()
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container:
    ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.my_friends_fragment,
            container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState:
    Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLocalDB()
        initView()
    }
    private fun initView() {
        fabAddFriend = activity?.findViewById(R.id.fabAddFriend)
        listMyFriends = activity?.findViewById(R.id.listMyFriends)

        fabAddFriend?.setOnClickListener {
            (activity as MainActivity).tampilMyFriendsAddFragment() }

//        simulasiDataTeman()
//        tampilTeman()
        ambilDataTeman()
    }

    private fun initLocalDB(){
        db = AppDataBase.getAppDataBase(requireActivity())
        myFriendDao = db?.myFriendDao()
    }
    private fun ambilDataTeman() {
        listTeman = ArrayList()
        myFriendDao?.ambilSemuaTeman()?.observe(requireActivity()) { r -> listTeman = r
            when {
                listTeman?.size == 0 -> tampilToast("Belum ada data teman")
                else -> {
                    tampilTeman()
                }
            }
        }
    }
    private fun tampilToast(message: String){
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }
    private fun tampilTeman(){
        listMyFriends?.layoutManager = LinearLayoutManager(requireActivity())
        listMyFriends?.adapter = MyFriendAdapter(requireActivity(),
            listTeman!! as ArrayList<MyFriends>
        )
    }
    override fun onDestroy() {
        super.onDestroy()

    }

    private fun simulasiDataTeman(){
        listTeman = ArrayList()

//        listTeman.add(MyFriends("Nori","Perempuan","norinyawa@gmail.com","081233180050","Malang"))
//        listTeman.add(MyFriends("Adek","Laki-Laki","aihihi@gmail.com","082228501209","Malang"))
    }

}