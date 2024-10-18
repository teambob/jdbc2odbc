package io.github.jdbc2odbc;

import org.lwjgl.*;

import com.jcabi.aspects.Loggable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.*;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.*;


import static org.lwjgl.odbc.SQL.*;

public class ResultSetMetaData implements java.sql.ResultSetMetaData {
    private static Logger LOGGER = LoggerFactory.getLogger(ResultSetMetaData.class);
    Long statementHandle = null;
    List<String> columnNames = null;
    Map<Short, Short> columnMapping = null;

    public ResultSetMetaData(long statementHandle, List<String> columnNames, Map<Short, Short> columnMapping) throws SQLException {
        this.statementHandle = statementHandle;
        this.columnNames = columnNames;
        this.columnMapping = columnMapping;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public int getColumnCount() throws SQLException {
        return columnNames.size();
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean isAutoIncrement(int i) throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean isCaseSensitive(int i) throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean isSearchable(int i) throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean isCurrency(int i) throws SQLException {
        //TODO
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public int isNullable(int i) throws SQLException {
        //TODO
        return 0;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean isSigned(int i) throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public int getColumnDisplaySize(int i) throws SQLException {
        ByteBuffer outValue = BufferUtils.createByteBuffer(1024);
        ShortBuffer outStringLength = BufferUtils.createShortBuffer(16);
        PointerBuffer outNumericAttributePtr = BufferUtils.createPointerBuffer(16);
        SQLColAttribute(statementHandle,
                (columnMapping != null) ? columnMapping.get((short)i) : (short)i,
                SQL_DESC_DISPLAY_SIZE,
                outValue,
                outStringLength,
                outNumericAttributePtr);

        return outValue.getInt();
    }

    @Override
    @Loggable(Loggable.TRACE)
    public String getColumnLabel(int i) throws SQLException {
        ByteBuffer outColumnName = BufferUtils.createByteBuffer(1024);
        ShortBuffer outStringLength = BufferUtils.createShortBuffer(16);
        PointerBuffer outNumericAttributePtr = BufferUtils.createPointerBuffer(16);
        SQLColAttribute(
                statementHandle,
                (columnMapping != null) ? columnMapping.get((short)i) : (short)i,
                SQL_DESC_LABEL,
                outColumnName,
                outStringLength,
                outNumericAttributePtr);
        outColumnName.limit(outStringLength.get());
        return StandardCharsets.UTF_16LE.decode(outColumnName).toString();
    }

    @Override
    @Loggable(Loggable.TRACE)
    public String getColumnName(int i) throws SQLException {
        return columnNames.get(i - 1);
    }

    @Override
    @Loggable(Loggable.TRACE)
    public String getSchemaName(int i) throws SQLException {
        ByteBuffer outColumnName = BufferUtils.createByteBuffer(1024);
        ShortBuffer outStringLength = BufferUtils.createShortBuffer(16);
        PointerBuffer outNumericAttributePtr = BufferUtils.createPointerBuffer(16);
        SQLColAttribute(
                statementHandle,
                (columnMapping != null) ? columnMapping.get((short)i) : (short)i,
                SQL_DESC_SCHEMA_NAME,
                outColumnName,
                outStringLength,
                outNumericAttributePtr);
        outColumnName.limit(outStringLength.get());
        return StandardCharsets.UTF_16LE.decode(outColumnName).toString();
    }

    @Override
    @Loggable(Loggable.TRACE)
    public int getPrecision(int i) throws SQLException {
        ByteBuffer outValue = BufferUtils.createByteBuffer(1024);
        ShortBuffer outStringLength = BufferUtils.createShortBuffer(16);
        PointerBuffer outNumericAttributePtr = BufferUtils.createPointerBuffer(16);
        SQLColAttribute(
                statementHandle,
                (columnMapping != null) ? columnMapping.get((short)i) : (short)i,
                SQL_DESC_PRECISION,
                outValue,
                outStringLength,
                outNumericAttributePtr);

        return outValue.getInt();
    }

    @Override
    @Loggable(Loggable.TRACE)
    public int getScale(int i) throws SQLException {
        ByteBuffer outValue = BufferUtils.createByteBuffer(1024);
        ShortBuffer outStringLength = BufferUtils.createShortBuffer(16);
        PointerBuffer outNumericAttributePtr = BufferUtils.createPointerBuffer(16);
        SQLColAttribute(
                statementHandle,
                (columnMapping != null) ? columnMapping.get((short)i) : (short)i,
                SQL_DESC_SCALE,
                outValue,
                outStringLength,
                outNumericAttributePtr);

        return outValue.getInt();
    }

    @Override
    @Loggable(Loggable.TRACE)
    public String getTableName(int i) throws SQLException {
        ByteBuffer outColumnName = BufferUtils.createByteBuffer(1024);
        ShortBuffer outStringLength = BufferUtils.createShortBuffer(16);
        PointerBuffer outNumericAttributePtr = BufferUtils.createPointerBuffer(16);
        SQLColAttribute(statementHandle,
                (columnMapping != null) ? columnMapping.get((short)i) : (short)i,
                SQL_DESC_TABLE_NAME,
                outColumnName,
                outStringLength,
                outNumericAttributePtr);
        outColumnName.limit(outStringLength.get());
        return StandardCharsets.UTF_16LE.decode(outColumnName).toString();
    }

    @Override
    @Loggable(Loggable.TRACE)
    public String getCatalogName(int i) throws SQLException {
        ByteBuffer outColumnName = BufferUtils.createByteBuffer(1024);
        ShortBuffer outStringLength = BufferUtils.createShortBuffer(16);
        PointerBuffer outNumericAttributePtr = BufferUtils.createPointerBuffer(16);
        SQLColAttribute(
                statementHandle,
                (columnMapping != null) ? columnMapping.get((short)i) : (short)i,
                SQL_DESC_CATALOG_NAME,
                outColumnName,
                outStringLength,
                outNumericAttributePtr);
        outColumnName.limit(outStringLength.get());
        return StandardCharsets.UTF_16LE.decode(outColumnName).toString();
    }

    @Override
    @Loggable(Loggable.TRACE)
    public int getColumnType(int i) throws SQLException {
        ByteBuffer outValue = BufferUtils.createByteBuffer(1024);
        ShortBuffer outStringLength = BufferUtils.createShortBuffer(16);
        PointerBuffer outNumericAttributePtr = BufferUtils.createPointerBuffer(16);
        SQLColAttribute(
                statementHandle,
                (columnMapping != null) ? columnMapping.get((short)i) : (short)i,
                SQL_DESC_TYPE,
                outValue,
                outStringLength,
                outNumericAttributePtr);
        int type = (int)outNumericAttributePtr.get();
        System.out.print("column type: ");
        System.out.println(type);

        return type;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public String getColumnTypeName(int i) throws SQLException {
        ByteBuffer outColumnName = BufferUtils.createByteBuffer(1024);
        ShortBuffer outStringLength = BufferUtils.createShortBuffer(16);
        PointerBuffer outNumericAttributePtr = BufferUtils.createPointerBuffer(16);
        SQLColAttribute(
                statementHandle,
                (columnMapping != null) ? columnMapping.get((short)i) : (short)i,
                SQL_DESC_TYPE_NAME,
                outColumnName,
                outStringLength,
                outNumericAttributePtr);
        outColumnName.limit(outStringLength.get());
        String type = StandardCharsets.UTF_16LE.decode(outColumnName).toString();
        System.out.print("column type: ");
        System.out.println(type);
        return type;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean isReadOnly(int i) throws SQLException {
        return true;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean isWritable(int i) throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean isDefinitelyWritable(int i) throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public String getColumnClassName(int i) throws SQLException {
        throw new SQLFeatureNotSupportedException("getColumnClassName() not supported");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public <T> T unwrap(Class<T> aClass) throws SQLException {
        return null;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean isWrapperFor(Class<?> aClass) throws SQLException {
        return false;
    }
}
