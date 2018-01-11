package com.dwaplatform.android.profile.db.user

import com.dwaplatform.android.iban.models.UserResidential
import com.dwaplatform.android.profile.models.UserContacts
import com.dwaplatform.android.profile.models.UserJobInfo
import com.dwaplatform.android.profile.models.UserLightData
import com.dwaplatform.android.profile.models.UserProfile
import javax.inject.Inject

class UsersPersistanceDB @Inject constructor(val dbUsers: UsersDB) {

    fun fullName(userId: String): String {
        val optuser = dbUsers.findUser(userId)
        return optuser?.let { user ->
            "${user.myname ?: ""} ${user.surname ?: ""}"
        } ?: ""
    }

    fun userProfile(userId: String): UserProfile? {
        val optuserobj = dbUsers.findUser(userId)
        return optuserobj?.let {
            UserProfile(it.id,
                    it.myname,
                    it.surname,
                    it.nationality,
                    it.dateOfBirth,
                    it.address,
                    it.ziPcode,
                    it.city,
                    it.telephone,
                    it.email,
                    it.photo,
                    it.countryofresidence,
                    it.jobinfo,
                    it.income, null)
        }
    }

    fun residential(userId: String): UserResidential? {
        val optuserobj = dbUsers.findUser(userId)
        return optuserobj?.let { userobj ->
            UserResidential(userId, userobj.address,
                    userobj.ZIPcode, userobj.city, userobj.countryofresidence)
        }
    }

    fun iconBase64Image(userId: String): String? {
        val optuser = dbUsers.findUser(userId)
        return optuser?.photo
    }

    fun saveUserPhoto(userId: String, photo: String) {
        val optobj = dbUsers.findUser(userId)
        optobj?.let { obj ->
            obj.photo = photo
            dbUsers.saveUser(obj)
        }
    }

    fun saveResidential(residential: UserResidential): Boolean {
        val optobj = dbUsers.findUser(residential.userid)
        return optobj?.let { obj ->
            obj.id = residential.userid
            obj.address = residential.address
            obj.city = residential.city
            obj.ZIPcode = residential.ZIPcode
            obj.countryofresidence = residential.countryofresidence
            dbUsers.saveUser(obj)
            true
        } ?: false
    }

    fun saveLightData(userProfile: UserLightData) {
        val user = dbUsers.findUser(userProfile.userid) ?: Users()
            user.id = userProfile.userid
            user.myname = userProfile.name
            user.surname = userProfile.surname
            user.nationality = userProfile.nationality
            user.dateOfBirth = userProfile.birthday
        dbUsers.saveUser(user)
    }

    fun saveContacts(userProfile: UserContacts): Boolean {
        val optUser = dbUsers.findUser(userProfile.userid)
        return optUser?.let {
            it.id = userProfile.userid
            it.email = userProfile.email
            it.telephone = userProfile.telephone
            dbUsers.saveUser(it)
            true
        }?: false
    }

    fun saveJobInfo(userProfile: UserJobInfo): Boolean {
        val optUser = dbUsers.findUser(userProfile.userid)
        return optUser?.let {
            it.id = userProfile.userid
            it.jobinfo = userProfile.jobinfo
            it.income = userProfile.income
            dbUsers.saveUser(it)
            true
        }?: false
    }

    fun saveLimitAccount(id: String, limitAccount: String){
        val optUser = dbUsers.findUser(id)
        optUser?.let {
            it.accountLimit = limitAccount
            dbUsers.saveUser(it)
        }
    }

    fun limitAccountUser(id: String): String? {
        val optuser = dbUsers.findUser(id)
        return optuser?.accountLimit
    }
}