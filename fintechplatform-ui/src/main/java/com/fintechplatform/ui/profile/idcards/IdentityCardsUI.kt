package com.fintechplatform.ui.profile.idcards

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.android.volley.toolbox.Volley
import com.fintechplatform.api.account.kyc.di.KycAPIModule
import com.fintechplatform.api.net.NetModule
import com.fintechplatform.api.profile.api.IdsDocumentsAPIModule
import com.fintechplatform.api.profile.api.ProfileAPIModule
import com.fintechplatform.ui.models.DataAccount
import com.fintechplatform.ui.profile.idcards.di.DaggerIdentityCardsViewComponent
import com.fintechplatform.ui.profile.idcards.di.IdentityCardsPresenterModule
import com.fintechplatform.ui.profile.idcards.di.IdentityCardsViewComponent


class IdentityCardsUI(private val hostName: String, private val configuration: DataAccount) {
    
    fun startActivity(context: Context) {
        val intent = Intent(context, IdentityCardsActivity::class.java)
        val args = Bundle()
        args.putString("hostname", hostName)
        args.putParcelable("dataAccount", configuration)
        intent.putExtras(args)
        context.startActivity(intent)
    }
    
    fun createFragment(hostname: String, dataAccount: DataAccount): IdentityCardsFragment {
        return IdentityCardsFragment.newInstance(hostname, dataAccount)
    }
    
    object Builder {
        fun buildIdCardsViewComponent(context: Context, view: IdentityCardsContract.View, hostName: String, configuration: DataAccount): IdentityCardsViewComponent {
            return DaggerIdentityCardsViewComponent.builder()
                    .netModule(NetModule(Volley.newRequestQueue(context), hostName))
                    .identityCardsPresenterModule(IdentityCardsPresenterModule(view, configuration))
                    .idsDocumentsAPIModule(IdsDocumentsAPIModule(hostName))
                    .kycAPIModule(KycAPIModule(hostName))
                    .profileAPIModule(ProfileAPIModule(hostName))
                    .build()
        }
    }

}
