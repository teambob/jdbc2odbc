# jdbc2odbc
## Overview
Open source JDBC-ODBC bridge driver. Allows you to open ODBC datasources from Java as a JDBC driver

This project should be considered ALPHA and should not be used for production. Not all functions are supported and not all unsupported functions throw SQLFeatureNotSupportedException. Run with " -Dorg.slf4j.simpleLogger.log.io.github.jdbc2odbc=trace" to find out what functions are being used

The URL format follows the old built-in [bridge syntax](https://download.oracle.com/otn_hosted_doc/jdeveloper/904preview/jdk14doc/docs/guide/jdbc/getstart/bridge.doc.html)

The Bridge driver uses the odbc subprotocol. URLs for this subprotocol are of the form:
jdbc:odbc:<data-source-name>[<attribute-name>=<attribute-value>]*

For example:

    jdbc:odbc:sybase
    jdbc:odbc:mydb;UID=me;PWD=secret
    jdbc:odbc:ora123;Cachesize=300

