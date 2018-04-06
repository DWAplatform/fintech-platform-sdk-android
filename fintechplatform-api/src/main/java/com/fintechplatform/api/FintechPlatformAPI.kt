package com.fintechplatform.api

import android.content.Context
import com.fintechplatform.api.account.balance.BalanceAPIBuilder
import com.fintechplatform.api.account.balance.api.BalanceAPI
import com.fintechplatform.api.card.PaymentCardAPIBuilder
import com.fintechplatform.api.card.api.PaymentCardAPI
import com.fintechplatform.api.cashin.CashInAPIBuilder
import com.fintechplatform.api.cashin.api.CashInAPI
import com.fintechplatform.api.enterprise.EnterpriseAPIBuilder
import com.fintechplatform.api.enterprise.api.EnterpriseAPI
import com.fintechplatform.api.iban.IBANAPIBuilder
import com.fintechplatform.api.iban.api.IbanAPI
import com.fintechplatform.api.payout.PayOutAPIBuilder
import com.fintechplatform.api.payout.api.PayOutAPI
import com.fintechplatform.api.profile.ProfileAPIBuilder
import com.fintechplatform.api.profile.api.ProfileAPI
import com.fintechplatform.api.sct.SctAPIBuilder
import com.fintechplatform.api.sct.api.SctAPI
import com.fintechplatform.api.transactions.TransactionAPIBuilder
import com.fintechplatform.api.transactions.api.TransactionsAPI
import com.fintechplatform.api.transfer.TransferAPIBuilder
import com.fintechplatform.api.transfer.api.TransferAPI

class FintechPlatformAPI {

    companion object {

        fun getBalance(hostName: String, context: Context): BalanceAPI {
            return BalanceAPIBuilder().createBalanceAPIComponent(hostName, context).balanceAPI
        }


        fun getPayOut(hostName: String, context: Context): PayOutAPI {
            return PayOutAPIBuilder().createPayOutAPI(context, hostName).payOutAPI
        }

        fun getPaymentCard(hostName: String, context: Context, isSandbox: Boolean): PaymentCardAPI {
            return PaymentCardAPIBuilder().createPaymentCardAPI(context, hostName, isSandbox).paymentCardAPI
        }

        fun getLinkedBank(hostName: String, context: Context): IbanAPI {
            return IBANAPIBuilder().createIbanAPIComponent(context, hostName).ibanAPI
        }

        fun getPayIn(hostName: String, context: Context) : CashInAPI {
            return CashInAPIBuilder().createCashInAPIComponent(hostName, context).payInAPI
        }

        fun getTransactions(hostName: String, context: Context): TransactionsAPI {
            return TransactionAPIBuilder().createTransactionsAPI(context, hostName).transactionsAPI
        }

        fun getProfile(hostName: String, context: Context): ProfileAPI {
            return ProfileAPIBuilder().createProfileAPIComponent(hostName, context).profileAPI
        }

        fun getEnterprise(hostName: String, context: Context): EnterpriseAPI {
            return EnterpriseAPIBuilder().createEnterpriseAPIComponent(hostName, context).enterpriseAPI
        }

        fun buildTransfer(hostName: String, context: Context): TransferAPI {
            return TransferAPIBuilder().createTrasnferAPIComponent(context, hostName).transferAPI
        }

        fun buildSct(hostName: String, context: Context): SctAPI {
            return SctAPIBuilder().createSctAPIComponent(context, hostName).sctAPI
        }

    }

}

