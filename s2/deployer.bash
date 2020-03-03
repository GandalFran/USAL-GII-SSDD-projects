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

deploy_tomcat(){
	user_host=$1

	tomcat_uri=https://apache.brunneis.com/tomcat/tomcat-7/v7.0.100/bin/apache-tomcat-7.0.100.tar.gz
	tomcat_tmp=/tmp/apache-tomcat-7.0.100.tar.gz

	# download tomcat
	remote_exec $user_host "wget $tomcat_uri -O $tomcat_tmp"
	# unzip tomcat
	remote_exec $user_host "tar -zxf $tomcat_tmp -C $tomcat7_path"
	# rm tar.gz
	remote_exec $user_host "rm $tomcat_tmp"
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
        	echo -e "\t-deploy <user>@<server> <user1>@<client1> <user2>@<client2> <server_ip> <num_atletas_per_host>"
        	echo -e "\t-redeploy <user>@<server> <user1>@<client1> <user2>@<client2> <server_ip> <num_atletas_per_host>"
        	echo -e "\t-h help"
        	echo -e "EXAMPLES"
        	echo -e "\t- deploy all"
        	echo -e "\t\tbash deployer.bash -deploy user0@172.20.1.0 user1@172.20.1.1 user2@172.20.1.2 172.20.1.1 2"
        	echo -e "\t- redeploy all"
        	echo -e "\t\tbash deployer.bash -redeploy user0@172.20.1.0 user1@172.20.1.1 user2@172.20.1.2 172.20.1.1 2"
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
      "-deploy")
			i=$((i+1))
			user_server=${args[$i]}
			i=$((i+1))
			user_client1=${args[$i]}
			i=$((i+1))
			user_client2=${args[$i]}
			i=$((i+1))
			server_ip=${args[$i]}
			i=$((i+1))
			num_atletas_per_host=${args[$i]}

			# generate session key and share keys
			gen_key
			share_key $user_server
			share_key $user_client1
			share_key $user_client2

			# deploy tomcat
			deploy_tomcat $user_server
			remote_exec $user_server "$tomcat7_path/bin/startup.sh"

			# copy server files
			copy_files $user_server $server_war_initial $server_war_final
			sleep 5

			# copy client files
			copy_files $user_server $client_jar_initial $client_jar_final
			copy_files $user_client1 $client_jar_initial $client_jar_final
			copy_files $user_client2 $client_jar_initial $client_jar_final

			# calculate service uri
			service_uri="http://$server_ip$service_path"
			# run main client
			remote_exec $user_server "java -jar $client_jar_final $service_uri $num_atletas_per_host $user_host true" &
			sleep 2
			# run other clients
			remote_exec $user_client1 "java -jar $client_jar_final $service_uri $num_atletas_per_host $user_host false" &
			remote_exec $user_client2 "java -jar $client_jar_final $service_uri $num_atletas_per_host $user_host false"
      ;;
      "-redeploy")			
			i=$((i+1))
			user_server=${args[$i]}
			i=$((i+1))
			user_client1=${args[$i]}
			i=$((i+1))
			user_client2=${args[$i]}
			i=$((i+1))
			server_ip=${args[$i]}
			i=$((i+1))
			num_atletas_per_host=${args[$i]}

			# clean server and client files
			clean_file $user_server $server_war_final
			clean_file $user_server $client_jar_final
			clean_file $user_client1 $client_jar_final
			clean_file $user_client2 $client_jar_final

			# restart tomcat
			remote_exec $user_server "$tomcat7_path/bin/shutdown.sh"
			remote_exec $user_server "$tomcat7_path/bin/startup.sh"

			# copy server files
			copy_files $user_server $server_war_initial $server_war_final

			# copy client files
			copy_files $user_server $client_jar_initial $client_jar_final
			copy_files $user_client1 $client_jar_initial $client_jar_final
			copy_files $user_client2 $client_jar_initial $client_jar_final

			# calculate service uri
			service_uri="http://$server_ip$service_path"
			# run main client
			remote_exec $user_server "java -jar $client_jar_final $service_uri $num_atletas_per_host $user_host true" &
			sleep 2
			# run other clients
			remote_exec $user_client1 "java -jar $client_jar_final $service_uri $num_atletas_per_host $user_host false" &
			remote_exec $user_client2 "java -jar $client_jar_final $service_uri $num_atletas_per_host $user_host false"
      ;;
      *)
        	echo "ERROR: unknown command: $selected_command, run deployer.bash -h for help"
        	break
      ;;
  	esac
done