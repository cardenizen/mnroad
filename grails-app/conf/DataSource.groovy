dataSource {
                pooled = true
                driverClassName = "oracle.jdbc.OracleDriver"
    dialect="org.hibernate.dialect.Oracle10gDialect"
}
hibernate {
  default_schema='MNR'
    cache.use_second_level_cache=true
    cache.use_query_cache=true
    cache.provider_class='com.opensymphony.oscache.hibernate.OSCacheProvider'
}
// environment specific settings
environments {
  development {
    dataSource {
      url = "jdbc:oracle:thin:@localhost:1521:XE"
      username = "mnr"
      password = "mnr"
//      loggingSql = "true"
      }
    }
    test {
      dataSource {
// When deployed with Tomcat use this jndiName
          jndiName = "java:comp/env/jdbc/test_mnr"
// When deployed with JBoss use this jndiName
//        jndiName = "java:jdbc/xxxDS"
//        jndiName = "java:comp/env/jdbc/prod_fwd"
//        jndiName = "java:comp/env/jdbc/mnroadLocal"
/*
A jndi connection depends on:
1. A resource-ref entry in web.xml (in between </jsp-config> and </web-app>)
    <resource-ref>
                   <description>DB Connection</description>
                   <res-ref-name>jdbc/prod_fwd</res-ref-name>
                   <res-type>javax.sqlConnection.DataSource</res-type>
                   <res-auth>Application</res-auth>
                </resource-ref>
2. A resource entry in the server context.xml file specifying the connection properties
<Resource name="jdbc/prod_fwd" auth="Container"
              type="javax.sqlConnection.DataSource" driverClassName="oracle.jdbc.driver.OracleDriver"
              url="jdbc:oracle:thin:@server.ad.dot.state.mn.us:1521:sid"
              username="user" password="pass" maxActive="20" maxIdle="10"
              maxWait="-1"/>
3. A jdbc driver in the appropriate directory. (Tomcat 6.0.18: lib, Tomcat 5.5: common/lib, JBoss 4.2)
*/
    }
  }
  production {
    dataSource {
      jndiName = "java:comp/env/jdbc/prod_mnr"
    }
    }
}