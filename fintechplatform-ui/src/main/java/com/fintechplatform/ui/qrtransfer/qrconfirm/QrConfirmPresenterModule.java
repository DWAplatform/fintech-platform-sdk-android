package com.fintechplatform.ui.qrtransfer.qrconfirm;

import com.fintechplatform.api.net.NetHelper;
import com.fintechplatform.api.transfer.api.TransferAPI;
import com.fintechplatform.ui.account.balance.helpers.BalanceHelper;
import com.fintechplatform.ui.models.DataAccount;
import com.fintechplatform.ui.money.FeeHelper;
import com.fintechplatform.ui.money.MoneyHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ingrid on 19/09/17.
 */
@Module
public class QrConfirmPresenterModule {
    private QrConfirmContract.View view;
    private DataAccount dataAccount;

    public QrConfirmPresenterModule(QrConfirmContract.View view, DataAccount dataAccount) {
        this.view = view;
        this.dataAccount = dataAccount;
    }

    @Singleton
    @Provides
    QrConfirmContract.Presenter providesPaymentConfirmPresenter(TransferAPI api,
                                                                BalanceHelper balanceHelper,
                                                                MoneyHelper moneyHelper,
                                                                FeeHelper feeHelper,
                                                                NetHelper netHelper){
        return new QrConfirmPresenter(view, api,  balanceHelper, dataAccount, moneyHelper, feeHelper, netHelper);
    }
}
