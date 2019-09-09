package com.fintechplatform.ui

import android.content.Context
import com.fintechplatform.ui.account.balance.BalanceBuilder
import com.fintechplatform.ui.account.financialdata.FinancialDataBuilder
import com.fintechplatform.ui.card.PaymentCardUI
import com.fintechplatform.ui.db.PlatformDB
import com.fintechplatform.ui.enterprise.EnterpriseBuilder
import com.fintechplatform.ui.iban.IbanUI
import com.fintechplatform.ui.models.DataAccount
import com.fintechplatform.ui.payin.PayInUI
import com.fintechplatform.ui.payout.PayOutUI
import com.fintechplatform.ui.profile.ProfileBuilder
import com.fintechplatform.ui.qrtransfer.QrTransferBuilder
import com.fintechplatform.ui.sct.SctBuilder
import com.fintechplatform.ui.secure3d.Secure3DUI
import com.fintechplatform.ui.transactions.ui.TransactionsUI
import com.fintechplatform.ui.transfer.TransferUI
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

        fun buildPayInUI(hostName: String, dataAccount: DataAccount, isSandbox: Boolean) : PayInUI {
            return PayInUI(hostName, dataAccount, isSandbox)
        }

        fun buildBalance(): BalanceBuilder {
            return BalanceBuilder()
        }

        fun build3DSecureUI(): Secure3DUI {
            return Secure3DUI()
        }

        fun buildPayOutUI(hostName: String, dataAccount: DataAccount): PayOutUI {
            return PayOutUI(hostName, dataAccount)
        }

        fun buildPaymentCardUI(hostName: String, dataAccount: DataAccount, isSandbox: Boolean): PaymentCardUI {
            return PaymentCardUI(hostName, dataAccount, isSandbox)
        }

        fun buildIBAN(): IbanUI {
            return IbanUI()
        }

        fun buildTransactionsUI(hostName: String, dataAccount: DataAccount): TransactionsUI {
            return TransactionsUI(hostName, dataAccount)
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

        fun buildTransferUI(hostName: String, dataAccount: DataAccount): TransferUI {
            return TransferUI(hostName, dataAccount)
        }

        fun buildSct(): SctBuilder {
            return SctBuilder()
        }

        fun buildQr(): QrTransferBuilder {
            return QrTransferBuilder()
        }

    }

}

