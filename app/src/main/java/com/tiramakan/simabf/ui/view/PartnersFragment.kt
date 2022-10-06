package com.tiramakan.simabf.ui.view

/**
 * Created by tiramakan on 17/01/2016.
 */
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager

import com.tiramakan.simabf.R
import com.tiramakan.simabf.bootstrap.BootstrapApplication
import com.tiramakan.simabf.bootstrap.util.UIUtils
import com.tiramakan.simabf.core.modelView.PartnerForUI
import com.tiramakan.simabf.ui.view.baseClasses.BaseFragment
import com.tiramakan.simabf.ui.view.recyclers.ContextMenuRecyclerView
import com.tiramakan.simabf.ui.view.recyclers.RecyclerViewAdapterPartner

import java.util.ArrayList

/**
 * Created by tiramakan on 17/01/2016.
 */
class PartnersFragment : BaseFragment() {
    //Wire the layout to the step
    override fun onResume() {
        super.onResume()
        myParent.setTitle("Partenaires")

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BootstrapApplication.component().inject(this)
        setHasOptionsMenu(false)
    }

    override fun doBack(): Boolean {
        return true
    }

    //Set your layout here
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.partners_layout, container, false)

        val recyclerView = view.findViewById<View>(R.id.partnerRecycler) as ContextMenuRecyclerView
        val context = context
        if (UIUtils.isPaysage(requireContext()))
            recyclerView.layoutManager = GridLayoutManager(context, 4)
        else
            recyclerView.layoutManager = GridLayoutManager(context, 3)

        //  recyclerView.setLayoutManager(new LinearLayoutManager(context));
        val partnerList = ArrayList<PartnerForUI>()
        val role = ""
        //        String description="APROSSA Afrique Verte ";
        //        partnerList.add(new PartnerForUI(R.drawable.aprossa_logo,description,role));
        //        description="AMASSA Afrique Verte ";
        //        partnerList.add(new PartnerForUI(R.drawable.amassa_afv_logo,description,role));
        //        description="Confédération Paysanne du Faso ";
        //        partnerList.add(new PartnerForUI(R.drawable.cpf_logo,description,role));
        var description = "Bureau d'Appui en Management et Informatique de gestion " + System.getProperty("line.separator") + " Développeur de solutions de gestion et de plateformes web-mobiles"
        partnerList.add(PartnerForUI(R.mipmap.bamig_logo, description, role))
        description = "SECODEV "
        partnerList.add(PartnerForUI(R.mipmap.logo_secodev_foreground, description, role))
        description = "Banque Mondiale "
        partnerList.add(PartnerForUI(R.mipmap.logo_banque_mondiale, description, role))
        //        description="Table Filière Karité "+System.getProperty("line.separator");
        //        partnerList.add(new PartnerForUI(R.drawable.logo_tfk_45x35px, description,role));
        //        description="FARMAF "+System.getProperty("line.separator");
        //        partnerList.add(new PartnerForUI(R.drawable.farmaf_logo, description,role));
        //        description="ORANGE "+System.getProperty("line.separator");
        //        partnerList.add(new PartnerForUI(R.drawable.orange_logo, description,role));


        val adapter = RecyclerViewAdapterPartner(requireContext(), partnerList)
        recyclerView.adapter = adapter
        registerForContextMenu(recyclerView)

        setHasOptionsMenu(true)




        return view

    }


    override fun onDetach() {
        super.onDetach()
    }


    private fun validate() {

    }


}
