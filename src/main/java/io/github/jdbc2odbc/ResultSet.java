package io.github.jdbc2odbc;
import org.lwjgl.BufferUtils;
import org.lwjgl.PointerBuffer;

import com.jcabi.aspects.Loggable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.*;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.sql.Date;
import java.sql.Statement;
import java.util.*;
import java.util.HashMap;

import static org.lwjgl.odbc.SQL.*;

// Data types: https://learn.microsoft.com/en-us/sql/odbc/reference/appendixes/c-data-types?view=sql-server-ver16

public class ResultSet implements java.sql.ResultSet{
    private static Logger LOGGER = LoggerFactory.getLogger(ResultSet.class);
    Statement statement = null;
    Long statementHandle = null;
    List<String> columnNames = new ArrayList<String>();
    Map<Short, Short> columnMapping; // Key=JDBC column, value = ODBC column

    public ResultSet(Statement statement, Long statementHandle) throws SQLException {
        this(statement, statementHandle, null);
    }

    public ResultSet(Statement statement, Long statementHandle, Map<Short, Short> columnMapping) throws SQLException {
        this.statement = statement;
        this.statementHandle = statementHandle;
        this.columnMapping = columnMapping;


        ShortBuffer outNumColumns = BufferUtils.createShortBuffer(4);
        SQLNumResultCols(statementHandle, outNumColumns);

        short numColumns = outNumColumns.get();
        if (columnMapping!=null) {
            columnNames = new ArrayList<String>(columnMapping.size());
            for (Map.Entry<Short, Short> entry : columnMapping.entrySet()) {
                ByteBuffer outColumnName = BufferUtils.createByteBuffer(1024);
                ShortBuffer outStringLength = BufferUtils.createShortBuffer(4);
                PointerBuffer outNumericAttributePtr = BufferUtils.createPointerBuffer(8);
                SQLColAttribute(statementHandle, entry.getValue(), SQL_DESC_NAME, outColumnName, outStringLength, outNumericAttributePtr);
                outColumnName.limit(outStringLength.get());
                String columnName = StandardCharsets.UTF_16LE.decode(outColumnName).toString();
                columnNames.add(entry.getKey()-1, columnName);
            }
        }
        else {
            for (short index = 1; index< numColumns+1; index++) {
                ByteBuffer outColumnName = BufferUtils.createByteBuffer(1024);
                ShortBuffer outStringLength = BufferUtils.createShortBuffer(4);
                PointerBuffer outNumericAttributePtr = BufferUtils.createPointerBuffer(8);
                SQLColAttribute(statementHandle, index, SQL_DESC_NAME, outColumnName, outStringLength, outNumericAttributePtr);
                outColumnName.limit(outStringLength.get());
                String columnName = StandardCharsets.UTF_16LE.decode(outColumnName).toString();
                columnNames.add(columnName);
            }
        }
    }

