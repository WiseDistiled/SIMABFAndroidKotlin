package com.tiramakan.simabf.core.myEnums

import java.io.Serializable

/**
 * Created by tiramakan on 19/02/2016.
 */
enum class RequestType : Serializable {
    sell_offer,
    buy_offer,
    sell_offer_write,
    buy_offer_write,
    read_price,
    write_price,
    read_stock,
    write_stock,
    gps

}

