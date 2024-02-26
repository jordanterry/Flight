package jt.flights.networking

import com.squareup.anvil.annotations.MergeSubcomponent
import jt.flights.di.SingleIn

@SingleIn(NetworkingScope::class)
@MergeSubcomponent(NetworkingScope::class)
interface NetworkingComponent
