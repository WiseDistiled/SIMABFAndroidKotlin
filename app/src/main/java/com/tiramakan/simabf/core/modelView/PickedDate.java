package com.tiramakan.simabf.core.modelView;

import androidx.databinding.ObservableInt;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;

public class PickedDate {
    public final ObservableInt year = new ObservableInt();
    public final ObservableInt month = new ObservableInt();
    public final ObservableInt day = new ObservableInt();

    public PickedDate() {
        Calendar calendar = Calendar.getInstance();
        year.set(calendar.get(Calendar.YEAR));
        month.set(calendar.get(Calendar.MONTH));
        day.set(calendar.get(Calendar.DAY_OF_MONTH));
    }

    public void dateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        this.year.set(year);
        this.month.set(monthOfYear);
        this.day.set(dayOfMonth);
    }
    public Date getDate() {
        Calendar c = Calendar.getInstance();
        c.set(this.year.get(), this.month.get(), this.day.get(), 0, 0);
        return c.getTime();
    }
}
