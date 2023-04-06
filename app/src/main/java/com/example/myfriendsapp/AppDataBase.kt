package com.example.myfriendsapp

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MyFriends::class], version = 1)
abstract class AppDataBase : RoomDatabase(){

    abstract fun myFriendDao(): MyFriendDao

    companion object{
        var INSTANCE: AppDataBase? = null

        fun getAppDataBase(context: Context) : AppDataBase? {
            if (INSTANCE == null){
                synchronized(AppDataBase::class){
                    INSTANCE = androidx.room.Room.databaseBuilder(context.applicationContext,com.example.myfriendsapp.AppDataBase::class.java, "MyFriendAppDB").build()
                }
            }
            return INSTANCE
        }
        fun destroyDataBase(){
            INSTANCE = null
        }
    }
}