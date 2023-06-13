package com.capstone.kuma

import android.content.Context

internal class SessionPreference(context: Context) {
    companion object{
        private const val PREFS_NAME ="login_session"
        private const val USERID ="userid"
        private const val NAME ="name"
        private const val TOKEN ="token"
    }

    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setSession(session: LoginSession){
        val edit = preferences.edit()
        edit.putString(USERID, session.userId)
        edit.putString(NAME, session.name)
        edit.putString(TOKEN, session.token)
        edit.apply()
    }

    fun getSession(): LoginSession{
        val model = LoginSession()
        model.userId = preferences.getString(USERID, "")
        model.name = preferences.getString(NAME, "")
        model.token = preferences.getString(TOKEN, "")

        return model
    }

    fun deleteSession(){
        val edit = preferences.edit()
        edit.clear()
        edit.apply()
    }
}