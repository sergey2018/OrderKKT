/*
 * ContinuePrint.java
 *
 * Created on 2 April 2008, 20:17
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.shtrih.fiscalprinter.command;

/**
 *
 * @author V.Kravtsov
 */
/*****************************************************************************
 * Continue printing Command: B0H. Length: 5 bytes. · Operator, Administrator or
 * System Administrator password (4 bytes) Answer: B0H. Length: 3 bytes. ·
 * Result Code (1 byte) · Operator index number (1 byte) 1…30
 *****************************************************************************/
public final class ContinuePrint extends PrinterCommand {
    // in

    private int password;
    // out
    private int operator = 0;

    /**
     * Creates a new instance of ContinuePrint
     */
    public ContinuePrint() {
        super();
    }

    public final int getCode() {
        return 0xB0;
    }

    public final String getText() {
        return "Continue printing";
    }

    public final void encode(CommandOutputStream out) throws Exception {
        out.writeInt(getPassword());
    }

    public final void decode(CommandInputStream in) throws Exception {
        setOperator(in.readByte());
    }

    public int getOperator() {
        return operator;
    }

    public int getPassword() {
        return password;
    }

    public void setPassword(int password) {
        this.password = password;
    }

    public void setOperator(int operator) {
        this.operator = operator;
    }
}
