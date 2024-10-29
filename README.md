# jdbc2odbc
## Overview
Open source JDBC-ODBC bridge driver. This library allows JDBC clients to access ODBC databases. There are closed sourced libraries that offer similar functionality but no open source libraries that I can find.

This project should be considered ALPHA and should not be used for production. Not all functions are supported and not all unsupported functions throw SQLFeatureNotSupportedException. Run with " -Dorg.slf4j.simpleLogger.log.io.github.jdbc2odbc=trace" to find out what functions are being used

The URL format follows the old built-in [bridge syntax](https://download.oracle.com/otn_hosted_doc/jdeveloper/904preview/jdk14doc/docs/guide/jdbc/getstart/bridge.doc.html)

The Bridge driver uses the odbc subprotocol. URLs for this subprotocol are of the form:
jdbc:odbc:<data-source-name>[<attribute-name>=<attribute-value>]*

For example:

    jdbc:odbc:sybase
    jdbc:odbc:mydb;UID=me;PWD=secret
    jdbc:odbc:ora123;Cachesize=300

To enable debugging set the system property:
org.slf4j.simpleLogger.log.io.github.jdbc2odbc=trace

## See Also
* [sun.jdbc.odbc.JdbcOdbcDriver](https://stackoverflow.com/a/57408440/141736) - Only unofficial, closed sourced downloads. Free. Not supported
* [CData JDBC-ODBC Bridge](https://www.cdata.com/drivers/bridge/jdbc/) - closed source, supported, commercial (free trial available).
