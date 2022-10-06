package com.tiramakan.simabf.core.modelView

import com.tiramakan.simabf.bootstrap.util.UIUtils
import com.tiramakan.simabf.core.models.realm.Info
import java.util.*
import com.tiramakan.simabf.ui.view.utils.Binding.*
/**
 * Created by tiramakan on 22/12/2015.
 */

class NewsForUI(  var  date: BindableDate = BindableDate(),
                var dateExpiration:Date = Date(),
                var contenu:BindableString = BindableString(),
                var url:BindableString = BindableString(),
                var titre:BindableString = BindableString(),
                var longitude:BindableDouble = BindableDouble(),
                var latitude:BindableDouble = BindableDouble(),
                var id: Int? = 0) {

   
    val newsDate: String
        get() = UIUtils.prettyDateFormat.format(date.get())
    val prettyDate: String
        get() = UIUtils.prettyDateFormat.format(date.get())
    val prettyDateAndHour: String
        get() = UIUtils.prettyDateAndHourFormat.format(date.get())
    val isValid: Boolean?
        get() = true

    override fun toString(): String {

        return "   Url : " + url + System.getProperty("line.separator") +
                " Titre :" + titre + " contenu" + contenu + " date" + dateExpiration

    }

    init {
          this.date=BindableDate()
    }
    constructor(poNews: Info):this() {
        titre.set(poNews.titre.toString())
        contenu.set(poNews.contenu.toString())
        url.set(poNews.url.toString())
        date.set(poNews.date)
        longitude.set(poNews.longitude.toDouble())
        latitude.set(poNews.latitude.toDouble())
        this.id = poNews.id
    }

    fun validate(): Boolean {
        return contenu.get() != ""
    }


}
