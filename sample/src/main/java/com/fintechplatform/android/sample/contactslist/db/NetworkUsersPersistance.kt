package com.fintechplatform.android.sample.contactslist.db

import com.fintechplatform.android.sample.contactslist.models.NetworkAccounts


class NetworkUsersPersistance constructor(val dbp2p: NetworkUsersDB) {
    fun loadAll(): List<NetworkAccounts> {
        val peers = dbp2p.getP2PPeers()
        return peers.map { p ->
            NetworkAccounts(p.userid, p.accountid, p.tenantid, p.name, p.surname, p.photo, p.aspName, p.accountType)
        }
    }

    fun save(networkModel: NetworkAccounts) {
        val dp2p = NetworkUsers()
        dp2p.setUserid(networkModel.ownerId)
        dp2p.setAccountid(networkModel.accountId)
        dp2p.setTenantid(networkModel.tenantId)
        dp2p.setName(networkModel.name)
        dp2p.setSurname(networkModel.surname)
        dp2p.setPhoto(networkModel.photo)
        dp2p.setAspName(networkModel.aspName)
        dp2p.setAccountType(networkModel.accountType)
        return this.dbp2p.saveP2P(dp2p)
    }

    fun saveAll(p2pusers: List<NetworkAccounts>) {
        dbp2p.deleteP2P()
        p2pusers.forEach { p2puser -> save(p2puser) }
    }


    fun findP2P(p2pid: String) : NetworkAccounts? {
        val optu = dbp2p.findP2P(p2pid)
        return optu?.let { p ->
            NetworkAccounts(p.userid, p.accountid, p.tenantid, p.name, p.surname, p.photo, p.aspName, p.accountType)
        }
    }

    fun getFullName(p2puser: NetworkAccounts): String {
        val surname = p2puser.surname
        val name = p2puser.name
        return "${surname} ${name}"
    }
}