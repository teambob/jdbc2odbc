package io.github.jdbc2odbc;

import org.lwjgl.*;
import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.sql.Connection;
import java.sql.ResultSet;

import static org.lwjgl.odbc.SQL.*;

public class DatabaseMetaData implements java.sql.DatabaseMetaData {
    Connection connection = null;
    Long connHandle = null;

    public DatabaseMetaData(Connection connection, long connHandle) {
        this.connection = connection;
        this.connHandle = connHandle;
    }

    @Override
    public boolean allProceduresAreCallable() throws SQLException {
        return false;
    }

    @Override
    public boolean allTablesAreSelectable() throws SQLException {
        return false;
    }

    @Override
    public String getURL() throws SQLException {
        return "";
    }

    @Override
    public String getUserName() throws SQLException {
        return "";
    }

    @Override
    public boolean isReadOnly() throws SQLException {
        return false;
    }

    @Override
    public boolean nullsAreSortedHigh() throws SQLException {
        return false;
    }

    @Override
    public boolean nullsAreSortedLow() throws SQLException {
        return false;
    }

    @Override
    public boolean nullsAreSortedAtStart() throws SQLException {
        return false;
    }

    @Override
    public boolean nullsAreSortedAtEnd() throws SQLException {
        return false;
    }

    @Override
    public String getDatabaseProductName() throws SQLException {
        return "";
    }

    @Override
    public String getDatabaseProductVersion() throws SQLException {
        return "";
    }

    @Override
    public String getDriverName() throws SQLException {
        return "jdbc2odbc.github.io";
    }

    @Override
    public String getDriverVersion() throws SQLException {
        return "";
    }

    @Override
    public int getDriverMajorVersion() {
        return 0;
    }

    @Override
    public int getDriverMinorVersion() {
        return 0;
    }

    @Override
    public boolean usesLocalFiles() throws SQLException {
        return false;
    }

    @Override
    public boolean usesLocalFilePerTable() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsMixedCaseIdentifiers() throws SQLException {
        return false;
    }

    @Override
    public boolean storesUpperCaseIdentifiers() throws SQLException {
        return false;
    }

    @Override
    public boolean storesLowerCaseIdentifiers() throws SQLException {
        return false;
    }

    @Override
    public boolean storesMixedCaseIdentifiers() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsMixedCaseQuotedIdentifiers() throws SQLException {
        return false;
    }

    @Override
    public boolean storesUpperCaseQuotedIdentifiers() throws SQLException {
        return false;
    }

    @Override
    public boolean storesLowerCaseQuotedIdentifiers() throws SQLException {
        return false;
    }

    @Override
    public boolean storesMixedCaseQuotedIdentifiers() throws SQLException {
        return false;
    }

    @Override
    public String getIdentifierQuoteString() throws SQLException {
        return "";
    }

    @Override
    public String getSQLKeywords() throws SQLException {
        return "";
    }

    @Override
    public String getNumericFunctions() throws SQLException {
        return "";
    }

    @Override
    public String getStringFunctions() throws SQLException {
        return "";
    }

    @Override
    public String getSystemFunctions() throws SQLException {
        return "";
    }

    @Override
    public String getTimeDateFunctions() throws SQLException {
        return "";
    }

    @Override
    public String getSearchStringEscape() throws SQLException {
        return "";
    }

    @Override
    public String getExtraNameCharacters() throws SQLException {
        return "";
    }

    @Override
    public boolean supportsAlterTableWithAddColumn() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsAlterTableWithDropColumn() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsColumnAliasing() throws SQLException {
        return false;
    }

    @Override
    public boolean nullPlusNonNullIsNull() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsConvert() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsConvert(int i, int i1) throws SQLException {
        return false;
    }

    @Override
    public boolean supportsTableCorrelationNames() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsDifferentTableCorrelationNames() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsExpressionsInOrderBy() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsOrderByUnrelated() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsGroupBy() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsGroupByUnrelated() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsGroupByBeyondSelect() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsLikeEscapeClause() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsMultipleResultSets() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsMultipleTransactions() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsNonNullableColumns() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsMinimumSQLGrammar() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsCoreSQLGrammar() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsExtendedSQLGrammar() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsANSI92EntryLevelSQL() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsANSI92IntermediateSQL() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsANSI92FullSQL() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsIntegrityEnhancementFacility() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsOuterJoins() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsFullOuterJoins() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsLimitedOuterJoins() throws SQLException {
        return false;
    }

