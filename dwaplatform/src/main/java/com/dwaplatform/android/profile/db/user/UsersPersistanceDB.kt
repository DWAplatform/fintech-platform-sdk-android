package com.dwaplatform.android.profile.db.user

import com.dwaplatform.android.iban.models.UserResidential
import com.dwaplatform.android.profile.models.UserContacts
import com.dwaplatform.android.profile.models.UserJobInfo
import com.dwaplatform.android.profile.models.UserLightData
import com.dwaplatform.android.profile.models.UserProfile
import javax.inject.Inject

class UsersPersistanceDB @Inject constructor(val dbUsers: UsersDB) {

    fun userid(): String {
        val user = dbUsers.findUser()
        return user!!.id!!
    }

    fun fullName(): String {
        val optuser = dbUsers.findUser()
        return optuser?.let { user ->
            "${user.myname ?: ""} ${user.surname ?: ""}"
        } ?: ""
    }

    fun userProfile(): UserProfile? {
        val optuserobj = dbUsers.findUser()
        return optuserobj?.let {
            UserProfile(userid(),
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

    fun residential(): UserResidential? {
        val optuserobj = dbUsers.findUser()
        return optuserobj?.let { userobj ->
            UserResidential(userobj.id, userobj.address,
                    userobj.ZIPcode, userobj.city, userobj.countryofresidence)
        }
    }

    fun iconBase64Image(): String? {
        val optuser = dbUsers.findUser()
        return optuser?.photo
    }

    fun saveUserPhoto(photo: String) {
        val optobj = dbUsers.findUser()
        optobj?.let { obj ->
            obj.photo = photo
            dbUsers.saveUser(obj)
        }
    }

    fun saveResidential(residential: UserResidential): Boolean {

        val optobj = dbUsers.findUser()
        return optobj?.let { obj ->
            obj.address = residential.address
            obj.city = residential.city
            obj.ZIPcode = residential.ZIPcode
            obj.countryofresidence = residential.countryofresidence
            dbUsers.saveUser(obj)
            true
        } ?: false
    }

    fun saveLightData(userProfile: UserLightData): Boolean {
        val optUser = dbUsers.findUser()
        return optUser?.let {
            it.myname = userProfile.name
            it.surname = userProfile.surname
            it.nationality = userProfile.nationality
            it.dateOfBirth = userProfile.birthday
            dbUsers.saveUser(it)
            true
        }?: false
    }

    fun saveContacts(userProfile: UserContacts): Boolean {
        val optUser = dbUsers.findUser()
        return optUser?.let {
            it.email = userProfile.email
            it.telephone = userProfile.telephone
            dbUsers.saveUser(it)
            true
        }?: false
    }

    fun saveJobInfo(userProfile: UserJobInfo): Boolean {
        val optUser = dbUsers.findUser()
        return optUser?.let {
            it.jobinfo = userProfile.jobinfo
            it.income = userProfile.income
            dbUsers.saveUser(it)
            true
        }?: false
    }

    fun saveLimitAccount(limitAccount: String){
        val optUser = dbUsers.findUser()
        optUser?.let { it.accountLimit = limitAccount
            dbUsers.saveUser(it)}
    }

    fun limitAccountUser(): String? {
        val optuser = dbUsers.findUser()
        return optuser?.accountLimit
    }
}