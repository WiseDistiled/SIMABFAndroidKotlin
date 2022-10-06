package com.tiramakan.simabf.events

/**
 * Pub/Sub event used to communicate between fragment and activity.
 * Subscription occurs in the [MainActivityOLD]
 */
class NavItemSelectedEvent(val itemPosition: Int)