    @Override
    public String getSchemaTerm() throws SQLException {
        return "";
    }

    @Override
    public String getProcedureTerm() throws SQLException {
        return "";
    }

    @Override
    public String getCatalogTerm() throws SQLException {
        return "";
    }

    @Override
    public boolean isCatalogAtStart() throws SQLException {
        return false;
    }

    @Override
    public String getCatalogSeparator() throws SQLException {
        return "";
    }

    @Override
    public boolean supportsSchemasInDataManipulation() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsSchemasInProcedureCalls() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsSchemasInTableDefinitions() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsSchemasInIndexDefinitions() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsSchemasInPrivilegeDefinitions() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsCatalogsInDataManipulation() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsCatalogsInProcedureCalls() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsCatalogsInTableDefinitions() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsCatalogsInIndexDefinitions() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsCatalogsInPrivilegeDefinitions() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsPositionedDelete() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsPositionedUpdate() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsSelectForUpdate() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsStoredProcedures() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsSubqueriesInComparisons() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsSubqueriesInExists() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsSubqueriesInIns() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsSubqueriesInQuantifieds() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsCorrelatedSubqueries() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsUnion() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsUnionAll() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsOpenCursorsAcrossCommit() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsOpenCursorsAcrossRollback() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsOpenStatementsAcrossCommit() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsOpenStatementsAcrossRollback() throws SQLException {
        return false;
    }

    @Override
    public int getMaxBinaryLiteralLength() throws SQLException {
        return 0;
    }

    @Override
    public int getMaxCharLiteralLength() throws SQLException {
        return 0;
    }

    @Override
    public int getMaxColumnNameLength() throws SQLException {
        return 0;
    }

    @Override
    public int getMaxColumnsInGroupBy() throws SQLException {
        return 0;
    }

    @Override
    public int getMaxColumnsInIndex() throws SQLException {
        return 0;
    }

    @Override
    public int getMaxColumnsInOrderBy() throws SQLException {
        return 0;
    }

    @Override
    public int getMaxColumnsInSelect() throws SQLException {
        return 0;
    }

    @Override
    public int getMaxColumnsInTable() throws SQLException {
        return 0;
    }

    @Override
    public int getMaxConnections() throws SQLException {
        return 0;
    }

    @Override
    public int getMaxCursorNameLength() throws SQLException {
        return 0;
    }

    @Override
    public int getMaxIndexLength() throws SQLException {
        return 0;
    }

    @Override
    public int getMaxSchemaNameLength() throws SQLException {
        return 0;
    }

    @Override
    public int getMaxProcedureNameLength() throws SQLException {
        return 0;
    }

    @Override
    public int getMaxCatalogNameLength() throws SQLException {
        return 0;
    }

    @Override
    public int getMaxRowSize() throws SQLException {
        return 0;
    }

    @Override
    public boolean doesMaxRowSizeIncludeBlobs() throws SQLException {
        return false;
    }

    @Override
    public int getMaxStatementLength() throws SQLException {
        return 0;
    }

    @Override
    public int getMaxStatements() throws SQLException {
        return 0;
    }

    @Override
    public int getMaxTableNameLength() throws SQLException {
        return 0;
    }

    @Override
    public int getMaxTablesInSelect() throws SQLException {
        return 0;
    }

    @Override
    public int getMaxUserNameLength() throws SQLException {
        return 0;
    }

    @Override
    public int getDefaultTransactionIsolation() throws SQLException {
        return 0;
    }

    @Override
    public boolean supportsTransactions() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsTransactionIsolationLevel(int i) throws SQLException {
        return false;
    }

    @Override
    public boolean supportsDataDefinitionAndDataManipulationTransactions() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsDataManipulationTransactionsOnly() throws SQLException {
        return false;
    }

    @Override
    public boolean dataDefinitionCausesTransactionCommit() throws SQLException {
        return false;
    }

