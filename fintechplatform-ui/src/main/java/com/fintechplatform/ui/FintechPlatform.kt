package com.fintechplatform.ui

import android.content.Context
import com.fintechplatform.ui.account.balance.BalanceBuilder
import com.fintechplatform.ui.account.financialdata.FinancialDataBuilder
import com.fintechplatform.ui.card.PaymentCardBuilder
import com.fintechplatform.ui.cashin.CashInBuilder
import com.fintechplatform.ui.db.PlatformDB
import com.fintechplatform.ui.enterprise.EnterpriseBuilder
import com.fintechplatform.ui.iban.IBANBuilder
import com.fintechplatform.ui.iban.ui.IbanUI
import com.fintechplatform.ui.payout.PayOutBuilder
import com.fintechplatform.ui.profile.ProfileBuilder
import com.fintechplatform.ui.qrtransfer.QrTransferBuilder
import com.fintechplatform.ui.sct.SctBuilder
import com.fintechplatform.ui.secure3d.Secure3DBuilder
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

        fun buildIBAN(): IbanUI {
            return IBANBuilder().createIBANUI().ibanUI
        }

//
//        fun deleteEverything() {
//            PlatformDB.deleteEverything()
//        }


        /**
         * Factory method to get ClientUI object
         */

        fun buildCashIn() : CashInBuilder {
            return CashInBuilder()
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

