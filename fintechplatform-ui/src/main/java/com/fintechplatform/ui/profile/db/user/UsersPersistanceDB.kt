package com.fintechplatform.ui.profile.db.user

import com.fintechplatform.api.iban.models.UserResidential
import com.fintechplatform.api.profile.models.UserContacts
import com.fintechplatform.api.profile.models.UserJobInfo
import com.fintechplatform.api.profile.models.UserLightData
import com.fintechplatform.api.profile.models.UserProfile
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
            val id = it.id?: ""
            UserProfile(id,
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
            UserResidential(userId, "", userobj.address,
                    userobj.ziPcode, userobj.city, userobj.countryofresidence)
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

    fun saveResidential(residential: UserResidential) {
        val user = dbUsers.findUser(residential.userid) ?: Users()
            user.id = residential.userid
            user.address = residential.address
            user.city = residential.city
            user.ziPcode = residential.ZIPcode
            user.countryofresidence = residential.countryofresidence
        dbUsers.saveUser(user)
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

    fun saveContacts(userProfile: UserContacts) {
        val user = dbUsers.findUser(userProfile.userid) ?: Users()
            user.id = userProfile.userid
            user.email = userProfile.email
            user.telephone = userProfile.telephone
        dbUsers.saveUser(user)
    }

    fun saveJobInfo(userProfile: UserJobInfo) {
        val user = dbUsers.findUser(userProfile.userid) ?: Users()
            user.id = userProfile.userid
            user.jobinfo = userProfile.jobinfo
            user.income = userProfile.income
        dbUsers.saveUser(user)
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