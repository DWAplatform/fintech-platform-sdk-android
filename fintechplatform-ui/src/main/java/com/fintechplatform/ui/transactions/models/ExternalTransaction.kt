package com.fintechplatform.ui.transactions.models

import com.fintechplatform.api.money.Money

data class ExternalTransaction(val transactionId: String,
                               val ownerId: String,
                               val accountId: String,
                               val amount: Money,
                               val fee: Money,
                               val status: String,
                               val message: String?,
                               val accountingDate: String?,
                               val valueDate: String?,
                               val error: String?)

/*

{
  "status": {
    "code": "OK",
    "description": "Transactions retrieved successfully."
  },
  "error": {
    "description": ""
  },
  "payload": [
    {
      "transactionId": "00000000273015",
      "accountingDate": "01/08/2017",
      "valueDate": "01/08/2017",
      "amount": "-800.00",
      "currency": "EUR",
      "description": "BA JOHN DOE PAYMENT INVOICE 75/2017"
    }
  ]
}



 */