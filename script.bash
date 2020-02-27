#!/bin/bash

gen_key(){
	ssh-keygen -t rsa	
}


share_key(){
	user=$1
	host=$2
	ssh-copy-id -i $HOME/.ssh/id_rsa.pub $user@$host
}

copy_files(){
	user=$1
	host=$2
	initial=$3
	final=$3
	scp -rq $initial $user@$host:$final
}

remote_exec(){
	user=$1
	host=$2
	command=$3
	ssh $user@$host "$command"
}

# constants
user=root

server=192.168.1.43
client1=192.168.1.47
client2=192.168.1.49

tomcat7_path=/opt/tomcat7

client_jar_initial=s2.jar
server_war_initial=s2.war
client_jar_final=/root/s2.jar
server_war_final=$tomcat7_path/webapps/s2.war

num_atletas=2
service_uri=http://$server:8080/s2/carrera100

# application

if [ $# -eq 2 ] && [ $1 = "--session" ]
then
	if [ $2 = "--genkey" ]
	then
		echo "generating keys ..."
		gen_key
	fi
	echo "sharing keys ..."
	share_key $user $server
	share_key $user $client1
	share_key $user $client2
fi

# copy files
echo "copying server files ..."
sudo cp $server_war_initial $server_war_final
echo "copying files in remotes ..."
copy_files $user $client1 $client_jar_initial $client_jar_final
copy_files $user $client2 $client_jar_initial $client_jar_final

# wait to .war to deploy
echo "wait 5 seconds for server to deploy copied .war file properly..."
sleep 7

# start clients
echo "starting server client ..."
remote_exec $user $server "java -jar $client_jar_final $service_uri $num_atletas server_$server true" &

# sleep 2 second to allow server client to restart
sleep 2

echo "starting client 1 ..."
remote_exec $user $client1 "java -jar $client_jar_final $service_uri $num_atletas client1_$client1 false" &

echo "starting client 2 ..."
remote_exec $user $client2 "java -jar $client_jar_final $service_uri $num_atletas client2_$client2 false"