    @Override
    public boolean dataDefinitionIgnoredInTransactions() throws SQLException {
        return false;
    }

    @Override
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
        return new io.github.jdbc2odbc.ResultSet(statementHandle);
    }

    @Override
    public ResultSet getProcedureColumns(String s, String s1, String s2, String s3) throws SQLException {
        return null;
    }

    @Override
    public ResultSet getTables(String catalog, String schemaPattern, String tableNamePattern, String[] types) throws SQLException {
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

        return new io.github.jdbc2odbc.ResultSet(statementHandle);
    }

    @Override
    public ResultSet getSchemas() throws SQLException {
        return getSchemas(null, null);
    }

    @Override
    public ResultSet getCatalogs() throws SQLException {
        PointerBuffer outHandle = BufferUtils.createPointerBuffer(8);
        SQLAllocHandle(
                SQL_HANDLE_STMT, connHandle, outHandle);
        long statementHandle = outHandle.get();

        SQLTables(statementHandle, StringToUTF16ByteBuffer(SQL_ALL_CATALOGS), null, null,  null);

        return new io.github.jdbc2odbc.ResultSet(statementHandle);
    }

    @Override
    public ResultSet getTableTypes() throws SQLException {
        PointerBuffer outHandle = BufferUtils.createPointerBuffer(8);
        SQLAllocHandle(
                SQL_HANDLE_STMT, connHandle, outHandle);
        long statementHandle = outHandle.get();

        SQLTables(statementHandle, null, null, null,  StringToUTF16ByteBuffer(SQL_ALL_TABLE_TYPES));

        return new io.github.jdbc2odbc.ResultSet(statementHandle);
    }

    @Override
    public ResultSet getColumns(String catalog, String schemaPattern, String tableNamePattern, String columnNamePatter) throws SQLException {
        PointerBuffer outHandle = BufferUtils.createPointerBuffer(8);
        SQLAllocHandle(
                SQL_HANDLE_STMT, connHandle, outHandle);
        long statementHandle = outHandle.get();

        BufferUtils bbCatalog, bbSchema, bbTable, bbColumn;

        SQLColumns(statementHandle, StringToUTF16ByteBuffer(catalog), StringToUTF16ByteBuffer(schemaPattern), StringToUTF16ByteBuffer(tableNamePattern),  StringToUTF16ByteBuffer(columnNamePatter));
        return new io.github.jdbc2odbc.ResultSet(statementHandle);
    }

    @Override
    public ResultSet getColumnPrivileges(String s, String s1, String s2, String s3) throws SQLException {
        return null;
    }

    @Override
    public ResultSet getTablePrivileges(String s, String s1, String s2) throws SQLException {
        return null;
    }

    @Override
    public ResultSet getBestRowIdentifier(String s, String s1, String s2, int i, boolean b) throws SQLException {
        return null;
    }

    @Override
    public ResultSet getVersionColumns(String s, String s1, String s2) throws SQLException {
        return null;
    }

    @Override
    public ResultSet getPrimaryKeys(String s, String s1, String s2) throws SQLException {
        return null;
    }

    @Override
    public ResultSet getImportedKeys(String s, String s1, String s2) throws SQLException {
        return null;
    }

    @Override
    public ResultSet getExportedKeys(String s, String s1, String s2) throws SQLException {
        return null;
    }

    @Override
    public ResultSet getCrossReference(String s, String s1, String s2, String s3, String s4, String s5) throws SQLException {
        return null;
    }

    @Override
    public ResultSet getTypeInfo() throws SQLException {
        return null;
    }

    @Override
    public ResultSet getIndexInfo(String s, String s1, String s2, boolean b, boolean b1) throws SQLException {
        return null;
    }

    @Override
    public boolean supportsResultSetType(int i) throws SQLException {
        return false;
    }

    @Override
    public boolean supportsResultSetConcurrency(int i, int i1) throws SQLException {
        return false;
    }

    @Override
    public boolean ownUpdatesAreVisible(int i) throws SQLException {
        return false;
    }

    @Override
    public boolean ownDeletesAreVisible(int i) throws SQLException {
        return false;
    }

    @Override
    public boolean ownInsertsAreVisible(int i) throws SQLException {
        return false;
    }

    @Override
    public boolean othersUpdatesAreVisible(int i) throws SQLException {
        return false;
    }

    @Override
    public boolean othersDeletesAreVisible(int i) throws SQLException {
        return false;
    }

    @Override
    public boolean othersInsertsAreVisible(int i) throws SQLException {
        return false;
    }

    @Override
    public boolean updatesAreDetected(int i) throws SQLException {
        return false;
    }

    @Override
    public boolean deletesAreDetected(int i) throws SQLException {
        return false;
    }

    @Override
    public boolean insertsAreDetected(int i) throws SQLException {
        return false;
    }

    @Override
    public boolean supportsBatchUpdates() throws SQLException {
        return false;
    }

    @Override
    public ResultSet getUDTs(String s, String s1, String s2, int[] ints) throws SQLException {
        return null;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return connection;
    }

    @Override
    public boolean supportsSavepoints() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsNamedParameters() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsMultipleOpenResults() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsGetGeneratedKeys() throws SQLException {
        return false;
    }

    @Override
    public ResultSet getSuperTypes(String s, String s1, String s2) throws SQLException {
        return null;
    }

    @Override
    public ResultSet getSuperTables(String s, String s1, String s2) throws SQLException {
        return null;
    }

    @Override
    public ResultSet getAttributes(String s, String s1, String s2, String s3) throws SQLException {
        return null;
    }

    @Override
    public boolean supportsResultSetHoldability(int i) throws SQLException {
        return false;
    }

    @Override
    public int getResultSetHoldability() throws SQLException {
        return 0;
    }

    @Override
    public int getDatabaseMajorVersion() throws SQLException {
        return 0;
    }

    @Override
    public int getDatabaseMinorVersion() throws SQLException {
        return 0;
    }

    @Override
    public int getJDBCMajorVersion() throws SQLException {
        return 0;
    }

    @Override
    public int getJDBCMinorVersion() throws SQLException {
        return 0;
    }

    @Override
    public int getSQLStateType() throws SQLException {
        return 0;
    }

    @Override
    public boolean locatorsUpdateCopy() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsStatementPooling() throws SQLException {
        return false;
    }

    @Override
    public RowIdLifetime getRowIdLifetime() throws SQLException {
        return null;
    }

    @Override
    public ResultSet getSchemas(String catalog, String schemaPattern) throws SQLException {
        PointerBuffer outHandle = PointerBuffer.allocateDirect(8);
        SQLAllocHandle(
                SQL_HANDLE_STMT, connHandle, outHandle);
        long statementHandle = outHandle.get();

        SQLTables(statementHandle, this.StringToUTF16ByteBuffer(catalog), StringToUTF16ByteBuffer(schemaPattern), null,  null);

        return new io.github.jdbc2odbc.ResultSet(statementHandle);
    }

    @Override
    public boolean supportsStoredFunctionsUsingCallSyntax() throws SQLException {
        return false;
    }

    @Override
    public boolean autoCommitFailureClosesAllResultSets() throws SQLException {
        return false;
    }

    @Override
    public ResultSet getClientInfoProperties() throws SQLException {
        return null;
    }

    @Override
    public ResultSet getFunctions(String catalog, String schemaPattern, String functionNamePattern) throws SQLException {
        throw new SQLFeatureNotSupportedException("getFunctions() not implemented");
    }

    @Override
    public ResultSet getFunctionColumns(String s, String s1, String s2, String s3) throws SQLException {
        return null;
    }

    @Override
    public ResultSet getPseudoColumns(String s, String s1, String s2, String s3) throws SQLException {
        return null;
    }

    @Override
    public boolean generatedKeyAlwaysReturned() throws SQLException {
        return false;
    }

    @Override
    public <T> T unwrap(Class<T> aClass) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> aClass) throws SQLException {
        return false;
    }

    private ByteBuffer StringToUTF16ByteBuffer(String s) {
        if (s == null) {
            return null;
        }
        ByteBuffer bb = BufferUtils.createByteBuffer(s.length()*2);
        bb.put(s.getBytes(StandardCharsets.UTF_16LE));
        return bb;
    }
}

