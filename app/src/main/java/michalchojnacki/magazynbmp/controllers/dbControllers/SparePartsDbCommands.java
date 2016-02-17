package michalchojnacki.magazynbmp.controllers.dbControllers;

import michalchojnacki.magazynbmp.model.SparePart;

abstract class SparePartsDbCommands {

    static final String TEXT_TYPE = " TEXT";
    static final String COMMA_SEP = ",";
    static final String PERCENT_SEP = "%'";

    static final String WHERE_COMMAND = " WHERE ";
    static final String AND_COMMAND = " AND ";
    static final String OR_COMMAND = " OR ";
    static final String OPEN_LIKE_COMMAND = " LIKE '%";


    static final String SQL_READ_ENTRIES = "SELECT  * FROM " + SparePartsDbEntry.TABLE_NAME;
    static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + SparePartsDbEntry.TABLE_NAME;
    static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + SparePartsDbEntry.TABLE_NAME + " (" +
            SparePartsDbEntry._ID + " INTEGER PRIMARY KEY," +
            SparePartsDbEntry.COLUMN_NAME_SPARE_PART_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
            SparePartsDbEntry.COLUMN_NAME_SPARE_PART_NUMBER + TEXT_TYPE + COMMA_SEP +
            SparePartsDbEntry.COLUMN_NAME_SPARE_PART_TYPE + TEXT_TYPE + COMMA_SEP +
            SparePartsDbEntry.COLUMN_NAME_SPARE_PART_PRODUCER + TEXT_TYPE + COMMA_SEP +
            SparePartsDbEntry.COLUMN_NAME_SPARE_PART_LOCATION + TEXT_TYPE + COMMA_SEP +
            SparePartsDbEntry.COLUMN_NAME_SPARE_PART_SUPPLIER + TEXT_TYPE +
            ")";

    static String sqlCheckEntry(SparePart sparePart) {
        if (allFieldsAreNull(sparePart)) {
            return null;
        }

        String condition = WHERE_COMMAND;
        StringBuilder builder = new StringBuilder().append(SQL_READ_ENTRIES);

        if (sparePart.getNumber() != null) {
            appendSqlConditionToStringBuilder(builder, condition,
                                              SparePartsDbEntry.COLUMN_NAME_SPARE_PART_NUMBER,
                                              sparePart.getNumber());
            condition = AND_COMMAND;
        }

        if (sparePart.getDescription() != null) {
            appendDescriptionsToStringBuilder(builder, sparePart.getDescription());
            condition = AND_COMMAND;
        }

        if (sparePart.getType() != null) {
            appendSqlConditionToStringBuilder(builder, condition,
                                              SparePartsDbEntry.COLUMN_NAME_SPARE_PART_TYPE,
                                              sparePart.getType());
            condition = AND_COMMAND;
        }

        if (sparePart.getProducer() != null) {
            appendSqlConditionToStringBuilder(builder, condition,
                                              SparePartsDbEntry.COLUMN_NAME_SPARE_PART_PRODUCER,
                                              sparePart.getProducer());
            condition = AND_COMMAND;
        }

        if (sparePart.getSupplier() != null) {
            appendSqlConditionToStringBuilder(builder, condition,
                                              SparePartsDbEntry.COLUMN_NAME_SPARE_PART_SUPPLIER,
                                              sparePart.getSupplier());
        }

        return builder.toString();
    }

    private static boolean allFieldsAreNull(SparePart sparePart) {
        return sparePart.getDescription() == null && sparePart.getNumber() == null && sparePart.getType() == null && sparePart
                .getProducer() == null && sparePart.getSupplier() == null;
    }

    private static StringBuilder appendSqlConditionToStringBuilder(StringBuilder stringBuilder,
                                                                   String condition, String dbEntry,
                                                                   String searchedText) {
        return stringBuilder.append(condition)
                .append(dbEntry + OPEN_LIKE_COMMAND)
                .append(searchedText)
                .append(PERCENT_SEP);
    }

    private static void appendDescriptionsToStringBuilder(StringBuilder builder, String desc) {
        String[] descriptions = SqlCheckEntryBuilder.getWordsToCheck(desc);

        builder.append("(");
        for (String description : descriptions) {
            appendSqlConditionToStringBuilder(builder, "",
                                              SparePartsDbEntry.COLUMN_NAME_SPARE_PART_DESCRIPTION,
                                              description);
            builder.append(PERCENT_SEP).append(OR_COMMAND);
        }
        builder.delete(builder.length() - 4, builder.length());
        builder.append(")");
    }

    static String sqlCheckEntry(String searchedText) {
        if (searchedText == null) {
            return null;
        }

        String[] wordsToCheck = SqlCheckEntryBuilder.getWordsToCheck(searchedText);
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(SQL_READ_ENTRIES);
        appendSqlConditionToStringBuilder(stringBuilder, WHERE_COMMAND,
                                          SparePartsDbEntry.COLUMN_NAME_SPARE_PART_NUMBER,
                                          searchedText);

        for (String word : wordsToCheck) {
            appendSqlConditionToStringBuilder(stringBuilder, OR_COMMAND,
                                              SparePartsDbEntry.COLUMN_NAME_SPARE_PART_DESCRIPTION,
                                              word);
            appendSqlConditionToStringBuilder(stringBuilder, OR_COMMAND,
                                              SparePartsDbEntry.COLUMN_NAME_SPARE_PART_TYPE, word);
            appendSqlConditionToStringBuilder(stringBuilder, OR_COMMAND,
                                              SparePartsDbEntry.COLUMN_NAME_SPARE_PART_PRODUCER,
                                              word);
            appendSqlConditionToStringBuilder(stringBuilder, OR_COMMAND,
                                              SparePartsDbEntry.COLUMN_NAME_SPARE_PART_SUPPLIER,
                                              word);
        }

        return stringBuilder.toString();
    }
}
