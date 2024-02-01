package com.heart.ticket.demo;

/**
 * About:
 * Other:
 * Created: wfli on 2024/1/8 14:12.
 * Editored:
 */
public enum OState {
    tobeAssignedMerchant("tobeAssignedMerchant"),
    tobeAssignedCourier("tobeAssignedCourier"),
    tobeFetched("tobeFetched"),
    delivering("delivering"),
    completed("completed"),
    cancelled("cancelled"),
    exception("exception"),
    arrived("arrived"),
    selfDelivery("selfDelivery"),
    noMoreDelivery("noMoreDelivery"),
    reject("reject");

    private String orderDesc;

    private OState(String orderDesc) {
        this.orderDesc = orderDesc;
    }
}
