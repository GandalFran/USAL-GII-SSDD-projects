#!/bin/bash

# example:
# NOTA: 2 atletas por host
# args:
#	user: i0918455
#	host1: 172.20.1.1
#	host2: 172.20.1.2
#	serverapplicationFiles: /home/i0918455/Escritorio/s2.war
#	applicationFiles: /home/i0918455/Escritorio/s2.jsar
#	num_atletas: 2 (2 por host)
#	server_path: :8081/s2/carrera100 ---> http:$HOST:8081/s2/carrera100
# bash script.bash i0918455 172.20.1.1 172.20.1.2 /home/i0918455/Escritorio/s2.war /home/i0918455/Escritorio/s2.jar :8081/s2/carrera100

prepareKeys(){
	sh shareKeys.sh "$host1"
	sh shareKeys.sh "$host1"
}

prepareFiles(){
	scp -r "$applicationFiles" "$user@$host1:$applicationFiles"
	scp -r "$applicationFiles" "$user@$host2:$applicationFiles"
}

startServer(){
	java -jar "$serverapplicationFiles"
}

startClients(){
	MYSERVER=$(hostname -I)
	java -jar "$applicationFiles $applicationFiles $num_atletas $server_uri $MYSERVER"
	ssh "$user@$host1" "java -jar $applicationFiles $num_atletas $server_uri $host1"
	#ssh "$user@$host2" "java -jar $applicationFiles $num_atletas $server_uri $host2"
}

user=$1
host1=$2
host2=$3
serverapplicationFiles=$4
applicationFiles=$5
num_atletas=$6
server_path=$7
server_uri="http://$(hostname -I):8081$server_path"

#prepareKeys
prepareFiles
startServer
startClients
