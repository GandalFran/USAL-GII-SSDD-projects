#!/bin/bash

gen_key(){
	ssh-keygen -t rsa	
}

share_key(){
	user=$1
	host=$2
	ssh-copy-id -i $HOME/.ssh/id_rsa.pub $user@$host
}

remote_exec(){
	user=$1
	host=$2
	command=$3
	ssh $user@$host "$command"
}

copy_files(){
	user=$1
	host=$2
	initial=$3
	final=$3
	scp -q $initial $user@$host:$final
}

copy_files_server(){
	user=$1
	host=$2
	initial=$3
	final=$4
	scp -q $initial $user@$host:/tmp/files_server_tmp
	remote_exec $user $host "sudo cp /tmp/files_server_tmp $final"
}

get_service_uri(){
	ip=$1
	echo http://$ip:8080/s3/ntp
}

# constants
user=root

client=192.168.1.43
server1=192.168.1.43
server2=192.168.1.47
server3=192.168.1.49

tomcat7_path=/opt/tomcat7

client_jar_initial=s3.jar
server_war_initial=s3.war
client_jar_final=/root/s3.jar
server_war_final=$tomcat7_path/webapps/s3.war

num_iterations=8
run_marzullo=false

# application
if [ $# -eq 2 ] && [ $1 = "--session" ]
then
	if [ $2 = "--genkey" ]
	then
		echo "generating keys ..."
		gen_key
	fi
	echo "sharing keys ..."
	share_key $user $client
	share_key $user $server1
	share_key $user $server2
fi

# copy files
echo "copying client files ..."
copy_files $user $client $client_jar_initial $client_jar_final
echo "copying server files in remotes ..."
copy_files_server $user $server1 $server_war_initial $server_war_final
copy_files_server $user $server2 $server_war_initial $server_war_final
copy_files_server $user $server3 $server_war_initial $server_war_final

# start servers
remote_exec $user $server1 "systemctl start tomcat.service"
remote_exec $user $server2 "systemctl start tomcat.service"
remote_exec $user $server3 "systemctl start tomcat.service"

# wait to for servers to deploy
echo "wait 7 seconds for servers to deploy copied .war file properly..."
sleep 5

# start client
echo "starting ntp client ..."
remote_exec $user $client "java -jar $client_jar_final $num_iterations $run_marzullo $(get_service_uri $server1) $(get_service_uri $server2) $(get_service_uri $server3)"