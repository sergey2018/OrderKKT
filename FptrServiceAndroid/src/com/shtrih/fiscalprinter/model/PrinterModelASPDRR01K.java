package com.shtrih.fiscalprinter.model;

import com.shtrih.fiscalprinter.command.PrinterConst;

public class PrinterModelASPDRR01K extends PrinterModelDefault {
    public PrinterModelASPDRR01K() throws Exception {
        setName("ASPD RR-01K");
        setId(28);
        setModelID(24);
        setProtocolVersion(1);
        setProtocolSubVersion(12);
        setCapEJPresent(true);
        setCapFMPresent(true);
        setCapRecPresent(true);
        setCapJrnPresent(false);
        setCapSlpPresent(false);
        setCapSlpEmptySensor(false);
        setCapSlpNearEndSensor(false);
        setCapRecEmptySensor(true);
        setCapRecEmptySensor(true);
        setCapRecNearEndSensor(true);
        setCapRecLeverSensor(false);
        setCapJrnEmptySensor(false);
        setCapJrnNearEndSensor(false);
        setCapJrnLeverSensor(false);
        setCapPrintGraphicsLine(true);
        setCapHasVatTable(true);
        setCapCoverSensor(true);
        setCapDoubleWidth(true);
        setCapDuplicateReceipt(true);
        setCapFullCut(true);
        setCapPartialCut(true);
        setCapGraphics(true);
        setCapGraphicsEx(true);
        setCapPrintStringFont(true);
        setCapShortStatus(false);
        setCapFontMetrics(false);
        setCapOpenReceipt(true);
        setNumVatRates(4);
        setAmountDecimalPlace(2);
        setNumHeaderLines(3);
        setNumTrailerLines(3);
        setTrailerTableNumber(4);
        setHeaderTableNumber(4);
        setHeaderTableRow(12);
        setTrailerTableRow(1);
        setMinHeaderLines(3);
        setMinTrailerLines(0);
        setMaxGraphicsWidth(320);
        setMaxGraphicsHeight(255);
        setPrintWidth(576);
        setTextLength(new int[] {  48, 24, 48, 24, 57, 48, 48 });
        setFontHeight(new int[] {  });
        setSupportedBaudRates(new int[] {  2400, 4800, 9600, 19200, 38400, 57600, 115200 });
        setCapCashInAutoCut(false);
        setCapCashOutAutoCut(false);
        setCapPrintBarcode2(false);
        setDeviceFontNormal(1);
        setDeviceFontDouble(2);
        setDeviceFontSmall(3);
        setSwapGraphicsLine(true);
        setMinCashRegister(0);
        setMaxCashRegister(255);
        setMinCashRegister2(4096);
        setMaxCashRegister2(4191);
        setMinOperationRegister(0);
        setMaxOperationRegister(253);
        setCapGraphicsLineMargin(false);

        addParameter("FDOServerHost", 19, 1, 1);
        addParameter("FDOServerPort", 19, 1, 2);
        addParameter("FDOServerTimeout", 19, 1, 3);
        addParameter("DrawerEnabled", 1, 1, 6);
        addParameter("CutMode", 1, 1, 7);
        addParameter("ReceiptFormatEnabled", 1, 1, 25);
        addParameter("ReceiptItemNameLength", 9, 1, 3);
        addParameter("LineSpacing", 1, 1, 29);
    }
}