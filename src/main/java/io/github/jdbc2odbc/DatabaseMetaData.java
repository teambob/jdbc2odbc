package io.github.jdbc2odbc;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import com.jcabi.aspects.Loggable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import org.lwjgl.*;
import org.lwjgl.system.Checks;
import org.lwjgl.system.MemoryUtil;
import static org.lwjgl.odbc.SQL.*;

public class DatabaseMetaData implements java.sql.DatabaseMetaData {
    private static Logger LOGGER = LoggerFactory.getLogger(DatabaseMetaData.class);
    Connection connection = null;
    Long connHandle = null;
    ByteBuffer BB_EMPTY = StringToUTF16ByteBuffer("");

    public DatabaseMetaData(Connection connection, long connHandle) {
        this.connection = connection;
        this.connHandle = connHandle;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean allProceduresAreCallable() throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean allTablesAreSelectable() throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public String getURL() throws SQLException {
        return "";
    }

    @Override
    @Loggable(Loggable.TRACE)
    public String getUserName() throws SQLException {
        return "";
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean isReadOnly() throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean nullsAreSortedHigh() throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean nullsAreSortedLow() throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean nullsAreSortedAtStart() throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean nullsAreSortedAtEnd() throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public String getDatabaseProductName() throws SQLException {
        return "";
    }

    @Override
    @Loggable(Loggable.TRACE)
    public String getDatabaseProductVersion() throws SQLException {
        return "";
    }

    @Override
    @Loggable(Loggable.TRACE)
    public String getDriverName() throws SQLException {
        return "jdbc2odbc.github.io";
    }

    @Override
    @Loggable(Loggable.TRACE)
    public String getDriverVersion() throws SQLException {
        return "";
    }

    @Override
    @Loggable(Loggable.TRACE)
    public int getDriverMajorVersion() {
        return 0;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public int getDriverMinorVersion() {
        return 0;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean usesLocalFiles() throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean usesLocalFilePerTable() throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean supportsMixedCaseIdentifiers() throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean storesUpperCaseIdentifiers() throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean storesLowerCaseIdentifiers() throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean storesMixedCaseIdentifiers() throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean supportsMixedCaseQuotedIdentifiers() throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean storesUpperCaseQuotedIdentifiers() throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean storesLowerCaseQuotedIdentifiers() throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean storesMixedCaseQuotedIdentifiers() throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public String getIdentifierQuoteString() throws SQLException {
        return "";
    }

    @Override
    @Loggable(Loggable.TRACE)
    public String getSQLKeywords() throws SQLException {
        return "";
    }

    @Override
    @Loggable(Loggable.TRACE)
    public String getNumericFunctions() throws SQLException {
        return "";
    }

    @Override
    @Loggable(Loggable.TRACE)
    public String getStringFunctions() throws SQLException {
        return "";
    }

    @Override
    @Loggable(Loggable.TRACE)
    public String getSystemFunctions() throws SQLException {
        return "";
    }

    @Override
    @Loggable(Loggable.TRACE)
    public String getTimeDateFunctions() throws SQLException {
        return "";
    }

    @Override
    @Loggable(Loggable.TRACE)
    public String getSearchStringEscape() throws SQLException {
        return "";
    }

    @Override
    @Loggable(Loggable.TRACE)
    public String getExtraNameCharacters() throws SQLException {
        return "";
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean supportsAlterTableWithAddColumn() throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean supportsAlterTableWithDropColumn() throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean supportsColumnAliasing() throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean nullPlusNonNullIsNull() throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean supportsConvert() throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean supportsConvert(int i, int i1) throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean supportsTableCorrelationNames() throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean supportsDifferentTableCorrelationNames() throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean supportsExpressionsInOrderBy() throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean supportsOrderByUnrelated() throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean supportsGroupBy() throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean supportsGroupByUnrelated() throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean supportsGroupByBeyondSelect() throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean supportsLikeEscapeClause() throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean supportsMultipleResultSets() throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean supportsMultipleTransactions() throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean supportsNonNullableColumns() throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean supportsMinimumSQLGrammar() throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean supportsCoreSQLGrammar() throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean supportsExtendedSQLGrammar() throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean supportsANSI92EntryLevelSQL() throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean supportsANSI92IntermediateSQL() throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean supportsANSI92FullSQL() throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean supportsIntegrityEnhancementFacility() throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean supportsOuterJoins() throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean supportsFullOuterJoins() throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean supportsLimitedOuterJoins() throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public String getSchemaTerm() throws SQLException {
        return "";
    }

    @Override
    @Loggable(Loggable.TRACE)
    public String getProcedureTerm() throws SQLException {
        return "";
    }

    @Override
    @Loggable(Loggable.TRACE)
    public String getCatalogTerm() throws SQLException {
        return "";
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean isCatalogAtStart() throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public String getCatalogSeparator() throws SQLException {
        return "";
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean supportsSchemasInDataManipulation() throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean supportsSchemasInProcedureCalls() throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean supportsSchemasInTableDefinitions() throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean supportsSchemasInIndexDefinitions() throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean supportsSchemasInPrivilegeDefinitions() throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean supportsCatalogsInDataManipulation() throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean supportsCatalogsInProcedureCalls() throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean supportsCatalogsInTableDefinitions() throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean supportsCatalogsInIndexDefinitions() throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean supportsCatalogsInPrivilegeDefinitions() throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean supportsPositionedDelete() throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean supportsPositionedUpdate() throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean supportsSelectForUpdate() throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean supportsStoredProcedures() throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean supportsSubqueriesInComparisons() throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean supportsSubqueriesInExists() throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean supportsSubqueriesInIns() throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean supportsSubqueriesInQuantifieds() throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean supportsCorrelatedSubqueries() throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean supportsUnion() throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean supportsUnionAll() throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean supportsOpenCursorsAcrossCommit() throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean supportsOpenCursorsAcrossRollback() throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean supportsOpenStatementsAcrossCommit() throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean supportsOpenStatementsAcrossRollback() throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public int getMaxBinaryLiteralLength() throws SQLException {
        return 0;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public int getMaxCharLiteralLength() throws SQLException {
        return 0;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public int getMaxColumnNameLength() throws SQLException {
        return 0;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public int getMaxColumnsInGroupBy() throws SQLException {
        return 0;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public int getMaxColumnsInIndex() throws SQLException {
        return 0;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public int getMaxColumnsInOrderBy() throws SQLException {
        return 0;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public int getMaxColumnsInSelect() throws SQLException {
        return 0;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public int getMaxColumnsInTable() throws SQLException {
        return 0;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public int getMaxConnections() throws SQLException {
        return 0;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public int getMaxCursorNameLength() throws SQLException {
        return 0;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public int getMaxIndexLength() throws SQLException {
        return 0;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public int getMaxSchemaNameLength() throws SQLException {
        return 0;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public int getMaxProcedureNameLength() throws SQLException {
        return 0;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public int getMaxCatalogNameLength() throws SQLException {
        return 0;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public int getMaxRowSize() throws SQLException {
        return 0;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean doesMaxRowSizeIncludeBlobs() throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public int getMaxStatementLength() throws SQLException {
        return 0;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public int getMaxStatements() throws SQLException {
        return 0;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public int getMaxTableNameLength() throws SQLException {
        return 0;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public int getMaxTablesInSelect() throws SQLException {
        return 0;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public int getMaxUserNameLength() throws SQLException {
        return 0;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public int getDefaultTransactionIsolation() throws SQLException {
        return 0;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean supportsTransactions() throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean supportsTransactionIsolationLevel(int i) throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean supportsDataDefinitionAndDataManipulationTransactions() throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean supportsDataManipulationTransactionsOnly() throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean dataDefinitionCausesTransactionCommit() throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean dataDefinitionIgnoredInTransactions() throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public ResultSet getProcedures(String catalog, String schemaPattern, String procedureNamePattern) throws SQLException {
        PointerBuffer outHandle = PointerBuffer.allocateDirect(8);
        SQLAllocHandle(
                SQL_HANDLE_STMT, connHandle, outHandle);

        long statementHandle = outHandle.get();
        SQLProcedures(
                statementHandle,
                StandardCharsets.UTF_16LE.encode(catalog),
                StandardCharsets.UTF_16LE.encode(schemaPattern),
                StandardCharsets.UTF_16LE.encode(procedureNamePattern)
        );
        return new io.github.jdbc2odbc.ResultSet(null, statementHandle);
    }

    @Override
    @Loggable(Loggable.TRACE)
    public ResultSet getProcedureColumns(String s, String s1, String s2, String s3) throws SQLException {
        return null;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public ResultSet getTables(String catalog, String schemaPattern, String tableNamePattern, String[] types) throws SQLException {
        System.out.println("getTables()");
        PointerBuffer outHandle = BufferUtils.createPointerBuffer(8);
        if (SQLAllocHandle(
                SQL_HANDLE_STMT, connHandle, outHandle) != SQL_SUCCESS) {
            throw new SQLException("getTables() failed");
        }
        long statementHandle = outHandle.get();

        String tableName = new String();
        if (tableNamePattern != null){
            tableName = tableNamePattern;
        } else {
            tableName = SQL_ALL_TABLE_TYPES;
        }

        if (SQLTables(statementHandle, StringToUTF16ByteBuffer(catalog), StringToUTF16ByteBuffer(schemaPattern), StringToUTF16ByteBuffer(tableName),  null) != SQL_SUCCESS) {
            throw new SQLException("getTables() failed");
        }

        return new io.github.jdbc2odbc.ResultSet(null, statementHandle);
    }

    @Override
    @Loggable(Loggable.TRACE)
    public ResultSet getSchemas() throws SQLException {
        return getSchemas(null, null);
    }

    @Override
    @Loggable(Loggable.TRACE)
    public ResultSet getCatalogs() throws SQLException {
        System.out.println("getCatalogs()");
        PointerBuffer outHandle = BufferUtils.createPointerBuffer(8);
        SQLAllocHandle(
                SQL_HANDLE_STMT, connHandle, outHandle);
        long statementHandle = outHandle.get();

        SQLTables(statementHandle, StringToUTF16ByteBuffer(SQL_ALL_CATALOGS), null, null,  null);

        return new io.github.jdbc2odbc.ResultSet(null, statementHandle);
    }

    @Override
    @Loggable(Loggable.TRACE)
    public ResultSet getTableTypes() throws SQLException {
        PointerBuffer outHandle = BufferUtils.createPointerBuffer(8);
        if (SQLAllocHandle(
                SQL_HANDLE_STMT, connHandle, outHandle) != SQL_SUCCESS) {
            throw new SQLException("getTableTypes() failed");
        }
        long statementHandle = outHandle.get();

        if (SQLTables(statementHandle, StringToUTF16ByteBuffer(""), StringToUTF16ByteBuffer(""), StringToUTF16ByteBuffer(""),  StringToUTF16ByteBuffer(SQL_ALL_TABLE_TYPES))!=SQL_SUCCESS) {
            throw new SQLException("getTableTypes() failed");
        };
        Map<Short, Short> columnMapping = new HashMap<>();
        columnMapping.put((short)1, (short)4);

        return new io.github.jdbc2odbc.ResultSet(null, statementHandle, columnMapping);
    }

    @Override
    @Loggable(Loggable.TRACE)
    public ResultSet getColumns(String catalog, String schemaPattern, String tableNamePattern, String columnNamePatter) throws SQLException {
        PointerBuffer outHandle = BufferUtils.createPointerBuffer(8);
        SQLAllocHandle(
                SQL_HANDLE_STMT, connHandle, outHandle);
        long statementHandle = outHandle.get();

        BufferUtils bbCatalog, bbSchema, bbTable, bbColumn;

        SQLColumns(statementHandle, StringToUTF16ByteBuffer(catalog), StringToUTF16ByteBuffer(schemaPattern), StringToUTF16ByteBuffer(tableNamePattern),  StringToUTF16ByteBuffer(columnNamePatter));
        return new io.github.jdbc2odbc.ResultSet(null, statementHandle);
    }

    @Override
    @Loggable(Loggable.TRACE)
    public ResultSet getColumnPrivileges(String s, String s1, String s2, String s3) throws SQLException {
        return null;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public ResultSet getTablePrivileges(String s, String s1, String s2) throws SQLException {
        return null;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public ResultSet getBestRowIdentifier(String s, String s1, String s2, int i, boolean b) throws SQLException {
        return null;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public ResultSet getVersionColumns(String s, String s1, String s2) throws SQLException {
        return null;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public ResultSet getPrimaryKeys(String s, String s1, String s2) throws SQLException {
        return null;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public ResultSet getImportedKeys(String s, String s1, String s2) throws SQLException {
        return null;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public ResultSet getExportedKeys(String s, String s1, String s2) throws SQLException {
        return null;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public ResultSet getCrossReference(String s, String s1, String s2, String s3, String s4, String s5) throws SQLException {
        return null;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public ResultSet getTypeInfo() throws SQLException {
        return null;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public ResultSet getIndexInfo(String s, String s1, String s2, boolean b, boolean b1) throws SQLException {
        return null;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean supportsResultSetType(int i) throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean supportsResultSetConcurrency(int i, int i1) throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean ownUpdatesAreVisible(int i) throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean ownDeletesAreVisible(int i) throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean ownInsertsAreVisible(int i) throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean othersUpdatesAreVisible(int i) throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean othersDeletesAreVisible(int i) throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean othersInsertsAreVisible(int i) throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean updatesAreDetected(int i) throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean deletesAreDetected(int i) throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean insertsAreDetected(int i) throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean supportsBatchUpdates() throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public ResultSet getUDTs(String s, String s1, String s2, int[] ints) throws SQLException {
        return null;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public Connection getConnection() throws SQLException {
        return connection;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean supportsSavepoints() throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean supportsNamedParameters() throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean supportsMultipleOpenResults() throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean supportsGetGeneratedKeys() throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public ResultSet getSuperTypes(String s, String s1, String s2) throws SQLException {
        return null;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public ResultSet getSuperTables(String s, String s1, String s2) throws SQLException {
        return null;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public ResultSet getAttributes(String s, String s1, String s2, String s3) throws SQLException {
        return null;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean supportsResultSetHoldability(int i) throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public int getResultSetHoldability() throws SQLException {
        return 0;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public int getDatabaseMajorVersion() throws SQLException {
        return 0;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public int getDatabaseMinorVersion() throws SQLException {
        return 0;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public int getJDBCMajorVersion() throws SQLException {
        return 0;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public int getJDBCMinorVersion() throws SQLException {
        return 0;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public int getSQLStateType() throws SQLException {
        return 0;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean locatorsUpdateCopy() throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean supportsStatementPooling() throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public RowIdLifetime getRowIdLifetime() throws SQLException {
        return null;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public ResultSet getSchemas(String catalog, String schemaPattern) throws SQLException {
        System.out.println("getSchemas()");
        PointerBuffer outHandle = PointerBuffer.allocateDirect(8);
        SQLAllocHandle(
                SQL_HANDLE_STMT, connHandle, outHandle);
        long statementHandle = outHandle.get();

        SQLTables(statementHandle, this.StringToUTF16ByteBuffer(catalog), StringToUTF16ByteBuffer(schemaPattern), null,  null);
        Map<Short, Short> schemaMap = new HashMap<Short, Short>();
        schemaMap.put((short)2, (short)1); // TABLE_SCHEM is column 1
        schemaMap.put((short)1, (short)2); // TABLE_CATALOG is column 2
        return new io.github.jdbc2odbc.ResultSet(null, statementHandle, schemaMap);
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean supportsStoredFunctionsUsingCallSyntax() throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean autoCommitFailureClosesAllResultSets() throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public ResultSet getClientInfoProperties() throws SQLException {
        return null;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public ResultSet getFunctions(String catalog, String schemaPattern, String functionNamePattern) throws SQLException {
        throw new SQLFeatureNotSupportedException("getFunctions() not implemented");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public ResultSet getFunctionColumns(String s, String s1, String s2, String s3) throws SQLException {
        return null;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public ResultSet getPseudoColumns(String s, String s1, String s2, String s3) throws SQLException {
        return null;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean generatedKeyAlwaysReturned() throws SQLException {
        return false;
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

    private ByteBuffer StringToUTF16ByteBuffer(String s) {
        if (s == null) {
            return null;
        }
        ByteBuffer bb = BufferUtils.createByteBuffer((s.length()+1)*2);
        bb.put(s.getBytes(StandardCharsets.UTF_16LE));
        bb.put((byte) 0);
        bb.position(0);
        return bb;
    }
}

