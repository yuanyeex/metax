#!/bin/bash

case $1 in
  "start") {
    for i in k8s-master k8s-node1 k8s-node2 ;
    do
        echo "*******************$1 $i***************************"
        ssh admin@$i "sudo /bin/bash /home/admin/kafka-start.sh"
    done
  };;

  "stop") {
    for i in k8s-master k8s-node1 k8s-node2 ;
    do
        echo "*******************$1 $i***************************"
        ssh -t admin@$i 'sudo /bin/bash /home/admin/kafka-stop.sh'
    done
  };;
esac