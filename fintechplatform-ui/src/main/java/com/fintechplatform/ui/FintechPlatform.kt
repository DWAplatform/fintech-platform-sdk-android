package com.fintechplatform.ui

import android.content.Context
import com.fintechplatform.ui.account.balance.BalanceBuilder
import com.fintechplatform.ui.account.financialdata.FinancialDataBuilder
import com.fintechplatform.ui.card.PaymentCardUI
import com.fintechplatform.ui.db.PlatformDB
import com.fintechplatform.ui.enterprise.EnterpriseBuilder
import com.fintechplatform.ui.iban.ui.IbanUI
import com.fintechplatform.ui.models.DataAccount
import com.fintechplatform.ui.payin.PayInUI
import com.fintechplatform.ui.payout.PayOutBuilder
import com.fintechplatform.ui.profile.ProfileBuilder
import com.fintechplatform.ui.qrtransfer.QrTransferBuilder
import com.fintechplatform.ui.sct.SctBuilder
import com.fintechplatform.ui.secure3d.Secure3DUI
import com.fintechplatform.ui.transactions.TransactionsBuilder
import com.fintechplatform.ui.transfer.TransferBuilder
import com.raizlabs.android.dbflow.config.FlowManager

class FintechPlatform {

    companion object {

        /**
         * Initialize FintechPlatform
         */
        fun initialize(context: Context) {
            FlowManager.init(context)
            PlatformDB.init()
        }

//        fun deleteEverything() {
//            PlatformDB.deleteEverything()
//        }


        /**
         * Factory method to get ClientU)Iobject
         */

        fun buildPayInUI(hostName: String, dataAccount: DataAccount, isSandbox: Boolean) : com.fintechplatform.ui.payin.PayInUI {
            return PayInUI(hostName, dataAccount, isSandbox)
        }

        fun buildBalance(): BalanceBuilder {
            return BalanceBuilder()
        }

        fun build3DSecureUI(): Secure3DUI {
            return Secure3DUI()
        }

        fun buildPayOut(): PayOutBuilder {
            return PayOutBuilder()
        }

        fun buildPaymentCardUI(hostName: String, dataAccount: DataAccount, isSandbox: Boolean): PaymentCardUI {
            return PaymentCardUI(hostName, dataAccount, isSandbox)
        }

        fun buildIBAN(): IbanUI {
            return IbanUI()
        }

        fun buildTransactions(): TransactionsBuilder {
            return TransactionsBuilder()
        }

        fun buildProfile(): ProfileBuilder {
            return ProfileBuilder()
        }

        fun buildEnterpriseData(): EnterpriseBuilder {
            return EnterpriseBuilder()
        }

        fun buildFinancialData(): FinancialDataBuilder {
            return FinancialDataBuilder()
        }

        fun buildTransfer(): TransferBuilder {
            return TransferBuilder()
        }

        fun buildSct(): SctBuilder {
            return SctBuilder()
        }

        fun buildQr(): QrTransferBuilder {
            return QrTransferBuilder()
        }

    }

}

