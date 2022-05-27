docker volume create backend
docker volume create elk
docker run --name=mysql1 -p 3307:3306  -v backend:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=root -e  MYSQL_DATABASE=prod_sdv_bdd -d mysql:5.7

docker run -d --name elasticsearch3 -v elk:/usr/share/elasticsearch/data -p 9200:9200 -p 9300:9300 -e "ELASTICSEARCH_HEAP_SIZE=512m" bitnami/elasticsearch

docker build --tag store_micro ./store
docker build --tag gw_micro ./securitygw
docker build --tag storerate_micro ./storerate

::docker run -d --name store -p XXXX:XXXX img_name
docker run -d --name store -p 8081:8081 store_micro
docker run -d --name gateway -p 8082:8082 gw_micro
docker run -d --name storerate -p 8083:8083 storerate_micro
