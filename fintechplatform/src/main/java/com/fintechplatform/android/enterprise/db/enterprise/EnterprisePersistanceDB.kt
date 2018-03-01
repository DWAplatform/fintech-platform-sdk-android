package com.fintechplatform.android.enterprise.db.enterprise

import com.fintechplatform.android.enterprise.models.EnterpriseAddress
import com.fintechplatform.android.enterprise.models.EnterpriseContacts
import com.fintechplatform.android.enterprise.models.EnterpriseInfo
import com.fintechplatform.android.enterprise.models.EnterpriseProfile
import javax.inject.Inject

class EnterprisePersistanceDB @Inject constructor(val enterpriseDB: EnterpriseDB){

    fun enterpriseProfile(ownerId: String) : EnterpriseProfile? {
        val optEnterprise = enterpriseDB.findEnterprise(ownerId)
        return optEnterprise?.let{
            EnterpriseProfile(ownerId,
                    it.legalRapresentativeId,
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

    fun saveAddress(enterpriseAddress: EnterpriseAddress) {
        val enterprise = enterpriseDB.findEnterprise(enterpriseAddress.ownerId) ?: Enterprise()
        enterprise.id = enterpriseAddress.ownerId
        enterprise.address = enterpriseAddress.address
        enterprise.postalCode = enterpriseAddress.postalCode
        enterprise.city = enterpriseAddress.city
        enterprise.country = enterpriseAddress.country
        enterpriseDB.saveEnterprise(enterprise)
    }

    fun saveInfo(info: EnterpriseInfo) {
        val enterprise = enterpriseDB.findEnterprise(info.ownerId) ?: Enterprise()
        enterprise.id = info.ownerId
        enterprise.name = info.name
        enterprise.type = info.enterpriseType
        enterpriseDB.saveEnterprise(enterprise)
    }

    fun saveContacts(contacts: EnterpriseContacts) {
        val enterprise = enterpriseDB.findEnterprise(contacts.ownerId) ?: Enterprise()
        enterprise.id = contacts.ownerId
        enterprise.email = contacts.email
        enterprise.telephone = contacts.telephone
        enterpriseDB.saveEnterprise(enterprise)
    }
}