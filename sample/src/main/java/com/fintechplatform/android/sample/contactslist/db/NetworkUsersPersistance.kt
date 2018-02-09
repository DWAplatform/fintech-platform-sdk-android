package com.fintechplatform.android.sample.contactslist.db

import com.fintechplatform.android.sample.contactslist.models.NetworkUserModel


class NetworkUsersPersistance constructor(val dbp2p: NetworkUsersDB) {
    fun loadAll(): List<NetworkUserModel> {
        val peers = dbp2p.getP2PPeers()
        return peers.map { p ->
            NetworkUserModel(p.userid, p.name, p.surname, p.telephone, p.photo, p.type)
        }
    }

    fun save(networkModel: NetworkUserModel) {
        val dp2p = NetworkUsers()
        dp2p.setUserid(networkModel.userid)
        dp2p.setName(networkModel.name)
        dp2p.setSurname(networkModel.surname)
        dp2p.setTelephone(networkModel.telephone)
        dp2p.setPhoto(networkModel.photo)
        return this.dbp2p.saveP2P(dp2p)
    }

    fun saveAll(p2pusers: List<NetworkUserModel>) {
        dbp2p.deleteP2P()
        p2pusers.forEach { p2puser -> save(p2puser) }
    }


    fun findP2P(p2pid: String) : NetworkUserModel? {
        val optu = dbp2p.findP2P(p2pid)
        return optu?.let { u ->
            NetworkUserModel(u.userid, u.name, u.surname, u.telephone, u.photo, u.type)
        }
    }

    fun getFullName(p2puser: NetworkUserModel): String {
        val surname = p2puser.surname ?: ""
        val name = p2puser.name ?: ""
        return "${surname} ${name}"
    }
}