#!/bin/bash

# ================================================== #
# 					constants						 #
# ================================================== #
project_name=s2
service=/carrera100
tomcat7_path=/home/i0901148/Escritorio/tomcat

# other constants
client_jar_initial=$project_name.jar
client_jar_final=/tmp/$project_name.jar

server_war_initial=$project_name.war
server_war_final=$tomcat7_path/webapps/$project_name.war

service_path=/$project_name$service

# ================================================== #
# 					  utils							 #
# ================================================== #
gen_key(){
	ssh-keygen -t rsa	
}

share_key(){
	user_host=$1
	ssh-copy-id -i $HOME/.ssh/id_rsa.pub $user_host
}

remote_exec(){
	user_host=$1
	command=$2
	ssh $user_host "$command"
}

copy_files(){
	user_host=$1
	initial=$2
	final=$3
	scp -q $initial $user_host:$final
}

clean_file(){
	user_host=$1
	file=$2
	remote_exec $user_host "rm $file"
}

# ================================================== #
# 					application						 #
# ================================================== #
args=( "$@" )
argsNumber=$#

if [ $argsNumber -eq 0 ]
then
	echo "ERROR: no arguments detected, run deployer.bash -h for help"
fi


for (( i=0; i<$argsNumber; i+=1 ))
do
	selected_command=${args[$i]}
	case $selected_command in
      	"-h") 
        	echo -e "SSDD deployer"
        	echo -e "\t-genkey: generates ssh key"
        	echo -e "\t-session <user>@<host>: copy ssh key in host"
        	echo -e "\t-cleanclient <user>@<host>: copy client files in host"
        	echo -e "\t-cleanserver <user>@<host>: copy server files in host"
        	echo -e "\t-copyclient <user>@<host>: copy client files in host"
        	echo -e "\t-copyserver <user>@<host>: copy server files in host"
        	echo -e "\t-run <user>@<host> <server_ip> <num_atletas> <is_main_host>: run client in host"
        	echo -e "\t-h help"
        	echo -e "EXAMPLES"
        	echo -e "\t- create key and sessions"
        	echo -e "\t\tbash deployer.bash -genkey -session user@172.20.1.1"
        	echo -e "\t- deploy server"
        	echo -e "\t\tbash deployer.bash -copyserver user@172.20.1.1"
        	echo -e "\t- depoly client"
        	echo -e "\t\tbash deployer.bash -copyclient user@172.20.1.2"
        	echo -e "\t- run client"
        	echo -e "\t\tbash deployer.bash -run user@172.20.1.2 172.20.1.1 2 true"
      ;;
      "-genkey")
			gen_key
      ;;
      "-session")
			i=$((i+1))
			user_host=${args[$i]}
			share_key $user_host
      ;;
      "-cleanclient")
			i=$((i+1))
			user_host=${args[$i]}
			clean_file $user_host $client_jar_final
      ;;
      "-cleanserver")
			i=$((i+1))
			user_host=${args[$i]}
			clean_file $user_host $server_war_final
      ;;
      "-copyclient")
			i=$((i+1))
			user_host=${args[$i]}
			copy_files $user_host $client_jar_initial $client_jar_final
      ;;
      "-copyserver")
			i=$((i+1))
			user_host=${args[$i]}
			copy_files $user_host $server_war_initial $server_war_final
			remote_exec $user_host "$tomcat7_path/bin/shutdown.sh"
			remote_exec $user_host "$tomcat7_path/bin/startup.sh"
			echo "wait 5 seconds for server to deploy copied .war file properly..."
			sleep 5
      ;;
      "-run")
			i=$((i+1))
			user_host=${args[$i]}
			i=$((i+1))
			server_ip=${args[$i]}
			i=$((i+1))
			num_atletas=${args[$i]}
			i=$((i+1))
			is_main_host=${args[$i]}
			service_uri="http://$server_ip$service_path"

			remote_exec $user_host "java -jar $client_jar_final $service_uri $num_atletas $user_host $is_main_host" &
			if [ $is_main_host = "true" ]
			then 
				sleep 2
			fi
      ;;
      *)
        	echo "ERROR: unknown command: $selected_command, run deployer.bash -h for help"
        	break
      ;;
  	esac
done