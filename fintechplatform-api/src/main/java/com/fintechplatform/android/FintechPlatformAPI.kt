package com.fintechplatform.android

import android.content.Context
import com.fintechplatform.android.account.balance.BalanceBuilder
import com.fintechplatform.android.account.balance.api.BalanceAPI
import com.fintechplatform.android.card.PaymentCardBuilder
import com.fintechplatform.android.card.api.PaymentCardAPI
import com.fintechplatform.android.enterprise.EnterpriseBuilder
import com.fintechplatform.android.enterprise.api.EnterpriseAPI
import com.fintechplatform.android.iban.IBANBuilder
import com.fintechplatform.android.iban.api.IbanAPI
import com.fintechplatform.android.payin.PayInBuilder
import com.fintechplatform.android.payin.api.PayInAPI
import com.fintechplatform.android.payout.PayOutBuilder
import com.fintechplatform.android.payout.api.PayOutAPI
import com.fintechplatform.android.profile.ProfileBuilder
import com.fintechplatform.android.profile.api.ProfileAPI
import com.fintechplatform.android.sct.SctBuilder
import com.fintechplatform.android.sct.api.SctAPI
import com.fintechplatform.android.transactions.TransactionsBuilder
import com.fintechplatform.android.transactions.api.TransactionsAPI
import com.fintechplatform.android.transfer.TransferBuilder
import com.fintechplatform.android.transfer.api.TransferAPI

class FintechPlatformAPI {

    companion object {

        fun getBalance(hostName: String, context: Context): BalanceAPI {
            return BalanceBuilder().createBalanceAPIComponent(hostName, context).balanceAPI
        }


        fun getPayOut(hostName: String, context: Context): PayOutAPI {
            return PayOutBuilder().createPayOutAPI(context, hostName).payOutAPI
        }

        fun getPaymentCard(hostName: String, context: Context, isSandbox: Boolean): PaymentCardAPI {
            return PaymentCardBuilder().createPaymentCardAPI(context, hostName, isSandbox).paymentCardAPI
        }

        fun getLinkedBank(hostName: String, context: Context): IbanAPI {
            return IBANBuilder().createIbanAPIComponent(context, hostName).ibanAPI
        }

        fun getPayIn(hostName: String, context: Context) : PayInAPI {
            return PayInBuilder().createPayInAPIComponent(hostName, context).payInAPI
        }

        fun buildTransactions(hostName: String, context: Context): TransactionsAPI {
            return TransactionsBuilder().createTransactionsAPI(context, hostName).transactionsAPI
        }

        fun getProfile(hostName: String, context: Context): ProfileAPI {
            return ProfileBuilder().createProfileAPIComponent(hostName, context).profileAPI
        }

        fun getEnterprise(hostName: String, context: Context): EnterpriseAPI {
            return EnterpriseBuilder().createEnterpriseAPIComponent(hostName, context).enterpriseAPI
        }

        fun buildTransfer(hostName: String, context: Context): TransferAPI {
            return TransferBuilder().createTrasnferAPIComponent(context, hostName).transferAPI
        }

        fun buildSct(hostName: String, context: Context): SctAPI {
            return SctBuilder().createSctAPIComponent(context, hostName).sctAPI
        }

    }

}

