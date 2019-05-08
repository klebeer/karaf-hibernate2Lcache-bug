**Error with Second Level Cache with karaf/hibernate/Jcache/ehcache**

This project reproduce an error (or a misconfiguration ) trying to use second level cache withÑ
 * Hibernate 5.4.2
 * Karaf 4.25 
 * Ehcache 3
 * Jcache
 
 
 
 The application has 3 modules:
 
 * app
 * features
 * command
 
 In the app module there is a `persistence.xml` file:
 
 ````xml 
 <?xml version="1.0" encoding="UTF-8"?>
 <persistence version="2.0" xmlns="http://java.sun.com/xml/ns/persistence"
              xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
              xsi:schemaLocation="http://java.sun.com/xml/ns/persistence http://java.sun.com/xml/ns/persistence/persistence_2_0.xsd">
 
 
     <persistence-unit name="user-hibernate" transaction-type="JTA">
         <provider>org.hibernate.jpa.HibernatePersistenceProvider</provider>
 
         <jta-data-source>osgi:service/javax.sql.DataSource/(osgi.jndi.service.name=user)</jta-data-source>
         <non-jta-data-source>osgi:service/javax.sql.DataSource/(osgi.jndi.service.name=user)</non-jta-data-source>
         <class>org.ecuadorjug.jpa.User</class>
         <properties>
             <property name="hibernate.dialect" value="org.hibernate.dialect.H2Dialect"/>
             <property name="hibernate.hbm2ddl.auto" value="create-drop"/>
             
             <property name="hibernate.cache.use_second_level_cache" value="true" />
             <property name="hibernate.cache.use_query_cache" value="true" />
             
             <property name="hibernate.cache.region.factory_class" value="org.hibernate.cache.jcache.internal.JCacheRegionFactory" />
             <property name="hibernate.javax.cache.provider" value="org.ehcache.jsr107.EhcacheCachingProvider" />
 
         </properties>
     </persistence-unit>
 
 </persistence>
 ````
 
The application works fine if the second level cache is off, but if the property  
`hibernate.cache.use_second_level_cache` is true there is  a 

```
Caused by: java.lang.ClassNotFoundException: org.ehcache.jsr107.EhcacheCachingProvider
```

 
Ehcache bundle is exporting `EhcacheCachingProvider` but is not accessible by  hibernete classloader 

```
service; [javax.cache.spi.CachingProvider] with properties:
   component.id = 20
   component.name = org.ehcache.jsr107.EhcacheCachingProvider
   service.bundleid = 97
   service.id = 170
   service.scope = bundle
 ```
 
*Steps to reproduce:* 
 
Using Apache Maven, use:
 ```
  mvn clean install
 ```
 
 Add Feature repo
 ```
 karaf@root()> feature:repo-add mvn:org.ecuadorjug.karaf/feature/LATEST/xml
 ```
 
 Install feature
 
  ```
  karaf@root()> feature:install hiernate-2lcache
  ```
  
  