package com.wasim.recycleviewdp

import android.widget.ImageView
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.PropertyName
import java.time.temporal.TemporalAmount

@IgnoreExtraProperties
data class Todo(
    @Exclude
    var name: String? = "",
//    @get:PropertyName("second_name")
//    @set:PropertyName("second_name")
    var amount: Int? = null
)

