package com.fintechplatform.android.transactions.models

class TransactionDetailHelper {

    // FIXME: load from server in configuration phase.
    val statusMap = mapOf(
            "SUCCEEDED" to "Transazione completata",
            "FAILED" to "Transazione annullata",
            "CREATED" to "Transazione in fase di completamento" )

    val resultCodeMap = mapOf(
            // Operation failed
            "asp_insufficient_wallet_balance" to "Credito insufficiente",
            "asp_author_is_not_wallet_owner" to "L'autore non è il proprietario del conto",
            "asp_transaction_amount_is_higher_than_maximum_permitted_amount" to "L'importo indicato è più elevato del massimo permesso",
            "asp_transaction_amount_is_lower_than_maximum_permitted_amount" to "L'importo indicato è minore del minimo permesso",
            "asp_invalid_transaction_amount" to "L'importo indicato non è valido",
            "asp_creditedfunds_must_be_more_than_0" to "Il credito deve essere maggiore di 0",
            "asp_transaction_refused" to "Transazione rifiutata: debitore e creditore devono avere due wallet differenti",

            // PayIn Web errors
            "asp_user_has_not_been_redirected" to "Sessione di pagamento non aperta",
            "asp_user_canceled_the_payment" to "Il pagamento è stato annullato",
            "asp_user_is_filling_in_the_payment_card_details" to "L'utente è ancora nella pagina di pagamento",
            "asp_user_has_not_been_redirected_then_the_payment_session_has_expired" to "Sessione web terminata",
            "asp_user_has_let_the_payment_session_expire_without_paying" to "Sessione terminata senza effettuare il pagamento",


            // Refund transaction errors
            "asp_transaction_has_already_been_successfully_refunded" to "L'operazione è già stata effettuata con successo",


            "asp_card_registration_errors" to "Errore registrazione carta di credito",


            // Operation failed
            "asp_Generic Operation error" to "Informazioni bancarie non disponibili",

            "asp_refund_transaction_errors" to "Errore operazione",

            "asp_card_registration_errors" to "Errore registrazione carta di credito",

            "asp_the_user_does_not_complete_transaction" to "La transazione non è stata completata",
            "asp_the_transaction_has_been_cancelled_by_the_user" to "Il pagamento è stato annullato",

            // Transaction Refused
            "asp_transaction_refused_by_the_bank" to "Transazione rifiutata dalla banca",
            "asp_Transaction_refused_by_the_bank_amount_limit" to "Transazione rifiutata dalla banca causa limite importo",
            "asp_transaction_refused_by_the_terminal" to "Transazione rifiutata dal terminale",
            "asp_transaction_refused_by_the_bank_card_limit_reached" to "Transazione rifiutata dalla banca causa limite raggiunto dalla carta di credito",
            "asp_the_card_has_expired" to "Carta di credito scaduta",
            "asp_the_card_is_inactive" to "Carta di credito non attivata",


            "asp_maximum_number_of_attempts_reached" to "Raggiunto numero massimo tentativi per la stessa transazione",
            "asp_maximum_amount_exceeded" to "Raggiunto limite importo carta di credito",
            "asp_maximum_uses_exceeded" to "Raggiunto il numero massimo di tentativi per la carta di credito, riprovare tra 24 ore",
            "asp_debit_limit_exceeded" to "Raggiunto limite di spesa per la carta di credito",
            "asp_amount_limit" to "Errore nel trasferimento fondi",
            "asp_transaction_refused" to "Transazione rifiutata dalla banca. Contattare la banca per avere informazioni in merito",

            // Secure mode / 3DSecure errors
            "asp_secure_mode_3dsecure_authentication_has_failed" to "Autenticazione 3D fallita",
            "asp_secure_mode_the_card_is_not_enrolled_with_3dsecure\"" to "Carta non compatibile con il 3D secure",
            "asp_secure_mode_the_card_is_not_compatible_with_3dsecure\"" to "Carta non compatibile con il 3D secure",
            "asp_secure_mode_the_3dsecure_authentication_session_has_expired" to "Sessione 3D secure terminata",
            "asp_secure_mode_3dsecure_authentication_is_not_available" to "Autenticazione 3D non disponibile",



            "asp_The card is not active" to "Carta di credito non attiva",

            // Tokenization / Card registration errors
            // Card input errors
            "asp_invalid_card_number" to "Numero carta non valida",
            "asp_invalid_cardholder_name" to "Nome indicato nella carta non corrisponde al proprietario",
            "asp_invalid_pin_code\"" to "Codice PIN della carta di credito non valido",
            "asp_invalid_pin_format" to "Formato codice PIN della carta di credito non valido",

            // Technical errors
            "asp_psp_configuration_error" to "Errore tecnico di configurazione",
            "asp_psp_technical_error" to "Errore tecnico EMI",
            "asp_bank_technical_error" to "Errore tecnico Bancario",
            "asp_psp_timeout_please_try_later" to "Errore tecnico di timeout. Si prega di riprovare",

            // Specific JS Kit card registration errors


            // KYC transaction errors
            "asp_blocked_due_to_a_debited_user_kyc_limitations" to "Raggiunto il limite per utente light. Attendere validazione documenti di identità",
            "asp_blocked_due_to_the_bank_account_owner_kyc_limitations" to "Operazione bloccata dalla banca",

            // Transaction fraud issue
            "asp_fraud_policy_error" to "Rilevata transazione fraudolenta",

            // Payout error codes
            "asp_generic_withdrawal_error" to "Errore prelievo generico",

            "000000" to "Trasazione completata con successo",
            "asp_"  to "",

            "asp_errore_comunicazione_emi" to "Errore comunicazione EMI"
    )

    fun stateDescription(state: String?): String =
            state?.let { s -> statusMap[s] ?: "" } ?: ""


    fun resultCodeDescription(resultcode: String?): String =
            resultcode?.let { r -> resultCodeMap[r] ?: "(${resultcode}) Errore generico" } ?: ""


}