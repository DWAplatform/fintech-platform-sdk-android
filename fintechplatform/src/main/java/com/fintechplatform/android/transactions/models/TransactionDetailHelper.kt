package com.fintechplatform.android.transactions.models

class TransactionDetailHelper {

    // FIXME: load from server in configuration phase.
    val statusMap = mapOf(
            "SUCCEEDED" to "Transazione completata",
            "FAILED" to "Transazione annullata")

    // FIXME: load from server in configuration phase.
    // TODO vedi codice su Mangopay.scala account.impl.asp.mangopay.application iniziano per asp_xxxx

    val resultCodeMap = mapOf(
            // Operation failed
            "001001" to "Credito insufficiente",
            "001002" to "L'autore non è il proprietario del conto",
            "001011" to "L'importo indicato è più elevato del massimo permesso",
            "001012" to "L'importo indicato è minore del minimo permesso",
            "001013" to "L'importo indicato non è valido",
            "001014" to "Il credito deve essere maggiore di 0",

            /*
(
    // Operation failed

    "001001" -> com.fintechplatform.account.impl.domain.model.ErrorASPSpecific("insufficient_wallet_balance", "Insufficient wallet balance"),
    "001002" -> com.fintechplatform.account.impl.domain.model.ErrorASPSpecific("author_is_not_wallet_owner", "The user ID used as Author has to be the wallet owner"),
    "001011" -> com.fintechplatform.account.impl.domain.model.ErrorASPSpecific("transaction_amount_is_higher_than_maximum_permitted_amount", "Transaction amount is higher than maximum permitted amount"),
    "001012" -> com.fintechplatform.account.impl.domain.model.ErrorASPSpecific("transaction_amount_is_lower_than_maximum_permitted_amount", "Transaction amount is lower than minimum permitted amount"),
    "001013" -> com.fintechplatform.account.impl.domain.model.ErrorASPSpecific("invalid_transaction_amount", "Invalid transaction amount"),
    "001014" -> com.fintechplatform.account.impl.domain.model.ErrorASPSpecific("creditedfunds_must_be_more_than_0", "CreditedFunds must be more than 0 (DebitedFunds can not equal Fees)"),

    "101108" -> com.fintechplatform.account.impl.domain.model.ErrorASPSpecific("transaction_refused", "Transaction refused: the Debited Wallet and the Credited Wallet must be different"),


    // PayIn Web errors
    "001030" -> com.fintechplatform.account.impl.domain.model.ErrorASPSpecific("user_has_not_been_redirected", "The user never gets the payment page and never opens the Payline session"),
    "001031" -> com.fintechplatform.account.impl.domain.model.ErrorASPSpecific("user_canceled_the_payment","The User clicks on Cancelled on the payment page"),
    "001032" -> com.fintechplatform.account.impl.domain.model.ErrorASPSpecific("user_is_filling_in_the_payment_card_details","The user is still on the payment page (Payline session)"),
    "001033" -> com.fintechplatform.account.impl.domain.model.ErrorASPSpecific("user_has_not_been_redirected_then_the_payment_session_has_expired","The session has expired so the Payin Web is failed. The user has gone on the payment page"),
    "001034" -> com.fintechplatform.account.impl.domain.model.ErrorASPSpecific("user_has_let_the_payment_session_expire_without_paying","The user went to the payment page but let the session expired. So the Payin Web has failed"),

    // Refund transaction errors
    "001401" -> com.fintechplatform.account.impl.domain.model.ErrorASPSpecific("transaction_has_already_been_successfully_refunded","Transaction has already been successfully refunded"),


    "001596" -> com.fintechplatform.account.impl.domain.model.ErrorASPSpecific("card_registration_errors", "card registration errors"),
    "001597" -> com.fintechplatform.account.impl.domain.model.ErrorASPSpecific("card_registration_errors", "card registration errors"),
    "001598" -> com.fintechplatform.account.impl.domain.model.ErrorASPSpecific("card_registration_errors", "card registration errors"),
    "001599" -> com.fintechplatform.account.impl.domain.model.ErrorASPSpecific("card_registration_errors", "card registration errors"),


    // Operation failed
    "001999" -> com.fintechplatform.account.impl.domain.model.ErrorASPSpecific("Generic Operation error", "EMI has no information for the bank yet"),

    "005403" -> com.fintechplatform.account.impl.domain.model.ErrorASPSpecific("refund_transaction_errors", "Refund transaction errors"),
    "005404" -> com.fintechplatform.account.impl.domain.model.ErrorASPSpecific("refund_transaction_errors", "Refund transaction errors"),
    "005405" -> com.fintechplatform.account.impl.domain.model.ErrorASPSpecific("refund_transaction_errors", "Refund transaction errors"),
    "005407" -> com.fintechplatform.account.impl.domain.model.ErrorASPSpecific("refund_transaction_errors", "Refund transaction errors"),


    "009999" -> com.fintechplatform.account.impl.domain.model.ErrorASPSpecific("card_registration_errors", "card registration errors"),

    "101001" -> com.fintechplatform.account.impl.domain.model.ErrorASPSpecific("the_user_does_not_complete_transaction", "The user does not complete transaction"),
    "101002" -> com.fintechplatform.account.impl.domain.model.ErrorASPSpecific("the_transaction_has_been_cancelled_by_the_user", "The transaction has been cancelled by the user"),

    // Transaction Refused
    "101101" -> com.fintechplatform.account.impl.domain.model.ErrorASPSpecific("transaction_refused_by_the_bank", "The error \"Do not honor\" is a message from the bank. You could get it for several raisons: Maximum amount spent per month has been reached on this card // Maximum amount spent on internet per month has been reached on this card // No more funds on bank account"),
    "101102" -> com.fintechplatform.account.impl.domain.model.ErrorASPSpecific("Transaction_refused_by_the_bank_amount_limit", "You will get this error if the user reached a bank amount limit. It could be: Maximum pre authorized amount reached // Maximum amount spent per month has been reached on this card // Maximum amount spent on internet per month has been reached on this card"),
    "101103" -> com.fintechplatform.account.impl.domain.model.ErrorASPSpecific("transaction_refused_by_the_terminal", "Transaction refused by the terminal"),
    "101104" -> com.fintechplatform.account.impl.domain.model.ErrorASPSpecific("transaction_refused_by_the_bank_card_limit_reached", "Transaction refused by the bank (card limit reached)"),
    "101105" -> com.fintechplatform.account.impl.domain.model.ErrorASPSpecific("the_card_has_expired", "The card has expired"),
    "101106" -> com.fintechplatform.account.impl.domain.model.ErrorASPSpecific("the_card_is_inactive", "The card is not active accourding to the bank and can therefore not be used"),


    "101111" -> com.fintechplatform.account.impl.domain.model.ErrorASPSpecific("maximum_number_of_attempts_reached", "Too much attempts for the same transaction"),
    "101112" -> com.fintechplatform.account.impl.domain.model.ErrorASPSpecific("maximum_amount_exceeded", "This is a card limitation on spent amount"),
    "101113" -> com.fintechplatform.account.impl.domain.model.ErrorASPSpecific("maximum_uses_exceeded", "Maximum attempts with this cards reached. You must try again after 24 hours"),
    "101115" -> com.fintechplatform.account.impl.domain.model.ErrorASPSpecific("debit_limit_exceeded", "This is a card limitation on spent amount"),
    "101116" -> com.fintechplatform.account.impl.domain.model.ErrorASPSpecific("amount_limit", "The contribution transaction has failed"),
    "101199" -> com.fintechplatform.account.impl.domain.model.ErrorASPSpecific("transaction_refused", "The transaction has been refused by the bank. Contact your bank in order to have more information about it"),

    // Secure mode / 3DSecure errors
    "101301" -> com.fintechplatform.account.impl.domain.model.ErrorASPSpecific("secure_mode_3dsecure_authentication_has_failed", "Secure mode: 3DSecure authentication has failed"),
    "101302" -> com.fintechplatform.account.impl.domain.model.ErrorASPSpecific("secure_mode_the_card_is_not_enrolled_with_3dsecure", "Secure mode: The card is not enrolled with 3DSecure"),
    "101303" -> com.fintechplatform.account.impl.domain.model.ErrorASPSpecific("secure_mode_the_card_is_not_compatible_with_3dsecure", "Secure mode: The card is not compatible with 3DSecure"),
    "101304" -> com.fintechplatform.account.impl.domain.model.ErrorASPSpecific("secure_mode_the_3dsecure_authentication_session_has_expired", "Secure mode: The 3DSecure authentication session has expired"),
    "101399" -> com.fintechplatform.account.impl.domain.model.ErrorASPSpecific("secure_mode_3dsecure_authentication_is_not_available", "Secure mode: 3DSecure authentication is not available"),



    "101410" -> com.fintechplatform.account.impl.domain.model.ErrorASPSpecific("The card is not active", "The card has not been disabled on Mangopay and is no longer useable"),

    // Tokenization / Card registration errors
    // Card input errors
    "105101" -> com.fintechplatform.account.impl.domain.model.ErrorASPSpecific("invalid_card_number", "Invalid card number"),
    "105102" -> com.fintechplatform.account.impl.domain.model.ErrorASPSpecific("invalid_cardholder_name", "The card holder name given doesn’t match the real owner of the card"),
    "105103" -> com.fintechplatform.account.impl.domain.model.ErrorASPSpecific("invalid_pin_code", "Invalid PIN code"),
    "105104" -> com.fintechplatform.account.impl.domain.model.ErrorASPSpecific("invalid_pin_format", "Invalid PIN format"),





    "105202" -> com.fintechplatform.account.impl.domain.model.ErrorASPSpecific("card_registration_errors", "Card registration errors"),
    "105203" -> com.fintechplatform.account.impl.domain.model.ErrorASPSpecific("card_registration_errors", "Card registration errors"),
    "105204" -> com.fintechplatform.account.impl.domain.model.ErrorASPSpecific("card_registration_errors", "Card registration errors"),
    "105205" -> com.fintechplatform.account.impl.domain.model.ErrorASPSpecific("card_registration_errors", "Card registration errors"),
    "105206" -> com.fintechplatform.account.impl.domain.model.ErrorASPSpecific("card_registration_errors", "Card registration errors"),
    "105299" -> com.fintechplatform.account.impl.domain.model.ErrorASPSpecific("card_registration_errors", "Card registration errors"),

    "101699" -> com.fintechplatform.account.impl.domain.model.ErrorASPSpecific("card_registration_errors", "Card registration errors"),

    ///////////
    "01902" -> com.fintechplatform.account.impl.domain.model.ErrorASPSpecific("card_registration_errors", "Card registration errors"),

    "02101" -> com.fintechplatform.account.impl.domain.model.ErrorASPSpecific("card_registration_errors", "Card registration errors"),

    "02624" -> com.fintechplatform.account.impl.domain.model.ErrorASPSpecific("card_registration_errors", "Card registration errors"),
    "02625" -> com.fintechplatform.account.impl.domain.model.ErrorASPSpecific("card_registration_errors", "Card registration errors"),
    "02626" -> com.fintechplatform.account.impl.domain.model.ErrorASPSpecific("card_registration_errors", "Card registration errors"),
    "02627" -> com.fintechplatform.account.impl.domain.model.ErrorASPSpecific("card_registration_errors", "Card registration errors"),
    "02628" -> com.fintechplatform.account.impl.domain.model.ErrorASPSpecific("card_registration_errors", "Card registration errors"),

    "02631" -> com.fintechplatform.account.impl.domain.model.ErrorASPSpecific("card_registration_errors", "Card registration errors"),
    "02632" -> com.fintechplatform.account.impl.domain.model.ErrorASPSpecific("card_registration_errors", "Card registration errors"),

    "09101" -> com.fintechplatform.account.impl.domain.model.ErrorASPSpecific("card_registration_errors", "Card registration errors"),
    "09102" -> com.fintechplatform.account.impl.domain.model.ErrorASPSpecific("card_registration_errors", "Card registration errors"),
    "09104" -> com.fintechplatform.account.impl.domain.model.ErrorASPSpecific("card_registration_errors", "Card registration errors"),


    "09201" -> com.fintechplatform.account.impl.domain.model.ErrorASPSpecific("card_registration_errors", "Card registration errors"),


    // Technical errors
    "009103" -> com.fintechplatform.account.impl.domain.model.ErrorASPSpecific("psp_configuration_error", "PSP configuration error"),
    "009199" -> com.fintechplatform.account.impl.domain.model.ErrorASPSpecific("psp_technical_error", "PSP technical error"),
    "009499" -> com.fintechplatform.account.impl.domain.model.ErrorASPSpecific("bank_technical_error", "Bank technical error"),
    "009101" -> com.fintechplatform.account.impl.domain.model.ErrorASPSpecific("psp_timeout_please_try_later", "PSP timeout please try later"),

    // Specific JS Kit card registration errors


    // KYC transaction errors
    "002999" -> com.fintechplatform.account.impl.domain.model.ErrorASPSpecific("blocked_due_to_a_debited_user_kyc_limitations", "One of the user’s who has contributed to the wallet being debited needs to be KYC verified (more info)"),
    "002998" -> com.fintechplatform.account.impl.domain.model.ErrorASPSpecific("blocked_due_to_the_bank_account_owner_kyc_limitations", "Blocked due to the Bank Account Owner’s KYC limitations (maximum debited or credited amount reached)"),

    // Transaction fraud issue
    "008999" -> com.fintechplatform.account.impl.domain.model.ErrorASPSpecific("fraud_policy_error", "Fraud policy error"),
    "008001" -> com.fintechplatform.account.impl.domain.model.ErrorASPSpecific("fraud_policy_error", "Fraud policy error"),
    "008002" -> com.fintechplatform.account.impl.domain.model.ErrorASPSpecific("fraud_policy_error", "Fraud policy error"),
    "008003" -> com.fintechplatform.account.impl.domain.model.ErrorASPSpecific("fraud_policy_error", "Fraud policy error"),
    "008004" -> com.fintechplatform.account.impl.domain.model.ErrorASPSpecific("fraud_policy_error", "Fraud policy error"),
    "008005" -> com.fintechplatform.account.impl.domain.model.ErrorASPSpecific("fraud_policy_error", "Fraud policy error"),
    "008006" -> com.fintechplatform.account.impl.domain.model.ErrorASPSpecific("fraud_policy_error", "Fraud policy error"),
    "008007" -> com.fintechplatform.account.impl.domain.model.ErrorASPSpecific("fraud_policy_error", "Fraud policy error"),
    "008500" -> com.fintechplatform.account.impl.domain.model.ErrorASPSpecific("fraud_policy_error", "Fraud policy error"),
    "008600" -> com.fintechplatform.account.impl.domain.model.ErrorASPSpecific("fraud_policy_error", "Fraud policy error"),
    "008700" -> com.fintechplatform.account.impl.domain.model.ErrorASPSpecific("fraud_policy_error", "Fraud policy error"),




    // Payout error codes
    "121999" -> com.fintechplatform.account.impl.domain.model.ErrorASPSpecific("generic_withdrawal_error", "Generic withdrawal error"),
    "121001" -> com.fintechplatform.account.impl.domain.model.ErrorASPSpecific("generic_withdrawal_error", "Generic withdrawal error"),
    "121002" -> com.fintechplatform.account.impl.domain.model.ErrorASPSpecific("generic_withdrawal_error", "Generic withdrawal error"),
    "121003" -> com.fintechplatform.account.impl.domain.model.ErrorASPSpecific("generic_withdrawal_error", "Generic withdrawal error"),
    "121004" -> com.fintechplatform.account.impl.domain.model.ErrorASPSpecific("generic_withdrawal_error", "Generic withdrawal error"),
    "121005" -> com.fintechplatform.account.impl.domain.model.ErrorASPSpecific("generic_withdrawal_error", "Generic withdrawal error"),

    "UNKNOWN" -> com.fintechplatform.account.impl.domain.model.ErrorASPSpecific("", ""),

    "DWA000001" -> com.fintechplatform.account.impl.domain.model.ErrorASPSpecific("errore_comunicazione_emi", "Errore comunicazione EMI")


  )
 */

            // PayIn Web errors
            "001030" to "Sessione di pagamento non aperta",
            "001031" to "Il pagamento è stato annullato",
            "001032" to "L'utente è ancora nella pagina di pagamento",
            "001033" to "Sessione web terminata",
            "001034" to "Sessione terminata senza effettuare il pagamento",

            // Refund transaction errors
            "001401" to "Errore refund",


            "001596" to "Errore registrazione carta di credito",
            "001597" to "Errore registrazione carta di credito",
            "001598" to "Errore registrazione carta di credito",
            "001599" to "Errore registrazione carta di credito",

            // Operation failed
            "001999" to "Informazioni bancarie non disponibili",

            "005403" to "Errore refund",
            "005404" to "Errore refund",
            "005405" to "Errore refund",
            "005407" to "Errore refund",


            "009999" to "Errore registrazione carta di credito",

            "101001" to "La transazione non è stata completata",
            "101002" to "Il pagamento è stato annullato",

            // Transaction Refused
            "101101" to "Transazione rifiutata dalla banca",
            "101102" to "Transazione rifiutata dalla banca causa limite importo",
            "101103" to "Transazione rifiutata dal terminale",
            "101104" to "Transazione rifiutata dalla banca causa limite raggiunto dalla carta di credito",
            "101105" to "Carta di credito scaduta",
            "101106" to "Carta di credito non attivata",


            "101111" to "Raggiunto numero massimo tentativi per la stessa transazione",
            "101112" to "Raggiunto limite importo carta di credito",
            "101113" to "Raggiunto il numero massimo di tentativi per la carta di credito, riprovare tra 24 ore",
            "101115" to "Raggiunto limite di spesa per la carta di credito",
            "101116" to "Errore nel trasferimento fondi",
            "101199" to "Transazione rifiutata dalla banca. Contattare la banca per avere informazioni in merito",

            // Secure mode / 3DSecure errors
            "101301" to "Autenticazione 3D fallita",
            "101302" to "Carta non compatibile con il 3D secure",
            "101303" to "Carta non compatibile con il 3D secure",
            "101304" to "Sessione 3D secure terminata",
            "101399" to "Autenticazione 3D non disponibile",



            "101410" to "Carta di credito non attiva",

            // Tokenization / Card registration errors
            // Card input errors
            "105101" to "Numero carta non valida",
            "105102" to "Nome indicato nella carta non corrisponde al proprietario",
            "105103" to "Codice PIN della carta di credito non valido",
            "105104" to "Formato codice PIN della carta di credito non valido",





            "105202" to "Errore registrazione carta di credito",
            "105203" to "Errore registrazione carta di credito",
            "105204" to "Errore registrazione carta di credito",
            "105205" to "Errore registrazione carta di credito",
            "105206" to "Errore registrazione carta di credito",
            "105299" to "Errore registrazione carta di credito",

            "101699" to "Errore registrazione carta di credito",

            ///////////
            "01902" to "Errore registrazione carta di credito",

            "02101" to "Errore registrazione carta di credito",

            "02624" to "Errore registrazione carta di credito",
            "02625" to "Errore registrazione carta di credito",
            "02626" to "Errore registrazione carta di credito",
            "02627" to "Errore registrazione carta di credito",
            "02628" to "Errore registrazione carta di credito",

            "02631" to "Errore registrazione carta di credito",
            "02632" to "Errore registrazione carta di credito",

            "09101" to "Errore registrazione carta di credito",
            "09102" to "Errore registrazione carta di credito",
            "09104" to "Errore registrazione carta di credito",


            "09201" to "Errore registrazione carta di credito",


            // Technical errors
            "009103" to "Errore tecnico di configurazione",
            "009199" to "Errore tecnico EMI",
            "009499" to "Errore tecnico Bancario",
            "009101" to "Errore tecnico di timeout. Si prega di riprovare",

            // Specific JS Kit card registration errors


            // KYC transaction errors
            "002999" to "Raggiunto il limite per utente light. Attendere validazione documenti di identità",
            "002998" to "Operazione bloccata dalla banca",

            // Transaction fraud issue
            "008999" to "Rilevata transazione fraudolenta",
            "008001" to "Rilevata transazione fraudolenta",
            "008002" to "Rilevata transazione fraudolenta",
            "008003" to "Rilevata transazione fraudolenta",
            "008004" to "Rilevata transazione fraudolenta",
            "008005" to "Rilevata transazione fraudolenta",
            "008006" to "Rilevata transazione fraudolenta",
            "008007" to "Rilevata transazione fraudolenta",
            "008500" to "Rilevata transazione fraudolenta",
            "008600" to "Rilevata transazione fraudolenta",
            "008700" to "Rilevata transazione fraudolenta",




            // Payout error codes
            "121999" to "Errore prelievo generico",
            "121001" to "Errore prelievo generico",
            "121002" to "Errore prelievo generico",
            "121003" to "Errore prelievo generico",
            "121004" to "Errore prelievo generico",
            "121005" to "Errore prelievo generico",

            "000000" to "Trasazione completata con successo",
            "UNKNOWN"  to "",

            "DWA000001" to "Errore comunicazione EMI"

    )

    fun stateDescription(state: String?): String =
            state?.let { s -> statusMap[s] ?: "" } ?: ""


    fun resultCodeDescription(resultcode: String?): String =
            resultcode?.let { r -> resultCodeMap[r] ?: "(${resultcode}) Errore generico" } ?: ""


}