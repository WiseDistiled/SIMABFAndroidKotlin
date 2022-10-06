package com.tiramakan.simabf.ui.view.request_responses

/**
 * Created by tiramakan on 17/01/2016.
 */

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.tiramakan.simabf.R
import com.tiramakan.simabf.bootstrap.BootstrapApplication
import com.tiramakan.simabf.core.constants.Constants
import com.tiramakan.simabf.core.models.notifiers.RequestResponse
import com.tiramakan.simabf.databinding.RequestResponseBinding
import com.tiramakan.simabf.ui.view.baseClasses.BaseFragment

import org.parceler.Parcels

/**
 * Created by tiramakan on 17/01/2016.
 */
class RequestResponseUI : BaseFragment() {
    internal lateinit var priceResponse: RequestResponse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BootstrapApplication.component().inject(this)
        setHasOptionsMenu(false)
        if (arguments != null) {
            priceResponse = Parcels.unwrap(arguments?.getParcelable(Constants.Extra.REQUEST_RESPONSE_TAG))
        }

        isSended = true
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(Constants.Extra.REQUEST_RESPONSE_TAG, Parcels.wrap(priceResponse))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<RequestResponseBinding>(inflater, R.layout.request_response, container, false)
        if (savedInstanceState != null) {
            priceResponse = Parcels.unwrap(savedInstanceState.getParcelable(Constants.Extra.REQUEST_RESPONSE_TAG))
        }
        binding.response = priceResponse
        binding.message.text = priceResponse.prettyMessage
        //    binding.subTitle.setText(priceResponse.getTitle());

        return binding.root
    }


    override fun doBack(): Boolean {
        return true
    }

}
