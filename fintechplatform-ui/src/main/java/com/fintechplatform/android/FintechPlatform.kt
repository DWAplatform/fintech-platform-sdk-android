package com.fintechplatform.android

import android.content.Context
import com.fintechplatform.android.account.balance.BalanceBuilder
import com.fintechplatform.android.account.financialdata.FinancialDataBuilder
import com.fintechplatform.android.card.PaymentCardBuilder
import com.fintechplatform.android.db.PlatformDB
import com.fintechplatform.android.enterprise.EnterpriseBuilder
import com.fintechplatform.android.iban.IBANBuilder
import com.fintechplatform.android.payin.PayInBuilder
import com.fintechplatform.android.payout.PayOutBuilder
import com.fintechplatform.android.profile.ProfileBuilder
import com.fintechplatform.android.qrtransfer.QrTransferBuilder
import com.fintechplatform.android.sct.SctBuilder
import com.fintechplatform.android.secure3d.Secure3DBuilder
import com.fintechplatform.android.transactions.TransactionsBuilder
import com.fintechplatform.android.transfer.TransferBuilder
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
//
//        fun deleteEverything() {
//            PlatformDB.deleteEverything()
//        }

        /**
         * Factory method to get ClientCardAPI object
         */

        fun buildPayIn() : PayInBuilder {
            return PayInBuilder()
        }

        fun buildBalance(): BalanceBuilder {
            return BalanceBuilder()
        }

        fun build3DSecure(): Secure3DBuilder {
            return Secure3DBuilder()
        }

        fun buildPayOut(): PayOutBuilder {
            return PayOutBuilder()
        }

        fun buildPaymentCardBuilder(): PaymentCardBuilder {
            return PaymentCardBuilder()
        }

        fun buildIBAN(): IBANBuilder {
            return IBANBuilder()
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

