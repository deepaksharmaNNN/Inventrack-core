package com.inventrack.inventrackcore.enums;

public enum StockMovementType {
    PURCHASE,    // incoming from PO receive
    SALE,        // outgoing for sales order
    ADJUSTMENT,  // manual adjustment
    RETURN_IN,   // returned to stock
    RETURN_OUT // returned from stock
}
