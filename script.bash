#!/bin/bash

# example:
# NOTA: 2 atletas por host
# args:
#	user: i0918455
#	host1: 172.20.1.1
#	host2: 172.20.1.2
#	applicationFiles: /home/i0918455/Escritorio/s2.war
#	num_atletas: 2 (2 por host)
#	server_path: :8081/s2/carrera100 ---> http:$HOST:8081/s2/carrera100
# bash script.bash i0918455 172.20.1.1 172.20.1.2 /home/i0918455/Escritorio/s2.war :8081/s2/carrera100

prepareKeys(){
	ssh-keygen -t dsa
	cat $HOME/.ssh/id_dsa.pub >> id_dsa.pub
	ssh-copy-id -i "$HOME/.ssh/id_dsa.pub" "$user@$host1"
	ssh-copy-id -i "$HOME/.ssh/id_dsa.pub" "$user@$host2"
}

prepareFiles(){
	scp -r "$applicationFiles" "$user@$host1:$applicationFiles"
	scp -r "$applicationFiles" "$user@$host2:$applicationFiles"
}

startServer(){
	java -jar "$applicationFiles"
}

startClients(){
	ssh "$user@$(hostname -I)" "java $applicationFiles $num_atletas $server_uri"
	ssh "$user@$host1" "java $applicationFiles $num_atletas $server_uri"
	ssh "$user@$host2" "java $applicationFiles $num_atletas $server_uri"
}

user=$1
host1=$2
host2=$3
applicationFiles=$4
num_atletas=$5
server_path=$6
server_uri="http://$(hostname -I):8081$server_path"

prepareKeys
prepareFiles
startServer
startClients