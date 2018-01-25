package com.fintechplatform.android.transactions.models

class TransactionDetailHelper {

    // FIXME: load from server in configuration phase.
    val statusMap = mapOf(
            "SUCCEEDED" to "Transazione completata",
            "FAILED" to "Transazione annullata")

    // FIXME: load from server in configuration phase.
    val resultCodeMap = mapOf(
            // Operation failed
            "001001" to "Credito insufficiente",
            "001002" to "L'autore non è il proprietario del conto",
            "001011" to "L'importo indicato è più elevato del massimo permesso",
            "001012" to "L'importo indicato è minore del minimo permesso",
            "001013" to "L'importo indicato non è valido",
            "001014" to "Il credito deve essere maggiore di 0",

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