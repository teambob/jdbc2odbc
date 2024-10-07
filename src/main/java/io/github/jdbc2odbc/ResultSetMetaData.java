package io.github.jdbc2odbc;

import org.lwjgl.*;
import java.nio.*;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.List;

import static org.lwjgl.odbc.SQL.*;

public class ResultSetMetaData implements java.sql.ResultSetMetaData {
    Long statementHandle = null;
    List<String> columnNames = null;
    public ResultSetMetaData(long statementHandle, List<String> columnNames) throws SQLException {
        this.statementHandle = statementHandle;
        this.columnNames = columnNames;
    }

    @Override
    public int getColumnCount() throws SQLException {
        return columnNames.size();
    }

    @Override
    public boolean isAutoIncrement(int i) throws SQLException {
        return false;
    }

    @Override
    public boolean isCaseSensitive(int i) throws SQLException {
        return false;
    }

    @Override
    public boolean isSearchable(int i) throws SQLException {
        return false;
    }

    @Override
    public boolean isCurrency(int i) throws SQLException {
        //TODO
        return false;
    }

    @Override
    public int isNullable(int i) throws SQLException {
        //TODO
        return 0;
    }

    @Override
    public boolean isSigned(int i) throws SQLException {
        return false;
    }

    @Override
    public int getColumnDisplaySize(int i) throws SQLException {
        ByteBuffer outValue = BufferUtils.createByteBuffer(1024);
        ShortBuffer outStringLength = BufferUtils.createShortBuffer(16);
        PointerBuffer outNumericAttributePtr = BufferUtils.createPointerBuffer(16);
        SQLColAttribute(statementHandle, (short)i, SQL_DESC_DISPLAY_SIZE, outValue, outStringLength, outNumericAttributePtr);

        return outValue.getInt();
    }

    @Override
    public String getColumnLabel(int i) throws SQLException {
        ByteBuffer outColumnName = BufferUtils.createByteBuffer(1024);
        ShortBuffer outStringLength = BufferUtils.createShortBuffer(16);
        PointerBuffer outNumericAttributePtr = BufferUtils.createPointerBuffer(16);
        SQLColAttribute(statementHandle, (short)i, SQL_DESC_LABEL, outColumnName, outStringLength, outNumericAttributePtr);
        outColumnName.limit(outStringLength.get());
        return StandardCharsets.UTF_16LE.decode(outColumnName).toString();
    }

    @Override
    public String getColumnName(int i) throws SQLException {
        return columnNames.get(i - 1);
    }

    @Override
    public String getSchemaName(int i) throws SQLException {
        ByteBuffer outColumnName = BufferUtils.createByteBuffer(1024);
        ShortBuffer outStringLength = BufferUtils.createShortBuffer(16);
        PointerBuffer outNumericAttributePtr = BufferUtils.createPointerBuffer(16);
        SQLColAttribute(statementHandle, (short)i, SQL_DESC_SCHEMA_NAME, outColumnName, outStringLength, outNumericAttributePtr);
        outColumnName.limit(outStringLength.get());
        return StandardCharsets.UTF_16LE.decode(outColumnName).toString();
    }

    @Override
    public int getPrecision(int i) throws SQLException {
        ByteBuffer outValue = BufferUtils.createByteBuffer(1024);
        ShortBuffer outStringLength = BufferUtils.createShortBuffer(16);
        PointerBuffer outNumericAttributePtr = BufferUtils.createPointerBuffer(16);
        SQLColAttribute(statementHandle, (short)i, SQL_DESC_PRECISION, outValue, outStringLength, outNumericAttributePtr);

        return outValue.getInt();

    }

    @Override
    public int getScale(int i) throws SQLException {
        ByteBuffer outValue = BufferUtils.createByteBuffer(1024);
        ShortBuffer outStringLength = BufferUtils.createShortBuffer(16);
        PointerBuffer outNumericAttributePtr = BufferUtils.createPointerBuffer(16);
        SQLColAttribute(statementHandle, (short)i, SQL_DESC_SCALE, outValue, outStringLength, outNumericAttributePtr);

        return outValue.getInt();

    }

    @Override
    public String getTableName(int i) throws SQLException {
        ByteBuffer outColumnName = BufferUtils.createByteBuffer(1024);
        ShortBuffer outStringLength = BufferUtils.createShortBuffer(16);
        PointerBuffer outNumericAttributePtr = BufferUtils.createPointerBuffer(16);
        SQLColAttribute(statementHandle, (short)i, SQL_DESC_TABLE_NAME, outColumnName, outStringLength, outNumericAttributePtr);
        outColumnName.limit(outStringLength.get());
        return StandardCharsets.UTF_16LE.decode(outColumnName).toString();
    }

    @Override
    public String getCatalogName(int i) throws SQLException {
        ByteBuffer outColumnName = BufferUtils.createByteBuffer(1024);
        ShortBuffer outStringLength = BufferUtils.createShortBuffer(16);
        PointerBuffer outNumericAttributePtr = BufferUtils.createPointerBuffer(16);
        SQLColAttribute(statementHandle, (short)i, SQL_DESC_CATALOG_NAME, outColumnName, outStringLength, outNumericAttributePtr);
        outColumnName.limit(outStringLength.get());
        return StandardCharsets.UTF_16LE.decode(outColumnName).toString();
    }

    @Override
    public int getColumnType(int i) throws SQLException {
        ByteBuffer outValue = BufferUtils.createByteBuffer(1024);
        ShortBuffer outStringLength = BufferUtils.createShortBuffer(16);
        PointerBuffer outNumericAttributePtr = BufferUtils.createPointerBuffer(16);
        SQLColAttribute(statementHandle, (short)i, SQL_DESC_TYPE, outValue, outStringLength, outNumericAttributePtr);

        return outValue.getInt();

    }

    @Override
    public String getColumnTypeName(int i) throws SQLException {
        ByteBuffer outColumnName = BufferUtils.createByteBuffer(1024);
        ShortBuffer outStringLength = BufferUtils.createShortBuffer(16);
        PointerBuffer outNumericAttributePtr = BufferUtils.createPointerBuffer(16);
        SQLColAttribute(statementHandle, (short)i, SQL_DESC_TYPE_NAME, outColumnName, outStringLength, outNumericAttributePtr);
        outColumnName.limit(outStringLength.get());
        return StandardCharsets.UTF_16LE.decode(outColumnName).toString();
    }

    @Override
    public boolean isReadOnly(int i) throws SQLException {
        return true;
    }

    @Override
    public boolean isWritable(int i) throws SQLException {
        return false;
    }

    @Override
    public boolean isDefinitelyWritable(int i) throws SQLException {
        return false;
    }

    @Override
    public String getColumnClassName(int i) throws SQLException {
        throw new SQLFeatureNotSupportedException("getColumnClassName() not supported");
    }

    @Override
    public <T> T unwrap(Class<T> aClass) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> aClass) throws SQLException {
        return false;
    }
}
