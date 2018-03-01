package com.fintechplatform.android.qrtransfer;

import com.fintechplatform.android.card.ui.PaymentCardUIModule;
import com.fintechplatform.android.models.DataAccount;
import com.fintechplatform.android.payin.ui.PayInUIModule;
import com.fintechplatform.android.qrtransfer.creditui.QrCreditTransferUIModule;
import com.fintechplatform.android.qrtransfer.qrconfirm.QrConfirmUIModule;

/**
 * Created by ingrid on 28/02/18.
 */

public class QrTransferBuilder {
    public QrTransferUIComponent createQrCreditTransferUI(String hostName, DataAccount dataAccount) {
        return DaggerQrTransferUIComponent.builder()
                .qrCreditTransferUIModule(new QrCreditTransferUIModule(hostName, dataAccount))
                .build();
    }

    public QrConfirmUIComponent createPaymentConfirmUI(String hostName, DataAccount dataAccount, boolean isSandbox) {
        return DaggerQrConfirmUIComponent.builder()
                .qrConfirmUIModule(new QrConfirmUIModule(dataAccount, hostName, isSandbox))
                .paymentCardUIModule(new PaymentCardUIModule(hostName, isSandbox, dataAccount))
                .payInUIModule(new PayInUIModule(hostName, dataAccount, isSandbox))
                .build();
    }
}
