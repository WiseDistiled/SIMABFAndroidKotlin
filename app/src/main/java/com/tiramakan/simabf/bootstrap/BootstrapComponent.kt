package com.tiramakan.simabf.bootstrap

import com.tiramakan.simabf.ui.view.*
import com.tiramakan.simabf.ui.view.dashboards.Dashboard
import com.tiramakan.simabf.ui.view.request_responses.RequestResponseUI
import com.tiramakan.simabf.ui.view.Lists.*
import com.tiramakan.simabf.ui.view.edits.*
import com.tiramakan.simabf.ui.view.widgets.ChoiceOfReqMesure

import javax.inject.Singleton

import dagger.Component
@Singleton
@Component(modules = [AndroidModule::class, BootstrapModule::class])
interface BootstrapComponent {

    fun inject(target: BootstrapApplication)
    fun inject(target: MainActivity)
    fun inject(target: ListProduitUI)
    fun inject(target: ListMarche)
    fun inject(target: ListDepot)
    fun inject(target: ListPricesUI)
    fun inject(target: ListOffersVenteUI)
    fun inject(target: ListStocksUI)
    fun inject(target: Welcome)
    fun inject(target: RequestUI)
    fun inject(target: EditSettingsUI.MesPreferences)
    fun inject(target: EditGPSDepotUI)
    fun inject(target: RequestResponseUI)
    fun inject(target: ListMarcheUI)
    fun inject(target: Dashboard)
    fun inject(target: EditStockRowUI)
    fun inject(target: SearchActivity)
    fun inject(target: ListsUI)
    fun inject(target: ListDepotUI)
    fun inject(target: EditUserUI)
    fun inject(target: ListUsersUI)
    fun inject(target: AuthenticateFragment)
    fun inject(target: PartnersFragment)
    fun inject(target: EditPriceRowUI)
    fun inject(target: ChoiceOfDepotAGeolocaliser)
    fun inject(target: EditGPSMarcheUI)
    fun inject(target: ListOffersAchatUI)
    fun inject(target: SaisiePrixUI)
    fun inject(target: ChoiceOfProduitPrix)
    fun inject(target: ChoiceOfMarchePrix)
    fun inject(target: ChoiceOfTypePrix)
    fun inject(target: ChoiceOfDepotSaisieStock)
    fun inject(target: ChoiceOfProduitStock)
    fun inject(target: ChoiceOfProduitOffreAchat)
    fun inject(target: SaisieStockUI)
    fun inject(target: SaisieOffreAchatUI)
    fun inject(target: ChoiceOfProduitOffreVente)
    fun inject(target: SaisieOffreVenteUI)
    fun inject(target: ChoiceOfTypeGeoLocal)
    fun inject(target: ChoiceOfMarcheAGeolocaliser)
    fun inject(target: ChoiceOfReqMesure)
    fun inject(target: ChoiceOfProduitReqPrix)
    fun inject(target: ChoiceOfMarcheReqPrix)
    fun inject(target: ResumePriceRequest)
    fun inject(target: ResumeStockRequest)
    fun inject(target: ChoiceOfDepotReqStock)
    fun inject(target: ChoiceOfProduitReqStock)
    fun inject(target: ChoiceOfTypeOffre)
    fun inject(target: ChoiceOfProduitReqOffre)
    fun inject(target: ResumeOffreRequest)
    fun inject(target: EditEtalonnageUI)
    fun inject(target: ListEtalonnagesUI)
    fun inject(target: ChoiceOfMarcheEtalonnage)
    fun inject(target: TelechargerVideoUI)


}
