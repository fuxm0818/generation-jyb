package com.cattsoft.velocity;

public class Column {
	/**
	 * table catalog (may be null)
	 */
	private String tableCat;
	/**
	 * table schema (may be null)
	 */
	private String tableSchem;
	/**
	 * table name
	 */
	private String tableName;
	/**
	 * column name
	 */
	private String columnName;
	/**
	 * SQL type from java.sql.Types
	 */
	private int dataType;
	/**
	 * Data source dependent type name, for a UDT the type name is fully
	 * qualified
	 */
	private String typeName;
	/**
	 * column size.
	 */
	private int columnSize;
	/**
	 * is not used.
	 */
	private String bufferLength;
	/**
	 * the number of fractional digits. <br>
	 * Null is returned for data types where DECIMAL_DIGITS is not applicable.
	 */
	private int decimalDigits;
	/**
	 * Radix (typically either 10 or 2)
	 */
	private int numPrecRadix;
	/**
	 * is NULL allowed. <br>
	 * columnNoNulls - might not allow NULL values <br>
	 * columnNullable - definitely allows NULL values <br>
	 * columnNullableUnknown - nullability unknown
	 */
	private int nullable;
	/**
	 * comment describing column (may be null)
	 */
	private String remarks;
	/**
	 * default value for the column, which should be interpreted as a string
	 * when the value is enclosed in single quotes (may be null)
	 */
	private String columnDef;
	/**
	 * unused
	 */
	private int sqlDataType;
	/**
	 * unused
	 */
	private int sqlDatetimeSub;
	/**
	 * for char types the maximum number of bytes in the column
	 */
	private int charOctetLength;
	/**
	 * index of column in table (starting at 1)
	 */
	private int ordinalPosition;
	/**
	 * ISO rules are used to determine the nullability for a column. <br>
	 * YES --- if the parameter can include NULLs <br>
	 * NO --- if the parameter cannot include NULLs <br>
	 * empty string --- if the nullability for the parameter is unknown
	 */
	private String isNullable;
	/**
	 * catalog of table that is the scope of a reference attribute (null if
	 * DATA_TYPE isn't REF)
	 */
	private String scopeCatlog;
	/**
	 * schema of table that is the scope of a reference attribute (null if the
	 * DATA_TYPE isn't REF)
	 */
	private String scopeSchema;
	/**
	 * table name that this the scope of a reference attribure (null if the
	 * DATA_TYPE isn't REF)
	 */
	private String scopeTable;
	/**
	 * source type of a distinct type or user-generated Ref type, SQL type from
	 * java.sql.Types (null if DATA_TYPE isn't DISTINCT or user-generated REF)
	 */
	private short sourceDataType;
	/**
	 * Indicates whether this column is auto incremented <br>
	 * YES --- if the column is auto incremented <br>
	 * NO --- if the column is not auto incremented <br>
	 * empty string --- if it cannot be determined whether the column is auto
	 * incremented parameter is unknown
	 */
	private String isAutoincrement;

	private boolean isprimaryKey;

	public String getTableCat() {
		return tableCat;
	}

	public void setTableCat(String tableCat) {
		this.tableCat = tableCat;
	}

	public String getTableSchem() {
		return tableSchem;
	}

	public void setTableSchem(String tableSchem) {
		this.tableSchem = tableSchem;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public int getDataType() {
		return dataType;
	}

	public void setDataType(int dataType) {
		this.dataType = dataType;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public int getColumnSize() {
		return columnSize;
	}

	public void setColumnSize(int columnSize) {
		this.columnSize = columnSize;
	}

	public String getBufferLength() {
		return bufferLength;
	}

	public void setBufferLength(String bufferLength) {
		this.bufferLength = bufferLength;
	}

	public int getDecimalDigits() {
		return decimalDigits;
	}

	public void setDecimalDigits(int decimalDigits) {
		this.decimalDigits = decimalDigits;
	}

	public int getNumPrecRadix() {
		return numPrecRadix;
	}

	public void setNumPrecRadix(int numPrecRadix) {
		this.numPrecRadix = numPrecRadix;
	}

	public int getNullable() {
		return nullable;
	}

	public void setNullable(int nullable) {
		this.nullable = nullable;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getColumnDef() {
		return columnDef;
	}

	public void setColumnDef(String columnDef) {
		this.columnDef = columnDef;
	}

	public int getSqlDataType() {
		return sqlDataType;
	}

	public void setSqlDataType(int sqlDataType) {
		this.sqlDataType = sqlDataType;
	}

	public int getSqlDatetimeSub() {
		return sqlDatetimeSub;
	}

	public void setSqlDatetimeSub(int sqlDatetimeSub) {
		this.sqlDatetimeSub = sqlDatetimeSub;
	}

	public int getCharOctetLength() {
		return charOctetLength;
	}

	public void setCharOctetLength(int charOctetLength) {
		this.charOctetLength = charOctetLength;
	}

	public int getOrdinalPosition() {
		return ordinalPosition;
	}

	public void setOrdinalPosition(int ordinalPosition) {
		this.ordinalPosition = ordinalPosition;
	}

	public String getIsNullable() {
		return isNullable;
	}

	public void setIsNullable(String isNullable) {
		this.isNullable = isNullable;
	}

	public String getScopeCatlog() {
		return scopeCatlog;
	}

	public void setScopeCatlog(String scopeCatlog) {
		this.scopeCatlog = scopeCatlog;
	}

	public String getScopeSchema() {
		return scopeSchema;
	}

	public void setScopeSchema(String scopeSchema) {
		this.scopeSchema = scopeSchema;
	}

	public String getScopeTable() {
		return scopeTable;
	}

	public void setScopeTable(String scopeTable) {
		this.scopeTable = scopeTable;
	}

	public short getSourceDataType() {
		return sourceDataType;
	}

	public void setSourceDataType(short sourceDataType) {
		this.sourceDataType = sourceDataType;
	}

	public String getIsAutoincrement() {
		return isAutoincrement;
	}

	public void setIsAutoincrement(String isAutoincrement) {
		this.isAutoincrement = isAutoincrement;
	}

	public boolean isIsprimaryKey() {
		return isprimaryKey;
	}

	public void setIsprimaryKey(boolean isprimaryKey) {
		this.isprimaryKey = isprimaryKey;
	}

}
