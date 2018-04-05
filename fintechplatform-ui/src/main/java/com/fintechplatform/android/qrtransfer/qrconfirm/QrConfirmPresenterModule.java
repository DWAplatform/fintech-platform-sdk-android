package com.fintechplatform.android.qrtransfer.qrconfirm;

import com.fintechplatform.android.account.balance.helpers.BalanceHelper;
import com.fintechplatform.android.models.DataAccount;
import com.fintechplatform.android.money.FeeHelper;
import com.fintechplatform.android.money.MoneyHelper;
import com.fintechplatform.android.net.NetHelper;
import com.fintechplatform.android.transfer.api.TransferAPI;

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
