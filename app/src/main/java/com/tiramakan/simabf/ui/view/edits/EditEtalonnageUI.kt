package com.tiramakan.simabf.ui.view.edits

/**
 * Created by tiramakan on 17/01/2016.
 */
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.tiramakan.simabf.bootstrap.BootstrapApplication
import com.tiramakan.simabf.R
import com.tiramakan.simabf.core.constants.Constants
import com.tiramakan.simabf.core.modelView.EtalonViewModel
import com.tiramakan.simabf.core.modelView.EtalonnageToUI
import com.tiramakan.simabf.core.modelView.StockViewModel
import com.tiramakan.simabf.core.models.realm.Depot
import com.tiramakan.simabf.databinding.SaisieEtalonnageBinding
import com.tiramakan.simabf.ui.view.Lists.ListEtalonnagesUI
import com.tiramakan.simabf.ui.view.Lists.ListStocksUI
import com.tiramakan.simabf.ui.view.adapters.TableDataAdapterEtalonnage
import com.tiramakan.simabf.ui.view.baseClasses.BaseFragment
import com.tiramakan.simabf.ui.view.interfaces.OnBackPressedListener
import com.tiramakan.simabf.ui.view.utils.Binding.BindingComponent

import org.parceler.Parcels
import java.util.ArrayList

import javax.inject.Inject

/**
 * Created by tiramakan on 17/01/2016.
 */
class EditEtalonnageUI : BaseFragment() {

    var etalonViewModel: EtalonViewModel? = null
        @Inject set
    protected var etalonnageUIList: List<EtalonnageToUI> = ArrayList()
    protected lateinit var mainView: View
    lateinit var dataAdapter: TableDataAdapterEtalonnage
    var marcheChoisi: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BootstrapApplication.component().inject(this)
        setHasOptionsMenu(false)
        if (arguments != null) {
            marcheChoisi = arguments?.getString(Constants.Extra.NOM_MARCHE_TAG).toString()
         }
    }

    override fun setMenuVisibility(visible: Boolean) {
        super.setMenuVisibility(false)

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(ETALONNAGE_TAG, Parcels.wrap(etalonnageUIList))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = item.itemId
        when (id) {
            R.id.action_next -> if (etalonViewModel?.isNotEmpty!!) {
                etalonViewModel?.save(marcheChoisi)
                myParent.showFragment(ListEtalonnagesUI())
            }
        }
        return true
    }

    //Set your layout here
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        DataBindingUtil.setDefaultComponent(BindingComponent())
        val binding = DataBindingUtil.inflate<SaisieEtalonnageBinding>(inflater, R.layout.saisie_etalonnage, container, false)
        mainView = binding.root
        setHasOptionsMenu(false)// je desactive la suite
        etalonViewModel?.updateEtalonnages(marcheChoisi)
        etalonnageUIList = etalonViewModel?.listEtalonnages!!
        dataAdapter =  TableDataAdapterEtalonnage(context, etalonnageUIList)
        binding.tableView.setDataAdapter(dataAdapter)
        binding.etalonViewModel = etalonViewModel


        val backPressedListener = object : OnBackPressedListener {
            override fun doBack(): Boolean {
                return true
            }
        }
        setOnBackPressedListener(backPressedListener)

        return mainView
    }

    companion object {
        val ETALONNAGE_TAG = "ETALONNAGE_TAG"
    }


}