    private Short getKey(Short value) throws NoSuchElementException{
        for (Map.Entry<Short, Short> entry : columnMapping.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        throw new NoSuchElementException();
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean next() throws SQLException {
        short result = SQLFetch(statementHandle);
        if (result == SQL_ERROR){
            throw new SQLException("next() failed");
        }
        return result != SQL_NO_DATA;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void close() throws SQLException {
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean wasNull() throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public String getString(int i) throws SQLException {
        // TODO: check index
        ByteBuffer outString = BufferUtils.createByteBuffer(1024); // TODO: make dynamic
        PointerBuffer outStrlen = BufferUtils.createPointerBuffer(8);

        short index = columnMapping != null ? columnMapping.getOrDefault((short)i, (short)i) : (short)i;

        if (SQLGetData(statementHandle, index, SQL_C_WCHAR, outString, outStrlen) != SQL_SUCCESS) {
            throw new SQLException("getString() failed");
        }
        outString.limit((int) outStrlen.get());
        String s = StandardCharsets.UTF_16LE.decode(outString).toString();
        return s;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean getBoolean(int i) throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public byte getByte(int i) throws SQLException {
        throw new SQLFeatureNotSupportedException("getByte() not supported");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public short getShort(int i) throws SQLException {
        ByteBuffer outValue = BufferUtils.createByteBuffer(1024); // TODO: make dynamic
        PointerBuffer outStrlen = BufferUtils.createPointerBuffer(8);

        short index = columnMapping != null ? columnMapping.getOrDefault((short)i, (short)i) : (short)i;

        SQLGetData(statementHandle, index, SQL_C_SHORT, outValue, outStrlen);
        return outValue.getShort();
    }

    @Override
    @Loggable(Loggable.TRACE)
    public int getInt(int i) throws SQLException {
        ByteBuffer outValue = BufferUtils.createByteBuffer(1024); // TODO: make dynamic
        PointerBuffer outStrlen = BufferUtils.createPointerBuffer(8);

        short index = columnMapping != null ? columnMapping.getOrDefault((short)i, (short)i) : (short)i;

        SQLGetData(statementHandle, index, SQL_C_LONG, outValue, outStrlen);
        return (int)outValue.getLong();
    }

    @Override
    @Loggable(Loggable.TRACE)
    public long getLong(int i) throws SQLException {
        ByteBuffer outValue = BufferUtils.createByteBuffer(1024); // TODO: make dynamic
        PointerBuffer outStrlen = BufferUtils.createPointerBuffer(8);

        short index = columnMapping != null ? columnMapping.getOrDefault((short)i, (short)i) : (short)i;

        SQLGetData(statementHandle, index, SQL_C_SHORT, outValue, outStrlen);
        return outValue.getLong();
    }

    @Override
    @Loggable(Loggable.TRACE)
    public float getFloat(int i) throws SQLException {
        return 0;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public double getDouble(int i) throws SQLException {
        return 0;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public BigDecimal getBigDecimal(int i, int i1) throws SQLException {
        return null;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public byte[] getBytes(int i) throws SQLException {
        ByteBuffer outValue = BufferUtils.createByteBuffer(1024); // TODO: make dynamic
        PointerBuffer outStrlen = BufferUtils.createPointerBuffer(8);

        short index = columnMapping != null ? columnMapping.getOrDefault((short)i, (short)i) : (short)i;

        SQLGetData(statementHandle, index, SQL_C_SHORT, outValue, outStrlen);
        byte[] bytesOut = new byte[(int)outStrlen.get()];
        outValue.get(bytesOut);
        return bytesOut;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public Date getDate(int i) throws SQLException {
        throw new SQLFeatureNotSupportedException("getDate()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public Time getTime(int i) throws SQLException {
        throw new SQLFeatureNotSupportedException("getTime()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public Timestamp getTimestamp(int i) throws SQLException {
        throw new SQLFeatureNotSupportedException("getTimestamp()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public InputStream getAsciiStream(int i) throws SQLException {
        throw new SQLFeatureNotSupportedException("getAsciiStream()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public InputStream getUnicodeStream(int i) throws SQLException {
        throw new SQLFeatureNotSupportedException("getUnicodeStream()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public InputStream getBinaryStream(int i) throws SQLException {
        throw new SQLFeatureNotSupportedException("getBinaryStream()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public String getString(String s) throws SQLException {
        return getString(findColumn(s));
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean getBoolean(String s) throws SQLException {
        return getBoolean(findColumn(s));
    }

    @Override
    @Loggable(Loggable.TRACE)
    public byte getByte(String s) throws SQLException {
        return getByte(findColumn(s));
    }

    @Override
    @Loggable(Loggable.TRACE)
    public short getShort(String s) throws SQLException {
        return getShort(findColumn(s));
    }

    @Override
    @Loggable(Loggable.TRACE)
    public int getInt(String s) throws SQLException {
        return getInt(findColumn(s));
    }

    @Override
    @Loggable(Loggable.TRACE)
    public long getLong(String s) throws SQLException {
        return getLong(findColumn(s));
    }

    @Override
    @Loggable(Loggable.TRACE)
    public float getFloat(String s) throws SQLException {
        return getFloat(findColumn(s));
    }

    @Override
    @Loggable(Loggable.TRACE)
    public double getDouble(String s) throws SQLException {
        return getDouble(findColumn(s));
    }

    @Override
    @Loggable(Loggable.TRACE)
    public BigDecimal getBigDecimal(String s, int i) throws SQLException {
        return getBigDecimal(findColumn(s), i);
    }

    @Override
    @Loggable(Loggable.TRACE)
    public byte[] getBytes(String s) throws SQLException {
        return getBytes(findColumn(s));
    }

    @Override
    @Loggable(Loggable.TRACE)
    public Date getDate(String s) throws SQLException {
        return getDate(findColumn(s));
    }

    @Override
    @Loggable(Loggable.TRACE)
    public Time getTime(String s) throws SQLException {
        return getTime(findColumn(s));
    }

    @Override
    @Loggable(Loggable.TRACE)
    public Timestamp getTimestamp(String s) throws SQLException {
        return getTimestamp(findColumn(s));
    }

    @Override
    @Loggable(Loggable.TRACE)
    public InputStream getAsciiStream(String s) throws SQLException {
        return getAsciiStream(findColumn(s));
    }

    @Override
    @Loggable(Loggable.TRACE)
    public InputStream getUnicodeStream(String s) throws SQLException {
        return getUnicodeStream(findColumn(s));
    }

    @Override
    @Loggable(Loggable.TRACE)
    public InputStream getBinaryStream(String s) throws SQLException {
        return getBinaryStream(findColumn(s));
    }

    @Override
    @Loggable(Loggable.TRACE)
    public SQLWarning getWarnings() throws SQLException {
        return null;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void clearWarnings() throws SQLException {

    }

    @Override
    @Loggable(Loggable.TRACE)
    public String getCursorName() throws SQLException {
        return "";
    }

    @Override
    @Loggable(Loggable.TRACE)
    public ResultSetMetaData getMetaData() throws SQLException {
        return new ResultSetMetaData(statementHandle, columnNames, columnMapping);
    }

    @Override
    @Loggable(Loggable.TRACE)
    public Object getObject(int i) throws SQLException {
        throw new SQLFeatureNotSupportedException("getObject()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public Object getObject(String s) throws SQLException {
        return getObject(findColumn(s));
    }

    @Override
    @Loggable(Loggable.TRACE)
    public int findColumn(String s) throws SQLException {
        return columnNames.indexOf(s)+1;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public Reader getCharacterStream(int i) throws SQLException {
        throw new SQLFeatureNotSupportedException("getCharacterStream()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public Reader getCharacterStream(String s) throws SQLException {
        return getCharacterStream(findColumn(s));
    }

    @Override
    @Loggable(Loggable.TRACE)
    public BigDecimal getBigDecimal(int i) throws SQLException {
        throw new SQLFeatureNotSupportedException("getBigDecimal()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public BigDecimal getBigDecimal(String s) throws SQLException {
        return getBigDecimal(findColumn(s));
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean isBeforeFirst() throws SQLException {
        throw new SQLFeatureNotSupportedException("isBeforeFirst()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean isAfterLast() throws SQLException {
        throw new SQLFeatureNotSupportedException("isAfterLast()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean isFirst() throws SQLException {
        throw new SQLFeatureNotSupportedException("isFirst()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean isLast() throws SQLException {
        throw new SQLFeatureNotSupportedException("isLast()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void beforeFirst() throws SQLException {
        throw new SQLFeatureNotSupportedException("beforeFirst()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void afterLast() throws SQLException {
        throw new SQLFeatureNotSupportedException("afterLast()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean first() throws SQLException {
        throw new SQLFeatureNotSupportedException("first()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean last() throws SQLException {
        throw new SQLFeatureNotSupportedException("last()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public int getRow() throws SQLException {
        throw new SQLFeatureNotSupportedException("getRow()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean absolute(int i) throws SQLException {
        throw new SQLFeatureNotSupportedException("absolute()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean relative(int i) throws SQLException {
        throw new SQLFeatureNotSupportedException("relative()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean previous() throws SQLException {
        throw new SQLFeatureNotSupportedException("previous()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void setFetchDirection(int i) throws SQLException {
        throw new SQLFeatureNotSupportedException("setFetchDirection()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public int getFetchDirection() throws SQLException {
        throw new SQLFeatureNotSupportedException("getFetchDirection()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void setFetchSize(int i) throws SQLException {
        throw new SQLFeatureNotSupportedException("setFetchSize()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public int getFetchSize() throws SQLException {
        throw new SQLFeatureNotSupportedException("getFetchSize()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public int getType() throws SQLException {
        throw new SQLFeatureNotSupportedException("getType()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public int getConcurrency() throws SQLException {
        throw new SQLFeatureNotSupportedException("getConcurrency()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean rowUpdated() throws SQLException {
        throw new SQLFeatureNotSupportedException("rowUpdated()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean rowInserted() throws SQLException {
        throw new SQLFeatureNotSupportedException("rowInserted()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean rowDeleted() throws SQLException {
        throw new SQLFeatureNotSupportedException("rowDeleted()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void updateNull(int i) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateNull()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void updateBoolean(int i, boolean b) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateBoolean()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void updateByte(int i, byte b) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateByte()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void updateShort(int i, short i1) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateShort()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void updateInt(int i, int i1) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateInt()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void updateLong(int i, long l) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateLong()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void updateFloat(int i, float v) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateFloat()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void updateDouble(int i, double v) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateDouble()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void updateBigDecimal(int i, BigDecimal bigDecimal) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateBigDecimal()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void updateString(int i, String s) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateString()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void updateBytes(int i, byte[] bytes) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateBytes()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void updateDate(int i, Date date) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateDate()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void updateTime(int i, Time time) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateTime()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void updateTimestamp(int i, Timestamp timestamp) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateTimestamp()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void updateAsciiStream(int i, InputStream inputStream, int i1) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateAsciiStream()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void updateBinaryStream(int i, InputStream inputStream, int i1) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateBinaryStream()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void updateCharacterStream(int i, Reader reader, int i1) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateCharacterStream()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void updateObject(int i, Object o, int i1) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateObject()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void updateObject(int i, Object o) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateObject()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void updateNull(String s) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateNull()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void updateBoolean(String s, boolean b) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateBoolean()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void updateByte(String s, byte b) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateByte()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void updateShort(String s, short i) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateShort()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void updateInt(String s, int i) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateInt()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void updateLong(String s, long l) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateLong()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void updateFloat(String s, float v) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateFloat()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void updateDouble(String s, double v) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateDouble()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void updateBigDecimal(String s, BigDecimal bigDecimal) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateBigDecimal()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void updateString(String s, String s1) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateString()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void updateBytes(String s, byte[] bytes) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateBytes()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void updateDate(String s, Date date) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateDate()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void updateTime(String s, Time time) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateTime()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void updateTimestamp(String s, Timestamp timestamp) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateTimestamp()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void updateAsciiStream(String s, InputStream inputStream, int i) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateAsciiStream()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void updateBinaryStream(String s, InputStream inputStream, int i) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateBinaryStream()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void updateCharacterStream(String s, Reader reader, int i) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateCharacterStream()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void updateObject(String s, Object o, int i) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateObject()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void updateObject(String s, Object o) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateObject()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void insertRow() throws SQLException {
        throw new SQLFeatureNotSupportedException("insertRow()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void updateRow() throws SQLException {
        throw new SQLFeatureNotSupportedException("updateRow()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void deleteRow() throws SQLException {
        throw new SQLFeatureNotSupportedException("deleteRow()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void refreshRow() throws SQLException {
        throw new SQLFeatureNotSupportedException("refreshRow()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void cancelRowUpdates() throws SQLException {
        throw new SQLFeatureNotSupportedException("cancelRowUpdates()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void moveToInsertRow() throws SQLException {
        throw new SQLFeatureNotSupportedException("moveToInsertRow()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void moveToCurrentRow() throws SQLException {
        throw new SQLFeatureNotSupportedException("moveToCurrentRow()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public Statement getStatement() throws SQLException {
        throw new SQLFeatureNotSupportedException("getStatement()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public Object getObject(int i, Map<String, Class<?>> map) throws SQLException {
        throw new SQLFeatureNotSupportedException("getObject()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public Ref getRef(int i) throws SQLException {
        throw new SQLFeatureNotSupportedException("getRef()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public Blob getBlob(int i) throws SQLException {
        throw new SQLFeatureNotSupportedException("getBlob()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public Clob getClob(int i) throws SQLException {
        throw new SQLFeatureNotSupportedException("getClob()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public Array getArray(int i) throws SQLException {
        throw new SQLFeatureNotSupportedException("getArray()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public Object getObject(String s, Map<String, Class<?>> map) throws SQLException {
        throw new SQLFeatureNotSupportedException("getObject()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public Ref getRef(String s) throws SQLException {
        throw new SQLFeatureNotSupportedException("getRef()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public Blob getBlob(String s) throws SQLException {
        return getBlob(findColumn(s));
    }

    @Override
    @Loggable(Loggable.TRACE)
    public Clob getClob(String s) throws SQLException {
        return getClob(findColumn(s));
    }

    @Override
    @Loggable(Loggable.TRACE)
    public Array getArray(String s) throws SQLException {
        return getArray(findColumn(s));
    }

    @Override
    @Loggable(Loggable.TRACE)
    public Date getDate(int i, Calendar calendar) throws SQLException {
        throw new SQLFeatureNotSupportedException("getDate()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public Date getDate(String s, Calendar calendar) throws SQLException {
        return getDate(findColumn(s), calendar);
    }

    @Override
    @Loggable(Loggable.TRACE)
    public Time getTime(int i, Calendar calendar) throws SQLException {
        throw new SQLFeatureNotSupportedException("getTime()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public Time getTime(String s, Calendar calendar) throws SQLException {
        return getTime(findColumn(s), calendar);
    }

    @Override
    @Loggable(Loggable.TRACE)
    public Timestamp getTimestamp(int i, Calendar calendar) throws SQLException {
        throw new SQLFeatureNotSupportedException("getTimestamp()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public Timestamp getTimestamp(String s, Calendar calendar) throws SQLException {
        return getTimestamp(findColumn(s), calendar);
    }

    @Override
    @Loggable(Loggable.TRACE)
    public URL getURL(int i) throws SQLException {
        throw new SQLFeatureNotSupportedException("getURL()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public URL getURL(String s) throws SQLException {
        return getURL(findColumn(s));
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void updateRef(int i, Ref ref) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateRef()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void updateRef(String s, Ref ref) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateRef()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void updateBlob(int i, Blob blob) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateBlob()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void updateBlob(String s, Blob blob) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateBlob()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void updateClob(int i, Clob clob) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateClob()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void updateClob(String s, Clob clob) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateClob()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void updateArray(int i, Array array) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateArray()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void updateArray(String s, Array array) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateArray()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public RowId getRowId(int i) throws SQLException {
        throw new SQLFeatureNotSupportedException("getRowId()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public RowId getRowId(String s) throws SQLException {
        return getRowId(findColumn(s));
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void updateRowId(int i, RowId rowId) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateRowId()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void updateRowId(String s, RowId rowId) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateRowId()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public int getHoldability() throws SQLException {
        throw new SQLFeatureNotSupportedException("getHoldability()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean isClosed() throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void updateNString(int i, String s) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateNString()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void updateNString(String s, String s1) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateNString()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void updateNClob(int i, NClob nClob) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateNClob()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void updateNClob(String s, NClob nClob) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateNClob()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public NClob getNClob(int i) throws SQLException {
        throw new SQLFeatureNotSupportedException("getNClob()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public NClob getNClob(String s) throws SQLException {
        return getNClob(findColumn(s));
    }

    @Override
    @Loggable(Loggable.TRACE)
    public SQLXML getSQLXML(int i) throws SQLException {
        throw new SQLFeatureNotSupportedException("getSQLXML()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public SQLXML getSQLXML(String s) throws SQLException {
        return getSQLXML(findColumn(s));
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void updateSQLXML(int i, SQLXML sqlxml) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateSQLXML()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void updateSQLXML(String s, SQLXML sqlxml) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateSQLXML()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public String getNString(int i) throws SQLException {
        throw new SQLFeatureNotSupportedException("getNString()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public String getNString(String s) throws SQLException {
        return getNString(findColumn(s));
    }

    @Override
    @Loggable(Loggable.TRACE)
    public Reader getNCharacterStream(int i) throws SQLException {
        throw new SQLFeatureNotSupportedException("getNCharacterStream()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public Reader getNCharacterStream(String s) throws SQLException {
        return getNCharacterStream(findColumn(s));
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void updateNCharacterStream(int i, Reader reader, long l) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateNCharacterStream()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void updateNCharacterStream(String s, Reader reader, long l) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateNCharacterStream()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void updateAsciiStream(int i, InputStream inputStream, long l) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateAsciiStream()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void updateBinaryStream(int i, InputStream inputStream, long l) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateBinaryStream()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void updateCharacterStream(int i, Reader reader, long l) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateCharacterStream()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void updateAsciiStream(String s, InputStream inputStream, long l) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateAsciiStream()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void updateBinaryStream(String s, InputStream inputStream, long l) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateBinaryStream()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void updateCharacterStream(String s, Reader reader, long l) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateCharacterStream()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void updateBlob(int i, InputStream inputStream, long l) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateBlob()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void updateBlob(String s, InputStream inputStream, long l) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateBlob()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void updateClob(int i, Reader reader, long l) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateClob()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void updateClob(String s, Reader reader, long l) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateClob()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void updateNClob(int i, Reader reader, long l) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateNClob()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void updateNClob(String s, Reader reader, long l) throws SQLException {
        updateNClob(findColumn(s), reader, l);
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void updateNCharacterStream(int i, Reader reader) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateNCharacterStream()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void updateNCharacterStream(String s, Reader reader) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateNCharacterStream()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void updateAsciiStream(int i, InputStream inputStream) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateAsciiStream()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void updateBinaryStream(int i, InputStream inputStream) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateBinaryStream()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void updateCharacterStream(int i, Reader reader) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateCharacterStream()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void updateAsciiStream(String s, InputStream inputStream) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateAsciiStream()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void updateBinaryStream(String s, InputStream inputStream) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateBinaryStream()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void updateCharacterStream(String s, Reader reader) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateCharacterStream()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void updateBlob(int i, InputStream inputStream) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateBlob()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void updateBlob(String s, InputStream inputStream) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateBlob()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void updateClob(int i, Reader reader) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateClob()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void updateClob(String s, Reader reader) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateClob()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void updateNClob(int i, Reader reader) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateNClob()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void updateNClob(String s, Reader reader) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateNClob()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public <T> T getObject(int i, Class<T> aClass) throws SQLException {
        throw new SQLFeatureNotSupportedException("getObject()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public <T> T getObject(String s, Class<T> aClass) throws SQLException {
        return getObject(findColumn(s), aClass);
    }

    @Override
    @Loggable(Loggable.TRACE)
    public <T> T unwrap(Class<T> aClass) throws SQLException {
        throw new SQLFeatureNotSupportedException("unwrap()");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean isWrapperFor(Class<?> aClass) throws SQLException {
        throw new SQLFeatureNotSupportedException("isWrapperFor()");
    }
}
