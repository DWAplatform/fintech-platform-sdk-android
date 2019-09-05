package com.fintechplatform.ui.qrtransfer;

import com.fintechplatform.ui.models.DataAccount;
import com.fintechplatform.ui.qrtransfer.creditui.QrCreditTransferUIModule;
import com.fintechplatform.ui.qrtransfer.qrconfirm.QrConfirmUIModule;

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
                .build();
    }
}
