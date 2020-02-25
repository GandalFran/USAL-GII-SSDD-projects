#Este script permite evitar que se nos pida la contrase–a cada vez que hagamos un ssh

if [ $# -eq 1 ]
then
	host=$1
	
	echo "Setting public and private keys on $host"
	#crear el par de claves
	if ! test -d ./ssh;
		then
		mkdir ./ssh
	fi
	cd ~/.ssh
	ssh-keygen
	
	#copiar la clave pUblica al servidor remoto
	echo "Copying key to remote server"
	scp ~/.ssh/id_rsa.pub $host:pubkey.txt 
	ssh $host "mkdir ~/.ssh; chmod 700 .ssh; cat pubkey.txt >> ~/.ssh/authorized_keys; rm ~/pubkey.txt; chmod 600 ~/.ssh/*; exit"

	#introducir la passphrase pUblica (sOlo una vez)
	echo "Entering ssh pasword (once)"
	ssh $host "exit"

	
	#comenzar el agente ssh
	echo "Starting SSH agent"
	eval `ssh-agent`
	
	#agnadir la clave privada a la cachE
	echo "Adding private key to cache"
	ssh-add
	
	
else
	echo "Uso: $0 [user@]host"
	exit

fi
