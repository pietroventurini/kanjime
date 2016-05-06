# Java Server Configuration

In order to configure a Java server on cloud9 you need to:

1) Update apt-get in this way: ```apt-get update``` (use ```sudo``` if it doesn't work)

2) Install javac through  ```apt-get install openjdk-7-jdk ```

3) Configure a new runner: (Java workspace) -- Command: ```path/to/src/YourMainFile.java ```

4) Install the following modules and dependencies for apache ```apt-get install libapache2-mod-proxy-html libxml2-dev ```

5) Run the following command to get a list of available Apache modules: ```a2enmod```. Once you are prompted with the choice of modules you desire, you can pass the below line listing the module names:
`proxy proxy_ajp proxy_http rewrite deflate headers proxy_balancer proxy_connect proxy_html`
    
Or alternatively, you can run the following commands to enable the modules one by one:
```
a2enmod proxy
a2enmod proxy_http
a2enmod proxy_ajp
a2enmod rewrite
a2enmod deflate
a2enmod headers
a2enmod proxy_balancer
a2enmod proxy_connect
a2enmod proxy_html
```

6) Configure apache

6.1 - Navigate to `apache2/sites-available` and prompt `sudo nano 001-cloud9.conf`
    
6.2 - Add the following rule after `</Directory>`:
```
ProxyPreserveHost On
<Location /api/>
    ProxyPass http://localhost:1221/api/
    ProxyPassReverse http://localhost:1221/api/
</Location>
```
Every request to `localhost/api/..YourContext..` will be sent to the port `1221` (where the Java Server's running)
