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

user=i0918455

server=$(hostname -I)
host1=172.20.1.1
host2=172.20.1.2

clientFiles=/home/i0918455/Escritorio/s2.jar
serverFiles=/home/i0918455/Escritorio/s2.war
serverFinalDestinationFiles=/bin/apache/.../webapps

numAtletasPerHost=4
serviceUri="http://$(hostname -I):8081/s2/carrera100"

prepareKeys(){
	sh shareKeys.sh "$host1"
	sh shareKeys.sh "$host2"
}

copyFiles(){
	cp "$serverFiles" "$serverFinalDestinationFiles"
	scp -r "$applicationFiles" "$user@$host1:$applicationFiles"
	scp -r "$applicationFiles" "$user@$host2:$applicationFiles"
}

startServer(){
	java -jar "$serverFinalDestinationFiles"
}

startClients(){
	java -jar "$applicationFiles" "$numAtletasPerHost" "$serviceUri" "$server"
	ssh "$user@$host1" "java -jar $applicationFiles $numAtletasPerHost $serviceUri $host1"
	#ssh "$user@$host2" "java -jar $applicationFiles $numAtletasPerHost $serviceUri $host2"
}

if [ $# -eq 1 ]
then
	prepareKeys
fi

prepareFiles
startServer
startClients
