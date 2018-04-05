package com.fintechplatform.android.iban.db

import com.fintechplatform.android.iban.models.BankAccount
import javax.inject.Inject

class IbanPersistanceDB @Inject constructor(val ibanDB: IbanDB){

    fun save(bankAccount: BankAccount) {
        val dbbankAccount = Iban()
        dbbankAccount.state = bankAccount.activestate
        dbbankAccount.accountId = bankAccount.accountId
        dbbankAccount.id = bankAccount.bankaccountid
        dbbankAccount.number = bankAccount.iban

        return ibanDB.saveBankAccount(dbbankAccount)
    }

    fun bankAccountId(accountId: String) : String? {
        return load(accountId)?.bankaccountid
    }

    fun replace(bankAccount: BankAccount) {
        ibanDB.deleteBankAccount()
        return save(bankAccount)
    }

    fun load(accountId: String): BankAccount? {
        val optiban = ibanDB.findBankAccount(accountId)
        return optiban?.let { iban ->
            BankAccount(iban.id, iban.accountId, iban.number, iban.state)
        }
    }

    fun delete(){
        ibanDB.deleteBankAccount()
    }
}