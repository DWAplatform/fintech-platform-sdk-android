package com.dwaplatform.android.enterprise.db.enterprise

import com.dwaplatform.android.enterprise.models.EnterpriseAddress
import com.dwaplatform.android.enterprise.models.EnterpriseContacts
import com.dwaplatform.android.enterprise.models.EnterpriseInfo
import com.dwaplatform.android.enterprise.models.EnterpriseProfile
import javax.inject.Inject

class EnterprisePersistanceDB @Inject constructor(val enterpriseDB: EnterpriseDB){

    fun enterpriseProfile(userid: String) : EnterpriseProfile? {
        val optEnterprise = enterpriseDB.findEnterprise()
        return optEnterprise?.let{
            EnterpriseProfile(userid,
                    it.name,
                    it.telephone,
                    it.email,
                    it.type,
                    it.country,
                    it.city,
                    it.address,
                    it.postalCode)
        }
    }

    fun enterpriseAddress(userid: String): EnterpriseAddress? {
        val optEnterprise = enterpriseDB.findEnterprise()
        return optEnterprise?.let {
            EnterpriseAddress(userid,
                    it.address,
                    it.city,
                    it.postalCode,
                    it.country)
        }
    }

    fun saveAddress(enterpriseAddress: EnterpriseAddress) {
        val enterprise = enterpriseDB.findEnterprise() ?: Enterprise()
        enterprise.id = enterpriseAddress.userid
        enterprise.address = enterpriseAddress.address
        enterprise.postalCode = enterpriseAddress.postalCode
        enterprise.city = enterpriseAddress.city
        enterprise.country = enterpriseAddress.country
        enterpriseDB.saveEnterprise(enterprise)
    }

    fun saveInfo(info: EnterpriseInfo) {
        val enterprise = enterpriseDB.findEnterprise() ?: Enterprise()
        enterprise.id = info.userid
        enterprise.name = info.name
        enterprise.type = info.enterpriseType
        enterpriseDB.saveEnterprise(enterprise)
    }

    fun saveContacts(contacts: EnterpriseContacts) {
        val enterprise = enterpriseDB.findEnterprise() ?: Enterprise()
        enterprise.id = contacts.userid
        enterprise.email = contacts.email
        enterprise.telephone = contacts.telephone
        enterpriseDB.saveEnterprise(enterprise)
    }
}