package com.shtrih.jpos.fiscalprinter.directIO;

import com.shtrih.jpos.fiscalprinter.FiscalPrinterImpl;

public class DIOFSWriteTLV extends DIOItem {

    public DIOFSWriteTLV(FiscalPrinterImpl service) {
        super(service);
    }

    public void execute(int[] data, Object object) throws Exception {
        service.fsWriteTLV((byte[]) object);
    }
}

