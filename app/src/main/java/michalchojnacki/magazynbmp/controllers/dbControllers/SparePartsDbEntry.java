package michalchojnacki.magazynbmp.controllers.dbControllers;

import android.provider.BaseColumns;

abstract class SparePartsDbEntry implements BaseColumns {

    static final String COLUMN_NAME_SPARE_PART_NUMBER = "sparePartNumber";
    static final String COLUMN_NAME_SPARE_PART_DESCRIPTION = "sparePartDescription";
    static final String COLUMN_NAME_SPARE_PART_TYPE = "sparePartType";
    static final String COLUMN_NAME_SPARE_PART_LOCATION = "sparePartLocation";
    static final String COLUMN_NAME_SPARE_PART_PRODUCER = "sparePartProducer";
    static final String COLUMN_NAME_SPARE_PART_SUPPLIER = "sparePartSupplier";
    static final String TABLE_NAME = "spareParts";
}
