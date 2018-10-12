package com.sergey.root.orderkkt.DataBase;

public class dbShema {
    public static class GOODS{
        public static final String NAME="goods";
        public static final class Cols{
            public static final String NAME="name";
            public static final String PRICE="price";
            public static final String CODE="code";
            public static final String QUANT="quantt";
            public static final String TAX="tax";
         }
    }

    public static class ORDER{
        public static final String NAME="zakaz";
        public static final class Cols{
            public static final String ACCT="acct";
            public static final String DAY="day";
            public static final String DATE="date";
            public static final String GOODS = "goods_id";
            public static final String ADRESS="adres";
            public static final String PHONE = "phone";
            public static final String CONTACT="contact";
            public static final String STATUS="status";
            public static final String NOTE="note";
            public static final String TYPE="sales_type";
        }
    }


}
